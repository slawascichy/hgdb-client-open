package io.hgdb.multi.client.rmi.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;

/**
 * 
 * RmiClient
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 * @param <W>
 *            interfejs usługi RMI
 */
public abstract class RmiClient<W> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IClientDynamicRegistry rmiClientDynamicRegistry;

	private Class<W> serviceInterface;

	protected RmiClient(Class<W> serviceInterface) {
		super();
		this.serviceInterface = serviceInterface;
	}

	/**
	 * Zwraca instancję zdalnej usługi.
	 */
	public W getService(Context context) {
		return rmiClientDynamicRegistry.getBean(context, getServiceInterface());
	}

	/**
	 * @return the {@link #serviceInterface}
	 */
	public Class<W> getServiceInterface() {
		return serviceInterface;
	}

	/**
	 * @param serviceInterface
	 *            the {@link #serviceInterface} to set
	 */
	public void setServiceInterface(Class<W> serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

}
