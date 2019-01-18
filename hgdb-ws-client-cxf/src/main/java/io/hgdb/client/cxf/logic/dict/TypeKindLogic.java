/**
 * 
 */
package io.hgdb.client.cxf.logic.dict;

import java.util.Collection;
import java.util.List;

import io.hgdb.client.cxf.logic.WsClientDictLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.dict.TypeKind;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.dict.ITypeKindLogic;
import pro.ibpm.mercury.ws.server.api.actions.dict.ITypeKindAction;

/**
 * @author Karol Kowalczyk
 * 
 */
public class TypeKindLogic extends
		WsClientDictLogic<TypeKind, String, ITypeKindAction> implements
		ITypeKindLogic {

	private static final long serialVersionUID = -5544574364626843561L;

	@Override
	public TypeKind insert(Context context, final TypeKind e)
			throws MercuryException {
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public EntityList<TypeKind, String> insertList(Context context,
			List<TypeKind> eBag) throws MercuryException {
		return getEntityCollection(context,
				getService().insertBag(context, (Collection<TypeKind>) eBag));
	}

	@Override
	public TypeKind update(Context context, final TypeKind e)
			throws MercuryException {
		return getEntity(context, getService().update(context, e));
	}

	@Override
	public EntityList<TypeKind, String> updateList(Context context,
			final List<TypeKind> eBag) throws MercuryException {
		return getEntityCollection(context,
				getService().updateBag(context, (Collection<TypeKind>) eBag));
	}

	@Override
	public String remove(Context context, final TypeKind e)
			throws MercuryException {
		return getId(getService().remove(context, e), e);
	}

	@Override
	public List<String> removeList(Context context, final List<TypeKind> eBag)
			throws MercuryException {
		return getIds(
				getService().removeBag(context, (Collection<TypeKind>) eBag),
				eBag);
	}

	@Override
	public List<TypeKind> findAll(Context context) throws MercuryException {
		return (List<TypeKind>) getEntityCollection(context, getService()
				.findAll(context));
	}

	@Override
	public TypeKind find(Context context, final String pk)
			throws MercuryException {
		return getEntity(context, getService().findByKey(context, pk));
	}

	@Override
	public TypeKind findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}
}
