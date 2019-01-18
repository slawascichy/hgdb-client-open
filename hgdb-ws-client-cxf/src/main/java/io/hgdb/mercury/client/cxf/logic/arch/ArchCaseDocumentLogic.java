/**
 * 
 */
package io.hgdb.mercury.client.cxf.logic.arch;

import java.util.Collection;
import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.arch.ArchCaseDocument;
import pro.ibpm.mercury.entities.arch.ArchCaseDocumentPK;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.arch.IArchCaseDocumentLogic;
import pro.ibpm.mercury.ws.server.api.actions.arch.IArchCaseDocumentAction;

/**
 * @author Karol Kowalczyk
 * 
 */
public class ArchCaseDocumentLogic
		extends
		WsClientDataLogic<ArchCaseDocument, ArchCaseDocumentPK, IArchCaseDocumentAction>
		implements IArchCaseDocumentLogic {

	private static final long serialVersionUID = 5285826916871784705L;

	@Override
	public ArchCaseDocument insert(Context context, final ArchCaseDocument e)
			throws MercuryException {
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public EntityList<ArchCaseDocument, ArchCaseDocumentPK> insertList(
			Context context, List<ArchCaseDocument> eBag)
			throws MercuryException {
		return getEntityCollection(context,
				getService().insertBag(context, eBag));
	}

	@Override
	public ArchCaseDocumentPK remove(Context context, final ArchCaseDocument e)
			throws MercuryException {
		return getId(getService().remove(context, e), e);
	}

	@Override
	public List<ArchCaseDocumentPK> removeList(Context context,
			final List<ArchCaseDocument> eBag) throws MercuryException {
		return getIds(getService().removeBag(context, eBag), eBag);
	}

	@Override
	public ArchCaseDocument find(Context context, final ArchCaseDocumentPK pk)
			throws MercuryException {
		return getEntity(context, getService().findByKey(context, pk));
	}

	@Override
	public ArchCaseDocument findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}

	@Override
	public ArchCaseDocument update(Context context, ArchCaseDocument e)
			throws MercuryException {
		return getEntity(context, getService().update(context, e));
	}

	@Override
	public EntityList<ArchCaseDocument, ArchCaseDocumentPK> updateList(
			Context context, List<ArchCaseDocument> eBag)
			throws MercuryException {
		return getEntityCollection(context,
				getService().updateBag(context, eBag));
	}

	@Override
	public List<ArchCaseDocument> filter(Context context, ArchCaseDocument e)
			throws MercuryException {
		return (List<ArchCaseDocument>) getEntityCollection(context,
				getService().filter(context, e));
	}

	@Override
	public List<ArchCaseDocument> findByIdList(Context context,
			List<ArchCaseDocumentPK> idList) throws MercuryException {
		return (List<ArchCaseDocument>) getEntityCollection(
				context,
				getService().findByKeyBag(context,
						(Collection<ArchCaseDocumentPK>) idList));
	}

	@Override
	public List<ArchCaseDocument> findAll(Context context)
			throws MercuryException {
		return (List<ArchCaseDocument>) getEntityCollection(context,
				getService().findAll(context));
	}

}
