package io.hgdb.mercury.client.cxf.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.INotyficationLogic;
import pro.ibpm.mercury.logic.api.NotyficationType;
import pro.ibpm.mercury.ws.server.api.actions.INotyficationAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatus;
import pro.ibpm.mercury.ws.server.api.returns.WsStatus;

public class NotyficationLogic implements INotyficationLogic {

	final protected Logger logger = LoggerFactory.getLogger(getClass());

	private INotyficationAction service;

	/**
	 * Ustawia instancję zdalnej usługi.
	 */
	public void setService(INotyficationAction service) {
		this.service = service;
	}

	/**
	 * Zwraca instancję zdalnej usługi.
	 */
	public INotyficationAction getService() {
		return service;
	}

	protected void checkStatus(final IWsStatus wsStatus) throws MercuryException {
		if (wsStatus != null) {
			/* FIXME status nie musi być przesyłany gdy wynik jest pusty */
			final String errorCode = wsStatus.getErrorCode();
			if (errorCode != null) {
				logger.error("wsStatus={} errorCode={}", new Object[] { wsStatus, wsStatus.getErrorCode() });
				throw new MercuryException(errorCode, wsStatus.getErrorMessage());
			}
		}
	}

	@Override
	public void sendNotyfication(Context context, NotyficationType notType) throws MercuryException {
		WsStatus wsStatus = getService().sendNotyfication(context, notType);
		checkStatus(wsStatus);
	}

	@Override
	public void sendNotyfication(Context context, NotyficationType notType, String content) throws MercuryException {
		WsStatus wsStatus = getService().sendNotyficationWithContent(context, notType, content);
		checkStatus(wsStatus);
	}

}
