package io.hgdb.multi.client.registry.error;

/**
 * 
 * IRmiProxyFactoryErrorHandler - obsługa błędów połączeń RMI
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public interface IRmiProxyFactoryErrorHandler {

	Throwable raiseError(String instanceName, Throwable e);

}
