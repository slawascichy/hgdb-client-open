package io.hgdb.mercury.client.cxf.business.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.mercury.cxf.client.WsStatusUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import io.hgdb.mercury.client.cxf.WsClient;
import io.hgdb.mercury.client.utils.MrcVariableReaderCollectorUtils;
import pro.ibpm.mercury.attrs.CaseDateUtils;
import pro.ibpm.mercury.attrs.javax.CaseDate;
import pro.ibpm.mercury.business.data.api.BpmCaseHistoryStream;
import pro.ibpm.mercury.business.data.api.CaseHeader;
import pro.ibpm.mercury.business.data.api.ConnectionStatus;
import pro.ibpm.mercury.business.data.api.ICaseBusiness;
import pro.ibpm.mercury.business.data.api.ICaseBusinessXML;
import pro.ibpm.mercury.business.data.api.IMrcCase;
import pro.ibpm.mercury.business.data.api.IMrcPagedResult;
import pro.ibpm.mercury.business.data.api.MrcCaseDocument;
import pro.ibpm.mercury.business.data.api.MrcCaseHistoryStream;
import pro.ibpm.mercury.business.data.api.MrcComment;
import pro.ibpm.mercury.business.data.api.MrcList;
import pro.ibpm.mercury.business.data.api.MrcObject;
import pro.ibpm.mercury.business.data.api.MrcObjectMetadata;
import pro.ibpm.mercury.business.data.api.MrcPagedResult;
import pro.ibpm.mercury.business.data.api.MrcQuickTask;
import pro.ibpm.mercury.business.data.api.MrcSimplePropertyMapGroupedByCaseId;
import pro.ibpm.mercury.business.data.utils.MrcVariableReaderCollector;
import pro.ibpm.mercury.business.data.utils.MrcVariableReaderDOM;
import pro.ibpm.mercury.business.data.utils.XMLReaderHelper;
import pro.ibpm.mercury.business.data.utils.XMLVariableBuilder;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.cse.CredentialsMode;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.attr.TypeCode;
import pro.ibpm.mercury.entities.data.Case;
import pro.ibpm.mercury.exceptions.InternalErrorException;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.GroupCase2ParticipantParticipant2Role;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.ws.server.api.actions.business.data.ICaseBusinessAction;
import pro.ibpm.mercury.ws.server.api.returns.DtoMrcDataUtils;
import pro.ibpm.mercury.ws.server.api.returns.DtoMrcList;
import pro.ibpm.mercury.ws.server.api.returns.DtoMrcObject;
import pro.ibpm.mercury.ws.server.api.returns.WsConnectionStatus;
import pro.ibpm.mercury.ws.server.api.returns.WsStatus;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithBpmCaseHistoryStreamDtos;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithLongValue;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithMapString2String;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithMrcCaseDocumentDtos;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithMrcCaseHistoryStreamDtos;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithMrcCommentDtos;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithMrcList;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithMrcObject;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithMrcQuickTaskDtos;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithMrcSimplePropertyMapGroupedByCaseIdDto;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithMrcSimplePropertyMapGroupedByCaseIdDtos;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithStringValue;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithXML;

/**
 * 
 * CaseBusiness
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
public class CaseBusiness extends WsClient<ICaseBusinessAction> implements ICaseBusiness, ICaseBusinessXML {

	public static final int FIRST_ITERATION = 0;

	/**
	 * parametr mówiący o tym, czy usługa klienta wykorzystuje usługi "remoting" (nie SOAP). Dla usług pobierających XML
	 * ma to znaczenie, bo pobieranie wyniku w postaci XML dla "remoting" się nie opłaca. Lepiej pobrać MrcObject i
	 * przekształcić go do XML'a już lokalnie. Z kolei dla usług SOAP lepiej pobrać dokument XML. Nasza implementacja
	 * klienta nastawiona jest na komunikację "remoting" zatem domyślna wartość to {@code true}.
	 */
	private Boolean isRemote = true;

	@Override
	public ConnectionStatus echoStatus(Context context, final String someText) {
		String helloWorld = "Hello world!";
		if (StringUtils.isNotBlank(someText)) {
			helloWorld = someText;
		}
		ConnectionStatus connectionStatus = new ConnectionStatus();
		try {
			WsConnectionStatus result = getService(context).echo(context, helloWorld);
			checkWsStatus(result);
			connectionStatus.setErrorCode(result.getErrorCode());
			connectionStatus.setErrorMessage(result.getErrorMessage());
			connectionStatus.setLuceneModelVersion(result.getLuceneModelVersion());
			connectionStatus.setSoapAPIVersion(result.getSoapAPIVersion());
			connectionStatus.setStatus(helloWorld.equals(result.getValue()));
		} catch (MercuryException e) {
			connectionStatus.setErrorCode(e.getErrorCode());
			connectionStatus.setErrorMessage(e.getMessage());
			connectionStatus.setStatus(false);
		} catch (Exception e) {
			connectionStatus.setErrorCode(InternalErrorException.ERROR_CODE);
			connectionStatus.setErrorMessage(e.getMessage());
			connectionStatus.setStatus(false);
		}
		return connectionStatus;
	}

	protected Document loadMrcObjectXML(Context context, MrcObject mrcObject) throws MercuryException {
		try {
			return XMLVariableBuilder.buildObjectVariable(context, XMLReaderHelper.TAG_MAIN_DOCUMENT, mrcObject);
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}
	}

	protected Document loadMrcListXML(Context context, MrcList mrcList) throws MercuryException {
		try {
			return XMLVariableBuilder.buildObjectVariable(context, XMLReaderHelper.TAG_MAIN_DOCUMENT, mrcList);
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}
	}

	protected Document loadMrcPagedResultXML(Context context, MrcPagedResult mrcPagedResult) throws MercuryException {
		try {
			return XMLVariableBuilder.buildObjectVariable(context, XMLReaderHelper.TAG_MAIN_DOCUMENT, mrcPagedResult);
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}
	}

	protected DtoMrcList getDtoList(final WsStatusWithMrcList wsStatusWithDto) throws MercuryException {
		if (checkWsStatus(wsStatusWithDto)) {
			if (logger.isDebugEnabled()) {
				logger.debug("-->getDtoList: status: {}", StringUtils.isBlank(wsStatusWithDto.getErrorMessage()) ? "OK"
						: wsStatusWithDto.getErrorMessage());
			}
			return wsStatusWithDto.getDto();
		}
		return null;
	}

	/**
	 * @return the {@link #isRemote}
	 */
	public Boolean getIsRemote() {
		return isRemote == null || isRemote;
	}

	/**
	 * @param isRemote
	 *                 the {@link #isRemote} to set
	 */
	public void setIsRemote(Boolean isRemote) {
		this.isRemote = isRemote == null || isRemote;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public MrcObject persistentClassNewInstance(Context context) {
		return new MrcObject();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public MrcPagedResult pagedResultNewInstance(Context context) {
		MrcObjectMetadata metadata = new MrcObjectMetadata();
		metadata.setClassName(IMrcPagedResult.CLASS_NAME);
		return new MrcPagedResult(metadata);
	}

	/* Overridden (non-Javadoc) */
	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public MrcObject find(Context context, String id) throws MercuryException {
		WsStatusWithMrcObject result = getService(context).find(context, id);
		DtoMrcObject dtoObject = getDto(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcObject) DtoMrcDataUtils.toMrcObject(context, dtoObject,
				collector != null ? collector : new MrcVariableReaderCollector());
	}

	/* Overridden (non-Javadoc) */
	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public MrcObject findFirst(Context context) throws MercuryException {
		WsStatusWithMrcObject result = getService(context).findFirst(context);
		DtoMrcObject dtoObject = getDto(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcObject) DtoMrcDataUtils.toMrcObject(context, dtoObject,
				collector != null ? collector : new MrcVariableReaderCollector());
	}

	/* Overridden (non-Javadoc) */
	@Override
	public String remove(Context context, String caseId) throws MercuryException {
		WsStatus result = getService(context).remove(context, caseId);
		checkWsStatus(result);
		return caseId;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<String> removeList(Context context, List<String> idList) throws MercuryException {
		WsStatus result = getService(context).removeList(context, idList);
		checkWsStatus(result);
		return idList;
	}

	/* Overridden (non-Javadoc) */
	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public MrcList findByIdList(Context context, List<String> idList) throws MercuryException {
		WsStatusWithMrcList result = getService(context).findByIdList(context, idList);
		DtoMrcList dtoList = getDtoList(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcList) DtoMrcDataUtils.toMrcList(context, dtoList,
				collector != null ? collector : new MrcVariableReaderCollector());
	}

	/* Overridden (non-Javadoc) */
	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * <p>
	 * Parametr {@code typeCodes} nie ma znaczenia, nie jest wykorzystywany w implementacji usługi. Jego istnienie
	 * podyktowane jest faktem, że istnieje w API, które jest wykorzystywane.
	 * </p>
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public MrcObject save(Context context, MrcObject entityObject, boolean forceAddStore2Type, Set<TypeCode> typeCodes)
			throws MercuryException {
		DtoMrcObject e4Update = DtoMrcDataUtils.toDtoMrcObject(context, entityObject);
		WsStatusWithMrcObject result = getService(context).save(context, e4Update, forceAddStore2Type);
		DtoMrcObject dtoObject = getDto(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcObject) DtoMrcDataUtils.toMrcObject(context, dtoObject,
				collector != null ? collector : new MrcVariableReaderCollector());
	}

	/* Overridden (non-Javadoc) */
	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * <p>
	 * Parametr {@code typeCodes} nie ma znaczenia, nie jest wykorzystywany w implementacji usługi. Jego istnienie
	 * podyktowane jest faktem, że istnieje w API, które jest wykorzystywane.
	 * </p>
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public MrcList saveList(Context context, MrcList entityObjects, boolean forceAddStore2Type, Set<TypeCode> typeCodes)
			throws MercuryException {
		DtoMrcList e4Update = DtoMrcDataUtils.toDtoMrcList(context, entityObjects);
		WsStatusWithMrcList result = getService(context).saveList(context, e4Update, forceAddStore2Type);
		DtoMrcList dtoList = getDtoList(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcList) DtoMrcDataUtils.toMrcList(context, dtoList,
				collector != null ? collector : new MrcVariableReaderCollector());
	}

	/* Overridden (non-Javadoc) */
	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public MrcPagedResult searchInDB(Context context, Map<String, String> paramsMap, CredentialsMode mode,
			String objectId, String versionSeriesId, List<GroupCase2ParticipantParticipant2Role> gc2pList,
			CaseHeader header, Calendar dateFrom, Calendar dateTo, Boolean getOnlyLastCase, IPage page)
			throws MercuryException {
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
		WsStatusWithMrcObject result = getService(context).searchInDB(context, paramsMap, mode, objectId,
				versionSeriesId, gc2pList, header, cDateFrom, cDateTo, getOnlyLastCase, (PageTransportable) page);
		DtoMrcObject dtoObject = getDto(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject,
				collector != null ? collector : new MrcVariableReaderCollector());

	}

	/* Overridden (non-Javadoc) */
	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public MrcObject findLastByBpmId(Context context, Long bpmProcessId) throws MercuryException {
		WsStatusWithMrcObject result = getService(context).findLastByBpmId(context, bpmProcessId);
		DtoMrcObject dtoObject = getDto(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcObject) DtoMrcDataUtils.toMrcObject(context, dtoObject,
				collector != null ? collector : new MrcVariableReaderCollector());
	}

	/* Overridden (non-Javadoc) */
	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public MrcList findByGroupCaseIds(Context context, List<Long> groupCaseIds) throws MercuryException {
		WsStatusWithMrcList result = getService(context).findByGroupCaseIds(context, groupCaseIds);
		DtoMrcList dtoList = getDtoList(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcList) DtoMrcDataUtils.toMrcList(context, dtoList,
				collector != null ? collector : new MrcVariableReaderCollector());
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document loadSubCaseXML(Context context, String caseId, String fieldName, int maxDepth)
			throws MercuryException {
		WsStatusWithXML result = getService(context).loadSubCaseXML(context, caseId, fieldName, maxDepth);
		return WsStatusUtils.createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public IMrcCase loadSubCase(Context context, String caseId, String fieldName, int maxDepth)
			throws MercuryException {
		Document resultXML = loadSubCaseXML(context, caseId, fieldName, maxDepth);
		Node rootNode = resultXML.getDocumentElement();
		return (new MrcVariableReaderDOM()).readObject(context, rootNode);
	}

	@Override
	public Document loadCaseXML(Context context, String caseId, int maxDepth) throws MercuryException {
		WsStatusWithXML result = getService(context).findXML(context, caseId);
		return WsStatusUtils.createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document findXML(Context context, String id) throws MercuryException {
		WsStatusWithXML result = getService(context).findXML(context, id);
		return WsStatusUtils.createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document findFirstXML(Context context) throws MercuryException {
		WsStatusWithXML result = getService(context).findFirstXML(context);
		return WsStatusUtils.createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document findByIdListXML(Context context, List<String> idList) throws MercuryException {
		WsStatusWithXML result = getService(context).findByIdListXML(context, idList);
		return WsStatusUtils.createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document saveXML(Context context, Element documentXML, Boolean forceAddStore2Type) throws MercuryException {
		WsStatusWithXML result = getService(context).saveXML(context, documentXML, forceAddStore2Type);
		return WsStatusUtils.createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document searchInDBXML(Context context, Map<String, String> paramsMap, CredentialsMode mode, String objectId,
			String versionSeriesId, List<GroupCase2ParticipantParticipant2Role> gc2pList, CaseHeader header,
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
		WsStatusWithXML result = getService(context).searchInDBXML(context, paramsMap, mode, objectId, versionSeriesId,
				gc2pList, header, cDateFrom, cDateTo, getOnlyLastCase, (PageTransportable) page);
		return WsStatusUtils.createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document findLastByBpmIdXML(Context context, Long bpmProcessId) throws MercuryException {
		if (getIsRemote().booleanValue()) {
			MrcObject mrcObject = findLastByBpmId(context, bpmProcessId);
			return loadMrcObjectXML(context, mrcObject);
		} else {
			WsStatusWithXML result = getService(context).findLastByBpmIdXML(context, bpmProcessId);
			return WsStatusUtils.createDocument(result);
		}
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document findByGroupCaseIdsXML(Context context, List<Long> groupCaseIds) throws MercuryException {
		if (getIsRemote().booleanValue()) {
			MrcList mrcList = findByGroupCaseIds(context, groupCaseIds);
			return loadMrcListXML(context, mrcList);
		} else {
			WsStatusWithXML result = getService(context).findByGroupCaseIdsXML(context, groupCaseIds);
			return WsStatusUtils.createDocument(result);
		}
	}

	/* Overridden (non-Javadoc) */
	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public MrcPagedResult loadLastUpdated(Context context, IPage page) throws MercuryException {
		WsStatusWithMrcObject result = getService(context).loadLastUpdated(context, (PageTransportable) page);
		DtoMrcObject dtoObject = getDto(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject,
				collector != null ? collector : new MrcVariableReaderCollector());

	}

	/* Overridden (non-Javadoc) */
	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public MrcPagedResult loadLastUpdatedByTypeCodes(Context context, Set<String> typeCodes, IPage page)
			throws MercuryException {
		WsStatusWithMrcObject result = getService(context).loadLastUpdatedByTypeCodes(context, typeCodes,
				(PageTransportable) page);
		DtoMrcObject dtoObject = getDto(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject,
				collector != null ? collector : new MrcVariableReaderCollector());

	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document loadLastUpdatedXML(Context context, IPage page) throws MercuryException {
		if (getIsRemote().booleanValue()) {
			MrcPagedResult mrcPagedResult = loadLastUpdated(context, page);
			return loadMrcPagedResultXML(context, mrcPagedResult);
		} else {
			WsStatusWithXML result = getService(context).loadLastUpdatedXML(context, (PageTransportable) page);
			return WsStatusUtils.createDocument(result);
		}
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document loadLastUpdatedByTypeCodesXML(Context context, Set<String> typeCodes, IPage page)
			throws MercuryException {
		if (getIsRemote().booleanValue()) {
			MrcPagedResult mrcPagedResult = loadLastUpdatedByTypeCodes(context, typeCodes, page);
			return loadMrcPagedResultXML(context, mrcPagedResult);
		} else {
			WsStatusWithXML result = getService(context).loadLastUpdatedByTypeCodesXML(context, typeCodes,
					(PageTransportable) page);
			return WsStatusUtils.createDocument(result);
		}
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Long getSystemChangeNumber(Context context, String caseId) throws MercuryException {
		WsStatusWithLongValue result = getService(context).getSystemChangeNumber(context, caseId);
		if (checkWsStatus(result)) {
			return result.getValue();
		}
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public MrcSimplePropertyMapGroupedByCaseId getCaseParamsByParamNames(Context context, String caseId,
			Set<String> fields) throws MercuryException {
		WsStatusWithMrcSimplePropertyMapGroupedByCaseIdDto result = getService(context)
				.getCaseParamsByParamNames(context, caseId, fields);
		if (checkWsStatus(result)) {
			return result.getDto();
		}
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public MrcSimplePropertyMapGroupedByCaseId updateCaseParamsByParams(Context context,
			MrcSimplePropertyMapGroupedByCaseId mrcSimplePropertyMapGroupedByCaseId) throws MercuryException {
		WsStatusWithMrcSimplePropertyMapGroupedByCaseIdDto result = getService(context)
				.updateCaseParamsByParams(context, mrcSimplePropertyMapGroupedByCaseId);
		if (checkWsStatus(result)) {
			return result.getDto();
		}
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public String checkCaseStatus(Context context, String caseId) throws MercuryException {
		WsStatusWithStringValue result = getService(context).checkCaseStatus(context, caseId);
		checkWsStatus(result);
		return result.getValue();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Map<String, String> validateMrcObject(Context context, Case entityObject) throws MercuryException {
		WsStatusWithMapString2String result = getService(context).validateMrcObject(context, entityObject);
		if (checkWsStatus(result)) {
			return result.getMap();
		}
		return Collections.emptyMap();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<MrcSimplePropertyMapGroupedByCaseId> updateCasesParamsByParams(Context context,
			List<MrcSimplePropertyMapGroupedByCaseId> mrcSimplePropertyMapsGroupedByCaseId) throws MercuryException {
		WsStatusWithMrcSimplePropertyMapGroupedByCaseIdDtos result = getService(context)
				.updateCasesParamsByParams(context, mrcSimplePropertyMapsGroupedByCaseId);
		if (checkWsStatus(result)) {
			return new ArrayList<>(result.getDtos());
		}
		return Collections.emptyList();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<MrcCaseHistoryStream> loadCaseHistoryStream(Context context, String caseId, Boolean isAsc)
			throws MercuryException {
		WsStatusWithMrcCaseHistoryStreamDtos result = getService(context).loadCaseHistoryStream(context, caseId, isAsc);
		if (checkWsStatus(result)) {
			return (result.getDtos() != null ? new ArrayList<MrcCaseHistoryStream>(result.getDtos())
					: new ArrayList<MrcCaseHistoryStream>());
		}
		return Collections.emptyList();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<BpmCaseHistoryStream> loadBpmCaseHistoryStream(Context context, String caseId,
			Set<String> bpmTaskStatuses, Boolean isAsc) throws MercuryException {
		WsStatusWithBpmCaseHistoryStreamDtos result = getService(context).loadBpmCaseHistoryStream(context, caseId,
				bpmTaskStatuses, isAsc);
		if (checkWsStatus(result)) {
			return (result.getDtos() != null ? new ArrayList<BpmCaseHistoryStream>(result.getDtos())
					: new ArrayList<BpmCaseHistoryStream>());
		}
		return Collections.emptyList();
	}

	/* Overridden (non-Javadoc) */
	/**
	 * @deprecated metoda przestarzała. Obecnie istnieje usługa dedykowana do wykonywania tej operacji. Zobacz
	 *             implementację {@link CaseHistoryTraceBusiness}
	 */
	@Override
	@Deprecated
	public MrcList loadCaseHistoryTraces(Context context, String caseId, Boolean isAsc) throws MercuryException {
		WsStatusWithMrcList result = getService(context).loadCaseHistoryTraces(context, caseId, isAsc);
		DtoMrcList dtoList = getDtoList(result);
		return (MrcList) DtoMrcDataUtils.toMrcList(context, dtoList, new MrcVariableReaderCollector());
	}

	/* Overridden (non-Javadoc) */
	/**
	 * @deprecated metoda przestarzała. Obecnie istnieje usługa dedykowana do wykonywania tej operacji. Zobacz
	 *             implementację {@link CaseHistoryTraceBusiness}
	 */
	@Override
	@Deprecated
	public Document loadCaseHistoryTracesXML(Context context, String caseId, Boolean isAsc) throws MercuryException {
		WsStatusWithXML result = getService(context).loadCaseHistoryTracesXML(context, caseId, isAsc);
		return WsStatusUtils.createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<MrcComment> loadCaseComments(Context context, String caseId, Boolean isAsc) throws MercuryException {
		WsStatusWithMrcCommentDtos result = getService(context).loadCaseComments(context, caseId, isAsc);
		if (checkWsStatus(result)) {
			return (result.getDtos() != null ? new ArrayList<MrcComment>(result.getDtos())
					: new ArrayList<MrcComment>());
		}
		return Collections.emptyList();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<MrcQuickTask> loadCaseQuickTasks(Context context, String caseId, Boolean isAsc)
			throws MercuryException {
		WsStatusWithMrcQuickTaskDtos result = getService(context).loadCaseQuickTasks(context, caseId, isAsc);
		if (checkWsStatus(result)) {
			return (result.getDtos() != null ? new ArrayList<MrcQuickTask>(result.getDtos())
					: new ArrayList<MrcQuickTask>());
		}
		return Collections.emptyList();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<MrcCaseDocument> loadCaseDocuments(Context context, String caseId, Boolean isAsc)
			throws MercuryException {
		WsStatusWithMrcCaseDocumentDtos result = getService(context).loadCaseDocuments(context, caseId, isAsc);
		if (checkWsStatus(result)) {
			return (result.getDtos() != null ? new ArrayList<MrcCaseDocument>(result.getDtos())
					: new ArrayList<MrcCaseDocument>());
		}
		return Collections.emptyList();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document loadSubCaseListXML(Context context, String caseId, String fieldName, int maxDepth, IPage page)
			throws MercuryException {
		WsStatusWithXML result = getService(context).loadSubCaseListXML(context, caseId, fieldName, maxDepth,
				(PageTransportable) page);
		return WsStatusUtils.createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public MrcPagedResult loadSubCaseList(Context context, String caseId, String fieldName, int maxDepth, IPage page)
			throws MercuryException {
		WsStatusWithMrcObject result = getService(context).loadSubCaseList(context, caseId, fieldName, maxDepth,
				(PageTransportable) page);
		DtoMrcObject dtoObject = getDto(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject,
				collector != null ? collector : new MrcVariableReaderCollector());
	}

	/* Overridden (non-Javadoc) */
	@Override
	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	public MrcObject findByPKProperty(Context context, String typeCode, String paramValue) throws MercuryException {
		WsStatusWithMrcObject result = getService(context).findByPKProperty(context, typeCode, paramValue);
		DtoMrcObject dtoObject = getDto(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcObject) DtoMrcDataUtils.toMrcObject(context, dtoObject,
				collector != null ? collector : new MrcVariableReaderCollector());
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document findByPKPropertyXML(Context context, String typeCode, String paramValue) throws MercuryException {
		WsStatusWithXML result = getService(context).findByPKPropertyXML(context, typeCode, paramValue);
		return WsStatusUtils.createDocument(result);

	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document loadSampleByTypeCodeXML(Context context, String typeCode) throws MercuryException {
		WsStatusWithXML result = getService(context).loadSampleByTypeCodeXML(context, typeCode);
		return WsStatusUtils.createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document loadSampleByTypeNameXML(Context context, String typeName) throws MercuryException {
		WsStatusWithXML result = getService(context).loadSampleByTypeNameXML(context, typeName);
		return WsStatusUtils.createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public MrcObject loadSampleByTypeCode(Context context, String typeCode) throws MercuryException {
		WsStatusWithMrcObject result = getService(context).loadSampleByTypeCode(context, typeCode);
		DtoMrcObject dtoObject = getDto(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcObject) DtoMrcDataUtils.toMrcObject(context, dtoObject,
				collector != null ? collector : new MrcVariableReaderCollector());
	}

	/* Overridden (non-Javadoc) */
	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public MrcObject loadSampleByTypeName(Context context, String typeName) throws MercuryException {
		WsStatusWithMrcObject result = getService(context).loadSampleByTypeName(context, typeName);
		DtoMrcObject dtoObject = getDto(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcObject) DtoMrcDataUtils.toMrcObject(context, dtoObject,
				collector != null ? collector : new MrcVariableReaderCollector());
	}

	/* Overridden (non-Javadoc) */
	@Override
	public void clearQueryCache(Context context) throws MercuryException {
		WsStatus wsStatus = getService(context).clearQueryCache(context);
		checkWsStatus(wsStatus);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Long initModelXML(Context context, Element documentXML, Boolean forceAddStore2Type) throws MercuryException {
		WsStatusWithLongValue wsStatus = getService(context).initModelXML(context, documentXML, forceAddStore2Type);
		checkWsStatus(wsStatus);
		return wsStatus.getValue();
	}

	/* Overridden (non-Javadoc) */
	/**
	 * <p>
	 * Parametr {@code typeCodes} nie ma znaczenia, nie jest wykorzystywany w implementacji usługi. Jego istnienie
	 * podyktowane jest faktem, że istnieje w API, które jest wykorzystywane.
	 * </p>
	 */
	@Override
	public Long initModel(Context context, MrcObject entityObject, boolean forceAddStore2Type, Set<TypeCode> typeCodes)
			throws MercuryException {
		DtoMrcObject e4Update = DtoMrcDataUtils.toDtoMrcObject(context, entityObject);
		WsStatusWithLongValue wsStatus = getService(context).initModel(context, e4Update, forceAddStore2Type);
		checkWsStatus(wsStatus);
		return wsStatus.getValue();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Long changeModel(Context context, Long fromTypeId, Long toTypeId, boolean forceAddStore2Type)
			throws MercuryException {
		WsStatusWithLongValue wsStatus = getService(context).changeModel(context, fromTypeId, toTypeId,
				forceAddStore2Type);
		checkWsStatus(wsStatus);
		return wsStatus.getValue();
	}

	/* Overridden (non-Javadoc) */
	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public MrcObject searchByLuceneId(Context context, String caseId) throws MercuryException {
		WsStatusWithMrcObject result = getService(context).searchByLuceneId(context, caseId);
		DtoMrcObject dtoObject = getDto(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcObject) DtoMrcDataUtils.toMrcObject(context, dtoObject,
				collector != null ? collector : new MrcVariableReaderCollector());
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document searchByLuceneIdXML(Context context, String caseId) throws MercuryException {
		WsStatusWithXML result = getService(context).searchByLuceneIdXML(context, caseId);
		return WsStatusUtils.createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public MrcPagedResult searchByInventoryCode(Context context, String searchText, IPage page)
			throws MercuryException {
		WsStatusWithMrcObject result = getService(context).searchByInventoryCode(context, searchText,
				(PageTransportable) page);
		DtoMrcObject dtoObject = getDto(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject,
				collector != null ? collector : new MrcVariableReaderCollector());
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document searchByInventoryCodeXML(Context context, String searchText, IPage page) throws MercuryException {
		WsStatusWithXML result = getService(context).searchByInventoryCodeXML(context, searchText,
				(PageTransportable) page);
		return WsStatusUtils.createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public MrcPagedResult searchByQuery(Context context, String query, IPage page, String sortClause,
			String additionalDateRange) throws MercuryException {
		WsStatusWithMrcObject result = getService(context).searchByQuery(context, query, (PageTransportable) page,
				sortClause, additionalDateRange);
		DtoMrcObject dtoObject = getDto(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject,
				collector != null ? collector : new MrcVariableReaderCollector());

	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document searchByQueryXML(Context context, String query, IPage page, String sortClause,
			String additionalDateRange) throws MercuryException {
		if (getIsRemote().booleanValue()) {
			MrcPagedResult mrcPagedResult = searchByQuery(context, query, page, sortClause, additionalDateRange);
			return loadMrcPagedResultXML(context, mrcPagedResult);
		} else {
			WsStatusWithXML result = getService(context).searchByQueryXML(context, query, (PageTransportable) page,
					sortClause, additionalDateRange);
			return WsStatusUtils.createDocument(result);
		}
	}

	/* Overridden (non-Javadoc) */
	/**
	 * @deprecated Metoda przestarzała, do usunięcia od wersji 3.0.4.x. Należy używać metody
	 *             {@link #searchByQuery(Context, String, IPage, String, String)}.
	 */
	@Deprecated
	@Override
	public MrcPagedResult searchByParams(Context context, Map<String, String> paramsMap, CredentialsMode mode,
			IPage page, String sortClause, String additionalDateRange) throws MercuryException {
		WsStatusWithMrcObject result = getService(context).searchByParams(context, paramsMap, mode,
				(PageTransportable) page, sortClause, additionalDateRange);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject, new MrcVariableReaderCollector());
	}

	/* Overridden (non-Javadoc) */
	/**
	 * @deprecated Metoda przestarzała, do usunięcia od wersji 3.0.4.x. Należy używać metody
	 *             {@link #searchByQueryXML(Context, String, IPage, String, String)}.
	 */
	@Deprecated
	@Override
	public Document searchByParamsXML(Context context, Map<String, String> paramsMap, CredentialsMode mode, IPage page,
			String sortClause, String additionalDateRange) throws MercuryException {
		WsStatusWithXML result = getService(context).searchByParamsXML(context, paramsMap, mode,
				(PageTransportable) page, sortClause, additionalDateRange);
		return WsStatusUtils.createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	/**
	 * @deprecated Metoda przestarzała, do usunięcia od wersji 3.0.4.x. Należy używać metody
	 *             {@link #searchByQuery(Context, String, IPage, String, String)}.
	 */
	@Deprecated
	@Override
	public MrcPagedResult searchByQueries(Context context, Map<String, String> gueriesMap, IPage page,
			String sortClause, String additionalDateRange) throws MercuryException {
		WsStatusWithMrcObject result = getService(context).searchLuceneWithQueriesWithSorting(context, gueriesMap,
				(PageTransportable) page, sortClause);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject, new MrcVariableReaderCollector());
	}

	/* Overridden (non-Javadoc) */
	/**
	 * @deprecated Metoda przestarzała, do usunięcia od wersji 3.0.4.x. Należy używać metody
	 *             {@link #searchByQueryXML(Context, String, IPage, String, String)}.
	 */
	@Deprecated
	@Override
	public Document searchByQueriesXML(Context context, Map<String, String> queriesMap, IPage page, String sortClause,
			String additionalDateRange) throws MercuryException {
		WsStatusWithXML result = getService(context).searchLuceneWithQueriesXMLWithSorting(context, queriesMap,
				(PageTransportable) page, sortClause);
		return WsStatusUtils.createDocument(result);
	}

}
