package io.hgdb.mercury.client.cxf.logic.attr;

import java.util.Collection;
import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.attr.TypeParam;
import pro.ibpm.mercury.entities.attr.TypeParamPK;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.attr.ITypeParamLogic;
import pro.ibpm.mercury.ws.server.api.actions.attr.ITypeParamAction;

/**
 * @author Karol Kowalczyk
 * 
 */
public class TypeParamLogic extends
		WsClientDataLogic<TypeParam, TypeParamPK, ITypeParamAction> implements
		ITypeParamLogic {

	private static final long serialVersionUID = 7440999069342866029L;

	@Override
	public TypeParam insert(Context context, final TypeParam e)
			throws MercuryException {
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public EntityList<TypeParam, TypeParamPK> insertList(Context context,
			List<TypeParam> eBag) throws MercuryException {
		return getEntityCollection(context,
				getService(context).insertBag(context, (Collection<TypeParam>) eBag));
	}

	@Override
	public TypeParamPK remove(Context context, final TypeParam e)
			throws MercuryException {
		return getId(getService(context).remove(context, e), e);
	}

	@Override
	public List<TypeParamPK> removeList(Context context,
			final List<TypeParam> eBag) throws MercuryException {
		return getIds(
				getService(context).removeBag(context, (Collection<TypeParam>) eBag),
				eBag);
	}

	@Override
	public List<TypeParam> findAll(Context context) throws MercuryException {
		return (List<TypeParam>) getEntityCollection(context, getService(context)
				.findAll(context));
	}

	@Override
	public TypeParam find(Context context, final TypeParamPK pk)
			throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public TypeParam findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

	@Override
	public TypeParam update(Context context, TypeParam e)
			throws MercuryException {
		return getEntity(context, getService(context).update(context, e));
	}

	@Override
	public EntityList<TypeParam, TypeParamPK> updateList(Context context,
			List<TypeParam> eBag) throws MercuryException {
		return getEntityCollection(context,
				getService(context).updateBag(context, (Collection<TypeParam>) eBag));
	}

	@Override
	public List<TypeParam> findByTypeCaseId(Context context, Long typeCaseId)
			throws MercuryException {
		return (List<TypeParam>) getEntityCollection(context, getService(context)
				.findByTypeCase(context, typeCaseId));
	}

	@Override
	public List<TypeParam> findByIdList(Context context,
			List<TypeParamPK> idList) throws MercuryException {
		return (List<TypeParam>) getEntityCollection(context, getService(context)
				.findByKeyBag(context, (Collection<TypeParamPK>) idList));
	}

	@Override
	public List<TypeParam> filter(Context context, TypeParam e)
			throws MercuryException {
		return (List<TypeParam>) getEntityCollection(context, getService(context)
				.filter(context, e));
	}
}
