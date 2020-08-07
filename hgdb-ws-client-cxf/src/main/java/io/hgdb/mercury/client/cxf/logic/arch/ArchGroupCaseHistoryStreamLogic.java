/**
 * 
 */
package io.hgdb.mercury.client.cxf.logic.arch;

import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.arch.ArchGroupCaseHistoryStream;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.arch.IArchGroupCaseHistoryStreamLogic;
import pro.ibpm.mercury.ws.server.api.actions.arch.IArchGroupCaseHistoryStreamAction;

/**
 * @author Karol Kowalczyk
 * 
 */
public class ArchGroupCaseHistoryStreamLogic
		extends WsClientDataLogic<ArchGroupCaseHistoryStream, Long, IArchGroupCaseHistoryStreamAction>
		implements IArchGroupCaseHistoryStreamLogic {

	private static final long serialVersionUID = 1543636265317372386L;

	@Override
	public ArchGroupCaseHistoryStream insert(Context context, final ArchGroupCaseHistoryStream e)
			throws MercuryException {
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public EntityList<ArchGroupCaseHistoryStream, Long> insertList(Context context,
			List<ArchGroupCaseHistoryStream> eBag) throws MercuryException {
		return getEntityCollection(context, getService(context).insertBag(context, eBag));
	}

	@Override
	public Long remove(Context context, final ArchGroupCaseHistoryStream e) throws MercuryException {
		return getId(getService(context).remove(context, e), e);
	}

	@Override
	public List<Long> removeList(Context context, final List<ArchGroupCaseHistoryStream> eBag) throws MercuryException {
		return getIds(getService(context).removeBag(context, eBag), eBag);
	}

	@Override
	public ArchGroupCaseHistoryStream find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public ArchGroupCaseHistoryStream findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

	@Override
	public ArchGroupCaseHistoryStream update(Context context, ArchGroupCaseHistoryStream e) throws MercuryException {
		return getEntity(context, getService(context).update(context, e));
	}

	@Override
	public EntityList<ArchGroupCaseHistoryStream, Long> updateList(Context context,
			List<ArchGroupCaseHistoryStream> eBag) throws MercuryException {
		return getEntityCollection(context, getService(context).updateBag(context, eBag));
	}

	@Override
	public List<ArchGroupCaseHistoryStream> filter(Context context, ArchGroupCaseHistoryStream e)
			throws MercuryException {
		return getEntityCollection(context, getService(context).filter(context, e));
	}

	@Override
	public List<ArchGroupCaseHistoryStream> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return getEntityCollection(context, getService(context).findByKeyBag(context, idList));
	}

	@Override
	public List<ArchGroupCaseHistoryStream> findAll(Context context) throws MercuryException {
		return getEntityCollection(context, getService(context).findAll(context));
	}

}
