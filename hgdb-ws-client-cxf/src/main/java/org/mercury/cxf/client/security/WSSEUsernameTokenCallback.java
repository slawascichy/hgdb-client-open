package org.mercury.cxf.client.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;

/**
 * 
 * WSSEUsernameTokenCallback kliencka implementacja zabezpieczenia komunikacji
 * tokenem użytkownika.
 * 
 * Implementuje dołożenie do żądania SOAP nagłówka:
 * 
 * <pre>
   <soapenv:Header>
       <wsse:Security xmlns:wsse=
"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
        <wsse:UsernameToken xmlns:wsu=
"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
            <wsse:Username>cxf</wsse:Username>
            <wsse:Password Type=
"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">cxf</wsse:Password>
        </wsse:UsernameToken>
    </wsse:Security>
   </soapenv:Header>
 * </pre>
 * 
 * https://examples.javacodegeeks.com/enterprise-java/jws/jax-ws-client-basic-authentication-example/
 * 
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
public class WSSEUsernameTokenCallback implements CallbackHandler {

	private static final Map<String, String> tokens = new HashMap<String, String>();
	private String identifier;

	public WSSEUsernameTokenCallback() {
		super();
	}

	public WSSEUsernameTokenCallback(String lIdentifier) {
		super();
		this.identifier = lIdentifier;
	}

	public WSSEUsernameTokenCallback(String lIdentifier, String lToken) {
		super();
		this.identifier = lIdentifier;
		tokens.put(lIdentifier, lToken);
	}

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for (Callback callback : callbacks) {
			WSPasswordCallback wpc = (WSPasswordCallback) callback;
			String pass = tokens.get(wpc.getIdentifier());
			if (pass != null) {
				wpc.setPassword(pass);
				return;
			}
		}
	}

	/**
	 * @return the {@link #identifier}
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 *            the {@link #identifier} to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public static String put(String lIdentifier, String lToken) {
		return tokens.put(lIdentifier, lToken);
	}

}
