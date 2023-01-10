package io.hgdb.mercury.client.cxf.logic.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.hgdb.mercury.client.cxf.WsClientRoot;
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
import pro.ibpm.mercury.ws.server.api.returns.WsStatus;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithLongBag;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithLongValue;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithStringBag;
import pro.ibpm.mercury.ws.server.api.returns.data.Case2SubCasePagedResult;
import pro.ibpm.mercury.ws.server.api.returns.data.WsStatusWithCase2SubCasePagedResult;

/**
 * 
 * Case2SubCaseLogic
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class Case2SubCaseLogic extends WsClientRoot<Case2SubCase, Long, ICase2SubCaseAction>
		implements ICase2SubCaseLogic {

	private static final String REMOVE_WS_STATUS_WITH_DTOS = "--> remove: wsStatusWithDtos={}";
	private static final long serialVersionUID = -4082967291846032006L;

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public List<Case2SubCase> filter(Context context, Map<String, Object> sqlParams) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public <U extends MUser<U>, R extends MRole<R>> MModifyInfoHelper<U, R> getModifyInfoHelper() {
		throw new IllegalAccessError();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public Case2SubCase find(Context context, Long id, boolean forUpdate) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public Case2SubCase findReference(Context context, Case2SubCase entity) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public List<Case2SubCase> find(Context context, String namedQuery, Map<String, Object> sqlParams)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public Case2SubCase findFirst(Context context, String namedQuery, Map<String, Object> sqlParams)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public Long checkExists(Context context, Long id) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public Case2SubCase persistentClassNewInstance(Context context) {
		return new Case2SubCase();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public Case2SubCase find(Context context, Long id) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public List<Case2SubCase> findAll(Context context) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public Case2SubCase findFirst(Context context) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Override
	public Case2SubCase insert(Context context, Case2SubCase entityObject) throws MercuryException {
		return getEntity(context, getService(context).insert(context, entityObject));
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public List<Case2SubCase> insertList(Context context, List<Case2SubCase> entityObjects) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public Case2SubCase update(Context context, Case2SubCase entityObject) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public List<Case2SubCase> updateList(Context context, List<Case2SubCase> entityObjects) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Override
	public Long remove(Context context, Case2SubCase entityObject) throws MercuryException {
		return getId(getService(context).removeByReferenceId(context, entityObject.getId()), entityObject);
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public List<Long> removeList(Context context, List<Case2SubCase> entityObjects) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Override
	public String getSpringBeanName() {
		return RegistrySupport.getBeanName(getClass());
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public List<Case2SubCase> findByIdList(Context context, List<Long> idList) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public List<Case2SubCase> filter(Context context, Case2SubCase entityObject) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Override
	public Case2SubCase findReferenceByKnownRelationShip(Context context, String parentCaseId, String fieldName,
			String subCaseId) throws MercuryException {
		return getEntity(context,
				getService(context).findReferenceByKnownRelationShip(context, parentCaseId, fieldName, subCaseId));
	}

	@Override
	public Case2SubCase findFirstReferenceByParentCaseIdAndFieldName(Context context, String parentCaseId,
			String fieldName) throws MercuryException {
		return getEntity(context,
				getService(context).findFirstReferenceByParentCaseIdAndFieldName(context, parentCaseId, fieldName));
	}

	@Override
	public Set<String> checkSubCaseIdByParentCaseIdAndFieldName(Context context, String parentCaseId, String fieldName,
			List<String> subCaseIds) throws MercuryException {
		WsStatusWithStringBag wsStatusWithDtos = getService(context).checkSubCaseIdByParentCaseIdAndFieldName(context,
				parentCaseId, fieldName, subCaseIds);
		if (wsStatusWithDtos == null) {
			return Collections.emptySet();
		}
		logger.debug("--> checkSubCaseIdByParentCaseIdAndFieldName: wsStatusWithDtos={}", wsStatusWithDtos);
		if (checkWsStatus(wsStatusWithDtos) && wsStatusWithDtos.getBag() != null
				&& !wsStatusWithDtos.getBag().isEmpty()) {
			return new HashSet<>(wsStatusWithDtos.getBag());
		}
		return Collections.emptySet();
	}

	@Override
	public IPagedResult<Case2SubCase, IPage> findReferencesByParentCaseIdAndFieldName(Context context,
			String parentCaseId, String fieldName, IPage page) throws MercuryException {
		WsStatusWithCase2SubCasePagedResult result = getService(context)
				.findReferencesByParentCaseIdAndFieldName(context, parentCaseId, fieldName, (PageTransportable) page);
		return getPagedResult(context, result);
	}

	protected IPagedResult<Case2SubCase, IPage> getPagedResult(Context context,
			final WsStatusWithCase2SubCasePagedResult wsStatusWithPagedResult) throws MercuryException {
		logger.debug("--> getPagedResult: wsStatusWithPagedResult={}", wsStatusWithPagedResult);
		if (checkWsStatus(wsStatusWithPagedResult)) {
			try {
				/* czytamy rezultat z serwisu */
				Case2SubCasePagedResult pagedResultDto = wsStatusWithPagedResult.getPagedResult();
				Collection<Case2SubCaseDto> dtos = pagedResultDto.getResult();
				Collection<Case2SubCase> eList = initCollection(context, dtos);
				/* Teraz trzeba utworzyć obiekt stronicowanego wyniku z encją */
				PagedResult<Case2SubCase> result = new PagedResult<>(new PagingInfo(pagedResultDto));
				result.setResult(eList);
				return result;
			} catch (Exception e) {
				throw new InternalErrorException(e);
			}
		}
		return null;
	}

	@Override
	public List<String> findSubCaseIdsByParentCaseIdAndFieldName(Context context, String parentCaseId, String fieldName)
			throws MercuryException {
		WsStatusWithStringBag wsStatusWithDtos = getService(context).findSubCaseIdsByParentCaseIdAndFieldName(context,
				parentCaseId, fieldName);
		if (wsStatusWithDtos == null) {
			return Collections.emptyList();
		}
		logger.debug("--> findSubCaseIdsByParentCaseIdAndFieldName: wsStatusWithDtos={}", wsStatusWithDtos);
		if (checkWsStatus(wsStatusWithDtos) && wsStatusWithDtos.getBag() != null
				&& !wsStatusWithDtos.getBag().isEmpty()) {
			return new ArrayList<>(wsStatusWithDtos.getBag());
		}
		return Collections.emptyList();
	}

	@Override
	public List<Long> findAllReferenceIdsByParentCaseId(Context context, String parentCaseId) throws MercuryException {
		WsStatusWithLongBag wsStatusWithDtos = getService(context).findAllReferenceIdsByParentCaseId(context,
				parentCaseId);
		if (wsStatusWithDtos == null) {
			return Collections.emptyList();
		}
		logger.debug("--> findAllReferenceIdsByParentCaseId: wsStatusWithDtos={}", wsStatusWithDtos);
		if (checkWsStatus(wsStatusWithDtos) && wsStatusWithDtos.getBag() != null
				&& !wsStatusWithDtos.getBag().isEmpty()) {
			return new ArrayList<>(wsStatusWithDtos.getBag());
		}
		return Collections.emptyList();
	}

	@Override
	public int remove(Context context, String parentCaseId, String fieldName, String subCaseId)
			throws MercuryException {
		WsStatusWithLongValue wsStatusWithDtos = getService(context).removeSubCase(context, parentCaseId, fieldName,
				subCaseId);
		if (wsStatusWithDtos == null) {
			return 0;
		}
		logger.debug(REMOVE_WS_STATUS_WITH_DTOS, wsStatusWithDtos);
		if (checkWsStatus(wsStatusWithDtos) && wsStatusWithDtos.getValue() != null) {
			return wsStatusWithDtos.getValue().intValue();
		}
		return 0;
	}

	@Override
	public int remove(Context context, String parentCaseId, String fieldName) throws MercuryException {
		WsStatusWithLongValue wsStatusWithDtos = getService(context).removeSubCasesList(context, parentCaseId,
				fieldName);
		if (wsStatusWithDtos == null) {
			return 0;
		}
		logger.debug(REMOVE_WS_STATUS_WITH_DTOS, wsStatusWithDtos);
		if (checkWsStatus(wsStatusWithDtos) && wsStatusWithDtos.getValue() != null) {
			return wsStatusWithDtos.getValue().intValue();
		}
		return 0;
	}

	@Override
	public int remove(Context context, String parentCaseId) throws MercuryException {
		WsStatusWithLongValue wsStatusWithDtos = getService(context).removeSubCasesAll(context, parentCaseId);
		if (wsStatusWithDtos == null) {
			return 0;
		}
		logger.debug(REMOVE_WS_STATUS_WITH_DTOS, wsStatusWithDtos);
		if (checkWsStatus(wsStatusWithDtos) && wsStatusWithDtos.getValue() != null) {
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
		WsStatus wsStatus = getService(context).removeByReferenceId(context, referenceId);
		if (wsStatus == null) {
			return null;
		}
		logger.debug(REMOVE_WS_STATUS_WITH_DTOS, wsStatus);
		if (checkWsStatus(wsStatus)) {
			return referenceId;
		}
		return null;
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public NameValuePair loadNameValuePair(Context context, String idAsString) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public List<NameValuePair> searchNameValuePairByName(Context context, String searchText) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public NameValuePair loadNameValuePair(Context context, Object id, String additionalStaticCredentials)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public List<NameValuePair> loadNameValuePairList(Context context, String additionalStaticCredentials)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public List<NameValuePair> searchNameValuePairByName(Context context, String searchText,
			String additionalStaticCredentials) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public NameValuePairWSC loadNameValuePairWSC(Context context, Object id, String additionalStaticCredentials)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public List<NameValuePairWSC> loadNameValuePairWSCList(Context context, String additionalStaticCredentials)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/**
	 * @deprecated metoda nie jest wystawiona jako usługa
	 */
	@Deprecated
	@Override
	public List<NameValuePairWSC> searchNameValuePairWSCByCredentials(Context context, String searchText,
			String additionalStaticCredentials) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

}
