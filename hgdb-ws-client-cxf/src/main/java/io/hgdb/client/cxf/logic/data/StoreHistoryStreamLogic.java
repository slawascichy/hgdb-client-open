package io.hgdb.client.cxf.logic.data;

import java.util.List;

import io.hgdb.client.cxf.logic.WsClientDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.data.StoreHistoryStream;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.IStoreHistoryStreamLogic;
import pro.ibpm.mercury.ws.server.api.actions.data.IStoreHistoryStreamAction;

/**
 * @author Karol Kowalczyk
 * 
 */
public class StoreHistoryStreamLogic extends WsClientDataLogic<StoreHistoryStream, Long, IStoreHistoryStreamAction>
		implements IStoreHistoryStreamLogic {

	private static final long serialVersionUID = -7967554685193583171L;

	@Override
	public StoreHistoryStream insert(Context context, final StoreHistoryStream e) throws MercuryException {
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public EntityList<StoreHistoryStream, Long> insertList(Context context, List<StoreHistoryStream> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService().insertBag(context, eBag));
	}

	@Override
	public Long remove(Context context, final StoreHistoryStream e) throws MercuryException {
		return getId(getService().remove(context, e), e);
	}

	@Override
	public List<Long> removeList(Context context, final List<StoreHistoryStream> eBag) throws MercuryException {
		return getIds(getService().removeBag(context, eBag), eBag);
	}

	@Override
	public StoreHistoryStream find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService().findByKey(context, pk));
	}

	@Override
	public StoreHistoryStream findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}

	@Override
	public StoreHistoryStream update(Context context, StoreHistoryStream e) throws MercuryException {
		return getEntity(context, getService().update(context, e));
	}

	@Override
	public EntityList<StoreHistoryStream, Long> updateList(Context context, List<StoreHistoryStream> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService().updateBag(context, eBag));
	}

	@Override
	public List<StoreHistoryStream> filter(Context context, StoreHistoryStream e) throws MercuryException {
		return getEntityCollection(context, getService().filter(context, e));
	}

	@Override
	public List<StoreHistoryStream> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return getEntityCollection(context, getService().findByKeyBag(context, idList));
	}

	@Override
	public List<StoreHistoryStream> findAll(Context context) throws MercuryException {
		return getEntityCollection(context, getService().findAll(context));
	}
}
