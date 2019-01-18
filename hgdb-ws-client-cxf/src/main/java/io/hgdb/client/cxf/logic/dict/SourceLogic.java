/**
 * 
 */
package io.hgdb.client.cxf.logic.dict;

import java.util.Collection;
import java.util.List;

import io.hgdb.client.cxf.logic.WsClientDictLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.dict.Source;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.dict.ISourceLogic;
import pro.ibpm.mercury.ws.server.api.actions.dict.ISourceAction;

/**
 * @author Karol Kowalczyk
 * 
 */
public class SourceLogic extends
		WsClientDictLogic<Source, String, ISourceAction> implements
		ISourceLogic {

	private static final long serialVersionUID = -1330528543338514037L;

	@Override
	public Source insert(Context context, final Source e)
			throws MercuryException {
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public EntityList<Source, String> insertList(Context context,
			List<Source> eBag) throws MercuryException {
		return getEntityCollection(context,
				getService().insertBag(context, (Collection<Source>) eBag));
	}

	@Override
	public Source update(Context context, final Source e)
			throws MercuryException {
		return getEntity(context, getService().update(context, e));
	}

	@Override
	public EntityList<Source, String> updateList(Context context,
			final List<Source> eBag) throws MercuryException {
		return getEntityCollection(context,
				getService().updateBag(context, (Collection<Source>) eBag));
	}

	@Override
	public String remove(Context context, final Source e)
			throws MercuryException {
		return getId(getService().remove(context, e), e);
	}

	@Override
	public List<String> removeList(Context context, final List<Source> eBag)
			throws MercuryException {
		return getIds(getService()
				.removeBag(context, (Collection<Source>) eBag), eBag);
	}

	@Override
	public List<Source> findAll(Context context) throws MercuryException {
		return (List<Source>) getEntityCollection(context, getService()
				.findAll(context));
	}

	@Override
	public Source find(Context context, final String pk)
			throws MercuryException {
		return getEntity(context, getService().findByKey(context, pk));
	}

	@Override
	public Source findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}
}
