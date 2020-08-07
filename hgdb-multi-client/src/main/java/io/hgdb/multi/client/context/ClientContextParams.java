package io.hgdb.multi.client.context;

/**
 * 
 * ClientContextParams
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class ClientContextParams {

	private ClientContextParams() {
	}

	/**
	 * Parametr kontekstu, w którym przesyłana jest nazwa/akronim klienta w
	 * komunikacji RMI. Parametr wykorzystywany do definiowania dynamicznej
	 * komunikacji z wieloma instancjami bazy danych Parametr czyszczony zaraz po
	 * nawiązaniu/pobraniu bean'a usługi.
	 */
	public static final String RMI_PROXY_FACTORY_INSTANCE_NAME = "RmiProxyFactoryDynamicRegistry.InstanceName";

	/**
	 * Parametr kontekstu, w którym przesyłana jest nazwa/akronim klienta w
	 * komunikacji HTTP. Parametr wykorzystywany do definiowania dynamicznej
	 * komunikacji z wieloma instancjami bazy danych Parametr czyszczony zaraz po
	 * nawiązaniu/pobraniu bean'a usługi.
	 */
	public static final String HTTP_INVOCER_PROXY_FACTORY_INSTANCE_NAME = "HttpInvokerProxyFactoryDynamicRegistry.InstanceName";

}
