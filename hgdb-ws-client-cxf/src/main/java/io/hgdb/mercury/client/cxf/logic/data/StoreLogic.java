package io.hgdb.mercury.client.cxf.logic.data;

import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.data.Store;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.IStoreLogic;
import pro.ibpm.mercury.ws.server.api.actions.data.IStoreAction;

/**
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * 
 */
public class StoreLogic extends WsClientDataLogic<Store, Long, IStoreAction> implements IStoreLogic {

	private static final long serialVersionUID = 6654997299373900643L;

	@Override
	public Store insert(Context context, final Store e) throws MercuryException {
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public EntityList<Store, Long> insertList(Context context, List<Store> eBag) throws MercuryException {
		return getEntityCollection(context, getService(context).insertBag(context, eBag));
	}

	@Override
	public Long remove(Context context, final Store e) throws MercuryException {
		return getId(getService(context).remove(context, e), e);
	}

	@Override
	public List<Long> removeList(Context context, final List<Store> eBag) throws MercuryException {
		return getIds(getService(context).removeBag(context, eBag), eBag);
	}

	@Override
	public Store find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public Store findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

	@Override
	public Store update(Context context, Store e) throws MercuryException {
		return getEntity(context, getService(context).update(context, e));
	}

	@Override
	public EntityList<Store, Long> updateList(Context context, List<Store> eBag) throws MercuryException {
		return getEntityCollection(context, getService(context).updateBag(context, eBag));
	}

	@Override
	public List<Store> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return getEntityCollection(context, getService(context).findByKeyBag(context, idList));
	}

	@Override
	public List<Store> filter(Context context, Store e) throws MercuryException {
		return getEntityCollection(context, getService(context).filter(context, e));
	}

	@Override
	public List<Store> findByTypeName(Context context, String typeName) throws MercuryException {
		return getEntityCollection(context, getService(context).findByTypeName(context, typeName));
	}

	@Override
	public List<Store> findByTypeCode(Context context, String typeCode) throws MercuryException {
		return getEntityCollection(context, getService(context).findByTypeCode(context, typeCode));
	}

	@Override
	public List<Store> findAll(Context context) throws MercuryException {
		return getEntityCollection(context, getService(context).findAll(context));
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Store findStoreByNameAndSource(Context context, String name, String sourceOfObject) throws MercuryException {
		return getEntity(context, getService(context).findStoreByNameAndSource(context, name, sourceOfObject));
	}
}
