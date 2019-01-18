/**
 * 
 */
package io.hgdb.mercury.client.cxf.logic.arch;

import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientBigDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.arch.ArchKtmNumber;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.arch.IArchKtmNumberLogic;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.arch.IArchKtmNumberAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;

/**
 * @author Karol Kowalczyk
 * 
 */
public class ArchKtmNumberLogic extends WsClientBigDataLogic<ArchKtmNumber, Long, IArchKtmNumberAction>
		implements IArchKtmNumberLogic {

	private static final long serialVersionUID = -1229618070885226823L;

	@Override
	public ArchKtmNumber insert(Context context, final ArchKtmNumber e) throws MercuryException {
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public EntityList<ArchKtmNumber, Long> insertList(Context context, List<ArchKtmNumber> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService().insertBag(context, eBag));
	}

	@Override
	public Long remove(Context context, final ArchKtmNumber e) throws MercuryException {
		return getId(getService().remove(context, e), e);
	}

	@Override
	public List<Long> removeList(Context context, final List<ArchKtmNumber> eBag) throws MercuryException {
		return getIds(getService().removeBag(context, eBag), eBag);
	}

	@Override
	public ArchKtmNumber find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService().findByKey(context, pk));
	}

	@Override
	public ArchKtmNumber findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}

	@Override
	public ArchKtmNumber update(Context context, ArchKtmNumber e) throws MercuryException {
		return getEntity(context, getService().update(context, e));
	}

	@Override
	public EntityList<ArchKtmNumber, Long> updateList(Context context, List<ArchKtmNumber> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService().updateBag(context, eBag));
	}

	@Override
	public List<ArchKtmNumber> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return (List<ArchKtmNumber>) getEntityCollection(context, getService().findByKeyBag(context, idList));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<ArchKtmNumber, IPage> filterPaged(Context context, ArchKtmNumber e, IPage page)
			throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService().filterPaged(context, e, (PageTransportable) page));
	}

	@Override
	public List<ArchKtmNumber> findAll(Context context) throws MercuryException {
		return (List<ArchKtmNumber>) getEntityCollection(context, getService().findAll(context));
	}

}
