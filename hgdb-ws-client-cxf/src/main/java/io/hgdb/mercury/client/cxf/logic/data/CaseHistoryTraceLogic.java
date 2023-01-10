package io.hgdb.mercury.client.cxf.logic.data;

import java.util.List;
import java.util.Map;

import io.hgdb.mercury.client.cxf.logic.WsClientCustomLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.data.CaseHistoryTrace;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.ICaseHistoryTraceLogic;
import pro.ibpm.mercury.logic.exceptions.LC025MethodNotSupportedException;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.data.ICaseHistoryTraceAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;
import pro.ibpm.mercury.ws.server.api.returns.WsStatus;

/**
 * 
 * CaseHistoryTraceLogic
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
public class CaseHistoryTraceLogic extends WsClientCustomLogic<CaseHistoryTrace, Long, ICaseHistoryTraceAction>
		implements ICaseHistoryTraceLogic {

	private static final long serialVersionUID = 2991967188307553981L;

	@Override
	public CaseHistoryTrace find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public CaseHistoryTrace findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

	@Override
	public List<CaseHistoryTrace> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return getEntityCollection(context, getService(context).findByKeyBag(context, idList));
	}

	@Override
	public List<CaseHistoryTrace> findByCaseId(Context context, String caseId) throws MercuryException {
		return getEntityCollection(context, getService(context).findByCaseId(context, caseId));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<CaseHistoryTrace, IPage> filterPaged(Context context, CaseHistoryTrace e, IPage page)
			throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService(context).filterPaged(context, e, (PageTransportable) page));
	}

	@Override
	public List<CaseHistoryTrace> filter(Context context, CaseHistoryTrace entityObject) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Override
	public IPagedResult<CaseHistoryTrace, IPage> filter(Context context, Map<String, Object> sqlParams, IPage page)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Override
	public void clearQueryCache(Context context) throws MercuryException {
		WsStatus wsStatus = getService(context).clearQueryCache(context);
		checkWsStatus(wsStatus);
	}

}
