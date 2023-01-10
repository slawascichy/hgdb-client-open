/**
 * 
 */
package io.hgdb.mercury.client.cxf.logic.dict;

import java.util.Collection;
import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientDictLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.dict.Role;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.dict.IRoleLogic;
import pro.ibpm.mercury.ws.server.api.actions.dict.IRoleAction;

/**
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * 
 */
public class RoleLogic extends WsClientDictLogic<Role, String, IRoleAction>
		implements IRoleLogic {

	private static final long serialVersionUID = 7307429930132676781L;

	@Override
	public Role insert(Context context, final Role e) throws MercuryException {
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public EntityList<Role, String> insertList(Context context, List<Role> eBag)
			throws MercuryException {
		return getEntityCollection(context,
				getService(context).insertBag(context, (Collection<Role>) eBag));
	}

	@Override
	public Role update(Context context, final Role e) throws MercuryException {
		return getEntity(context, getService(context).update(context, e));
	}

	@Override
	public EntityList<Role, String> updateList(Context context,
			final List<Role> eBag) throws MercuryException {
		return getEntityCollection(context,
				getService(context).updateBag(context, (Collection<Role>) eBag));
	}

	@Override
	public String remove(Context context, final Role e) throws MercuryException {
		return getId(getService(context).remove(context, e), e);
	}

	@Override
	public List<String> removeList(Context context, final List<Role> eBag)
			throws MercuryException {
		return getIds(getService(context).removeBag(context, (Collection<Role>) eBag),
				eBag);
	}

	@Override
	public List<Role> findAll(Context context) throws MercuryException {
		return (List<Role>) getEntityCollection(context,
				getService(context).findAll(context));
	}

	@Override
	public Role find(Context context, final String pk) throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public Role findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}
}
