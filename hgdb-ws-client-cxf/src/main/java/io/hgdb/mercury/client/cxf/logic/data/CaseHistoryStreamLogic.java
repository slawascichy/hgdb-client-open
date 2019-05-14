package io.hgdb.mercury.client.cxf.logic.data;

import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientCustomLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.data.CaseHistoryStream;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.ICaseHistoryStreamLogic;
import pro.ibpm.mercury.ws.server.api.actions.data.ICaseHistoryStreamAction;

/**
 * 
 * CaseHistoryStreamLogic
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $ 
 *
 */
public class CaseHistoryStreamLogic extends WsClientCustomLogic<CaseHistoryStream, Long, ICaseHistoryStreamAction>
		implements ICaseHistoryStreamLogic {

	private static final long serialVersionUID = -2017969288028348196L;

	@Override
	public CaseHistoryStream insert(Context context, final CaseHistoryStream e) throws MercuryException {
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public EntityList<CaseHistoryStream, Long> insertList(Context context, List<CaseHistoryStream> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService().insertBag(context, eBag));
	}

	@Override
	public CaseHistoryStream find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService().findByKey(context, pk));
	}

	@Override
	public CaseHistoryStream findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}

	@Override
	public List<CaseHistoryStream> filter(Context context, CaseHistoryStream e) throws MercuryException {
		return getEntityCollection(context, getService().filter(context, e));
	}

	@Override
	public List<CaseHistoryStream> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return getEntityCollection(context, getService().findByKeyBag(context, idList));
	}

	@Override
	public List<CaseHistoryStream> findByCaseId(Context context, Long caseId) throws MercuryException {
		return getEntityCollection(context, getService().findByCaseId(context, caseId));
	}

}
