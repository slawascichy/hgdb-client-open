package io.hgdb.multi.client.registry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.mercury.cxf.client.security.BasicHttpInvokerRequestExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import io.hgdb.multi.client.config.ClientConfigParams;
import io.hgdb.multi.client.context.ClientContextParams;
import io.hgdb.multi.client.registry.api.IHttpInvokerProxyFactoryRegistry;
import io.hgdb.multi.client.registry.error.IHttpInvokerProxyFactoryErrorHandler;
import pro.ibpm.mercury.config.MercuryConfig;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.registry.RegistrySupport;

/**
 * 
 * HttpInvokerProxyFactoryDynamicRegistry - dynamiczny rejestr połączeń HTTP (SOAP)
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class HttpInvokerProxyFactoryDynamicRegistry
		implements ApplicationContextAware, IHttpInvokerProxyFactoryRegistry {

	private static final long serialVersionUID = 4726926603168066912L;
	private static Logger logger = LoggerFactory.getLogger(HttpInvokerProxyFactoryDynamicRegistry.class);
	@SuppressWarnings("squid:S1948")
	private ApplicationContext registryCtx;
	private final Map<String, String> interface2conextUrlMap = new HashMap<>();
	private String defaultServiceUrl;
	private Class<? extends IHttpInvokerProxyFactoryErrorHandler> errorHandlerClass;
	@SuppressWarnings("squid:S1948")
	private final Map<String, BasicHttpInvokerRequestExecutor> basicHttpInvokerRequestExecutorsMap = new HashMap<>();
	@SuppressWarnings("squid:S1948")
	private final Object basicHttpInvokerRequestExecutorLock = new Object();
	@SuppressWarnings("squid:S1948")
	private final Object initLock = new Object();
	private final Set<String> servicesList = new HashSet<>();
	private boolean initBeans = true;

	@Override
	public void setApplicationContext(ApplicationContext arg0) {
		registryCtx = arg0;
	}

	public void init() throws ClassNotFoundException {
		synchronized (initLock) {
			MercuryConfig config = MercuryConfig.getInstance();
			String services = config.get(ClientConfigParams.WS_SERVICE_LIST_PROP);
			if (StringUtils.isBlank(services)) {
				return;
			}
			String[] servicesElements = services.split("\\,");
			for (String element : servicesElements) {
				servicesList.add(element.trim());
			}
			if (initBeans) {
				/* Inicjalizacja wszystkich bean'ów usług - START */
				for (String instanceName : servicesList) {
					String serviceRemoteUrl = config
							.get(String.format(ClientConfigParams.WS_REMOTE_SERVICE_URL_PROP, instanceName));
					if (StringUtils.isNotBlank(serviceRemoteUrl)) {
						for (Entry<String, String> interface2conextUrl : interface2conextUrlMap.entrySet()) {
							String interfaceName = interface2conextUrl.getKey();
							Class<?> interfaceClazz = Class.forName(interfaceName);
							String serviceContext = interface2conextUrl.getValue();
							String serviceUrl = serviceRemoteUrl + serviceContext;
							String beanName = instanceName + "." + RegistrySupport.getBeanName(interfaceClazz);
							registerBean(instanceName, interfaceClazz, beanName, serviceUrl);
						}
					} else {
						logger.warn("Not found configuration for declared service name: '{}'", instanceName);
					}
				}
				initBeans = false;
				/* Inicjalizacja wszystkich bean'ów usług - KONIEC */
			}
		}
	}

	/* Overridden (non-Javadoc) */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getBean(Context context, String instanceName, Class<T> clazz) {
		if (!clazz.isInterface()) {
			throw new IllegalArgumentException(
					String.format("Declared in argument 'clazz' class %s in not interface", clazz.getName()));
		}
		if (StringUtils.isBlank(instanceName)) {
			throw new IllegalArgumentException(
					String.format("To obtain the remote service bean, a remote instance name is required."
							+ " Error for service with interface %s.", clazz.getName()));
		}
		if (!servicesList.contains(instanceName)) {
			String services = MercuryConfig.getInstance().get(ClientConfigParams.WS_SERVICE_LIST_PROP);
			String expected = StringUtils.isBlank(services) ? "No service has been declared."
					: String.format("Expected one of %s.", services);
			throw new IllegalArgumentException(
					String.format("The instance with the given name ('%s') is not in the list of declared services. %s",
							instanceName, expected));

		}

		String beanName = instanceName + "." + RegistrySupport.getBeanName(clazz);
		logger.trace("-->ClientDynamicRegistry.getBean : beanName = {}", beanName);
		synchronized (initLock) {
			if (!registryCtx.containsBeanDefinition(beanName)) {
				String serviceUrl = buildServiceUrl(instanceName, clazz);
				registerBean(instanceName, clazz, beanName, serviceUrl);
			}
		}
		return (T) registryCtx.getBean(beanName);
	}

	private <T> void registerBean(String instanceName, Class<T> clazz, String beanName, String serviceUrl) {
		logger.info("Register bean:\n----------------------\n beanName: {}\n serviceUrl: {}\n----------------------",
				beanName, serviceUrl);
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(MHttpInvokerProxyFactoryBean.class);
		beanDefinition.setAutowireCandidate(false);
		beanDefinition.setScope(ConfigurableBeanFactory.SCOPE_SINGLETON);
		Map<String, Object> original = new HashMap<>();
		original.put("serviceUrl", serviceUrl);
		original.put("serviceInterface", clazz);
		original.put("instanceName", instanceName);

		if (isRemoteSecurityEnabled(instanceName)) {
			BasicHttpInvokerRequestExecutor executor = getBasicHttpInvokerRequestExecutor(instanceName,
					/* isRemoteSecurityEnabled */ true);
			if (executor != null) {
				original.put("httpInvokerRequestExecutor", executor);
			}
		}

		if (getErrorHandlerClass() != null) {
			try {
				original.put("errorHandler", getErrorHandlerClass().newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				throw new IllegalStateException(e);
			}
		}
		MutablePropertyValues propertyValues = new MutablePropertyValues(original);
		beanDefinition.setPropertyValues(propertyValues);
		BeanDefinitionRegistry factory = (BeanDefinitionRegistry) registryCtx.getAutowireCapableBeanFactory();
		factory.registerBeanDefinition(beanName, beanDefinition);
	}

	private <T> String buildServiceUrl(String instanceName, Class<T> clazz) {
		MercuryConfig config = MercuryConfig.getInstance();
		String serviceUrl = config.get(String.format(ClientConfigParams.WS_REMOTE_SERVICE_URL_PROP, instanceName));
		String getContextUrl = interface2conextUrlMap.get(clazz.getName());
		if (StringUtils.isNotBlank(getContextUrl)) {
			return serviceUrl + getContextUrl;
		} else {
			return serviceUrl;
		}
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Map<String, String> getInterface2conextUrlMap() {
		return interface2conextUrlMap;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public void setInterface2conextUrlMap(Properties interface2conextUrlMap) {
		CollectionUtils.mergePropertiesIntoMap(interface2conextUrlMap, this.interface2conextUrlMap);
	}

	/**
	 * @return the {@link #defaultServiceUrl}
	 */
	public String getDefaultServiceUrl() {
		return defaultServiceUrl;
	}

	/**
	 * @param defaultServiceUrl
	 *                          the {@link #defaultServiceUrl} to set
	 */
	public void setDefaultServiceUrl(String defaultServiceUrl) {
		this.defaultServiceUrl = defaultServiceUrl;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public <T> T getBean(Context context, Class<T> clazz) {
		String instanceName = (String) context
				.getAdditionalPropertyValue(ClientContextParams.HTTP_INVOCER_PROXY_FACTORY_INSTANCE_NAME);
		if (StringUtils.isBlank(instanceName)) {
			instanceName = MercuryConfig.getInstance().get(ClientConfigParams.WS_SERVICE_DEFAULT_PROP);
		}
		return getBean(context, instanceName, clazz);
	}

	/**
	 * @return the {@link #errorHandlerClass}
	 */
	public Class<? extends IHttpInvokerProxyFactoryErrorHandler> getErrorHandlerClass() {
		return errorHandlerClass;
	}

	/**
	 * @param errorHandlerClass
	 *                          the {@link #errorHandlerClass} to set
	 */
	@SuppressWarnings("unchecked")
	public void setErrorHandlerClass(String errorHandlerClass) {
		Class<? extends IHttpInvokerProxyFactoryErrorHandler> clazz;
		try {
			clazz = (Class<? extends IHttpInvokerProxyFactoryErrorHandler>) Class.forName(errorHandlerClass);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		this.errorHandlerClass = clazz;
	}

	/**
	 * @return the {@link #basicHttpInvokerRequestExecutor}
	 */
	public BasicHttpInvokerRequestExecutor getBasicHttpInvokerRequestExecutor(String instanceName,
			boolean isRemoteSecurityEnabled) {
		if (!isRemoteSecurityEnabled) {
			return null;
		}
		synchronized (basicHttpInvokerRequestExecutorLock) {
			MercuryConfig config = MercuryConfig.getInstance();
			BasicHttpInvokerRequestExecutor basicHttpInvokerRequestExecutor = basicHttpInvokerRequestExecutorsMap
					.get(instanceName);
			if (basicHttpInvokerRequestExecutor == null) {
				basicHttpInvokerRequestExecutor = new BasicHttpInvokerRequestExecutor();
				basicHttpInvokerRequestExecutor.setIdentifier(
						config.get(String.format(ClientConfigParams.WS_SECURITY_IDENTIFIER_PROP, instanceName)));
				basicHttpInvokerRequestExecutor
						.setToken(config.get(String.format(ClientConfigParams.WS_SECURITY_TOKEN_PROP, instanceName)));

				int readTimeout = ClientConfigParams.getConnectionReadTimeout();
				if (ClientConfigParams.DEFAULT_READ_TIMEOUT_MILLISECONDS != readTimeout) {
					basicHttpInvokerRequestExecutor.setReadTimeout(readTimeout);
				}
				int connectionTimeout = ClientConfigParams.getConnectionConnectionTimeout();
				if (connectionTimeout != 0) {
					basicHttpInvokerRequestExecutor.setConnectTimeout(connectionTimeout);
				}
				basicHttpInvokerRequestExecutorsMap.put(instanceName, basicHttpInvokerRequestExecutor);
			}
			return basicHttpInvokerRequestExecutor;
		}
	}

	public boolean isRemoteSecurityEnabled(String instanceName) {
		String propertyName = String.format(ClientConfigParams.WS_REMOTE_SECURITY_ENABLED_PROP, instanceName);
		String securityEnabledPropValue = MercuryConfig.getInstance().get(propertyName);
		return securityEnabledPropValue != null && Boolean.parseBoolean(securityEnabledPropValue);
	}

	/**
	 * @return the servicesList
	 */
	public Set<String> getServicesList() {
		return servicesList;
	}

	/**
	 * @return the initBeans
	 */
	public boolean isInitBeans() {
		return initBeans;
	}

	/**
	 * @param initBeans
	 *                  the initBeans to set
	 */
	public void setInitBeans(boolean initBeans) {
		this.initBeans = initBeans;
	}

}
