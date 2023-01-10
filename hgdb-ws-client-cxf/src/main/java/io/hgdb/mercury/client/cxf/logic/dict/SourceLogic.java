/**
 * 
 */
package io.hgdb.mercury.client.cxf.logic.dict;

import java.util.Collection;
import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientDictLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.dict.Source;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.dict.ISourceLogic;
import pro.ibpm.mercury.ws.server.api.actions.dict.ISourceAction;

/**
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * 
 */
public class SourceLogic extends
		WsClientDictLogic<Source, String, ISourceAction> implements
		ISourceLogic {

	private static final long serialVersionUID = -1330528543338514037L;

	@Override
	public Source insert(Context context, final Source e)
			throws MercuryException {
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public EntityList<Source, String> insertList(Context context,
			List<Source> eBag) throws MercuryException {
		return getEntityCollection(context,
				getService(context).insertBag(context, (Collection<Source>) eBag));
	}

	@Override
	public Source update(Context context, final Source e)
			throws MercuryException {
		return getEntity(context, getService(context).update(context, e));
	}

	@Override
	public EntityList<Source, String> updateList(Context context,
			final List<Source> eBag) throws MercuryException {
		return getEntityCollection(context,
				getService(context).updateBag(context, (Collection<Source>) eBag));
	}

	@Override
	public String remove(Context context, final Source e)
			throws MercuryException {
		return getId(getService(context).remove(context, e), e);
	}

	@Override
	public List<String> removeList(Context context, final List<Source> eBag)
			throws MercuryException {
		return getIds(getService(context)
				.removeBag(context, (Collection<Source>) eBag), eBag);
	}

	@Override
	public List<Source> findAll(Context context) throws MercuryException {
		return (List<Source>) getEntityCollection(context, getService(context)
				.findAll(context));
	}

	@Override
	public Source find(Context context, final String pk)
			throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public Source findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}
}
