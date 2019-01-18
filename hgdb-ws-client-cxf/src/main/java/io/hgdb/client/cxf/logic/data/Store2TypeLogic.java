package io.hgdb.client.cxf.logic.data;

import java.util.Collection;
import java.util.List;

import io.hgdb.client.cxf.logic.WsClientDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.attr.TypeCode;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.data.Store;
import pro.ibpm.mercury.entities.data.Store2Type;
import pro.ibpm.mercury.entities.data.Store2TypePK;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.IStore2TypeLogic;
import pro.ibpm.mercury.ws.server.api.actions.data.IStore2TypeAction;

/**
 * @author Karol Kowalczyk
 * 
 */
public class Store2TypeLogic extends
		WsClientDataLogic<Store2Type, Store2TypePK, IStore2TypeAction>
		implements IStore2TypeLogic {

	private static final long serialVersionUID = -8214168627778834685L;

	@Override
	public Store2Type insert(Context context, final Store2Type e)
			throws MercuryException {
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public EntityList<Store2Type, Store2TypePK> insertList(Context context,
			List<Store2Type> eBag) throws MercuryException {
		return getEntityCollection(context,
				getService().insertBag(context, eBag));
	}

	@Override
	public Store2TypePK remove(Context context, final Store2Type e)
			throws MercuryException {
		return getId(getService().remove(context, e), e);
	}

	@Override
	public List<Store2TypePK> removeList(Context context,
			final List<Store2Type> eBag) throws MercuryException {
		return getIds(getService().removeBag(context, eBag), eBag);
	}

	@Override
	public Store2Type find(Context context, final Store2TypePK pk)
			throws MercuryException {
		logger.debug("Store2TypeLogic:findByKey:pk={}", new Object[] { pk });
		return getEntity(
				context,
				getService().findByKey(
						context,
						new Store2TypePK(new Store(pk.getStore().getId()),
								new TypeCode(pk.getTypeCode().getId()))));
	}

	@Override
	public Store2Type findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}

	@Override
	public Store2Type update(Context context, Store2Type e)
			throws MercuryException {
		return getEntity(context, getService().update(context, e));
	}

	@Override
	public EntityList<Store2Type, Store2TypePK> updateList(Context context,
			List<Store2Type> eBag) throws MercuryException {
		return getEntityCollection(context,
				getService().updateBag(context, eBag));
	}

	@Override
	public List<Store2Type> findByIdList(Context context,
			List<Store2TypePK> idList) throws MercuryException {
		return (List<Store2Type>) getEntityCollection(context, getService()
				.findByKeyBag(context, (Collection<Store2TypePK>) idList));
	}

	@Override
	public List<Store2Type> filter(Context context, Store2Type e)
			throws MercuryException {
		return (List<Store2Type>) getEntityCollection(context, getService()
				.filter(context, e));
	}

	@Override
	public List<Store2Type> findByTypeName(Context context, String typeName)
			throws MercuryException {
		return (List<Store2Type>) getEntityCollection(context, getService()
				.findByTypeName(context, typeName));
	}

	@Override
	public List<Store2Type> findByTypeCode(Context context, String typeCode)
			throws MercuryException {
		return (List<Store2Type>) getEntityCollection(context, getService()
				.findByTypeCode(context, typeCode));
	}

	@Override
	public List<Store2Type> findAll(Context context) throws MercuryException {
		return (List<Store2Type>) getEntityCollection(context, getService()
				.findAll(context));
	}

}
