package io.hgdb.multi.client.registry;

import java.util.Map;
import java.util.Properties;

import pro.ibpm.mercury.context.Context;

public interface IClientDynamicRegistry {

	/**
	 * Pobranie bean'a bezpośrednio z kontekstu Spring'a
	 * 
	 * @param mercuryInstanceName
	 *            nazwa instancji bazy danych Mercury, do której ma być zrealizowany
	 *            zdalny request
	 * @return bean
	 */
	<T> T getBean(Context context, String mercuryInstanceName, Class<T> clazz);

	/**
	 * Pobranie bean'a bezpośrednio z kontekstu Spring'a. nazwa instancji bazy
	 * danych Mercury, do której ma być zrealizowany zdalny request powinna być
	 * ustawiona jako dodatkowy parametr w kontekście
	 * {@link HttpInvokerProxyFactoryDynamicRegistry#HTTP_INVOCER_PROXY_FACTORY_INSTANCE_NAME}
	 * 
	 * @return bean
	 */
	<T> T getBean(Context context, Class<T> clazz);

	/**
	 * @return the {@link #interface2conextUrlMap}
	 */
	Map<String, String> getInterface2conextUrlMap();

	/**
	 * @param interface2conextUrlMap
	 *            the {@link #interface2conextUrlMap} to set
	 */
	void setInterface2conextUrlMap(Properties interface2conextUrlMap);

	/**
	 * @return the {@link #defaultServiceUrl}
	 */
	String getDefaultServiceUrl();

	/**
	 * @param defaultServiceUrl
	 *            the {@link #defaultServiceUrl} to set
	 */
	void setDefaultServiceUrl(String defaultServiceUrl);

}