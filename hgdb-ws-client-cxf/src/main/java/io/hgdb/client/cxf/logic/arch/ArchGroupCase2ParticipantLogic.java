/**
 * 
 */
package io.hgdb.client.cxf.logic.arch;

import java.util.Collection;
import java.util.List;

import io.hgdb.client.cxf.logic.WsClientBigDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.arch.ArchGroupCase2Participant;
import pro.ibpm.mercury.entities.arch.ArchGroupCase2ParticipantPK;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.arch.IArchGroupCase2ParticipantLogic;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.arch.IArchGroupCase2ParticipantAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;

/**
 * @author Karol Kowalczyk
 * 
 */
public class ArchGroupCase2ParticipantLogic
		extends
		WsClientBigDataLogic<ArchGroupCase2Participant, ArchGroupCase2ParticipantPK, IArchGroupCase2ParticipantAction>
		implements IArchGroupCase2ParticipantLogic {

	private static final long serialVersionUID = 1293739526010185211L;

	@Override
	public ArchGroupCase2Participant insert(Context context,
			final ArchGroupCase2Participant e) throws MercuryException {
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public EntityList<ArchGroupCase2Participant, ArchGroupCase2ParticipantPK> insertList(
			Context context, List<ArchGroupCase2Participant> eBag)
			throws MercuryException {
		return getEntityCollection(context,
				getService().insertBag(context, eBag));
	}

	@Override
	public ArchGroupCase2ParticipantPK remove(Context context,
			final ArchGroupCase2Participant e) throws MercuryException {
		return getId(getService().remove(context, e), e);
	}

	@Override
	public List<ArchGroupCase2ParticipantPK> removeList(Context context,
			final List<ArchGroupCase2Participant> eBag) throws MercuryException {
		return getIds(getService().removeBag(context, eBag), eBag);
	}

	@Override
	public ArchGroupCase2Participant find(Context context,
			final ArchGroupCase2ParticipantPK pk) throws MercuryException {
		return getEntity(context, getService().findByKey(context, pk));
	}

	@Override
	public ArchGroupCase2Participant findFirst(Context context)
			throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}

	@Override
	public ArchGroupCase2Participant update(Context context,
			ArchGroupCase2Participant e) throws MercuryException {
		return getEntity(context, getService().update(context, e));
	}

	@Override
	public EntityList<ArchGroupCase2Participant, ArchGroupCase2ParticipantPK> updateList(
			Context context, List<ArchGroupCase2Participant> eBag)
			throws MercuryException {
		return getEntityCollection(context,
				getService().updateBag(context, eBag));
	}

	@Override
	public List<ArchGroupCase2Participant> findByIdList(Context context,
			List<ArchGroupCase2ParticipantPK> idList) throws MercuryException {
		return (List<ArchGroupCase2Participant>) getEntityCollection(
				context,
				getService().findByKeyBag(context,
						(Collection<ArchGroupCase2ParticipantPK>) idList));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<ArchGroupCase2Participant, IPage> filterPaged(
			Context context, ArchGroupCase2Participant e, IPage page)
			throws MercuryException {
		return getPagedResult(context, (IWsStatusWithPagedResult) getService()
				.filterPaged(context, e, (PageTransportable) page));
	}

	@Override
	public List<ArchGroupCase2Participant> findAll(Context context)
			throws MercuryException {
		return (List<ArchGroupCase2Participant>) getEntityCollection(context,
				getService().findAll(context));
	}

}
