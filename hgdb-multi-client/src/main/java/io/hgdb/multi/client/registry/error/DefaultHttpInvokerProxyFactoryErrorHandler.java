package io.hgdb.multi.client.registry.error;

/**
 * 
 * DefaultHttpInvokerProxyFactoryErrorHandler - obsługa błędu połączenia do
 * usług HTTP (SOAP) w pojedynczym węźle
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class DefaultHttpInvokerProxyFactoryErrorHandler implements IHttpInvokerProxyFactoryErrorHandler {

	@Override
	public Throwable raiseError(String instanceName, Throwable e) {
		return e;
	}

}
