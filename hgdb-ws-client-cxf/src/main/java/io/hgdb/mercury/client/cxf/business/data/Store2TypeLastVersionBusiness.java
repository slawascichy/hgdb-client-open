package io.hgdb.mercury.client.cxf.business.data;

import java.util.Collection;
import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientDataLogic;
import pro.ibpm.mercury.business.data.api.IStore2TypeLastVersionBusiness;
import pro.ibpm.mercury.business.entities.Store2TypeLastVersion;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.attr.TypeCode;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.data.Store;
import pro.ibpm.mercury.entities.data.Store2TypePK;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.ws.server.api.actions.business.data.IStore2TypeLastVersionBusinessAction;

/**
 * 
 * Store2TypeLastVersionBusiness
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $ 
 *
 */
public class Store2TypeLastVersionBusiness
		extends WsClientDataLogic<Store2TypeLastVersion, Store2TypePK, IStore2TypeLastVersionBusinessAction>
		implements IStore2TypeLastVersionBusiness {

	private static final long serialVersionUID = -8214168627778834685L;

	@Override
	public Store2TypeLastVersion insert(Context context, final Store2TypeLastVersion e) throws MercuryException {
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public EntityList<Store2TypeLastVersion, Store2TypePK> insertList(Context context, List<Store2TypeLastVersion> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService().insertBag(context, eBag));
	}

	@Override
	public Store2TypePK remove(Context context, final Store2TypeLastVersion e) throws MercuryException {
		return getId(getService().remove(context, e), e);
	}

	@Override
	public List<Store2TypePK> removeList(Context context, final List<Store2TypeLastVersion> eBag)
			throws MercuryException {
		return getIds(getService().removeBag(context, eBag), eBag);
	}

	@Override
	public Store2TypeLastVersion find(Context context, final Store2TypePK pk) throws MercuryException {
		logger.debug("Store2TypeLastVersionBusiness:findByKey:pk={}", new Object[] { pk });
		return getEntity(context, getService().findByKey(context,
				new Store2TypePK(new Store(pk.getStore().getId()), new TypeCode(pk.getTypeCode().getId()))));
	}

	@Override
	public Store2TypeLastVersion findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}

	@Override
	public Store2TypeLastVersion update(Context context, Store2TypeLastVersion e) throws MercuryException {
		return getEntity(context, getService().update(context, e));
	}

	@Override
	public EntityList<Store2TypeLastVersion, Store2TypePK> updateList(Context context, List<Store2TypeLastVersion> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService().updateBag(context, eBag));
	}

	@Override
	public List<Store2TypeLastVersion> findByIdList(Context context, List<Store2TypePK> idList)
			throws MercuryException {
		return (List<Store2TypeLastVersion>) getEntityCollection(context,
				getService().findByKeyBag(context, (Collection<Store2TypePK>) idList));
	}

	@Override
	public List<Store2TypeLastVersion> filter(Context context, Store2TypeLastVersion e) throws MercuryException {
		return (List<Store2TypeLastVersion>) getEntityCollection(context, getService().filter(context, e));
	}

	@Override
	public List<Store2TypeLastVersion> findByTypeName(Context context, String typeName) throws MercuryException {
		return (List<Store2TypeLastVersion>) getEntityCollection(context,
				getService().findByTypeName(context, typeName));
	}

	@Override
	public List<Store2TypeLastVersion> findByTypeCode(Context context, String typeCode) throws MercuryException {
		return (List<Store2TypeLastVersion>) getEntityCollection(context,
				getService().findByTypeCode(context, typeCode));
	}

	@Override
	public List<Store2TypeLastVersion> findAll(Context context) throws MercuryException {
		return (List<Store2TypeLastVersion>) getEntityCollection(context, getService().findAll(context));
	}

}
