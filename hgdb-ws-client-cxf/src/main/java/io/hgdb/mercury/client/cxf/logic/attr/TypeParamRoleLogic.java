package io.hgdb.mercury.client.cxf.logic.attr;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.hgdb.mercury.client.cxf.logic.WsClientDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.attr.TypeParamRole;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.attr.ITypeParamRoleLogic;
import pro.ibpm.mercury.ws.server.api.actions.attr.ITypeParamRoleAction;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithStringBag;

/**
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * 
 */
public class TypeParamRoleLogic extends WsClientDataLogic<TypeParamRole, Long, ITypeParamRoleAction>
		implements ITypeParamRoleLogic {

	private static final long serialVersionUID = 7670904723400444287L;

	@Override
	public TypeParamRole insert(Context context, final TypeParamRole e) throws MercuryException {
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public EntityList<TypeParamRole, Long> insertList(Context context, List<TypeParamRole> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService(context).insertBag(context, (Collection<TypeParamRole>) eBag));
	}

	@Override
	public Long remove(Context context, final TypeParamRole e) throws MercuryException {
		return getId(getService(context).remove(context, e), e);
	}

	@Override
	public List<Long> removeList(Context context, final List<TypeParamRole> eBag) throws MercuryException {
		return getIds(getService(context).removeBag(context, (Collection<TypeParamRole>) eBag), eBag);
	}

	@Override
	public List<TypeParamRole> findAll(Context context) throws MercuryException {
		return (List<TypeParamRole>) getEntityCollection(context, getService(context).findAll(context));
	}

	@Override
	public TypeParamRole find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public TypeParamRole findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

	@Override
	public TypeParamRole update(Context context, TypeParamRole e) throws MercuryException {
		return getEntity(context, getService(context).update(context, e));
	}

	@Override
	public EntityList<TypeParamRole, Long> updateList(Context context, List<TypeParamRole> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService(context).updateBag(context, (Collection<TypeParamRole>) eBag));
	}

	@Override
	public List<TypeParamRole> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return (List<TypeParamRole>) getEntityCollection(context,
				getService(context).findByKeyBag(context, (Collection<Long>) idList));
	}

	@Override
	public List<TypeParamRole> filter(Context context, TypeParamRole e) throws MercuryException {
		return (List<TypeParamRole>) getEntityCollection(context, getService(context).filter(context, e));
	}

	@Override
	public List<TypeParamRole> findAllByRoleName(Context paramContext, String paramString) throws MercuryException {
		return (List<TypeParamRole>) getEntityCollection(paramContext,
				getService(paramContext).findAllByRoleName(paramContext, paramString));
	}

	@Override
	public List<TypeParamRole> findAllByTypeCode(Context paramContext, String paramString) throws MercuryException {
		return (List<TypeParamRole>) getEntityCollection(paramContext,
				getService(paramContext).findAllByTypeCode(paramContext, paramString));
	}

	@Override
	public List<TypeParamRole> findAllForRoleNames(Context paramContext, Collection<String> paramCollection)
			throws MercuryException {
		return (List<TypeParamRole>) getEntityCollection(paramContext,
				getService(paramContext).findAllForRoleNames(paramContext, paramCollection));
	}

	@Override
	public List<TypeParamRole> findAllForRoleNamesByTypeCode(Context paramContext, Collection<String> paramCollection,
			String paramString) throws MercuryException {
		return (List<TypeParamRole>) getEntityCollection(paramContext,
				getService(paramContext).findAllForRoleNamesByTypeCode(paramContext, paramCollection, paramString));
	}

	@Override
	public Set<String> loadReadableParams(Context paramContext, String paramString) throws MercuryException {
		WsStatusWithStringBag strinBag = getService(paramContext).loadReadableParams(paramContext, paramString);
		return new HashSet<>(strinBag.getBag());
	}

	@Override
	public Set<String> loadWritableParams(Context paramContext, String paramString) throws MercuryException {
		WsStatusWithStringBag strinBag = getService(paramContext).loadWritableParams(paramContext, paramString);
		return new HashSet<>(strinBag.getBag());
	}

}
