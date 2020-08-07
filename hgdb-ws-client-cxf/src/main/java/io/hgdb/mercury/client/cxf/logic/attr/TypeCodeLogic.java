/**
 * 
 */
package io.hgdb.mercury.client.cxf.logic.attr;

import java.util.Collection;
import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.attr.TypeCode;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.attr.ITypeCodeLogic;
import pro.ibpm.mercury.ws.server.api.actions.attr.ITypeCodeAction;

/**
 * @author Karol Kowalczyk
 * 
 */
public class TypeCodeLogic extends WsClientDataLogic<TypeCode, String, ITypeCodeAction> implements ITypeCodeLogic {

	private static final long serialVersionUID = -8623929945301562872L;

	@Override
	public TypeCode insert(Context context, final TypeCode e) throws MercuryException {
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public EntityList<TypeCode, String> insertList(Context context, List<TypeCode> eBag) throws MercuryException {
		return getEntityCollection(context, getService(context).insertBag(context, (Collection<TypeCode>) eBag));
	}

	@Override
	public String remove(Context context, final TypeCode e) throws MercuryException {
		return getId(getService(context).remove(context, e), e);
	}

	@Override
	public List<String> removeList(Context context, final List<TypeCode> eBag) throws MercuryException {
		return getIds(getService(context).removeBag(context, (Collection<TypeCode>) eBag), eBag);
	}

	@Override
	public List<TypeCode> findAll(Context context) throws MercuryException {
		return (List<TypeCode>) getEntityCollection(context, getService(context).findAll(context));
	}

	@Override
	public TypeCode find(Context context, final String pk) throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public TypeCode findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

	@Override
	public TypeCode update(Context context, TypeCode e) throws MercuryException {
		return getEntity(context, getService(context).update(context, e));
	}

	@Override
	public EntityList<TypeCode, String> updateList(Context context, List<TypeCode> eBag) throws MercuryException {
		return getEntityCollection(context, getService(context).updateBag(context, (Collection<TypeCode>) eBag));
	}

	@Override
	public List<TypeCode> findByIdList(Context context, List<String> idList) throws MercuryException {
		return (List<TypeCode>) getEntityCollection(context,
				getService(context).findByKeyBag(context, (Collection<String>) idList));
	}

	@Override
	public List<TypeCode> filter(Context context, TypeCode e) throws MercuryException {
		return (List<TypeCode>) getEntityCollection(context, getService(context).filter(context, e));
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<TypeCode> findByKindCode(Context context, String kindCode) throws MercuryException {
		return (List<TypeCode>) getEntityCollection(context, getService(context).findByKindCode(context, kindCode));
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<TypeCode> findAllCaseTypeCodes(Context context) throws MercuryException {
		return (List<TypeCode>) getEntityCollection(context, getService(context).findAllCaseTypeCodes(context));
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<TypeCode> findAllDocumentTypeCodes(Context context) throws MercuryException {
		return (List<TypeCode>) getEntityCollection(context, getService(context).findAllDocumentTypeCodes(context));
	}
}
