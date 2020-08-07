package io.hgdb.multi.client.security;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.remoting.httpinvoker.HttpComponentsHttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.HttpInvokerClientConfiguration;

import io.hgdb.multi.client.config.ClientConfigParams;

/**
 * 
 * BasicHttpInvokerRequestExecutor
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class BasicHttpInvokerRequestExecutor extends HttpComponentsHttpInvokerRequestExecutor {

	/**
	 * Domyślny czas weryfikacji połączenia po braku aktywności (używania
	 * połączenia) połączenia w puli. Ustawione na 6s.
	 */
	private static final int DEFAULT_TIME_VALIDATE_AFTER_INACTIVITY = 6000;
	/** Przygotowany string zakodowanej frazy "Basic" */
	private String credentials = null;
	/** identyfikator/nazwa użytkownika */
	private String identifier;
	/** security token/password użytkownika */
	private String token;

	public BasicHttpInvokerRequestExecutor() {
		super(createDefaultHttpClient());
	}

	private static HttpClient createDefaultHttpClient() {
		Registry<ConnectionSocketFactory> schemeRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory())
				.register("https", SSLConnectionSocketFactory.getSocketFactory()).build();

		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(schemeRegistry);
		connectionManager.setMaxTotal(ClientConfigParams.getConnectionMaxConnections());
		connectionManager.setDefaultMaxPerRoute(ClientConfigParams.getConnectionMaxConnectionsPerRoute());
		/* weryfikacja czasu wygaśnięcia połączenia w puli - START */
		int connectionTimeOut = ClientConfigParams.getConnectionConnectionTimeout();
		if (connectionTimeOut != 0 && DEFAULT_TIME_VALIDATE_AFTER_INACTIVITY > connectionTimeOut) {
			connectionManager.setValidateAfterInactivity(connectionTimeOut);
		} else {
			connectionManager.setValidateAfterInactivity(DEFAULT_TIME_VALIDATE_AFTER_INACTIVITY);
		}
		/* weryfikacja czasu wygaśnięcia połączenia w puli - KONIEC */
		return HttpClientBuilder.create().setConnectionManager(connectionManager).build();
	}

	/* FIXME Use a more secure method than basic authentication */
	@SuppressWarnings("squid:S2647")
	@Override
	protected HttpPost createHttpPost(HttpInvokerClientConfiguration config) throws IOException {
		HttpPost postMethod = super.createHttpPost(config);
		if (credentials == null) {
			if (StringUtils.isNotBlank(identifier) && StringUtils.isNotBlank(token)) {
				String base64 = identifier + ":" + token;
				credentials = new String(Base64.encodeBase64(base64.getBytes()));
			} else {
				credentials = StringUtils.EMPTY;
			}
		}
		if (StringUtils.isNotBlank(credentials)) {
			postMethod.addHeader("Authorization", "Basic " + credentials);
		}
		return postMethod;
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

	/**
	 * @return the {@link #token}
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the {@link #token} to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
}
