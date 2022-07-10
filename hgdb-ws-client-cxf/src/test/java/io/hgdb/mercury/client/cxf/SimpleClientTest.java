package io.hgdb.mercury.client.cxf;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.mercury.cxf.client.SOAPClientServiceFactory;
import org.mercury.cxf.client.security.BasicHttpInvokerRequestExecutor;
import org.mercury.cxf.client.security.ClientAuthConstants;
import org.mercury.cxf.client.security.WSSEUsernameTokenCallback;
import org.spring4hgdb.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

import io.hgdb.mercury.client.test.TestPropertiesLoader;
import junit.framework.TestCase;
import pro.ibpm.mercury.config.MercuryConfig;
import pro.ibpm.mercury.ws.server.api.actions.INotyficationAction;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithStringValue;

/**
 * 
 * SimpleClientTest
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class SimpleClientTest extends TestCase {

	private static final Properties testProperties = new Properties();

	static {
		Map<String, String> _Properties = TestPropertiesLoader.loadProperties();
		testProperties.putAll(_Properties);
	}

	public void testSoapSecurityConnection() {

		String identifier = testProperties.getProperty(ClientAuthConstants.WS_SECURITY_IDENTIFIER_PROP);
		if (StringUtils.isBlank(identifier)) {
			identifier = ClientAuthConstants.WS_DEFAULT_IDENTIFIER;
		}
		String token = testProperties.getProperty(ClientAuthConstants.WS_SECURITY_TOKEN_PROP);
		if (StringUtils.isBlank(token)) {
			token = ClientAuthConstants.WS_DEFAULT_TOKEN;
		}
		new WSSEUsernameTokenCallback(identifier, token);

		final JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		String serviceName = "NotyficationAction";
		String endpointName = "INotyficationActionPortType";
		String nameSpace = SOAPClientServiceFactory.createNameSpace(INotyficationAction.class);
		String mercuryServerSoapServices = testProperties.getProperty("test.server.soap.url");
		factory.setAddress(mercuryServerSoapServices + "/NotyficationAction");
		factory.setServiceClass(INotyficationAction.class);

		if ((endpointName != null) || (serviceName != null)) {
			if (endpointName != null) {
				factory.setEndpointName(new QName(nameSpace, endpointName));
			}
			if (serviceName != null) {
				factory.setServiceName(new QName(nameSpace, serviceName));
			}
		}

		Map<String, Object> props = new HashMap<String, Object>();
		props.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
		props.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
		props.put(WSHandlerConstants.PW_CALLBACK_CLASS, WSSEUsernameTokenCallback.class.getName());
		props.put(WSHandlerConstants.USER, identifier);

		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(props);

		factory.getOutInterceptors().add(wssOut);

		INotyficationAction service = (INotyficationAction) factory.create();

		WsStatusWithStringValue returnValue = service.echo(MercuryConfig.createDefaultContext(), "Test");
		String echoResponse = returnValue.getValue();
		System.out.println("--->testSoapSecurityConnection: " + echoResponse);

	}

	public void testRemoteSecurityConnection() {

		String mercuryRemoteServiceUrl = testProperties.getProperty("mercury.ws.url");
		HttpInvokerProxyFactoryBean remote = new HttpInvokerProxyFactoryBean();
		remote.setServiceUrl(mercuryRemoteServiceUrl + "/NotyficationAction");
		remote.setServiceInterface(INotyficationAction.class);
		BasicHttpInvokerRequestExecutor e = new BasicHttpInvokerRequestExecutor();
		e.setIdentifier(testProperties.getProperty(ClientAuthConstants.WS_SECURITY_IDENTIFIER_PROP));
		e.setToken(testProperties.getProperty(ClientAuthConstants.WS_SECURITY_TOKEN_PROP));
		remote.setHttpInvokerRequestExecutor(e);
		remote.afterPropertiesSet();

		INotyficationAction service = (INotyficationAction) remote.getObject();

		WsStatusWithStringValue returnValue = service.echo(MercuryConfig.createDefaultContext(), "Test");
		String echoResponse = returnValue.getValue();
		System.out.println("--->testRemoteSecurityConnection: " + echoResponse);

	}

}
