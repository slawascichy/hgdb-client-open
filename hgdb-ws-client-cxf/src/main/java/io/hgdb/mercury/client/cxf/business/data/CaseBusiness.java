package io.hgdb.mercury.client.cxf.business.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import io.hgdb.mercury.client.cxf.WsClient;
import pro.ibpm.mercury.attrs.CaseDateUtils;
import pro.ibpm.mercury.attrs.javax.CaseDate;
import pro.ibpm.mercury.business.data.api.BpmCaseHistoryStream;
import pro.ibpm.mercury.business.data.api.CaseHeader;
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
import pro.ibpm.mercury.business.data.utils.MrcVariableReaderDOM;
import pro.ibpm.mercury.business.data.utils.XMLReaderHelper;
import pro.ibpm.mercury.business.data.utils.XMLVariableBuilder;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.cse.CredentialsMode;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.data.Case;
import pro.ibpm.mercury.exceptions.InternalErrorException;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.GroupCase2ParticipantParticipant2Role;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.ws.server.api.actions.business.data.ICaseBusinessAction;
import pro.ibpm.mercury.ws.server.api.returns.DtoMrcDataUtils;
import pro.ibpm.mercury.ws.server.api.returns.DtoMrcList;
import pro.ibpm.mercury.ws.server.api.returns.DtoMrcObject;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatus;
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

	private Boolean isRemote;

	protected DtoMrcList getDtoList(final WsStatusWithMrcList wsStatusWithDto) throws MercuryException {
		if (checkWsStatus((IWsStatus) wsStatusWithDto)) {
			if (logger.isDebugEnabled()) {
				logger.debug("-->getDtoList: status: {}", StringUtils.isBlank(wsStatusWithDto.getErrorMessage()) ? "OK"
						: wsStatusWithDto.getErrorMessage());
			}
			return wsStatusWithDto.getDto();
		}
		return null;
	}

	protected Element getDtoXMLElement(final WsStatusWithXML wsStatusWithXML) throws MercuryException {
		Element currElement = null;
		if (checkWsStatus((IWsStatus) wsStatusWithXML)) {
			if (logger.isDebugEnabled()) {
				logger.debug("-->getDtoXMLElement: status: {}",
						StringUtils.isBlank(wsStatusWithXML.getErrorMessage()) ? "OK"
								: wsStatusWithXML.getErrorMessage());
			}
			Element response = (Element) wsStatusWithXML.getDocument();
			if (response != null) {
				String responseNodeName = response.getNodeName();
				if (logger.isDebugEnabled()) {
					logger.debug("-->getDtoXMLElement: response.nodeName: {}", responseNodeName);
				}
				if ("document".equals(responseNodeName)) {
					NodeList children = response.getChildNodes();
					int numChildren = children.getLength();
					for (int i = 0; i < numChildren; i++) {
						Node child = children.item(i);
						if (child instanceof Element) {
							currElement = (Element) child;
							break;
						}
					}
				} else {
					currElement = response;
				}
			} else if (logger.isDebugEnabled()) {
				logger.debug("-->getDtoXMLElement: response is NULL!");
			}
		}
		return currElement;
	}

	private Document createDocument(WsStatusWithXML result) throws MercuryException, FactoryConfigurationError {
		Long startTime = Calendar.getInstance().getTimeInMillis();
		Element xmlElement = getDtoXMLElement(result);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.newDocument();
			if (xmlElement != null) {
				Node firstDocImportedNode = document.importNode(xmlElement, true);
				document.appendChild(firstDocImportedNode);
			}
			return document;
		} catch (ParserConfigurationException e) {
			throw new InternalErrorException(e);
		} finally {
			if (logger.isDebugEnabled()) {
				Long endTime = Calendar.getInstance().getTimeInMillis();
				logger.debug("-->createDocument: time: {}[ms]", endTime - startTime);
			}
		}
	}

	@Override
	public MrcObject persistentClassNewInstance(Context context) {
		return new MrcObject();
	}

	@Override
	public MrcPagedResult pagedResultNewInstance(Context context) {
		MrcObjectMetadata metadata = new MrcObjectMetadata();
		metadata.setClassName(IMrcPagedResult.CLASS_NAME);
		return new MrcPagedResult(metadata);
	}

	@Override
	public MrcObject find(Context context, Long id) throws MercuryException {
		WsStatusWithMrcObject result = getService().find(context, id);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcObject) DtoMrcDataUtils.toMrcObject(context, dtoObject);
	}

	@Override
	public MrcObject findFirst(Context context) throws MercuryException {
		WsStatusWithMrcObject result = getService().findFirst(context);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcObject) DtoMrcDataUtils.toMrcObject(context, dtoObject);
	}

	@Override
	public Long remove(Context context, Long caseId) throws MercuryException {
		WsStatus result = getService().remove(context, caseId);
		checkWsStatus(result);
		return caseId;
	}

	@Override
	public List<Long> removeList(Context context, List<Long> idList) throws MercuryException {
		WsStatus result = getService().removeList(context, idList);
		checkWsStatus(result);
		return idList;
	}

	@Override
	public MrcList findByIdList(Context context, List<Long> idList) throws MercuryException {
		WsStatusWithMrcList result = getService().findByIdList(context, idList);
		DtoMrcList dtoList = getDtoList(result);
		return (MrcList) DtoMrcDataUtils.toMrcList(context, dtoList);
	}

	@Override
	public MrcObject save(Context context, MrcObject entityObject, boolean forceAddStore2Type) throws MercuryException {
		DtoMrcObject e4Update = DtoMrcDataUtils.toDtoMrcObject(context, entityObject);
		WsStatusWithMrcObject result = getService().save(context, e4Update, forceAddStore2Type);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcObject) DtoMrcDataUtils.toMrcObject(context, dtoObject);
	}

	@Override
	public MrcList saveList(Context context, MrcList entityObjects, boolean forceAddStore2Type)
			throws MercuryException {
		DtoMrcList e4Update = DtoMrcDataUtils.toDtoMrcList(context, entityObjects);
		WsStatusWithMrcList result = getService().saveList(context, e4Update, forceAddStore2Type);
		DtoMrcList dtoList = getDtoList(result);
		return (MrcList) DtoMrcDataUtils.toMrcList(context, dtoList);
	}

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
		WsStatusWithMrcObject result = getService().searchInDB(context, paramsMap, mode, objectId, versionSeriesId,
				gc2pList, header, cDateFrom, cDateTo, getOnlyLastCase, (PageTransportable) page);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject);
	}

	@Override
	public MrcPagedResult searchLuceneByParams(Context context, Map<String, String> paramsMap, CredentialsMode mode,
			IPage page) throws MercuryException {
		WsStatusWithMrcObject result = getService().searchLuceneByParams(context, paramsMap, mode,
				(PageTransportable) page);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject);
	}

	@Override
	public MrcPagedResult searchLuceneByQuery(Context context, String query, IPage page) throws MercuryException {
		WsStatusWithMrcObject result = getService().searchLuceneByQuery(context, query, (PageTransportable) page);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject);
	}

	@Override
	public MrcObject findLastByBpmId(Context context, Long bpmProcessId) throws MercuryException {
		WsStatusWithMrcObject result = getService().findLastByBpmId(context, bpmProcessId);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcObject) DtoMrcDataUtils.toMrcObject(context, dtoObject);
	}

	@Override
	public MrcList findByGroupCaseIds(Context context, List<Long> groupCaseIds) throws MercuryException {
		WsStatusWithMrcList result = getService().findByGroupCaseIds(context, groupCaseIds);
		DtoMrcList dtoList = getDtoList(result);
		return (MrcList) DtoMrcDataUtils.toMrcList(context, dtoList);
	}

	@Override
	public Document loadSubCaseXML(Context context, Long caseId, String fieldName, int maxDepth)
			throws MercuryException {
		WsStatusWithXML result = getService().loadSubCaseXML(context, caseId, fieldName, maxDepth);
		return createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public IMrcCase loadSubCase(Context context, Long caseId, String fieldName, int maxDepth) throws MercuryException {
		Document resultXML = loadSubCaseXML(context, caseId, fieldName, maxDepth);
		Node rootNode = resultXML.getDocumentElement();
		return (new MrcVariableReaderDOM()).readObject(context, rootNode);
	}

	@Override
	public Document loadCaseXML(Context context, Long caseId, int maxDepth) throws MercuryException {
		WsStatusWithXML result = getService().findXML(context, caseId);
		return createDocument(result);
	}

	@Override
	public Document loadMrcObjectXML(Context context, MrcObject mrcObject) throws MercuryException {
		try {
			return XMLVariableBuilder.buildObjectVariable(context, XMLReaderHelper.TAG_MAIN_DOCUMENT, mrcObject);
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}
	}

	@Override
	public Document loadMrcListXML(Context context, MrcList mrcList) throws MercuryException {
		try {
			return XMLVariableBuilder.buildObjectVariable(context, XMLReaderHelper.TAG_MAIN_DOCUMENT, mrcList);
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}
	}

	@Override
	public Document loadMrcPagedResultXML(Context context, MrcPagedResult mrcPagedResult) throws MercuryException {
		try {
			return XMLVariableBuilder.buildObjectVariable(context, XMLReaderHelper.TAG_MAIN_DOCUMENT, mrcPagedResult);
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document findXML(Context context, Long id) throws MercuryException {
		WsStatusWithXML result = getService().findXML(context, id);
		return createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document findFirstXML(Context context) throws MercuryException {
		WsStatusWithXML result = getService().findFirstXML(context);
		return createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document findByIdListXML(Context context, List<Long> idList) throws MercuryException {
		WsStatusWithXML result = getService().findByIdListXML(context, idList);
		return createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document saveXML(Context context, Element documentXML, Boolean forceAddStore2Type) throws MercuryException {
		WsStatusWithXML result = getService().saveXML(context, documentXML, forceAddStore2Type);
		return createDocument(result);
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
		WsStatusWithXML result = getService().searchInDBXML(context, paramsMap, mode, objectId, versionSeriesId,
				gc2pList, header, cDateFrom, cDateTo, getOnlyLastCase, (PageTransportable) page);
		return createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document searchLuceneByParamsXML(Context context, Map<String, String> paramsMap, CredentialsMode mode,
			IPage page) throws MercuryException {
		WsStatusWithXML result = getService().searchLuceneByParamsXML(context, paramsMap, mode,
				(PageTransportable) page);
		return createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document searchLuceneByQueryXML(Context context, String query, IPage page) throws MercuryException {
		if (getIsRemote()) {
			MrcPagedResult mrcPagedResult = searchLuceneByQuery(context, query, (PageTransportable) page);
			return loadMrcPagedResultXML(context, mrcPagedResult);
		} else {
			WsStatusWithXML result = getService().searchLuceneByQueryXML(context, query, (PageTransportable) page);
			return createDocument(result);
		}
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document findLastByBpmIdXML(Context context, Long bpmProcessId) throws MercuryException {
		if (getIsRemote()) {
			MrcObject mrcObject = findLastByBpmId(context, bpmProcessId);
			return loadMrcObjectXML(context, mrcObject);
		} else {
			WsStatusWithXML result = getService().findLastByBpmIdXML(context, bpmProcessId);
			return createDocument(result);
		}
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document findByGroupCaseIdsXML(Context context, List<Long> groupCaseIds) throws MercuryException {
		if (getIsRemote()) {
			MrcList mrcList = findByGroupCaseIds(context, groupCaseIds);
			return loadMrcListXML(context, mrcList);
		} else {
			WsStatusWithXML result = getService().findByGroupCaseIdsXML(context, groupCaseIds);
			return createDocument(result);
		}
	}

	@Override
	public MrcPagedResult loadLastUpdated(Context context, IPage page) throws MercuryException {
		WsStatusWithMrcObject result = getService().loadLastUpdated(context, (PageTransportable) page);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject);
	}

	@Override
	public MrcPagedResult loadLastUpdatedByTypeCodes(Context context, Set<String> typeCodes, IPage page)
			throws MercuryException {
		WsStatusWithMrcObject result = getService().loadLastUpdatedByTypeCodes(context, typeCodes,
				(PageTransportable) page);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject);
	}

	@Override
	public Document loadLastUpdatedXML(Context context, IPage page) throws MercuryException {
		if (getIsRemote()) {
			MrcPagedResult mrcPagedResult = loadLastUpdated(context, page);
			return loadMrcPagedResultXML(context, mrcPagedResult);
		} else {
			WsStatusWithXML result = getService().loadLastUpdatedXML(context, (PageTransportable) page);
			return createDocument(result);
		}
	}

	@Override
	public Document loadLastUpdatedByTypeCodesXML(Context context, Set<String> typeCodes, IPage page)
			throws MercuryException {
		if (getIsRemote()) {
			MrcPagedResult mrcPagedResult = loadLastUpdatedByTypeCodes(context, typeCodes, page);
			return loadMrcPagedResultXML(context, mrcPagedResult);
		} else {
			WsStatusWithXML result = getService().loadLastUpdatedByTypeCodesXML(context, typeCodes,
					(PageTransportable) page);
			return createDocument(result);
		}
	}

	@Override
	public MrcPagedResult searchLuceneWithQueries(Context context, Map<String, String> gueriesMap, IPage page)
			throws MercuryException {
		WsStatusWithMrcObject result = getService().searchLuceneWithQueries(context, gueriesMap,
				(PageTransportable) page);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject);
	}

	@Override
	public Document searchLuceneWithQueriesXML(Context context, Map<String, String> queriesMap, IPage page)
			throws MercuryException {
		WsStatusWithXML result = getService().searchLuceneWithQueriesXML(context, queriesMap, (PageTransportable) page);
		return createDocument(result);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Long getSystemChangeNumber(Context context, Long caseId) throws MercuryException {
		WsStatusWithLongValue result = getService().getSystemChangeNumber(context, caseId);
		if (checkWsStatus((IWsStatus) result)) {
			return result.getValue();
		}
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public MrcSimplePropertyMapGroupedByCaseId getCaseParamsByParamNames(Context context, Long caseId,
			Set<String> fields) throws MercuryException {
		WsStatusWithMrcSimplePropertyMapGroupedByCaseIdDto result = getService().getCaseParamsByParamNames(context,
				caseId, fields);
		if (checkWsStatus((IWsStatus) result)) {
			return result.getDto();
		}
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public MrcSimplePropertyMapGroupedByCaseId updateCaseParamsByParams(Context context,
			MrcSimplePropertyMapGroupedByCaseId mrcSimplePropertyMapGroupedByCaseId) throws MercuryException {
		WsStatusWithMrcSimplePropertyMapGroupedByCaseIdDto result = getService().updateCaseParamsByParams(context,
				mrcSimplePropertyMapGroupedByCaseId);
		if (checkWsStatus((IWsStatus) result)) {
			return result.getDto();
		}
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Long checkCaseStatus(Context context, Long caseId) throws MercuryException {
		WsStatusWithLongValue result = getService().checkCaseStatus(context, caseId);
		if (checkWsStatus((IWsStatus) result)) {
			return result.getValue();
		}
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Map<String, String> validateMrcObject(Context context, Case entityObject) throws MercuryException {
		WsStatusWithMapString2String result = getService().validateMrcObject(context, entityObject);
		if (checkWsStatus((IWsStatus) result)) {
			return result.getMap();
		}
		return Collections.emptyMap();
	}

	@Override
	public List<MrcSimplePropertyMapGroupedByCaseId> updateCasesParamsByParams(Context context,
			List<MrcSimplePropertyMapGroupedByCaseId> mrcSimplePropertyMapsGroupedByCaseId) throws MercuryException {
		WsStatusWithMrcSimplePropertyMapGroupedByCaseIdDtos result = getService().updateCasesParamsByParams(context,
				mrcSimplePropertyMapsGroupedByCaseId);
		if (checkWsStatus((IWsStatus) result)) {
			return new ArrayList<>(result.getDtos());
		}
		return Collections.emptyList();
	}

	@Override
	public List<MrcCaseHistoryStream> loadCaseHistoryStream(Context context, Long caseId, Boolean isAsc)
			throws MercuryException {
		WsStatusWithMrcCaseHistoryStreamDtos result = getService().loadCaseHistoryStream(context, caseId, isAsc);
		if (checkWsStatus((IWsStatus) result)) {
			return (result.getDtos() != null ? new ArrayList<MrcCaseHistoryStream>(result.getDtos())
					: new ArrayList<MrcCaseHistoryStream>());
		}
		return Collections.emptyList();
	}

	@Override
	public List<BpmCaseHistoryStream> loadBpmCaseHistoryStream(Context context, Long caseId,
			Set<String> bpmTaskStatuses, Boolean isAsc) throws MercuryException {
		WsStatusWithBpmCaseHistoryStreamDtos result = getService().loadBpmCaseHistoryStream(context, caseId,
				bpmTaskStatuses, isAsc);
		if (checkWsStatus((IWsStatus) result)) {
			return (result.getDtos() != null ? new ArrayList<BpmCaseHistoryStream>(result.getDtos())
					: new ArrayList<BpmCaseHistoryStream>());
		}
		return Collections.emptyList();
	}

	@Override
	public MrcList loadCaseHistoryTraces(Context context, Long caseId, Boolean isAsc) throws MercuryException {
		WsStatusWithMrcList result = getService().loadCaseHistoryTraces(context, caseId, isAsc);
		DtoMrcList dtoList = getDtoList(result);
		return (MrcList) DtoMrcDataUtils.toMrcList(context, dtoList);
	}

	@Override
	public Document loadCaseHistoryTracesXML(Context context, Long caseId, Boolean isAsc) throws MercuryException {
		WsStatusWithXML result = getService().loadCaseHistoryTracesXML(context, caseId, isAsc);
		return createDocument(result);
	}

	@Override
	public List<MrcComment> loadCaseComments(Context context, Long caseId, Boolean isAsc) throws MercuryException {
		WsStatusWithMrcCommentDtos result = getService().loadCaseComments(context, caseId, isAsc);
		if (checkWsStatus((IWsStatus) result)) {
			return (result.getDtos() != null ? new ArrayList<MrcComment>(result.getDtos())
					: new ArrayList<MrcComment>());
		}
		return Collections.emptyList();
	}

	@Override
	public List<MrcQuickTask> loadCaseQuickTasks(Context context, Long caseId, Boolean isAsc) throws MercuryException {
		WsStatusWithMrcQuickTaskDtos result = getService().loadCaseQuickTasks(context, caseId, isAsc);
		if (checkWsStatus((IWsStatus) result)) {
			return (result.getDtos() != null ? new ArrayList<MrcQuickTask>(result.getDtos())
					: new ArrayList<MrcQuickTask>());
		}
		return Collections.emptyList();
	}

	@Override
	public List<MrcCaseDocument> loadCaseDocuments(Context context, Long caseId, Boolean isAsc)
			throws MercuryException {
		WsStatusWithMrcCaseDocumentDtos result = getService().loadCaseDocuments(context, caseId, isAsc);
		if (checkWsStatus((IWsStatus) result)) {
			return (result.getDtos() != null ? new ArrayList<MrcCaseDocument>(result.getDtos())
					: new ArrayList<MrcCaseDocument>());
		}
		return Collections.emptyList();
	}

	@Override
	public Document loadSubCaseListXML(Context context, Long caseId, String fieldName, int maxDepth, IPage page)
			throws MercuryException {
		WsStatusWithXML result = getService().loadSubCaseListXML(context, caseId, fieldName, maxDepth,
				(PageTransportable) page);
		return createDocument(result);
	}

	@Override
	public MrcPagedResult loadSubCaseList(Context context, Long caseId, String fieldName, int maxDepth, IPage page)
			throws MercuryException {
		WsStatusWithMrcObject result = getService().loadSubCaseList(context, caseId, fieldName, maxDepth,
				(PageTransportable) page);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject);

	}

	/* Overridden (non-Javadoc) */
	@Override
	public MrcObject findByPKProperty(Context context, String typeCode, String paramValue) throws MercuryException {
		WsStatusWithMrcObject result = getService().findByPKProperty(context, typeCode, paramValue);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcObject) DtoMrcDataUtils.toMrcObject(context, dtoObject);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Document findByPKPropertyXML(Context context, String typeCode, String paramValue) throws MercuryException {
		WsStatusWithXML result = getService().findByPKPropertyXML(context, typeCode, paramValue);
		return createDocument(result);

	}

	@Override
	public Document searchLuceneByIdXML(Context context, Long caseId) throws MercuryException {
		WsStatusWithXML result = getService().searchLuceneByIdXML(context, caseId);
		return createDocument(result);
	}

	@Override
	public Document searchLuceneByInventoryCodeXML(Context context, String searchText, IPage page)
			throws MercuryException {
		WsStatusWithXML result = getService().searchLuceneByInventoryCodeXML(context, searchText,
				(PageTransportable) page);
		return createDocument(result);
	}

	@Override
	public MrcObject searchLuceneById(Context context, Long caseId) throws MercuryException {
		WsStatusWithMrcObject result = getService().searchLuceneById(context, caseId);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcObject) DtoMrcDataUtils.toMrcObject(context, dtoObject);
	}

	@Override
	public MrcPagedResult searchLuceneByInventoryCode(Context context, String searchText, IPage page)
			throws MercuryException {
		WsStatusWithMrcObject result = getService().searchLuceneByInventoryCode(context, searchText,
				(PageTransportable) page);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject);
	}

	@Override
	public Document loadSampleByTypeCodeXML(Context context, String typeCode) throws MercuryException {
		WsStatusWithXML result = getService().loadSampleByTypeCodeXML(context, typeCode);
		return createDocument(result);
	}

	@Override
	public Document loadSampleByTypeNameXML(Context context, String typeName) throws MercuryException {
		WsStatusWithXML result = getService().loadSampleByTypeNameXML(context, typeName);
		return createDocument(result);
	}

	@Override
	public MrcObject loadSampleByTypeCode(Context context, String typeCode) throws MercuryException {
		WsStatusWithMrcObject result = getService().loadSampleByTypeCode(context, typeCode);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcObject) DtoMrcDataUtils.toMrcObject(context, dtoObject);
	}

	@Override
	public MrcObject loadSampleByTypeName(Context context, String typeName) throws MercuryException {
		WsStatusWithMrcObject result = getService().loadSampleByTypeName(context, typeName);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcObject) DtoMrcDataUtils.toMrcObject(context, dtoObject);
	}

	/**
	 * @return the {@link #isRemote}
	 */
	public Boolean getIsRemote() {
		return isRemote;
	}

	/**
	 * @param isRemote
	 *            the {@link #isRemote} to set
	 */
	public void setIsRemote(Boolean isRemote) {
		this.isRemote = isRemote;
	}

	@Override
	public void clearQueryCache(Context context) throws MercuryException {
		WsStatus wsStatus = getService().clearQueryCache(context);
		checkWsStatus(wsStatus);
	}

}
