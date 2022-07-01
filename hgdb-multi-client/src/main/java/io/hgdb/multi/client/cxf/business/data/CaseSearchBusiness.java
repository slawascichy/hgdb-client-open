package io.hgdb.multi.client.cxf.business.data;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import io.hgdb.mercury.client.cxf.WsClient;
import io.hgdb.multi.client.cxf.business.data.api.ICaseSearchBusiness;
import io.hgdb.multi.client.cxf.business.data.api.ICaseSearchBusinessXML;
import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.business.data.api.CaseNarrative;
import pro.ibpm.mercury.business.data.api.MrcPagedResult;
import pro.ibpm.mercury.business.data.utils.XMLReaderHelper;
import pro.ibpm.mercury.business.data.utils.XMLVariableBuilder;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.dto.paging.PagedResult;
import pro.ibpm.mercury.dto.paging.PagingInfo;
import pro.ibpm.mercury.exceptions.InternalErrorException;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.business.data.ICaseSearchAction;
import pro.ibpm.mercury.ws.server.api.returns.CaseNarrativePagedResult;
import pro.ibpm.mercury.ws.server.api.returns.DtoMrcDataUtils;
import pro.ibpm.mercury.ws.server.api.returns.DtoMrcObject;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithCaseNarrativePagedResult;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithMrcObjectSearchResult;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithStringValue;

@Service
public class CaseSearchBusiness extends WsClient<ICaseSearchAction>
		implements ICaseSearchBusiness, ICaseSearchBusinessXML {

	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public ICaseSearchAction getService(Context context) {
		return httpClientDynamicRegistry.getBean(context, ICaseSearchAction.class);
	}

	@Override
	public WsStatusWithStringValue echo(Context context, String a) {
		return getService(context).echo(context, a);
	}

	@Override
	public MrcPagedResult searchByQuery(Context context, String query, IPage page, String sortClause,
			String additionalDateRange) throws MercuryException {
		WsStatusWithMrcObjectSearchResult result = getService(context).searchByQuery(context, query,
				(PageTransportable) page, sortClause, additionalDateRange);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject);
	}

	protected IPagedResult<CaseNarrative, IPage> getCaseNarrativePagedResult(
			WsStatusWithCaseNarrativePagedResult wsStatusWithPagedResult) throws MercuryException {
		logger.debug("--> getCaseNarrativePagedResult={}", wsStatusWithPagedResult);
		if (checkWsStatus(wsStatusWithPagedResult)) {
			logger.debug("--> getCaseNarrativePagedResult: wsStatusWithPagedResult={}", wsStatusWithPagedResult);
			try {
				/* czytamy rezultat z serwisu */
				CaseNarrativePagedResult pagedResultDto = wsStatusWithPagedResult.getPagedResult();
				Collection<CaseNarrative> dtos = pagedResultDto.getResult();
				/* Teraz trzeba utworzyć obiekt stronicowanego wyniku z encją */
				PagedResult<CaseNarrative> result = new PagedResult<>(new PagingInfo(pagedResultDto));
				result.setResult(dtos);
				return result;
			} catch (Exception e) {
				throw new InternalErrorException(e);
			}
		}
		return null;
	}

	@Override
	public IPagedResult<CaseNarrative, IPage> searchNarrativeByQuery(Context context, String query, IPage page,
			String sortClause, String additionalDateRange) throws MercuryException {
		WsStatusWithCaseNarrativePagedResult result = getService(context).searchNarrativeByQuery(context, query,
				(PageTransportable) page, sortClause, additionalDateRange);
		return getCaseNarrativePagedResult(result);
	}

	@Override
	public MrcPagedResult searchByQueryWithResultType(Context context, String query, IPage page, String sortClause,
			String additionalDateRange, String resultTypeName) throws MercuryException {
		WsStatusWithMrcObjectSearchResult result = getService(context).searchByQueryWithResultType(context, query,
				(PageTransportable) page, sortClause, additionalDateRange, resultTypeName);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject);
	}

	@Override
	public MrcPagedResult groupByQuery(Context context, String query, String groupByClause, String filterClause,
			String additionalDateRange, IPage page, String resultTypeName, String resultPkPropertyName)
			throws MercuryException {
		WsStatusWithMrcObjectSearchResult result = getService(context).groupByQuery(context, query, groupByClause,
				filterClause, additionalDateRange, (PageTransportable) page, resultTypeName, resultPkPropertyName);
		DtoMrcObject dtoObject = getDto(result);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject);
	}

	@Override
	public Document searchByQueryXML(Context context, String query, IPage page, String sortClause,
			String additionalDateRange) throws MercuryException {
		MrcPagedResult mrcPagedResult = searchByQuery(context, query, page, sortClause, additionalDateRange);
		return loadMrcPagedResultXML(context, mrcPagedResult);
	}

	@Override
	public Document searchByQueryWithResultTypeXML(Context context, String query, IPage page, String sortClause,
			String additionalDateRange, String resultTypeName) throws MercuryException {
		MrcPagedResult mrcPagedResult = searchByQueryWithResultType(context, query, page, sortClause,
				additionalDateRange, resultTypeName);
		return loadMrcPagedResultXML(context, mrcPagedResult);
	}

	@Override
	public Document groupByQueryXML(Context context, String query, String groupByClause, String filterClause,
			String additionalDateRange, IPage page, String resultTypeName, String resultPkPropertyName)
			throws MercuryException {
		MrcPagedResult mrcPagedResult = groupByQuery(context, query, groupByClause, filterClause, additionalDateRange,
				page, resultTypeName, resultPkPropertyName);
		return loadMrcPagedResultXML(context, mrcPagedResult);
	}

	public Document loadMrcPagedResultXML(Context context, MrcPagedResult mrcPagedResult) throws MercuryException {
		try {
			return XMLVariableBuilder.buildObjectVariable(context, XMLReaderHelper.TAG_MAIN_DOCUMENT, mrcPagedResult);
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}
	}

}
