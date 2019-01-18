/**
 * 
 */
package io.hgdb.mercury.client.cxf.logic.arch;

import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientBigDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.arch.ArchCaseHistoryTrace;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.arch.IArchCaseHistoryTraceLogic;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.arch.IArchCaseHistoryTraceAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;

/**
 * @author Karol Kowalczyk
 * 
 */
public class ArchCaseHistoryTraceLogic
		extends WsClientBigDataLogic<ArchCaseHistoryTrace, Long, IArchCaseHistoryTraceAction>
		implements IArchCaseHistoryTraceLogic {

	private static final long serialVersionUID = -8833781824851370404L;

	@Override
	public ArchCaseHistoryTrace insert(Context context, final ArchCaseHistoryTrace e) throws MercuryException {
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public EntityList<ArchCaseHistoryTrace, Long> insertList(Context context, List<ArchCaseHistoryTrace> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService().insertBag(context, eBag));
	}

	@Override
	public Long remove(Context context, final ArchCaseHistoryTrace e) throws MercuryException {
		return getId(getService().remove(context, e), e);
	}

	@Override
	public List<Long> removeList(Context context, final List<ArchCaseHistoryTrace> eBag) throws MercuryException {
		return getIds(getService().removeBag(context, eBag), eBag);
	}

	@Override
	public ArchCaseHistoryTrace find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService().findByKey(context, pk));
	}

	@Override
	public ArchCaseHistoryTrace findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}

	@Override
	public ArchCaseHistoryTrace update(Context context, ArchCaseHistoryTrace e) throws MercuryException {
		return getEntity(context, getService().update(context, e));
	}

	@Override
	public EntityList<ArchCaseHistoryTrace, Long> updateList(Context context, List<ArchCaseHistoryTrace> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService().updateBag(context, eBag));
	}

	@Override
	public List<ArchCaseHistoryTrace> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return getEntityCollection(context, getService().findByKeyBag(context, idList));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<ArchCaseHistoryTrace, IPage> filterPaged(Context context, ArchCaseHistoryTrace e, IPage page)
			throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService().filterPaged(context, e, (PageTransportable) page));
	}

	@Override
	public List<ArchCaseHistoryTrace> findAll(Context context) throws MercuryException {
		return getEntityCollection(context, getService().findAll(context));
	}

}
