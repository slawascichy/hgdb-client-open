package io.hgdb.mercury.client.cxf.logic.data;

import java.util.Collection;
import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientBigDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.data.GroupCase2Participant;
import pro.ibpm.mercury.entities.data.GroupCase2ParticipantPK;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.IGroupCase2ParticipantLogic;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.data.IGroupCase2ParticipantAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;

/**
 * @author Karol Kowalczyk
 * 
 */
public class GroupCase2ParticipantLogic
		extends
		WsClientBigDataLogic<GroupCase2Participant, GroupCase2ParticipantPK, IGroupCase2ParticipantAction>
		implements IGroupCase2ParticipantLogic {

	private static final long serialVersionUID = 7501979800061674173L;

	@Override
	public GroupCase2Participant insert(Context context,
			final GroupCase2Participant e) throws MercuryException {
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public EntityList<GroupCase2Participant, GroupCase2ParticipantPK> insertList(
			Context context, List<GroupCase2Participant> eBag)
			throws MercuryException {
		return getEntityCollection(context,
				getService(context).insertBag(context, eBag));
	}

	@Override
	public GroupCase2ParticipantPK remove(Context context,
			final GroupCase2Participant e) throws MercuryException {
		return getId(getService(context).remove(context, e), e);
	}

	@Override
	public List<GroupCase2ParticipantPK> removeList(Context context,
			final List<GroupCase2Participant> eBag) throws MercuryException {
		return getIds(getService(context).removeBag(context, eBag), eBag);
	}

	@Override
	public GroupCase2Participant find(Context context,
			final GroupCase2ParticipantPK pk) throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public GroupCase2Participant findFirst(Context context)
			throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

	@Override
	public GroupCase2Participant update(Context context, GroupCase2Participant e)
			throws MercuryException {
		return getEntity(context, getService(context).update(context, e));
	}

	@Override
	public EntityList<GroupCase2Participant, GroupCase2ParticipantPK> updateList(
			Context context, List<GroupCase2Participant> eBag)
			throws MercuryException {
		return getEntityCollection(context,
				getService(context).updateBag(context, eBag));
	}

	@Override
	public List<GroupCase2Participant> findByIdList(Context context,
			List<GroupCase2ParticipantPK> idList) throws MercuryException {
		return (List<GroupCase2Participant>) getEntityCollection(
				context,
				getService(context).findByKeyBag(context,
						(Collection<GroupCase2ParticipantPK>) idList));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<GroupCase2Participant, IPage> filterPaged(Context context,
			GroupCase2Participant e, IPage page) throws MercuryException {
		return getPagedResult(context, (IWsStatusWithPagedResult) getService(context)
				.filterPaged(context, e, (PageTransportable) page));
	}

	@Override
	public List<GroupCase2Participant> findAll(Context context)
			throws MercuryException {
		return (List<GroupCase2Participant>) getEntityCollection(context,
				getService(context).findAll(context));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<GroupCase2Participant, IPage> findByGroupId(Context context, Long groupId, IPage page)
			throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService(context).findByGroupId(context, groupId, (PageTransportable) page));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<GroupCase2Participant, IPage> findByParticipantId(Context context, Long participantId,
			IPage page) throws MercuryException {
		return getPagedResult(context, (IWsStatusWithPagedResult) getService(context).findByParticipantId(context,
				participantId, (PageTransportable) page));
	}

	
}
