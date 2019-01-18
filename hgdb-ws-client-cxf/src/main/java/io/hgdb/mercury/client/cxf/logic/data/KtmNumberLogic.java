package io.hgdb.mercury.client.cxf.logic.data;

import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientBigDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.data.KtmNumber;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.IKtmNumberLogic;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.data.IKtmNumberAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;

/**
 * @author Karol Kowalczyk
 * 
 */
public class KtmNumberLogic extends WsClientBigDataLogic<KtmNumber, Long, IKtmNumberAction> implements IKtmNumberLogic {

	private static final long serialVersionUID = -4035755833649478841L;

	@Override
	public KtmNumber insert(Context context, final KtmNumber e) throws MercuryException {
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public EntityList<KtmNumber, Long> insertList(Context context, List<KtmNumber> eBag) throws MercuryException {
		return getEntityCollection(context, getService().insertBag(context, eBag));
	}

	@Override
	public Long remove(Context context, final KtmNumber e) throws MercuryException {
		return getId(getService().remove(context, e), e);
	}

	@Override
	public List<Long> removeList(Context context, final List<KtmNumber> eBag) throws MercuryException {
		return getIds(getService().removeBag(context, eBag), eBag);
	}

	@Override
	public KtmNumber find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService().findByKey(context, pk));
	}

	@Override
	public KtmNumber findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}

	@Override
	public KtmNumber update(Context context, KtmNumber e) throws MercuryException {
		return getEntity(context, getService().update(context, e));
	}

	@Override
	public EntityList<KtmNumber, Long> updateList(Context context, List<KtmNumber> eBag) throws MercuryException {
		return getEntityCollection(context, getService().updateBag(context, eBag));
	}

	@Override
	public List<KtmNumber> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return (List<KtmNumber>) getEntityCollection(context, getService().findByKeyBag(context, idList));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<KtmNumber, IPage> filterPaged(Context context, KtmNumber e, IPage page)
			throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService().filterPaged(context, e, (PageTransportable) page));
	}

	@Override
	public List<KtmNumber> findAll(Context context) throws MercuryException {
		return (List<KtmNumber>) getEntityCollection(context, getService().findAll(context));
	}

}
