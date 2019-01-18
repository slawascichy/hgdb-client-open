package io.hgdb.client.cxf.logic.data;

import java.util.List;

import io.hgdb.client.cxf.logic.WsClientBigDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.data.CaseHistoryTrace;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.ICaseHistoryTraceLogic;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.data.ICaseHistoryTraceAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;

/**
 * 
 * CaseHistoryTraceLogic
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $ 
 *
 */
public class CaseHistoryTraceLogic extends WsClientBigDataLogic<CaseHistoryTrace, Long, ICaseHistoryTraceAction>
		implements ICaseHistoryTraceLogic {

	private static final long serialVersionUID = 2991967188307553981L;

	@Override
	public CaseHistoryTrace insert(Context context, final CaseHistoryTrace e) throws MercuryException {
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public EntityList<CaseHistoryTrace, Long> insertList(Context context, List<CaseHistoryTrace> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService().insertBag(context, eBag));
	}

	@Override
	public Long remove(Context context, final CaseHistoryTrace e) throws MercuryException {
		return getId(getService().remove(context, e), e);
	}

	@Override
	public List<Long> removeList(Context context, final List<CaseHistoryTrace> eBag) throws MercuryException {
		return getIds(getService().removeBag(context, eBag), eBag);
	}

	@Override
	public CaseHistoryTrace find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService().findByKey(context, pk));
	}

	@Override
	public CaseHistoryTrace findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}

	@Override
	public CaseHistoryTrace update(Context context, CaseHistoryTrace e) throws MercuryException {
		return getEntity(context, getService().update(context, e));
	}

	@Override
	public EntityList<CaseHistoryTrace, Long> updateList(Context context, List<CaseHistoryTrace> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService().updateBag(context, eBag));
	}

	@Override
	public List<CaseHistoryTrace> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return getEntityCollection(context, getService().findByKeyBag(context, idList));
	}

	@Override
	public List<CaseHistoryTrace> findByCaseId(Context context, Long caseId) throws MercuryException {
		return getEntityCollection(context, getService().findByCaseId(context, caseId));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<CaseHistoryTrace, IPage> filterPaged(Context context, CaseHistoryTrace e, IPage page)
			throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService().filterPaged(context, e, (PageTransportable) page));
	}

	@Override
	public List<CaseHistoryTrace> findAll(Context context) throws MercuryException {
		return getEntityCollection(context, getService().findAll(context));
	}

}
