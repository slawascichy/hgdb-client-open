/**
 * 
 */
package io.hgdb.mercury.client.cxf.logic.dict;

import java.util.Collection;
import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientDictLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.dict.InitStatus;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.dict.IInitStatusLogic;
import pro.ibpm.mercury.ws.server.api.actions.dict.IInitStatusAction;

/**
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * 
 */
public class InitStatusLogic extends
		WsClientDictLogic<InitStatus, String, IInitStatusAction> implements
		IInitStatusLogic {

	private static final long serialVersionUID = 4878390674305410914L;

	@Override
	public InitStatus insert(Context context, final InitStatus e)
			throws MercuryException {
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public EntityList<InitStatus, String> insertList(Context context,
			List<InitStatus> eBag) throws MercuryException {
		return getEntityCollection(context,
				getService(context).insertBag(context, (Collection<InitStatus>) eBag));
	}

	@Override
	public InitStatus update(Context context, final InitStatus e)
			throws MercuryException {
		return getEntity(context, getService(context).update(context, e));
	}

	@Override
	public EntityList<InitStatus, String> updateList(Context context,
			final List<InitStatus> eBag) throws MercuryException {
		return getEntityCollection(context,
				getService(context).updateBag(context, (Collection<InitStatus>) eBag));
	}

	@Override
	public String remove(Context context, final InitStatus e)
			throws MercuryException {
		return getId(getService(context).remove(context, e), e);
	}

	@Override
	public List<String> removeList(Context context, final List<InitStatus> eBag)
			throws MercuryException {
		return getIds(
				getService(context).removeBag(context, (Collection<InitStatus>) eBag),
				eBag);
	}

	@Override
	public List<InitStatus> findAll(Context context) throws MercuryException {
		return (List<InitStatus>) getEntityCollection(context, getService(context)
				.findAll(context));
	}

	@Override
	public InitStatus find(Context context, final String pk)
			throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public InitStatus findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

}
