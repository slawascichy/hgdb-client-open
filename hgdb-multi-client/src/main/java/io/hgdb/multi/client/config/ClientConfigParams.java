package io.hgdb.multi.client.config;

import org.apache.commons.lang.StringUtils;

import pro.ibpm.mercury.config.MercuryConfig;

/**
 * 
 * ClientConfigParams zbiór parametrów oraz wsparcie związane z definiowaniem komunikacji z serwerami bazy danych.
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class ClientConfigParams {

	private ClientConfigParams() {
	}

	public static final String WS_DEFAULT_IDENTIFIER = "cxf";
	public static final String WS_DEFAULT_TOKEN = "secret";

	public static final String RMI_SERVICE_DEFAULT_PROP = "mercury.rmi.service.default";
	public static final String RMI_SERVICE_LIST_PROP = "mercury.rmi.service.list";
	public static final String RMI_DEFAULT_SERVICE = "localhost";
	public static final String RMI_REMOTE_SERVICE_URL_PROP = "%s.rmi.url";
	public static final String RMI_SECURITY_IDENTIFIER_PROP = "%s.rmi.security.identifier";
	public static final String RMI_SECURITY_TOKEN_PROP = "%s.rmi.security.token";

	public static final String WS_SERVICE_DEFAULT_PROP = "mercury.ws.service.default";
	public static final String WS_SERVICE_LIST_PROP = "mercury.ws.service.list";
	public static final String WS_DEFAULT_SERVICE = "localhost";
	public static final String WS_REMOTE_SERVICE_URL_PROP = "%s.ws.url";
	public static final String WS_SECURITY_IDENTIFIER_PROP = "%s.ws.security.identifier";
	public static final String WS_SECURITY_TOKEN_PROP = "%s.ws.security.token";
	public static final String WS_REMOTE_SECURITY_ENABLED_PROP = "%s.ws.security.enabled";

	/**
	 * Parametr definiujący maksymalną liczbę połączeń w puli połączeń wykorzystywanych do przekierowania ruchu.
	 */
	public static final String CONNECTION_MAX_CONNECTIONS_PROP = "mercury.connection.max.connections.total";
	private static Integer connectionMaxConnections;

	/**
	 * Parametr definiujący maksymalną liczbę połączeń dla konkretnej usługi w puli połączeń wykorzystywanych do
	 * przekierowania ruchu.
	 */
	public static final String CONNECTION_MAX_CONNECTIONS_PER_ROUTE_PROP = "mercury.connection.max.connections.perRoute";
	private static Integer connectionMaxConnectionsPerRoute;

	/**
	 * Parametr definiujący timeout odczytu dla połączeń z instancjami bazy mercury wykorzystywanych do przekierowania
	 * ruchu. Wartość wyrażona w milisekundach.
	 */
	public static final String CONNECTION_READ_TIMEOUT_PROP = "mercury.connection.read.timeout";
	private static Integer connectionReadTimeout;

	/**
	 * Parametr definiujący timeout dla połączenia w puli. Po tym czasie, nieaktywne połączenie zostanie zamknięte.
	 */
	public static final String CONNECTION_CONNECTION_TIMEOUT_PROP = "mercury.connection.connection.timeout";
	private static Integer connectionConnectionTimeout;

	public static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 256;
	public static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 128;
	public static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = (60 * 1000);

	/**
	 * @return the {@link #connectionMaxConnections}
	 */
	public static int getConnectionMaxConnections() {
		if (connectionMaxConnections == null) {
			String propValue = MercuryConfig.getInstance().get(CONNECTION_MAX_CONNECTIONS_PROP);
			if (StringUtils.isBlank(propValue)) {
				connectionMaxConnections = DEFAULT_MAX_TOTAL_CONNECTIONS;
			} else {
				connectionMaxConnections = Integer.parseInt(propValue);
			}
		}
		return connectionMaxConnections;
	}

	/**
	 * @return the {@link #connectionMaxConnectionsPerRoute}
	 */
	public static int getConnectionMaxConnectionsPerRoute() {
		if (connectionMaxConnectionsPerRoute == null) {
			String propValue = MercuryConfig.getInstance().get(CONNECTION_MAX_CONNECTIONS_PER_ROUTE_PROP);
			if (StringUtils.isBlank(propValue)) {
				connectionMaxConnectionsPerRoute = DEFAULT_MAX_CONNECTIONS_PER_ROUTE;
			} else {
				connectionMaxConnectionsPerRoute = Integer.parseInt(propValue);
			}
		}
		return connectionMaxConnectionsPerRoute;
	}

	/**
	 * @return the {@link #connectionReadTimeout}
	 */
	public static int getConnectionReadTimeout() {
		if (connectionReadTimeout == null) {
			String propValue = MercuryConfig.getInstance().get(CONNECTION_READ_TIMEOUT_PROP);
			if (StringUtils.isBlank(propValue)) {
				connectionReadTimeout = DEFAULT_READ_TIMEOUT_MILLISECONDS;
			} else {
				connectionReadTimeout = Integer.parseInt(propValue);
			}
		}
		return connectionReadTimeout;
	}

	/**
	 * @return the {@link #connectionConnectionTimeout}
	 */
	public static int getConnectionConnectionTimeout() {
		if (connectionConnectionTimeout == null) {
			String propValue = MercuryConfig.getInstance().get(CONNECTION_CONNECTION_TIMEOUT_PROP);
			if (StringUtils.isBlank(propValue)) {
				connectionConnectionTimeout = 0;
			} else {
				connectionConnectionTimeout = Integer.parseInt(propValue);
			}
		}
		return connectionConnectionTimeout;
	}

}
