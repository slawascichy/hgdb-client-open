package io.hgdb.client.cxf.logic.data;

import java.util.Collection;
import java.util.List;

import io.hgdb.client.cxf.logic.WsClientDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.data.Participant2TypeStats;
import pro.ibpm.mercury.entities.data.Participant2TypeStatsPK;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.IParticipant2TypeStatsLogic;
import pro.ibpm.mercury.logic.exceptions.LC025MethodNotSupportedException;
import pro.ibpm.mercury.ws.server.api.actions.data.IParticipant2TypeStatsAction;

/**
 * @author Karol Kowalczyk
 * 
 */
public class Participant2TypeStatsLogic
		extends WsClientDataLogic<Participant2TypeStats, Participant2TypeStatsPK, IParticipant2TypeStatsAction>
		implements IParticipant2TypeStatsLogic {

	private static final long serialVersionUID = -2725440101636420294L;

	@Override
	public Participant2TypeStats insert(Context context, final Participant2TypeStats e) throws MercuryException {
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public EntityList<Participant2TypeStats, Participant2TypeStatsPK> insertList(Context context,
			List<Participant2TypeStats> eBag) throws MercuryException {
		return getEntityCollection(context, getService().insertBag(context, eBag));
	}

	@Override
	public Participant2TypeStatsPK remove(Context context, final Participant2TypeStats e) throws MercuryException {
		return getId(getService().remove(context, e), e);
	}

	@Override
	public List<Participant2TypeStatsPK> removeList(Context context, final List<Participant2TypeStats> eBag)
			throws MercuryException {
		return getIds(getService().removeBag(context, eBag), eBag);
	}

	@Override
	public Participant2TypeStats find(Context context, final Participant2TypeStatsPK pk) throws MercuryException {
		return getEntity(context, getService().findByKey(context, pk));
	}

	@Override
	public Participant2TypeStats findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}

	@Override
	public Participant2TypeStats update(Context context, Participant2TypeStats e) throws MercuryException {
		return getEntity(context, getService().update(context, e));
	}

	@Override
	public EntityList<Participant2TypeStats, Participant2TypeStatsPK> updateList(Context context,
			List<Participant2TypeStats> eBag) throws MercuryException {
		return getEntityCollection(context, getService().updateBag(context, eBag));
	}

	@Override
	public List<Participant2TypeStats> findByIdList(Context context, List<Participant2TypeStatsPK> idList)
			throws MercuryException {
		return (List<Participant2TypeStats>) getEntityCollection(context,
				getService().findByKeyBag(context, (Collection<Participant2TypeStatsPK>) idList));
	}

	@Override
	public List<Participant2TypeStats> filter(Context context, Participant2TypeStats e) throws MercuryException {
		return (List<Participant2TypeStats>) getEntityCollection(context, getService().filter(context, e));
	}

	@Override
	public List<Participant2TypeStats> findAll(Context context) throws MercuryException {
		return (List<Participant2TypeStats>) getEntityCollection(context, getService().findAll(context));
	}

	@Deprecated
	@Override
	public EntityList<Participant2TypeStats, Participant2TypeStatsPK> repairStats(Context context)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public Object getLockStat(Participant2TypeStatsPK id) {
		throw new IllegalAccessError();
	}

}
