package io.hgdb.client.cxf.logic.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.hgdb.client.cxf.WsClientRoot;
import pl.slawas.entities.NameValuePair;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.Case2SubCaseDto;
import pro.ibpm.mercury.dto.DtoObject;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.dto.paging.PagedResult;
import pro.ibpm.mercury.dto.paging.PagingInfo;
import pro.ibpm.mercury.entities.beans.NameValuePairWSC;
import pro.ibpm.mercury.entities.data.Case2SubCase;
import pro.ibpm.mercury.entities.data.MUser;
import pro.ibpm.mercury.entities.dict.MRole;
import pro.ibpm.mercury.exceptions.InternalErrorException;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.MModifyInfoHelper;
import pro.ibpm.mercury.logic.api.data.ICase2SubCaseLogic;
import pro.ibpm.mercury.logic.exceptions.LC025MethodNotSupportedException;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.registry.RegistrySupport;
import pro.ibpm.mercury.ws.server.api.actions.data.ICase2SubCaseAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatus;
import pro.ibpm.mercury.ws.server.api.returns.WsStatus;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithLongBag;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithLongValue;
import pro.ibpm.mercury.ws.server.api.returns.data.Case2SubCasePagedResult;
import pro.ibpm.mercury.ws.server.api.returns.data.WsStatusWithCase2SubCasePagedResult;

public class Case2SubCaseLogic extends WsClientRoot<Case2SubCase, Long, ICase2SubCaseAction>
		implements ICase2SubCaseLogic {

	private static final String REMOVE_WS_STATUS_WITH_DTOS = "--> remove: wsStatusWithDtos={}";
	private static final long serialVersionUID = -4082967291846032006L;

	@Deprecated
	@Override
	public List<Case2SubCase> filter(Context context, Map<String, Object> sqlParams) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public <U extends MUser<U>, R extends MRole<R>> MModifyInfoHelper<U, R> getModifyInfoHelper() {
		throw new IllegalAccessError();
	}

	@Deprecated
	@Override
	public Case2SubCase find(Context context, Long id, boolean forUpdate) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public Case2SubCase findReference(Context context, Case2SubCase entity) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public List<Case2SubCase> find(Context context, String namedQuery, Map<String, Object> sqlParams)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public Case2SubCase findFirst(Context context, String namedQuery, Map<String, Object> sqlParams)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public List<NameValuePair> loadNameValuePair(Context context, String namedQuery, Map<String, Object> sqlParams)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public List<NameValuePair> loadNameValuePair(Context context) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public List<NameValuePairWSC> loadNameValuePairWSC(Context context, String namedQuery,
			Map<String, Object> sqlParams) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public Long checkExists(Context context, Long id) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public Case2SubCase persistentClassNewInstance(Context context) {
		return new Case2SubCase();
	}

	@Deprecated
	@Override
	public Case2SubCase find(Context context, Long id) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public List<Case2SubCase> findAll(Context context) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public Case2SubCase findFirst(Context context) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Override
	public Case2SubCase insert(Context context, Case2SubCase entityObject) throws MercuryException {
		return getEntity(context, getService().insert(context, entityObject));
	}

	@Deprecated
	@Override
	public List<Case2SubCase> insertList(Context context, List<Case2SubCase> entityObjects) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public Case2SubCase update(Context context, Case2SubCase entityObject) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public List<Case2SubCase> updateList(Context context, List<Case2SubCase> entityObjects) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Override
	public Long remove(Context context, Case2SubCase entityObject) throws MercuryException {
		return getId(getService().removeByReferenceId(context, entityObject.getId()), entityObject);
	}

	@Deprecated
	@Override
	public List<Long> removeList(Context context, List<Case2SubCase> entityObjects) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public NameValuePair loadNameValuePair(Context context, Object id, String additionalStaticCredentials)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public List<NameValuePair> searchNameValuePairByName(Context context, String searchText,
			String additionalStaticCredentials) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public List<NameValuePairWSC> loadNameValuePairWSC(Context context) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public String getSpringBeanName() {
		return RegistrySupport.getBeanName(getClass());
	}

	@Deprecated
	@Override
	public List<Case2SubCase> findByIdList(Context context, List<Long> idList) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public List<Case2SubCase> filter(Context context, Case2SubCase entityObject) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Override
	public Case2SubCase findReferenceByKnownRelationShip(Context context, Long parentCaseId, String fieldName,
			Long subCaseId) throws MercuryException {
		return getEntity(context,
				getService().findReferenceByKnownRelationShip(context, parentCaseId, fieldName, subCaseId));
	}

	@Override
	public Case2SubCase findFirstReferenceByParentCaseIdAndFieldName(Context context, Long parentCaseId,
			String fieldName) throws MercuryException {
		return getEntity(context,
				getService().findFirstReferenceByParentCaseIdAndFieldName(context, parentCaseId, fieldName));
	}

	@Override
	public Set<Long> checkSubCaseIdByParentCaseIdAndFieldName(Context context, Long parentCaseId, String fieldName,
			List<Long> subCaseIds) throws MercuryException {
		WsStatusWithLongBag wsStatusWithDtos = getService().checkSubCaseIdByParentCaseIdAndFieldName(context,
				parentCaseId, fieldName, subCaseIds);
		if (wsStatusWithDtos == null) {
			return Collections.emptySet();
		}
		logger.debug("--> checkSubCaseIdByParentCaseIdAndFieldName: wsStatusWithDtos={}",
				new Object[] { wsStatusWithDtos });
		if (checkWsStatus((IWsStatus) wsStatusWithDtos) && wsStatusWithDtos.getBag() != null
				&& !wsStatusWithDtos.getBag().isEmpty()) {
			return new HashSet<Long>(wsStatusWithDtos.getBag());
		}
		return Collections.emptySet();
	}

	@Override
	public IPagedResult<Case2SubCase, IPage> findReferencesByParentCaseIdAndFieldName(Context context,
			Long parentCaseId, String fieldName, IPage page) throws MercuryException {
		WsStatusWithCase2SubCasePagedResult result = getService().findReferencesByParentCaseIdAndFieldName(context,
				parentCaseId, fieldName, (PageTransportable) page);
		return getPagedResult(context, result);
	}

	protected IPagedResult<Case2SubCase, IPage> getPagedResult(Context context,
			final WsStatusWithCase2SubCasePagedResult wsStatusWithPagedResult) throws MercuryException {
		logger.debug("--> getPagedResult: wsStatusWithPagedResult={}", new Object[] { wsStatusWithPagedResult });
		if (checkWsStatus((IWsStatus) wsStatusWithPagedResult)) {
			try {
				/* czytamy rezultat z serwisu */
				Case2SubCasePagedResult pagedResultDto = wsStatusWithPagedResult.getPagedResult();
				Collection<Case2SubCaseDto> dtos = pagedResultDto.getResult();
				Collection<Case2SubCase> eList = initCollection(context, dtos);
				/* Teraz trzeba utworzyć obiekt stronicowanego wyniku z encją */
				PagedResult<Case2SubCase> result = new PagedResult<Case2SubCase>(new PagingInfo(pagedResultDto));
				result.setResult(eList);
				return result;
			} catch (Exception e) {
				throw new InternalErrorException(e);
			}
		}
		return null;
	}

	@Override
	public List<Long> findSubCaseIdsByParentCaseIdAndFieldName(Context context, Long parentCaseId, String fieldName)
			throws MercuryException {
		WsStatusWithLongBag wsStatusWithDtos = getService().findSubCaseIdsByParentCaseIdAndFieldName(context,
				parentCaseId, fieldName);
		if (wsStatusWithDtos == null) {
			return Collections.emptyList();
		}
		logger.debug("--> findSubCaseIdsByParentCaseIdAndFieldName: wsStatusWithDtos={}",
				new Object[] { wsStatusWithDtos });
		if (checkWsStatus((IWsStatus) wsStatusWithDtos) && wsStatusWithDtos.getBag() != null
				&& !wsStatusWithDtos.getBag().isEmpty()) {
			return new ArrayList<Long>(wsStatusWithDtos.getBag());
		}
		return Collections.emptyList();
	}

	@Override
	public List<Long> findAllReferenceIdsByParentCaseId(Context context, Long parentCaseId) throws MercuryException {
		WsStatusWithLongBag wsStatusWithDtos = getService().findAllReferenceIdsByParentCaseId(context, parentCaseId);
		if (wsStatusWithDtos == null) {
			return Collections.emptyList();
		}
		logger.debug("--> findAllReferenceIdsByParentCaseId: wsStatusWithDtos={}", new Object[] { wsStatusWithDtos });
		if (checkWsStatus((IWsStatus) wsStatusWithDtos) && wsStatusWithDtos.getBag() != null
				&& !wsStatusWithDtos.getBag().isEmpty()) {
			return new ArrayList<Long>(wsStatusWithDtos.getBag());
		}
		return Collections.emptyList();
	}

	@Override
	public int remove(Context context, Long parentCaseId, String fieldName, Long subCaseId) throws MercuryException {
		WsStatusWithLongValue wsStatusWithDtos = getService().removeSubCase(context, parentCaseId, fieldName,
				subCaseId);
		if (wsStatusWithDtos == null) {
			return 0;
		}
		logger.debug(REMOVE_WS_STATUS_WITH_DTOS, new Object[] { wsStatusWithDtos });
		if (checkWsStatus((IWsStatus) wsStatusWithDtos) && wsStatusWithDtos.getValue() != null) {
			return wsStatusWithDtos.getValue().intValue();
		}
		return 0;
	}

	@Override
	public int remove(Context context, Long parentCaseId, String fieldName) throws MercuryException {
		WsStatusWithLongValue wsStatusWithDtos = getService().removeSubCasesList(context, parentCaseId, fieldName);
		if (wsStatusWithDtos == null) {
			return 0;
		}
		logger.debug(REMOVE_WS_STATUS_WITH_DTOS, new Object[] { wsStatusWithDtos });
		if (checkWsStatus((IWsStatus) wsStatusWithDtos) && wsStatusWithDtos.getValue() != null) {
			return wsStatusWithDtos.getValue().intValue();
		}
		return 0;
	}

	@Override
	public int remove(Context context, Long parentCaseId) throws MercuryException {
		WsStatusWithLongValue wsStatusWithDtos = getService().removeSubCasesAll(context, parentCaseId);
		if (wsStatusWithDtos == null) {
			return 0;
		}
		logger.debug(REMOVE_WS_STATUS_WITH_DTOS, new Object[] { wsStatusWithDtos });
		if (checkWsStatus((IWsStatus) wsStatusWithDtos) && wsStatusWithDtos.getValue() != null) {
			return wsStatusWithDtos.getValue().intValue();
		}
		return 0;
	}

	@Override
	public Class<Case2SubCase> getPersistentClass() {
		return Case2SubCase.class;
	}

	@Override
	public Class<? extends DtoObject> getPersistentClassDto() {
		return Case2SubCaseDto.class;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Long removeByReferenceId(Context context, Long referenceId) throws MercuryException {
		WsStatus wsStatus = getService().removeByReferenceId(context, referenceId);
		if (wsStatus == null) {
			return null;
		}
		logger.debug(REMOVE_WS_STATUS_WITH_DTOS, new Object[] { wsStatus });
		if (checkWsStatus((IWsStatus) wsStatus)) {
			return referenceId;
		}
		return null;
	}

}
