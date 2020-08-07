/**
 * 
 */
package io.hgdb.mercury.client.cxf.logic.arch;

import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientBigDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.arch.ArchQuickTask;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.arch.IArchQuickTaskLogic;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.arch.IArchQuickTaskAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;

/**
 * @author Karol Kowalczyk
 * 
 */
public class ArchQuickTaskLogic extends WsClientBigDataLogic<ArchQuickTask, Long, IArchQuickTaskAction>
		implements IArchQuickTaskLogic {

	private static final long serialVersionUID = -7769448689244992071L;

	@Override
	public ArchQuickTask insert(Context context, final ArchQuickTask e) throws MercuryException {
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public EntityList<ArchQuickTask, Long> insertList(Context context, List<ArchQuickTask> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService(context).insertBag(context, eBag));
	}

	@Override
	public Long remove(Context context, final ArchQuickTask e) throws MercuryException {
		return getId(getService(context).remove(context, e), e);
	}

	@Override
	public List<Long> removeList(Context context, final List<ArchQuickTask> eBag) throws MercuryException {
		return getIds(getService(context).removeBag(context, eBag), eBag);
	}

	@Override
	public ArchQuickTask find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public ArchQuickTask findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

	@Override
	public ArchQuickTask update(Context context, ArchQuickTask e) throws MercuryException {
		return getEntity(context, getService(context).update(context, e));
	}

	@Override
	public EntityList<ArchQuickTask, Long> updateList(Context context, List<ArchQuickTask> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService(context).updateBag(context, eBag));
	}

	@Override
	public List<ArchQuickTask> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return getEntityCollection(context, getService(context).findByKeyBag(context, idList));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<ArchQuickTask, IPage> filterPaged(Context context, ArchQuickTask e, IPage page)
			throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService(context).filterPaged(context, e, (PageTransportable) page));
	}

	@Override
	public List<ArchQuickTask> findAll(Context context) throws MercuryException {
		return getEntityCollection(context, getService(context).findAll(context));
	}
}
