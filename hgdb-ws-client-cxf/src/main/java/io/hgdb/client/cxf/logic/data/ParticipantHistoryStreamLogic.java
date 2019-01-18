package io.hgdb.client.cxf.logic.data;

import java.util.List;

import io.hgdb.client.cxf.logic.WsClientDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.data.ParticipantHistoryStream;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.IParticipantHistoryStreamLogic;
import pro.ibpm.mercury.ws.server.api.actions.data.IParticipantHistoryStreamAction;

/**
 * @author Karol Kowalczyk
 * 
 */
public class ParticipantHistoryStreamLogic
		extends WsClientDataLogic<ParticipantHistoryStream, Long, IParticipantHistoryStreamAction>
		implements IParticipantHistoryStreamLogic {

	private static final long serialVersionUID = -2214001980295072214L;

	@Override
	public ParticipantHistoryStream insert(Context context, final ParticipantHistoryStream e) throws MercuryException {
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public EntityList<ParticipantHistoryStream, Long> insertList(Context context, List<ParticipantHistoryStream> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService().insertBag(context, eBag));
	}

	@Override
	public Long remove(Context context, final ParticipantHistoryStream e) throws MercuryException {
		return getId(getService().remove(context, e), e);
	}

	@Override
	public List<Long> removeList(Context context, final List<ParticipantHistoryStream> eBag) throws MercuryException {
		return getIds(getService().removeBag(context, eBag), eBag);
	}

	@Override
	public ParticipantHistoryStream find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService().findByKey(context, pk));
	}

	@Override
	public ParticipantHistoryStream findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}

	@Override
	public ParticipantHistoryStream update(Context context, ParticipantHistoryStream e) throws MercuryException {
		return getEntity(context, getService().update(context, e));
	}

	@Override
	public EntityList<ParticipantHistoryStream, Long> updateList(Context context, List<ParticipantHistoryStream> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService().updateBag(context, eBag));
	}

	@Override
	public List<ParticipantHistoryStream> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return getEntityCollection(context, getService().findByKeyBag(context, idList));
	}

	@Override
	public List<ParticipantHistoryStream> filter(Context context, ParticipantHistoryStream e) throws MercuryException {
		return getEntityCollection(context, getService().filter(context, e));
	}

	@Override
	public List<ParticipantHistoryStream> findAll(Context context) throws MercuryException {
		return getEntityCollection(context, getService().findAll(context));
	}
}
