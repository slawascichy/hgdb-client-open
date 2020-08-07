package io.hgdb.multi.client.registry.error;

/**
 * 
 * IHttpInvokerProxyFactoryErrorHandler - obsługa błędów połączeń HTTP (SOAP)
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public interface IHttpInvokerProxyFactoryErrorHandler {

	Throwable raiseError(String instanceName, Throwable e);

}
