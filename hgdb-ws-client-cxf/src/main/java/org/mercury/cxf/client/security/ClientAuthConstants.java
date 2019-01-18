package org.mercury.cxf.client.security;

/**
 * 
 * ClientAuthConstants stałe powiązane z zabezpieczeniami po stronie klienta.
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
public class ClientAuthConstants {

	private ClientAuthConstants() {
	}

	public static final String WS_DEFAULT_IDENTIFIER = "cxf";
	public static final String WS_DEFAULT_TOKEN = "secret";
	public static final String WS_SECURITY_IDENTIFIER_PROP = "mercury.ws.security.identifier";
	public static final String WS_SECURITY_TOKEN_PROP = "mercury.ws.security.token";
	public static final String WS_SECURITY_ENABLED_PROP = "mercury.ws.security.enabled";

}
