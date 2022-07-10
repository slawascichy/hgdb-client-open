package org.mercury.cxf.client.security;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.spring4hgdb.remoting.httpinvoker.HttpInvokerRequestExecutor;
import org.spring4hgdb.remoting.httpinvoker.SimpleHttpInvokerRequestExecutor;

public class BasicHttpInvokerRequestExecutor extends SimpleHttpInvokerRequestExecutor
		implements HttpInvokerRequestExecutor {

	/** Przygotowany string zakodowanej frazy "Basic" */
	private String credentials = null;
	/** identyfikator/nazwa użytkownika */
	private String identifier;
	/** security token/password użytkownika */
	private String token;

	public BasicHttpInvokerRequestExecutor() {
		super();
	}

	/* Overridden (non-Javadoc) */
	@Override
	protected void prepareConnection(HttpURLConnection connection, int contentLength) throws IOException {
		if (credentials == null) {
			if (StringUtils.isNotBlank(identifier) && StringUtils.isNotBlank(token)) {
				String base64 = identifier + ":" + token;
				credentials = new String(Base64.encodeBase64(base64.getBytes()));
			} else {
				credentials = StringUtils.EMPTY;
			}
		}
		if (StringUtils.isNotBlank(credentials)) {
			connection.setRequestProperty("Authorization", "Basic " + credentials);
		}
		super.prepareConnection(connection, contentLength);
	}

	/**
	 * @return the {@link #identifier}
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 *                   the {@link #identifier} to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the {@link #token}
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *              the {@link #token} to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

}
