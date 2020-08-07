package io.hgdb.mercury.client.cxf.logic.data;

import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientBigDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.data.LoggerEvent;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.ILoggerEventLogic;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.data.ILoggerEventAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;

/**
 * @author Karol Kowalczyk
 * 
 */
public class LoggerEventLogic extends WsClientBigDataLogic<LoggerEvent, Long, ILoggerEventAction>
		implements ILoggerEventLogic {

	private static final long serialVersionUID = -46091394622637678L;

	@Override
	public LoggerEvent insert(Context context, final LoggerEvent e) throws MercuryException {
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public EntityList<LoggerEvent, Long> insertList(Context context, List<LoggerEvent> eBag) throws MercuryException {
		return getEntityCollection(context, getService(context).insertBag(context, eBag));
	}

	@Override
	public Long remove(Context context, final LoggerEvent e) throws MercuryException {
		return getId(getService(context).remove(context, e), e);
	}

	@Override
	public List<Long> removeList(Context context, final List<LoggerEvent> eBag) throws MercuryException {
		return getIds(getService(context).removeBag(context, eBag), eBag);
	}

	@Override
	public LoggerEvent find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public LoggerEvent findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

	@Override
	public LoggerEvent update(Context context, LoggerEvent e) throws MercuryException {
		return getEntity(context, getService(context).update(context, e));
	}

	@Override
	public EntityList<LoggerEvent, Long> updateList(Context context, List<LoggerEvent> eBag) throws MercuryException {
		return getEntityCollection(context, getService(context).updateBag(context, eBag));
	}

	@Override
	public List<LoggerEvent> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return getEntityCollection(context, getService(context).findByKeyBag(context, idList));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<LoggerEvent, IPage> filterPaged(Context context, LoggerEvent e, IPage page)
			throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService(context).filterPaged(context, e, (PageTransportable) page));
	}

	@Override
	public List<LoggerEvent> findByDocumentId(Context context, final String documentId) throws MercuryException {
		return getEntityCollection(context, getService(context).findByDocumentId(context, documentId));
	}

	@Override
	public List<LoggerEvent> findAll(Context context) throws MercuryException {
		return getEntityCollection(context, getService(context).findAll(context));
	}

}
