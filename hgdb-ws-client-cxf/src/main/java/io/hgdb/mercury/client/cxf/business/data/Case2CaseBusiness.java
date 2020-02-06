package io.hgdb.mercury.client.cxf.business.data;

import java.util.Collection;

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
import pro.ibpm.mercury.business.data.api.Case2CaseRelationship;
import pro.ibpm.mercury.business.data.api.ICase2CaseBusiness;
import pro.ibpm.mercury.business.data.api.ICase2CaseBusinessXML;
import pro.ibpm.mercury.business.data.api.MrcPagedResult;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.dto.paging.PagedResult;
import pro.ibpm.mercury.dto.paging.PagingInfo;
import pro.ibpm.mercury.entities.helpers.CatalogHelper;
import pro.ibpm.mercury.exceptions.InternalErrorException;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.exceptions.LC025MethodNotSupportedException;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.business.data.ICase2CaseBusinessAction;
import pro.ibpm.mercury.ws.server.api.returns.Case2CaseRelationshipPagedResult;
import pro.ibpm.mercury.ws.server.api.returns.DtoMrcDataUtils;
import pro.ibpm.mercury.ws.server.api.returns.DtoMrcObject;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatus;
import pro.ibpm.mercury.ws.server.api.returns.WsStatus;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithCase2CaseRelationship;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithCase2CaseRelationshipPagedResult;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithMrcObject;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithXML;

public class Case2CaseBusiness extends WsClient<ICase2CaseBusinessAction>
		implements ICase2CaseBusiness, ICase2CaseBusinessXML {

	private Boolean isRemote;

	protected Element getDtoXMLElement(final WsStatusWithXML wsStatusWithXML) throws MercuryException {
		Element currElement = null;
		if (checkWsStatus((IWsStatus) wsStatusWithXML)) {
			if (logger.isDebugEnabled()) {
				logger.debug("-->getDtoXMLElement: status: {}",
						StringUtils.isBlank(wsStatusWithXML.getErrorMessage()) ? "OK"
								: wsStatusWithXML.getErrorMessage());
			}
			Element response = (Element) wsStatusWithXML.getDto();
			if (response != null) {
				String responseNodeName = response.getNodeName();
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
			}
		}
		return currElement;
	}

	private Document createDocument(WsStatusWithXML result) throws MercuryException, FactoryConfigurationError {
		Element xmlElement = getDtoXMLElement(result);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.newDocument();
			Node firstDocImportedNode = document.importNode(xmlElement, true);
			document.appendChild(firstDocImportedNode);
			return document;
		} catch (ParserConfigurationException e) {
			throw new InternalErrorException(e);
		}
	}

	protected IPagedResult<Case2CaseRelationship, IPage> getPagedResult(Context context,
			final WsStatusWithCase2CaseRelationshipPagedResult wsStatusWithPagedResult) throws MercuryException {
		if (checkWsStatus((IWsStatus) wsStatusWithPagedResult)) {
			if (logger.isDebugEnabled()) {
				logger.debug("--> getPagedResult: wsStatusWithPagedResult={}", wsStatusWithPagedResult);
			}
			try {
				/* czytamy rezultat z serwisu */
				Case2CaseRelationshipPagedResult pagedResultDto = wsStatusWithPagedResult.getPagedResult();
				Collection<Case2CaseRelationship> dtos = pagedResultDto.getResult();
				/* Teraz trzeba utworzyć obiekt stronicowanego wyniku z encją */
				PagedResult<Case2CaseRelationship> result = new PagedResult<>(new PagingInfo(pagedResultDto));
				result.setResult(dtos);
				return result;
			} catch (Exception e) {
				throw new InternalErrorException(e);
			}
		}
		return null;
	}

	@Override
	public IPagedResult<Case2CaseRelationship, IPage> pagedResultNewInstance(Context context) {
		throw new IllegalStateException(new LC025MethodNotSupportedException());
	}

	@Override
	public Case2CaseRelationship find(Context context, String path) throws MercuryException {
		WsStatusWithCase2CaseRelationship wsStatusWithDto = getService().find(context, path);
		return getDto(wsStatusWithDto);
	}

	@Override
	public Case2CaseRelationship insert(Context context, Case2CaseRelationship entityObject) throws MercuryException {
		WsStatusWithCase2CaseRelationship wsStatusWithDto = getService().insert(context, entityObject);
		return getDto(wsStatusWithDto);
	}

	@Override
	public String remove(Context context, Case2CaseRelationship entityObject) throws MercuryException {
		WsStatus result = getService().remove(context, entityObject);
		checkWsStatus(result);
		if (StringUtils.isNotBlank(entityObject.getPath())) {
			return entityObject.getPath();
		} else {
			return CatalogHelper.buildSimplePath(entityObject.getParent().getCaseId(),
					entityObject.getChild().getCaseId(), entityObject.getMountPoint());
		}
	}

	@Override
	public Case2CaseRelationship addNodeToMountPoint(Context context, Long caseId, String mountPoint)
			throws MercuryException {
		WsStatusWithCase2CaseRelationship wsStatusWithDto = getService().addNodeToMountPoint(context, caseId,
				mountPoint);
		return getDto(wsStatusWithDto);
	}

	@Override
	public void removeNodeFromMountPoint(Context context, Long caseId, String mountPoint) throws MercuryException {
		WsStatus result = getService().removeNodeFromMountPoint(context, caseId, mountPoint);
		checkWsStatus(result);
	}

	@Override
	public MrcPagedResult getAllDependsNodes(Context context, Long caseId, String mountPoint, IPage page,
			Integer caseMaxDepth) throws MercuryException {
		WsStatusWithMrcObject result = getService().getAllDependsNodes(context, caseId, mountPoint,
				(PageTransportable) page, caseMaxDepth);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject);
	}

	@Override
	public MrcPagedResult getAllChildrenNodes(Context context, Long caseId, String mountPoint, IPage page,
			Integer caseMaxDepth) throws MercuryException {
		WsStatusWithMrcObject result = getService().getAllChildrenNodes(context, caseId, mountPoint,
				(PageTransportable) page, caseMaxDepth);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject);
	}

	@Override
	public MrcPagedResult getAllChildrenNodesWithTheTypeCode(Context context, Long caseId, String typeCode,
			String mountPoint, IPage page, Integer caseMaxDepth) throws MercuryException {
		WsStatusWithMrcObject result = getService().getAllChildrenNodesWithTheTypeCode(context, caseId, typeCode,
				mountPoint, (PageTransportable) page, caseMaxDepth);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject);
	}

	@Override
	public MrcPagedResult getAllParentsNodes(Context context, Long caseId, String mountPoint, IPage page,
			Integer caseMaxDepth) throws MercuryException {
		WsStatusWithMrcObject result = getService().getAllParentsNodes(context, caseId, mountPoint,
				(PageTransportable) page, caseMaxDepth);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject);
	}

	@Override
	public IPagedResult<Case2CaseRelationship, IPage> getAllByPathStartsWith(Context context, String preffix,
			String mountPoint, IPage page) throws MercuryException {
		WsStatusWithCase2CaseRelationshipPagedResult result = getService().getAllByPathStartsWith(context, preffix,
				mountPoint, (PageTransportable) page);
		return getPagedResult(context, result);
	}

	@Override
	public IPagedResult<Case2CaseRelationship, IPage> getAllParentsRelationships(Context context, Long caseId,
			String mountPoint, IPage page) throws MercuryException {
		WsStatusWithCase2CaseRelationshipPagedResult result = getService().getAllParentsRelationships(context, caseId,
				mountPoint, (PageTransportable) page);
		return getPagedResult(context, result);
	}

	@Override
	public IPagedResult<Case2CaseRelationship, IPage> getAllChildrenRelationships(Context context, Long caseId,
			String mountPoint, IPage page) throws MercuryException {
		WsStatusWithCase2CaseRelationshipPagedResult result = getService().getAllChildrenRelationships(context, caseId,
				mountPoint, (PageTransportable) page);
		return getPagedResult(context, result);
	}

	@Override
	public IPagedResult<Case2CaseRelationship, IPage> getAllDependsRelationships(Context context, Long caseId,
			String mountPoint, IPage page) throws MercuryException {
		WsStatusWithCase2CaseRelationshipPagedResult result = getService().getAllDependsRelationships(context, caseId,
				mountPoint, (PageTransportable) page);
		return getPagedResult(context, result);
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
	public Document getAllDependsNodesXML(Context context, Long caseId, String mountPoint, IPage page,
			Integer caseMaxDepth) throws MercuryException {
		WsStatusWithXML result = getService().getAllDependsNodesXML(context, caseId, mountPoint,
				(PageTransportable) page, caseMaxDepth);
		return createDocument(result);
	}

	@Override
	public Document getAllChildrenNodesXML(Context context, Long caseId, String mountPoint, IPage page,
			Integer caseMaxDepth) throws MercuryException {
		WsStatusWithXML result = getService().getAllChildrenNodesXML(context, caseId, mountPoint,
				(PageTransportable) page, caseMaxDepth);
		return createDocument(result);
	}

	@Override
	public Document getAllChildrenNodesWithTheTypeCodeXML(Context context, Long caseId, String typeCode,
			String mountPoint, IPage page, Integer caseMaxDepth) throws MercuryException {
		WsStatusWithXML result = getService().getAllChildrenNodesWithTheTypeCodeXML(context, caseId, typeCode,
				mountPoint, (PageTransportable) page, caseMaxDepth);
		return createDocument(result);
	}

	@Override
	public Document getAllParentsNodesXML(Context context, Long caseId, String mountPoint, IPage page,
			Integer caseMaxDepth) throws MercuryException {
		WsStatusWithXML result = getService().getAllParentsNodesXML(context, caseId, mountPoint,
				(PageTransportable) page, caseMaxDepth);
		return createDocument(result);
	}

}
