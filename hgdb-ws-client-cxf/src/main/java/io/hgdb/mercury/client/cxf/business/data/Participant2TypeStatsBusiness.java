package io.hgdb.mercury.client.cxf.business.data;

import java.util.Collection;
import java.util.List;

import io.hgdb.mercury.client.cxf.WsClientRoot;
import pro.ibpm.mercury.business.data.api.IParticipant2TypeStatsBusiness;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.DtoObject;
import pro.ibpm.mercury.dto.Participant2TypeStatsDto;
import pro.ibpm.mercury.entities.data.Participant2TypeStats;
import pro.ibpm.mercury.entities.data.Participant2TypeStatsPK;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.ws.server.api.actions.business.data.IParticipant2TypeStatsBusinessAction;
import pro.ibpm.mercury.ws.server.api.returns.WsStatus;
import pro.ibpm.mercury.ws.server.api.returns.data.WsStatusWithParticipant2TypeStatsDto;
import pro.ibpm.mercury.ws.server.api.returns.data.WsStatusWithParticipant2TypeStatsDtos;

/**
 * 
 * Participant2TypeStatsBusiness
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $ 
 *
 */
public class Participant2TypeStatsBusiness
		extends WsClientRoot<Participant2TypeStats, Participant2TypeStatsPK, IParticipant2TypeStatsBusinessAction>
		implements IParticipant2TypeStatsBusiness {

	@Override
	public void repairStats(Context context) throws MercuryException {
		WsStatus wsStatus = getService().repairStats(context);
		checkWsStatus(wsStatus);
	}

	@Override
	public void repairStatsByParticipantId(Context context, Long participantId) throws MercuryException {
		WsStatus wsStatus = getService().repairStatsByParticipantId(context, participantId);
		checkWsStatus(wsStatus);
	}

	@Override
	public Participant2TypeStats findFirst(Context context) throws MercuryException {
		WsStatusWithParticipant2TypeStatsDto result = getService().findFirst(context);
		return getEntity(context, result);
	}

	@Override
	public Participant2TypeStats find(Context context, Participant2TypeStatsPK pk) throws MercuryException {
		WsStatusWithParticipant2TypeStatsDto result = getService().findByKey(context, pk);
		return getEntity(context, result);
	}

	@Override
	public List<Participant2TypeStats> findByIdList(Context context, Collection<Participant2TypeStatsPK> pkBag)
			throws MercuryException {
		WsStatusWithParticipant2TypeStatsDtos result = getService().findByKeyBag(context, pkBag);
		return getEntityCollection(context, result);
	}

	@Override
	public List<Participant2TypeStats> filter(Context context, Participant2TypeStats e) throws MercuryException {
		WsStatusWithParticipant2TypeStatsDtos result = getService().filter(context, e);
		return getEntityCollection(context, result);
	}

	@Override
	public List<Participant2TypeStats> findByParticipantId(Context context, Long participantId)
			throws MercuryException {
		WsStatusWithParticipant2TypeStatsDtos result = getService().findByParticipantId(context, participantId);
		return getEntityCollection(context, result);
	}

	@Override
	public Class<Participant2TypeStats> getPersistentClass() {
		return Participant2TypeStats.class;
	}

	@Override
	public Class<? extends DtoObject> getPersistentClassDto() {
		return Participant2TypeStatsDto.class;
	}

}
