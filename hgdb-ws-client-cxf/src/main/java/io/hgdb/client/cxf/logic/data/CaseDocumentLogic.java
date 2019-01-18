/**
 * 
 */
package io.hgdb.client.cxf.logic.data;

import java.util.Collection;
import java.util.List;

import io.hgdb.client.cxf.logic.WsClientBigDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.data.CaseDocument;
import pro.ibpm.mercury.entities.data.CaseDocumentPK;
import pro.ibpm.mercury.exceptions.InternalErrorException;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.ICaseDocumentLogic;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.data.ICaseDocumentAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;

/**
 * 
 * CaseDocumentLogic
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $ 
 *
 */
public class CaseDocumentLogic extends WsClientBigDataLogic<CaseDocument, CaseDocumentPK, ICaseDocumentAction>
		implements ICaseDocumentLogic {

	private static final long serialVersionUID = -8115269135500169912L;

	@Override
	public CaseDocument insert(Context context, final CaseDocument e) throws MercuryException {
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public EntityList<CaseDocument, CaseDocumentPK> insertList(Context context, List<CaseDocument> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService().insertBag(context, eBag));
	}

	@Override
	public CaseDocumentPK remove(Context context, final CaseDocument e) throws MercuryException {
		return getId(getService().remove(context, e), e);
	}

	@Override
	public List<CaseDocumentPK> removeList(Context context, final List<CaseDocument> eBag) throws MercuryException {
		return getIds(getService().removeBag(context, eBag), eBag);
	}

	@Override
	public CaseDocument find(Context context, final CaseDocumentPK pk) throws MercuryException {
		return getEntity(context, getService().findByKey(context, pk));
	}

	@Override
	public CaseDocument findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}

	@Override
	public CaseDocument update(Context context, CaseDocument e) throws MercuryException {
		return getEntity(context, getService().update(context, e));
	}

	@Override
	public EntityList<CaseDocument, CaseDocumentPK> updateList(Context context, List<CaseDocument> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService().updateBag(context, eBag));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<CaseDocument, IPage> filterPaged(Context context, CaseDocument e, IPage page)
			throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService().filterPaged(context, e, (PageTransportable) page));
	}

	@Override
	public List<CaseDocument> findByIdList(Context context, List<CaseDocumentPK> idList) throws MercuryException {
		return getEntityCollection(context, getService().findByKeyBag(context, (Collection<CaseDocumentPK>) idList));
	}

	@Override
	public CaseDocument findLastVersionByCaseAndVerionSeries(Context context, CaseDocument caseDoc)
			throws MercuryException {
		return getEntity(context, getService().findLastVersionByCaseAndVersionSeries(context, caseDoc));
	}

	@Override
	public List<CaseDocument> findAllVersionsByCaseAndVerionSeries(Context context, CaseDocument caseDoc)
			throws MercuryException {
		return getEntityCollection(context, getService().findAllVersionsByCaseAndVersionSeries(context, caseDoc));
	}

	@Override
	public List<CaseDocument> findAllVersionsByCaseId(Context context, Long caseObjId) throws MercuryException {
		return getEntityCollection(context, getService().findAllVersionsByCaseId(context, caseObjId));
	}

	@Override
	public List<CaseDocument> findLastVersionsByCaseId(Context context, Long caseObjId) throws MercuryException {
		return getEntityCollection(context, getService().findLastVersionsByCaseId(context, caseObjId));
	}

	@Override
	public List<CaseDocument> getAllByVerionSeries(Context context, CaseDocument caseDoc) throws MercuryException {
		if (caseDoc.getId() == null) {
			throw new InternalErrorException(
					"Brak definicji identyfikatora, na podstawie, którego ma zostać zdefiniowana wartość pola 'versionSeriesId'.");
		}
		return getAllByVerionSeries(context, caseDoc.getId().getVersionSeriesId());
	}

	@Override
	public List<CaseDocument> getAllByVerionSeries(Context context, String versionSeriesId) throws MercuryException {
		return getEntityCollection(context,
				getService().findAllCasesByVersionSeries(context, (versionSeriesId == null) ? null : versionSeriesId));
	}

	@Override
	public List<CaseDocument> findByObjectId(Context context, String objectId) throws MercuryException {
		return getEntityCollection(context, getService().findByObjectId(context, (objectId == null) ? null : objectId));
	}

	@Override
	public CaseDocument getLastVersionByCaseAndSeries(Context context, Long caseId, String versionSeriesId)
			throws MercuryException {
		return getEntity(context, getService().findLastVersionByCaseIdAndSeries(context, caseId, versionSeriesId));
	}

	@Override
	public List<CaseDocument> getAllLastVersionsByCaseId(Context context, Long caseId) throws MercuryException {
		return getEntityCollection(context, getService().findAllLastVersionsByCaseId(context, caseId));
	}

	@Override
	public List<CaseDocument> getAllVersionsByCaseId(Context context, Long caseId) throws MercuryException {
		return getEntityCollection(context, getService().findAllVersionsByCaseId(context, caseId));
	}

	@Override
	public List<CaseDocument> findAll(Context context) throws MercuryException {
		return getEntityCollection(context, getService().findAll(context));
	}
}
