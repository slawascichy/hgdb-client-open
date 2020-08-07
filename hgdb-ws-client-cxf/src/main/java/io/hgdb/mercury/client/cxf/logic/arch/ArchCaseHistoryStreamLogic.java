/**
 * 
 */
package io.hgdb.mercury.client.cxf.logic.arch;

import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.arch.ArchCaseHistoryStream;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.arch.IArchCaseHistoryStreamLogic;
import pro.ibpm.mercury.ws.server.api.actions.arch.IArchCaseHistoryStreamAction;

/**
 * @author Karol Kowalczyk
 * 
 */
public class ArchCaseHistoryStreamLogic
		extends WsClientDataLogic<ArchCaseHistoryStream, Long, IArchCaseHistoryStreamAction>
		implements IArchCaseHistoryStreamLogic {

	private static final long serialVersionUID = -831646756540691712L;

	@Override
	public ArchCaseHistoryStream insert(Context context, final ArchCaseHistoryStream e) throws MercuryException {
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public EntityList<ArchCaseHistoryStream, Long> insertList(Context context, List<ArchCaseHistoryStream> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService(context).insertBag(context, eBag));
	}

	@Override
	public Long remove(Context context, final ArchCaseHistoryStream e) throws MercuryException {
		return getId(getService(context).remove(context, e), e);
	}

	@Override
	public List<Long> removeList(Context context, final List<ArchCaseHistoryStream> eBag) throws MercuryException {
		return getIds(getService(context).removeBag(context, eBag), eBag);
	}

	@Override
	public ArchCaseHistoryStream find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public ArchCaseHistoryStream findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

	@Override
	public ArchCaseHistoryStream update(Context context, ArchCaseHistoryStream e) throws MercuryException {
		return getEntity(context, getService(context).update(context, e));
	}

	@Override
	public EntityList<ArchCaseHistoryStream, Long> updateList(Context context, List<ArchCaseHistoryStream> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService(context).updateBag(context, eBag));
	}

	@Override
	public List<ArchCaseHistoryStream> filter(Context context, ArchCaseHistoryStream e) throws MercuryException {
		return getEntityCollection(context, getService(context).filter(context, e));
	}

	@Override
	public List<ArchCaseHistoryStream> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return getEntityCollection(context, getService(context).findByKeyBag(context, idList));
	}

	@Override
	public List<ArchCaseHistoryStream> findAll(Context context) throws MercuryException {
		return getEntityCollection(context, getService(context).findAll(context));
	}

}
