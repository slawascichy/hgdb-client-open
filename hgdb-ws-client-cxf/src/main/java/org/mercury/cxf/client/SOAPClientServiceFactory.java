package org.mercury.cxf.client;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.mercury.cxf.client.security.WSSEUsernameTokenCallback;

/**
 * 
 * SOAPClientServiceFactory
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
public class SOAPClientServiceFactory {

	private Class<?> interfaceClass;

	private String url;

	private String serviceName;

	private String endpointName;

	private String nameSpace;

	private WSSEUsernameTokenCallback passwordCallback;

	/**
	 * Czy w żądaniu klienta przesłania parametru z nazwą wywoływanej usługi.
	 * 
	 * @deprecated Nie ma implementacji dla tej flagi.
	 */
	@Deprecated
	private boolean methodNameToHttpParamsAddEnabled = false;
	/** Czas oczekiwania klienta na odpowiedź usługi [ms]. */
	private Long reciveTimeout;
	/** Czas oczekiwania klienta na połączenie z usługą [ms]. */
	private Long connectionTimeout;

	/**
	 * @return the {@link #interfaceClass}
	 */
	public Class<?> getInterfaceClass() {
		return interfaceClass;
	}

	/**
	 * @param interfaceClass
	 *            the {@link #interfaceClass} to set
	 */
	public void setInterfaceClass(Class<?> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	/**
	 * @return the {@link #url}
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the {@link #url} to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the {@link #serviceName}
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName
	 *            the {@link #serviceName} to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the {@link #endpointName}
	 */
	public String getEndpointName() {
		return endpointName;
	}

	/**
	 * @param endpointName
	 *            the {@link #endpointName} to set
	 */
	public void setEndpointName(String endpointName) {
		this.endpointName = endpointName;
	}

	/**
	 * @return the {@link #nameSpace}
	 */
	public String getNameSpace() {
		return nameSpace;
	}

	/**
	 * @param nameSpace
	 *            the {@link #nameSpace} to set
	 */
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	/**
	 * @return the {@link #passwordCallback}
	 */
	public WSSEUsernameTokenCallback getPasswordCallback() {
		return passwordCallback;
	}

	/**
	 * @param passwordCallback
	 *            the {@link #passwordCallback} to set
	 */
	public void setPasswordCallback(WSSEUsernameTokenCallback passwordCallback) {
		this.passwordCallback = passwordCallback;
	}

	/**
	 * Ustaw nazwę klasy interfejsu usługi.
	 * 
	 * @param interfaceClassName
	 * @throws ClassNotFoundException
	 */
	public void setInterfaceClassName(String interfaceClassName) throws ClassNotFoundException {
		interfaceClass = Class.forName(interfaceClassName);
	}

	public Object getService() throws SOAPClientServiceException {

		if (this.interfaceClass == null) {
			throw new SOAPClientServiceException("Empty interfaceClass or interfaceClassName for SOAP Service.");
		}

		if (StringUtils.isBlank(this.url)) {
			throw new SOAPClientServiceException("Url is not defined for SOAP Service.");
		}

		String lNameSpace = this.nameSpace;
		if (StringUtils.isBlank(lNameSpace)) {
			lNameSpace = SOAPClientServiceFactory.createNameSpace(this.interfaceClass);
		}

		final JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setAddress(this.url);
		factory.setServiceClass(this.interfaceClass);

		if ((endpointName != null) || (serviceName != null)) {
			if (endpointName != null) {
				factory.setEndpointName(new QName(lNameSpace, endpointName));
			}
			if (serviceName != null) {
				factory.setServiceName(new QName(lNameSpace, serviceName));
			}
		}

		if (this.passwordCallback != null) {
			Map<String, Object> props = new HashMap<>();
			props.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
			props.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
			props.put(WSHandlerConstants.PW_CALLBACK_CLASS, WSSEUsernameTokenCallback.class.getName());
			props.put(WSHandlerConstants.USER, passwordCallback.getIdentifier());
			WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(props);
			factory.getOutInterceptors().add(wssOut);
		}

		Object service = factory.create();

		if (reciveTimeout != null || connectionTimeout != null) {
			/* Ustawianie parametrów/ograniczeń dla nawiązanego połączenia - START */
			final HTTPConduit conduit = (HTTPConduit) ((ClientProxy) Proxy.getInvocationHandler(service)).getClient()
					.getConduit();
			if (reciveTimeout != null) {
				conduit.getClient().setReceiveTimeout(reciveTimeout);
			}
			if (connectionTimeout != null) {
				conduit.getClient().setConnectionTimeout(connectionTimeout);
			}
			/* Ustawianie parametrów/ograniczeń dla nawiązanego połączenia - KONIEC */
		}
		return service;
	}

	public static String createNameSpace(Class<?> interfaceClass) {
		String[] classNameElements = interfaceClass.getName().split("\\.");
		StringBuilder sb = new StringBuilder("http://");
		for (int i = classNameElements.length - 2; i >= 0; i--) {
			sb.append(classNameElements[i]);
			if (i != 0) {
				sb.append(".");
			}
		}
		sb.append("/");
		return sb.toString();

	}

	/**
	 * @return the {@link #methodNameToHttpParamsAddEnabled}
	 */
	public boolean isMethodNameToHttpParamsAddEnabled() {
		return methodNameToHttpParamsAddEnabled;
	}

	/**
	 * @param methodNameToHttpParamsAddEnabled
	 *            the {@link #methodNameToHttpParamsAddEnabled} to set
	 */
	public void setMethodNameToHttpParamsAddEnabled(boolean methodNameToHttpParamsAddEnabled) {
		this.methodNameToHttpParamsAddEnabled = methodNameToHttpParamsAddEnabled;
	}

	/**
	 * @return the {@link #reciveTimeout}
	 */
	public Long getReciveTimeout() {
		return reciveTimeout;
	}

	/**
	 * @param reciveTimeout
	 *            the {@link #reciveTimeout} to set
	 */
	public void setReciveTimeout(Long reciveTimeout) {
		this.reciveTimeout = reciveTimeout;
	}

	/**
	 * @return the {@link #connectionTimeout}
	 */
	public Long getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 * @param connectionTimeout
	 *            the {@link #connectionTimeout} to set
	 */
	public void setConnectionTimeout(Long connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

}
