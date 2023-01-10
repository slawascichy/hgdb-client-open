package io.hgdb.mercury.client.cxf.logic.data;

import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientCustomLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.data.ParticipantHistoryStream;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.IParticipantHistoryStreamLogic;
import pro.ibpm.mercury.ws.server.api.actions.data.IParticipantHistoryStreamAction;

/**
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * 
 */
public class ParticipantHistoryStreamLogic
		extends WsClientCustomLogic<ParticipantHistoryStream, Long, IParticipantHistoryStreamAction>
		implements IParticipantHistoryStreamLogic {

	private static final long serialVersionUID = -2214001980295072214L;

	@Override
	public ParticipantHistoryStream insert(Context context, final ParticipantHistoryStream e) throws MercuryException {
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public EntityList<ParticipantHistoryStream, Long> insertList(Context context, List<ParticipantHistoryStream> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService(context).insertBag(context, eBag));
	}

	@Override
	public ParticipantHistoryStream find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public ParticipantHistoryStream findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

	@Override
	public List<ParticipantHistoryStream> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return getEntityCollection(context, getService(context).findByKeyBag(context, idList));
	}

	@Override
	public List<ParticipantHistoryStream> filter(Context context, ParticipantHistoryStream e) throws MercuryException {
		return getEntityCollection(context, getService(context).filter(context, e));
	}

}
