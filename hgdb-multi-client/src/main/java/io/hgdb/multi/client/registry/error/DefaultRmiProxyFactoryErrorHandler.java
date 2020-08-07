package io.hgdb.multi.client.registry.error;

/**
 * 
 * DefaultRmiProxyFactoryErrorHandler - obsługa błędu połączenia do usług RMI w
 * pojedynczym węźle
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class DefaultRmiProxyFactoryErrorHandler implements IRmiProxyFactoryErrorHandler {

	@Override
	public Throwable raiseError(String instanceName, Throwable e) {
		return e;
	}

}
