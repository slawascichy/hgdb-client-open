package io.hgdb.multi.client.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
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
import io.hgdb.multi.client.registry.error.IHttpInvokerProxyFactoryErrorHandler;
import io.hgdb.multi.client.security.BasicHttpInvokerRequestExecutor;
import pro.ibpm.mercury.config.MercuryConfig;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.registry.RegistrySupport;

/**
 * 
 * HttpInvokerProxyFactoryDynamicRegistry - dynamiczny rejestr połączeń HTTP
 * (SOAP)
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class HttpInvokerProxyFactoryDynamicRegistry implements ApplicationContextAware, IClientDynamicRegistry {

	private static Logger logger = LoggerFactory.getLogger(HttpInvokerProxyFactoryDynamicRegistry.class);
	private ApplicationContext registryCtx;
	private final Map<String, String> interface2conextUrlMap = new HashMap<>();
	private String defaultServiceUrl;
	private Class<? extends IHttpInvokerProxyFactoryErrorHandler> errorHandlerClass;
	private BasicHttpInvokerRequestExecutor basicHttpInvokerRequestExecutor;

	@Override
	public void setApplicationContext(ApplicationContext arg0) {
		registryCtx = arg0;
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
		String beanName = instanceName + "." + RegistrySupport.getBeanName(clazz);
		logger.trace("-->ClientDynamicRegistry.getBean : beanName = {}", beanName);

		if (!registryCtx.containsBeanDefinition(beanName)) {
			String serviceUrl = buildServiceUrl(instanceName, clazz);
			BeanDefinitionRegistry factory = (BeanDefinitionRegistry) registryCtx.getAutowireCapableBeanFactory();
			GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
			beanDefinition.setBeanClass(MHttpInvokerProxyFactoryBean.class);
			beanDefinition.setAutowireCandidate(true);
			beanDefinition.setScope(ConfigurableBeanFactory.SCOPE_SINGLETON);
			Map<String, Object> original = new HashMap<>();
			original.put("serviceUrl", serviceUrl);
			original.put("serviceInterface", clazz);
			original.put("instanceName", instanceName);

			if (isRemoteSecurityEnabled(instanceName)) {
				original.put("httpInvokerRequestExecutor",
						getBasicHttpInvokerRequestExecutor(instanceName, /* isRemoteSecurityEnabled */ true));
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
			factory.registerBeanDefinition(beanName, beanDefinition);
		}
		return (T) registryCtx.getBean(beanName);
	}

	private <T> String buildServiceUrl(String instanceName, Class<T> clazz) {
		MercuryConfig config = MercuryConfig.getInstance();
		String serviceUrl = config.get(String.format(ClientConfigParams.WS_REMOTE_SERVICE_URL, instanceName));
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
	 *            the {@link #defaultServiceUrl} to set
	 */
	public void setDefaultServiceUrl(String defaultServiceUrl) {
		this.defaultServiceUrl = defaultServiceUrl;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public <T> T getBean(Context context, Class<T> clazz) {
		String instanceName = (String) context
				.getAdditionalPropertyValue(ClientContextParams.HTTP_INVOCER_PROXY_FACTORY_INSTANCE_NAME);
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
	 *            the {@link #errorHandlerClass} to set
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
		MercuryConfig config = MercuryConfig.getInstance();
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
			int connectionRequestTimeout = ClientConfigParams.getConnectionConnectionRequestTimeout();
			if (connectionRequestTimeout != 0) {
				basicHttpInvokerRequestExecutor.setConnectionRequestTimeout(connectionRequestTimeout);
			}
		}
		return basicHttpInvokerRequestExecutor;
	}

	public boolean isRemoteSecurityEnabled(String instanceName) {
		String securityEnabledPropValue = MercuryConfig.getInstance()
				.get(String.format(ClientConfigParams.WS_REMOTE_SECURITY_ENABLED_PROP, instanceName));
		return securityEnabledPropValue != null && Boolean.parseBoolean(securityEnabledPropValue);
	}

}
