package io.hgdb.mercury.client.cxf.logic.data;

import java.util.List;
import java.util.Map;

import io.hgdb.mercury.client.cxf.logic.WsClientCustomLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.data.ParticipantHistoryTrace;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.IParticipantHistoryTraceLogic;
import pro.ibpm.mercury.logic.exceptions.LC025MethodNotSupportedException;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.data.IParticipantHistoryTraceAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;

/**
 * @author Karol Kowalczyk
 * 
 */
public class ParticipantHistoryTraceLogic
		extends WsClientCustomLogic<ParticipantHistoryTrace, Long, IParticipantHistoryTraceAction>
		implements IParticipantHistoryTraceLogic {

	private static final long serialVersionUID = -5765026501703909192L;

	@Override
	public ParticipantHistoryTrace find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService().findByKey(context, pk));
	}

	@Override
	public ParticipantHistoryTrace findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}

	@Override
	public List<ParticipantHistoryTrace> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return getEntityCollection(context, getService().findByKeyBag(context, idList));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<ParticipantHistoryTrace, IPage> filterPaged(Context context, ParticipantHistoryTrace e,
			IPage page) throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService().filterPaged(context, e, (PageTransportable) page));
	}

	@Override
	public List<ParticipantHistoryTrace> filter(Context context, ParticipantHistoryTrace entityObject)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Override
	public IPagedResult<ParticipantHistoryTrace, IPage> filter(Context context, Map<String, Object> sqlParams,
			IPage page) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}
}
