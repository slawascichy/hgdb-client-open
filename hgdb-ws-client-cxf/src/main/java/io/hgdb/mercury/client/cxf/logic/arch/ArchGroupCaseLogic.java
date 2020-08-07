/**
 * 
 */
package io.hgdb.mercury.client.cxf.logic.arch;

import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientBigDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.arch.ArchGroupCase;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.arch.IArchGroupCaseLogic;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.arch.IArchGroupCaseAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;

/**
 * @author Karol Kowalczyk
 * 
 */
public class ArchGroupCaseLogic extends WsClientBigDataLogic<ArchGroupCase, Long, IArchGroupCaseAction>
		implements IArchGroupCaseLogic {

	private static final long serialVersionUID = -6535762794809044005L;

	@Override
	public ArchGroupCase insert(Context context, final ArchGroupCase e) throws MercuryException {
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public EntityList<ArchGroupCase, Long> insertList(Context context, List<ArchGroupCase> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService(context).insertBag(context, eBag));
	}

	@Override
	public Long remove(Context context, final ArchGroupCase e) throws MercuryException {
		return getId(getService(context).remove(context, e), e);
	}

	@Override
	public List<Long> removeList(Context context, final List<ArchGroupCase> eBag) throws MercuryException {
		return getIds(getService(context).removeBag(context, eBag), eBag);
	}

	@Override
	public ArchGroupCase find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public ArchGroupCase findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

	@Override
	public ArchGroupCase update(Context context, ArchGroupCase e) throws MercuryException {
		return getEntity(context, getService(context).update(context, e));
	}

	@Override
	public EntityList<ArchGroupCase, Long> updateList(Context context, List<ArchGroupCase> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService(context).updateBag(context, eBag));
	}

	@Override
	public List<ArchGroupCase> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return getEntityCollection(context, getService(context).findByKeyBag(context, idList));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<ArchGroupCase, IPage> filterPaged(Context context, ArchGroupCase e, IPage page)
			throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService(context).filterPaged(context, e, (PageTransportable) page));
	}

	@Override
	public List<ArchGroupCase> findAll(Context context) throws MercuryException {
		return getEntityCollection(context, getService(context).findAll(context));
	}

}
