package io.hgdb.multi.client.registry.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;

/**
 * 
 * InstanceRmiProxyFactoryErrorHandler - obsługa błędu połączenia do usług RMI w
 * klastrze
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class InstanceRmiProxyFactoryErrorHandler implements IRmiProxyFactoryErrorHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Throwable raiseError(String instanceName, Throwable e) {
		if (e instanceof RemoteAccessException) {
			logger.error(instanceName, e);
		}
		return e;
	}

}
