/**
 * 
 */
package io.hgdb.mercury.client.cxf.logic.data;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import io.hgdb.mercury.client.cxf.logic.WsClientBigDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.data.CaseDocument;
import pro.ibpm.mercury.entities.data.CaseDocumentPK;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.ICaseDocumentLogic;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.data.ICaseDocumentAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;
import pro.ibpm.mercury.ws.server.api.returns.WsStatus;

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
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public EntityList<CaseDocument, CaseDocumentPK> insertList(Context context, List<CaseDocument> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService(context).insertBag(context, eBag));
	}

	@Override
	public CaseDocumentPK remove(Context context, final CaseDocument e) throws MercuryException {
		return getId(getService(context).remove(context, e), e);
	}

	@Override
	public List<CaseDocumentPK> removeList(Context context, final List<CaseDocument> eBag) throws MercuryException {
		return getIds(getService(context).removeBag(context, eBag), eBag);
	}

	@Override
	public CaseDocument find(Context context, final CaseDocumentPK pk) throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public CaseDocument findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

	@Override
	public CaseDocument update(Context context, CaseDocument e) throws MercuryException {
		return getEntity(context, getService(context).update(context, e));
	}

	@Override
	public EntityList<CaseDocument, CaseDocumentPK> updateList(Context context, List<CaseDocument> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService(context).updateBag(context, eBag));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<CaseDocument, IPage> filterPaged(Context context, CaseDocument e, IPage page)
			throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService(context).filterPaged(context, e, (PageTransportable) page));
	}

	@Override
	public List<CaseDocument> findByIdList(Context context, List<CaseDocumentPK> idList) throws MercuryException {
		return getEntityCollection(context, getService(context).findByKeyBag(context, (Collection<CaseDocumentPK>) idList));
	}

	@Override
	public CaseDocument findLastVersionByCaseAndVerionSeries(Context context, CaseDocument caseDoc)
			throws MercuryException {
		return getEntity(context, getService(context).findLastVersionByCaseAndVersionSeries(context, caseDoc));
	}

	@Override
	public List<CaseDocument> findAllVersionsByCaseAndVerionSeries(Context context, CaseDocument caseDoc)
			throws MercuryException {
		return getEntityCollection(context, getService(context).findAllVersionsByCaseAndVersionSeries(context, caseDoc));
	}

	@Override
	public List<CaseDocument> findAllVersionsByCaseId(Context context, Long caseObjId) throws MercuryException {
		if (caseObjId != null) {
			return getEntityCollection(context, getService(context).findAllVersionsByCaseId(context, caseObjId));
		}
		return Collections.emptyList();
	}

	@Override
	public List<CaseDocument> findLastVersionsByCaseId(Context context, Long caseObjId) throws MercuryException {
		if (caseObjId != null) {
			return getEntityCollection(context, getService(context).findLastVersionsByCaseId(context, caseObjId));
		}
		return Collections.emptyList();
	}

	@Override
	public CaseDocument findLastVersionByCaseAndVerionSeries(Context context, Long caseId, String versionSeriesId)
			throws MercuryException {
		if (versionSeriesId != null && caseId != null) {
			return getEntity(context, getService(context).findLastVersionByCaseIdAndSeries(context, caseId, versionSeriesId));
		}
		return null;
	}

	@Override
	public List<CaseDocument> findAllByVersionSeries(Context context, CaseDocument caseDoc) throws MercuryException {
		String versionSeriesId = null;
		if (caseDoc != null && caseDoc.getId() != null) {
			versionSeriesId = caseDoc.getId().getVersionSeriesId();
		}
		return findAllByVersionSeries(context, versionSeriesId);
	}

	@Override
	public List<CaseDocument> findAllByVersionSeries(Context context, String versionSeriesId) throws MercuryException {
		if (StringUtils.isNotBlank(versionSeriesId)) {
			return getEntityCollection(context, getService(context).findAllVersionsByVersionSeries(context, versionSeriesId));
		}
		return Collections.emptyList();
	}

	@Override
	public List<CaseDocument> findByObjectId(Context context, String objectId) throws MercuryException {
		if (StringUtils.isNotBlank(objectId)) {
			return getEntityCollection(context, getService(context).findByObjectId(context, objectId));
		}
		return Collections.emptyList();
	}

	@Override
	public void clearQueryCache(Context context) throws MercuryException {
		WsStatus wsStatus = getService(context).clearQueryCache(context);
		checkWsStatus(wsStatus);
	}

}
