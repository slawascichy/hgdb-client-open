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
import io.hgdb.multi.client.registry.error.IRmiProxyFactoryErrorHandler;
import pro.ibpm.mercury.config.MercuryConfig;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.registry.RegistrySupport;

/**
 * 
 * RmiProxyFactoryDynamicRegistry - dynamiczny rejestr połączeń RMI
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class RmiProxyFactoryDynamicRegistry implements ApplicationContextAware, IClientDynamicRegistry {

	private static Logger logger = LoggerFactory.getLogger(RmiProxyFactoryDynamicRegistry.class);
	private ApplicationContext registryCtx;
	private final Map<String, String> interface2conextUrlMap = new HashMap<>();
	private Class<? extends IRmiProxyFactoryErrorHandler> errorHandlerClass;
	private String defaultServiceUrl;

	@Override
	public void setApplicationContext(ApplicationContext arg0) {
		registryCtx = arg0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getBean(Context context, String instanceName, Class<T> clazz) {
		String beanName = instanceName + ".rmi." + RegistrySupport.getBeanName(clazz);
		logger.trace("-->ClientDynamicRegistry.getBean : beanName = {}", beanName);
		if (!clazz.isInterface()) {
			throw new IllegalArgumentException(
					String.format("Declared in argument 'clazz' class %s in not interface", clazz.getName()));
		}
		if (!registryCtx.containsBeanDefinition(beanName)) {
			String serviceUrl = buildServiceUrl(instanceName, clazz);
			BeanDefinitionRegistry factory = (BeanDefinitionRegistry) registryCtx.getAutowireCapableBeanFactory();
			GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
			beanDefinition.setBeanClass(MRmiProxyFactoryBean.class);
			beanDefinition.setAutowireCandidate(true);
			beanDefinition.setScope(ConfigurableBeanFactory.SCOPE_SINGLETON);
			Map<String, Object> original = new HashMap<>();
			original.put("serviceUrl", serviceUrl);
			original.put("serviceInterface", clazz);
			original.put("instanceName", instanceName);

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
		String serviceUrl = config.get(String.format(ClientConfigParams.RMI_REMOTE_SERVICE_URL, instanceName));
		String getContextUrl = interface2conextUrlMap.get(clazz.getName());
		if (StringUtils.isNotBlank(getContextUrl)) {
			return serviceUrl + getContextUrl;
		} else {
			return serviceUrl;
		}
	}

	@Override
	public <T> T getBean(Context context, Class<T> clazz) {
		String instanceName = (String) context
				.getAdditionalPropertyValue(ClientContextParams.RMI_PROXY_FACTORY_INSTANCE_NAME);
		return getBean(context, instanceName, clazz);
	}

	@Override
	public Map<String, String> getInterface2conextUrlMap() {
		return interface2conextUrlMap;
	}

	@Override
	public void setInterface2conextUrlMap(Properties interface2conextUrlMap) {
		CollectionUtils.mergePropertiesIntoMap(interface2conextUrlMap, this.interface2conextUrlMap);
	}

	@Override
	public String getDefaultServiceUrl() {
		return defaultServiceUrl;
	}

	@Override
	public void setDefaultServiceUrl(String defaultServiceUrl) {
		this.defaultServiceUrl = defaultServiceUrl;
	}

	/**
	 * @return the {@link #errorHandlerClass}
	 */
	public Class<? extends IRmiProxyFactoryErrorHandler> getErrorHandlerClass() {
		return errorHandlerClass;
	}

	/**
	 * @param errorHandlerClass
	 *            the {@link #errorHandlerClass} to set
	 */
	@SuppressWarnings("unchecked")
	public void setErrorHandlerClass(String errorHandlerClass) {
		Class<? extends IRmiProxyFactoryErrorHandler> clazz;
		try {
			clazz = (Class<? extends IRmiProxyFactoryErrorHandler>) Class.forName(errorHandlerClass);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		this.errorHandlerClass = clazz;
	}
}
