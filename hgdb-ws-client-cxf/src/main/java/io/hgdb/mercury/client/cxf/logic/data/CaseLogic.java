package io.hgdb.mercury.client.cxf.logic.data;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.hgdb.mercury.client.cxf.logic.WsClientBigDataLogic;
import pro.ibpm.mercury.actions.DateAction;
import pro.ibpm.mercury.attrs.CaseDateUtils;
import pro.ibpm.mercury.attrs.javax.CaseDate;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.cse.CredentialsMode;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.data.Case;
import pro.ibpm.mercury.exceptions.InternalErrorException;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.GroupCase2ParticipantParticipant2Role;
import pro.ibpm.mercury.logic.api.data.ICaseLogic;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.search.SearchFieldSimple;
import pro.ibpm.mercury.ws.server.api.actions.data.ICaseAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;
import pro.ibpm.mercury.ws.server.api.returns.WsStatus;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithLongValue;

/**
 * 
 * CaseLogic
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
public class CaseLogic extends WsClientBigDataLogic<Case, Long, ICaseAction> implements ICaseLogic {

	private static final long serialVersionUID = 6830844704517933475L;

	@Override
	public Case insert(Context context, final Case e) throws MercuryException {
		return getEntity(context, getService(context).insert(context, e, /* forceAddStore2Type */false));
	}

	@Override
	public EntityList<Case, Long> insertList(Context context, List<Case> eBag) throws MercuryException {
		return getEntityCollection(context, getService(context).insertBag(context, eBag, /* forceAddStore2Type */
				false));
	}

	@Override
	public Long remove(Context context, final Case e) throws MercuryException {
		return getId(getService(context).remove(context, e), e);
	}

	@Override
	public List<Long> removeList(Context context, final List<Case> eBag) throws MercuryException {
		return getIds(getService(context).removeBag(context, eBag), eBag);
	}

	@Override
	public Case find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public Case findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

	@Override
	public Case update(Context context, Case e) throws MercuryException {
		return getEntity(context, getService(context).update(context, e, /* forceAddStore2Type */false));
	}

	@Override
	public EntityList<Case, Long> updateList(Context context, List<Case> eBag) throws MercuryException {
		return getEntityCollection(context, getService(context).updateBag(context, eBag, /* forceAddStore2Type */
				false));
	}

	@Override
	public Case findLastByBpmId(Context context, Long bpmId) throws MercuryException {
		return getEntity(context, getService(context).findLastByBpmId(context, (bpmId == null) ? null : bpmId.toString()));
	}

	@Override
	public Case findLastVersion(Context context, Long rootVersionId) throws MercuryException {
		return getEntity(context, getService(context).findLastVersion(context, rootVersionId));
	}

	@Override
	public Map<String, SearchFieldSimple> getLuceneSearchableFields(Context context) throws MercuryException {
		final Map<String, SearchFieldSimple> map = getMap(getService(context).getLuceneSearchableFields(context));
		return (map == null) ? null : new HashMap<>(map);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<Case, IPage> searchIndex(Context context, String query, IPage page) throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService(context).searchLuceneByQuery(context, query, (PageTransportable) page));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IPagedResult<Case, IPage> searchIndex(Context context, Map<String, String> paramsMap, CredentialsMode mode,
			IPage page) throws MercuryException {
		return getPagedResult(context, (IWsStatusWithPagedResult) getService(context).searchLuceneByParams(context, paramsMap,
				(mode == null) ? null : mode.toString(), (PageTransportable) page));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IPagedResult<Case, IPage> search(Context context, Map<String, String> paramsMap, CredentialsMode mode,
			String objectId, String versionSeriesId, List<GroupCase2ParticipantParticipant2Role> gc2pList, Case caseObj,
			Calendar dateFrom, Calendar dateTo, Boolean getOnlyLastCase, IPage page) throws MercuryException {
		CaseDate cDateTo = null;
		if (dateTo != null) {
			cDateTo = new CaseDate();
			CaseDateUtils.setDateValue(cDateTo, dateTo);
		}
		CaseDate cDateFrom = null;
		if (dateFrom != null) {
			cDateFrom = new CaseDate();
			CaseDateUtils.setDateValue(cDateFrom, dateFrom);
		}
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService(context).searchInDB(context, paramsMap,
						(mode == null) ? null : mode.toString(), objectId, versionSeriesId, gc2pList, caseObj,
						cDateFrom, cDateTo, /* dateAction */null, getOnlyLastCase, (PageTransportable) page));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IPagedResult<Case, IPage> search(Context context, Map<String, String> paramsMap, CredentialsMode mode,
			Integer year, IPage page) throws MercuryException {
		Calendar dateFrom = null;
		if (year != null) {
			dateFrom = Calendar.getInstance();
			dateFrom.set(Calendar.YEAR, year);
		}
		CaseDate cDateFrom = null;
		if (dateFrom != null) {
			cDateFrom = new CaseDate();
			CaseDateUtils.setDateValue(cDateFrom, dateFrom);
		}
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService(context).searchInDB(context, paramsMap,
						(mode == null) ? null : mode.toString(),
						/* objectId */
						null,
						/* versionSeriesId */
						null,
						/* gc2pBag */
						null,
						/* e */
						null,
						/* dateFrom */
						cDateFrom,
						/* dateTo */
						null,
						/* dateAction */
						DateAction.TRUNC_TO_YEAR,
						/* getOnlyLastCase */
						null,
						/* page */
						(PageTransportable) page));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<Case, IPage> search(Context context, Map<String, String> paramsMap, CredentialsMode mode,
			Integer year, Integer month, IPage page) throws MercuryException {
		Calendar dateFrom = null;
		DateAction dateAction = DateAction.TRUNC_TO_MONTH;
		if (year != null) {
			dateFrom = Calendar.getInstance();
			dateFrom.set(Calendar.YEAR, year);
			if (month != null) {
				dateFrom.set(Calendar.MONTH, month);
			} else {
				dateAction = DateAction.TRUNC_TO_YEAR;
			}
		}
		CaseDate cDateFrom = null;
		if (dateFrom != null) {
			cDateFrom = new CaseDate();
			CaseDateUtils.setDateValue(cDateFrom, dateFrom);
		}
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService(context).searchInDB(context, paramsMap,
						(mode == null) ? null : mode.toString(),
						/* objectId */
						null,
						/* versionSeriesId */
						null,
						/* gc2pBag */
						null,
						/* e */
						null,
						/* dateFrom */
						cDateFrom,
						/* dateTo */
						null,
						/* dateAction */
						dateAction,
						/* getOnlyLastCase */
						null,
						/* page */
						(PageTransportable) page));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<Case, IPage> search(Context context, Map<String, String> paramsMap, CredentialsMode mode,
			Calendar dateFrom, Calendar dateTo, IPage page) throws MercuryException {
		CaseDate cDateTo = null;
		if (dateTo != null) {
			cDateTo = new CaseDate();
			CaseDateUtils.setDateValue(cDateTo, dateTo);
		}
		CaseDate cDateFrom = null;
		if (dateFrom != null) {
			cDateFrom = new CaseDate();
			CaseDateUtils.setDateValue(cDateFrom, dateFrom);
		}
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService(context).searchInDB(context, paramsMap,
						(mode == null) ? null : mode.toString(),
						/* objectId */
						null,
						/* versionSeriesId */
						null,
						/* gc2pBag */
						null,
						/* e */
						null,
						/* dateFrom */
						cDateFrom,
						/* dateTo */
						cDateTo,
						/* dateAction */
						null,
						/* getOnlyLastCase */
						null,
						/* page */
						(PageTransportable) page));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<Case, IPage> search(Context context, Map<String, String> paramsMap, CredentialsMode mode,
			String objectId, String versionSeriesId, List<GroupCase2ParticipantParticipant2Role> gc2pList, Case caseObj,
			Calendar dateFrom, Calendar dateTo, IPage page) throws MercuryException {
		CaseDate cDateTo = null;
		if (dateTo != null) {
			cDateTo = new CaseDate();
			CaseDateUtils.setDateValue(cDateTo, dateTo);
		}
		CaseDate cDateFrom = null;
		if (dateFrom != null) {
			cDateFrom = new CaseDate();
			CaseDateUtils.setDateValue(cDateFrom, dateFrom);
		}
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService(context).searchInDB(context, paramsMap,
						(mode == null) ? null : mode.toString(),
						/* objectId */
						objectId,
						/* versionSeriesId */
						versionSeriesId,
						/* gc2pBag */
						gc2pList,
						/* e */
						caseObj,
						/* dateFrom */
						cDateFrom,
						/* dateTo */
						cDateTo,
						/* dateAction */
						null,
						/* getOnlyLastCase */
						null,
						/* page */
						(PageTransportable) page));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<Case, IPage> filterPaged(Context context, Case e, IPage page) throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService(context).filterPaged(context, e, (PageTransportable) page));
	}

	@Override
	public List<Case> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return getEntityCollection(context, getService(context).findByKeyBag(context, idList));
	}

	@Override
	public List<Case> findAll(Context context) throws MercuryException {
		return getEntityCollection(context, getService(context).findAll(context));
	}

	@Override
	public Case insert(Context context, Case entityObject, boolean forceAddStore2Type) throws MercuryException {
		return getEntity(context, getService(context).insert(context, entityObject, forceAddStore2Type));
	}

	@Override
	public Case update(Context context, Case caseFromUser, boolean forceAddStore2Type) throws MercuryException {
		return getEntity(context, getService(context).update(context, caseFromUser, forceAddStore2Type));
	}

	@Override
	public EntityList<Case, Long> insertList(Context context, List<Case> entityObjects, boolean forceAddStore2Type)
			throws MercuryException {
		return getEntityCollection(context,
				getService(context).insertBag(context, (Collection<Case>) entityObjects, forceAddStore2Type));
	}

	@Override
	public EntityList<Case, Long> updateList(Context context, List<Case> entityObjects, boolean forceAddStore2Type)
			throws MercuryException {
		return getEntityCollection(context,
				getService(context).updateBag(context, (Collection<Case>) entityObjects, forceAddStore2Type));
	}

	@Override
	public List<Case> findByGroupCaseIds(Context context, List<Long> groupCaseIds) throws MercuryException {
		return getEntityCollection(context, getService(context).findByGroupCaseIds(context, groupCaseIds));
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Case findByPKProperty(Context context, String typeCode, String paramValue) throws MercuryException {
		return getEntity(context, getService(context).findByPKProperty(context, typeCode, paramValue));
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<Case> findByProperty(Context context, Long typeId, String paramName, String paramValue)
			throws MercuryException {
		return getEntityCollection(context, getService(context).findByProperty(context, typeId, paramName, paramValue));
	}

	/* Overridden (non-Javadoc) */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<Case, IPage> loadLastUpdated(Context context, IPage page) throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService(context).loadLastUpdated(context, (PageTransportable) page));
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Map<String, SearchFieldSimple> getLuceneSearchableFieldsByTypeCodes(Context context, Set<String> arg1)
			throws MercuryException {
		final Map<String, SearchFieldSimple> map = getMap(
				getService(context).getLuceneSearchableFieldsByTypeCodes(context, arg1));
		return (map == null) ? null : new HashMap<String, SearchFieldSimple>((Map<String, SearchFieldSimple>) map);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Map<String, SearchFieldSimple> getLuceneSearchableFieldsByTypeIds(Context context, Set<Long> arg1)
			throws MercuryException {
		final Map<String, SearchFieldSimple> map = getMap(
				getService(context).getLuceneSearchableFieldsByTypeIds(context, arg1));
		return (map == null) ? null : new HashMap<String, SearchFieldSimple>((Map<String, SearchFieldSimple>) map);
	}

	/* Overridden (non-Javadoc) */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<Case, IPage> searchIndex(Context context, Map<String, String> queriesMap, IPage page)
			throws MercuryException {
		return getPagedResult(context, (IWsStatusWithPagedResult) getService(context).searchLuceneWithQueries(context,
				queriesMap, (PageTransportable) page));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<Case, IPage> loadLastUpdatedByTypeCodes(Context context, Set<String> typeCodes, IPage page)
			throws MercuryException {
		return getPagedResult(context, (IWsStatusWithPagedResult) getService(context).loadLastUpdatedByTypeCodes(context,
				typeCodes, (PageTransportable) page));
	}

	/* Overridden (non-Javadoc) */
	@Override
	public boolean checkStatus(Context context, Long caseId) throws MercuryException {
		WsStatusWithLongValue result = getService(context).checkCaseStatus(context, caseId);
		if (checkWsStatus(result)) {
			Long returnedCaseId = result.getValue();
			return caseId.equals(returnedCaseId);
		}
		return false;
	}

	@Override
	public Long checkStatus(Context context, Case entityObject) throws MercuryException {
		if (entityObject.getId() == null) {
			throw new InternalErrorException("Identyfikator badanej sprawy nie może być pusty");
		}
		WsStatusWithLongValue result = getService(context).checkCaseStatus(context, entityObject.getId());
		if (checkWsStatus(result)) {
			return result.getValue();
		}
		return null;
	}

	@Override
	public Case searchIndexById(Context context, Long id) throws MercuryException {
		return getEntity(context, getService(context).searchLuceneById(context, id));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<Case, IPage> searchIndexByInventoryCode(Context context, String searchText, IPage page)
			throws MercuryException {
		return getPagedResult(context, (IWsStatusWithPagedResult) getService(context).searchLuceneByInventoryCode(context,
				searchText, (PageTransportable) page));
	}

	@Override
	public Case loadNewestVersion(Context context, Long oldVersionCaseId) throws MercuryException {
		return getEntity(context, getService(context).loadNewestVersion(context, oldVersionCaseId));
	}

	@Override
	public void clearQueryCache(Context context) throws MercuryException {
		WsStatus wsStatus = getService(context).clearQueryCache(context);
		checkWsStatus(wsStatus);
	}

	@Override
	public Case findActiveByBpmId(Context context, Long bpmProcessId) throws MercuryException {
		if (bpmProcessId == null) {
			return null;
		}
		return getEntity(context, getService(context).findActiveByBpmId(context, bpmProcessId.toString()));
	}
}
