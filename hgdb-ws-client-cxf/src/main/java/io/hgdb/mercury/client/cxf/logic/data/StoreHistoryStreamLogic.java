package io.hgdb.mercury.client.cxf.logic.data;

import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientCustomLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.data.StoreHistoryStream;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.IStoreHistoryStreamLogic;
import pro.ibpm.mercury.ws.server.api.actions.data.IStoreHistoryStreamAction;

/**
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * 
 */
public class StoreHistoryStreamLogic extends WsClientCustomLogic<StoreHistoryStream, Long, IStoreHistoryStreamAction>
		implements IStoreHistoryStreamLogic {

	private static final long serialVersionUID = -7967554685193583171L;

	@Override
	public StoreHistoryStream insert(Context context, final StoreHistoryStream e) throws MercuryException {
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public EntityList<StoreHistoryStream, Long> insertList(Context context, List<StoreHistoryStream> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService(context).insertBag(context, eBag));
	}

	@Override
	public StoreHistoryStream find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public StoreHistoryStream findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

	@Override
	public List<StoreHistoryStream> filter(Context context, StoreHistoryStream e) throws MercuryException {
		return getEntityCollection(context, getService(context).filter(context, e));
	}

	@Override
	public List<StoreHistoryStream> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return getEntityCollection(context, getService(context).findByKeyBag(context, idList));
	}

}
