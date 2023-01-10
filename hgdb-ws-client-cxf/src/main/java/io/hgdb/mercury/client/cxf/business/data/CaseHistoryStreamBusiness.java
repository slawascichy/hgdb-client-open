package io.hgdb.mercury.client.cxf.business.data;

import java.util.Collection;
import java.util.Set;

import io.hgdb.mercury.client.cxf.WsClient;
import pro.ibpm.mercury.business.data.api.BpmCaseHistoryStream;
import pro.ibpm.mercury.business.data.api.ICaseHistoryStreamBusiness;
import pro.ibpm.mercury.business.data.api.MrcCaseHistoryStream;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.dto.paging.PagedResult;
import pro.ibpm.mercury.dto.paging.PagingInfo;
import pro.ibpm.mercury.exceptions.InternalErrorException;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.business.data.ICaseHistoryStreamBusinessAction;
import pro.ibpm.mercury.ws.server.api.returns.BpmCaseHistoryStreamPagedResult;
import pro.ibpm.mercury.ws.server.api.returns.MrcCaseHistoryStreamPagedResult;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithBpmCaseHistoryStreamPagedResult;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithMrcCaseHistoryStreamPagedResult;

/**
 * 
 * CaseHistoryStreamBusiness
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class CaseHistoryStreamBusiness extends WsClient<ICaseHistoryStreamBusinessAction>
		implements ICaseHistoryStreamBusiness {

	@SuppressWarnings("java:S1172")
	protected IPagedResult<MrcCaseHistoryStream, IPage> getMrcCaseHistoryStreamPagedResult(Context context,
			WsStatusWithMrcCaseHistoryStreamPagedResult wsStatusWithPagedResult) throws MercuryException {
		logger.debug("--> getMrcCaseHistoryStreamPagedResult={}", wsStatusWithPagedResult);
		if (checkWsStatus(wsStatusWithPagedResult)) {
			logger.debug("--> getMrcCaseHistoryStreamPagedResult: wsStatusWithPagedResult={}", wsStatusWithPagedResult);
			try {
				/* czytamy rezultat z serwisu */
				MrcCaseHistoryStreamPagedResult pagedResultDto = wsStatusWithPagedResult.getPagedResult();
				Collection<MrcCaseHistoryStream> dtos = pagedResultDto.getResult();
				/* Teraz trzeba utworzyć obiekt stronicowanego wyniku z encją */
				PagedResult<MrcCaseHistoryStream> result = new PagedResult<>(new PagingInfo(pagedResultDto));
				result.setResult(dtos);
				return result;
			} catch (Exception e) {
				throw new InternalErrorException(e);
			}
		}
		return null;
	}

	@SuppressWarnings("java:S1172")
	protected IPagedResult<BpmCaseHistoryStream, IPage> getBpmCaseHistoryStreamPagedResult(Context context,
			WsStatusWithBpmCaseHistoryStreamPagedResult wsStatusWithPagedResult) throws MercuryException {
		logger.debug("--> getBpmCaseHistoryStreamPagedResult={}", wsStatusWithPagedResult);
		if (checkWsStatus(wsStatusWithPagedResult)) {
			logger.debug("--> getBpmCaseHistoryStreamPagedResult: wsStatusWithPagedResult={}", wsStatusWithPagedResult);
			try {
				/* czytamy rezultat z serwisu */
				BpmCaseHistoryStreamPagedResult pagedResultDto = wsStatusWithPagedResult.getPagedResult();
				Collection<BpmCaseHistoryStream> dtos = pagedResultDto.getResult();
				/* Teraz trzeba utworzyć obiekt stronicowanego wyniku z encją */
				PagedResult<BpmCaseHistoryStream> result = new PagedResult<>(new PagingInfo(pagedResultDto));
				result.setResult(dtos);
				return result;
			} catch (Exception e) {
				throw new InternalErrorException(e);
			}
		}
		return null;
	}

	@Override
	public IPagedResult<MrcCaseHistoryStream, IPage> loadCaseHistoryStream(Context context, String caseId,
			Boolean isAsc, PageTransportable page) throws MercuryException {
		WsStatusWithMrcCaseHistoryStreamPagedResult result = getService(context).loadCaseHistoryStream(context, caseId,
				isAsc, page);
		return getMrcCaseHistoryStreamPagedResult(context, result);
	}

	@Override
	public IPagedResult<MrcCaseHistoryStream, IPage> loadCaseHistoryStreamAllVersions(Context context, String caseId,
			Boolean isAsc, PageTransportable page) throws MercuryException {
		WsStatusWithMrcCaseHistoryStreamPagedResult result = getService(context)
				.loadCaseHistoryStreamAllVersions(context, caseId, isAsc, page);
		return getMrcCaseHistoryStreamPagedResult(context, result);
	}

	@Override
	public IPagedResult<BpmCaseHistoryStream, IPage> loadBpmCaseHistoryStream(Context context, String caseId,
			Set<String> bpmTaskStatuses, Boolean isAsc, PageTransportable page) throws MercuryException {
		WsStatusWithBpmCaseHistoryStreamPagedResult result = getService(context).loadBpmCaseHistoryStream(context,
				caseId, bpmTaskStatuses, isAsc, page);
		return getBpmCaseHistoryStreamPagedResult(context, result);
	}

	@Override
	public IPagedResult<BpmCaseHistoryStream, IPage> loadBpmCaseHistoryStreamAllVersions(Context context, String caseId,
			Set<String> bpmTaskStatuses, Boolean isAsc, PageTransportable page) throws MercuryException {
		WsStatusWithBpmCaseHistoryStreamPagedResult result = getService(context)
				.loadBpmCaseHistoryStreamAllVersions(context, caseId, bpmTaskStatuses, isAsc, page);
		return getBpmCaseHistoryStreamPagedResult(context, result);
	}

	@Override
	public IPagedResult<MrcCaseHistoryStream, IPage> loadFieldHistoryStream(Context context, String caseId,
			String fieldName, Boolean isAsc, PageTransportable page) throws MercuryException {
		WsStatusWithMrcCaseHistoryStreamPagedResult result = getService(context).loadFieldHistoryStream(context, caseId,
				fieldName, isAsc, page);
		return getMrcCaseHistoryStreamPagedResult(context, result);
	}

	@Override
	public IPagedResult<MrcCaseHistoryStream, IPage> loadFieldHistoryStreamAllVersions(Context context, String caseId,
			String fieldName, Boolean isAsc, PageTransportable page) throws MercuryException {
		WsStatusWithMrcCaseHistoryStreamPagedResult result = getService(context)
				.loadFieldHistoryStreamAllVersions(context, caseId, fieldName, isAsc, page);
		return getMrcCaseHistoryStreamPagedResult(context, result);
	}

}
