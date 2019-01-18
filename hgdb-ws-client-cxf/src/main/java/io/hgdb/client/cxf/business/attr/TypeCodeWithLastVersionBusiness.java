/**
 * 
 */
package io.hgdb.client.cxf.business.attr;

import java.util.Collection;
import java.util.List;

import io.hgdb.client.cxf.logic.WsClientDataLogic;
import pro.ibpm.mercury.business.attr.api.ITypeCodeWithLastVersionBusiness;
import pro.ibpm.mercury.business.entities.TypeCodeWithLastVersion;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.ws.server.api.actions.business.attr.ITypeCodeWithLastVersionBusinessAction;

/**
 * 
 * TypeCodeLogic
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
public class TypeCodeWithLastVersionBusiness
		extends WsClientDataLogic<TypeCodeWithLastVersion, String, ITypeCodeWithLastVersionBusinessAction>
		implements ITypeCodeWithLastVersionBusiness {

	private static final long serialVersionUID = -8623929945302322372L;

	@Override
	public TypeCodeWithLastVersion insert(Context context, final TypeCodeWithLastVersion e) throws MercuryException {
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public List<TypeCodeWithLastVersion> insertList(Context context, List<TypeCodeWithLastVersion> eBag)
			throws MercuryException {
		return getEntityCollection(context,
				getService().insertBag(context, (Collection<TypeCodeWithLastVersion>) eBag));
	}

	@Override
	public String remove(Context context, final TypeCodeWithLastVersion e) throws MercuryException {
		return getId(getService().remove(context, e), e);
	}

	@Override
	public List<String> removeList(Context context, final List<TypeCodeWithLastVersion> eBag) throws MercuryException {
		return getIds(getService().removeBag(context, (Collection<TypeCodeWithLastVersion>) eBag), eBag);
	}

	@Override
	public List<TypeCodeWithLastVersion> findAll(Context context) throws MercuryException {
		return (List<TypeCodeWithLastVersion>) getEntityCollection(context, getService().findAll(context));
	}

	@Override
	public TypeCodeWithLastVersion find(Context context, final String pk) throws MercuryException {
		return getEntity(context, getService().findByKey(context, pk));
	}

	@Override
	public TypeCodeWithLastVersion findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}

	@Override
	public TypeCodeWithLastVersion update(Context context, TypeCodeWithLastVersion e) throws MercuryException {
		return getEntity(context, getService().update(context, e));
	}

	@Override
	public List<TypeCodeWithLastVersion> updateList(Context context, List<TypeCodeWithLastVersion> eBag)
			throws MercuryException {
		return getEntityCollection(context,
				getService().updateBag(context, (Collection<TypeCodeWithLastVersion>) eBag));
	}

	@Override
	public List<TypeCodeWithLastVersion> findByIdList(Context context, List<String> idList) throws MercuryException {
		return (List<TypeCodeWithLastVersion>) getEntityCollection(context,
				getService().findByKeyBag(context, (Collection<String>) idList));
	}

	@Override
	public List<TypeCodeWithLastVersion> filter(Context context, TypeCodeWithLastVersion e) throws MercuryException {
		return (List<TypeCodeWithLastVersion>) getEntityCollection(context, getService().filter(context, e));
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<TypeCodeWithLastVersion> findByKindCode(Context context, String kindCode) throws MercuryException {
		return getEntityCollection(context, getService().findByKindCode(context, kindCode));
	}
}
