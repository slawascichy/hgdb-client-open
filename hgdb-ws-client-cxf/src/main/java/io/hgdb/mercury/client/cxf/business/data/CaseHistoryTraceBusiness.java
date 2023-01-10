package io.hgdb.mercury.client.cxf.business.data;

import org.mercury.business.data.api.ICaseHistoryTraceBusiness;
import org.mercury.cxf.client.WsStatusUtils;
import org.mercury.cxf.client.business.data.ICaseHistoryTraceBusinessXML;
import org.mercury.ws.server.api.actions.business.data.ICaseHistoryTraceBusinessAction;
import org.w3c.dom.Document;

import io.hgdb.mercury.client.utils.MrcVariableReaderCollectorUtils;
import pro.ibpm.mercury.business.data.api.MrcObject;
import pro.ibpm.mercury.business.data.api.MrcPagedResult;
import pro.ibpm.mercury.business.data.utils.MrcVariableReaderCollector;
import pro.ibpm.mercury.business.data.utils.XMLReaderHelper;
import pro.ibpm.mercury.business.data.utils.XMLVariableBuilder;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.exceptions.InternalErrorException;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.ws.server.api.returns.DtoMrcDataUtils;
import pro.ibpm.mercury.ws.server.api.returns.DtoMrcObject;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithDto;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithMrcObject;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithXML;

public class CaseHistoryTraceBusiness implements ICaseHistoryTraceBusiness, ICaseHistoryTraceBusinessXML {

	/**
	 * parametr mówiący o tym, czy usługa klienta wykorzystuje usługi "remoting" (nie SOAP). Dla usług pobierających XML
	 * ma to znaczenie, bo pobieranie wyniku w postaci XML dla "remoting" się nie opłaca. Lepiej pobrać MrcObject i
	 * przekształcić go do XML'a już lokalnie. Z kolei dla usług SOAP lepiej pobrać dokument XML. Nasza implementacja
	 * klienta nastawiona jest na komunikację "remoting" zatem domyślna wartość to {@code true}.
	 */
	private Boolean isRemote = true;

	/** instancja zdalnej usługi spełniającej odpowiedni interfejs. */
	private ICaseHistoryTraceBusinessAction service;

	/**
	 * Ustawia instancję zdalnej usługi.
	 * 
	 * @param service
	 *                instancja zdalnej usługi
	 */
	public void setService(ICaseHistoryTraceBusinessAction service) {
		this.service = service;
	}

	/**
	 * Zwraca instancję zdalnej usługi.
	 * 
	 * @return instancja zdalnej usługi
	 */
	@SuppressWarnings("java:S1172")
	public ICaseHistoryTraceBusinessAction getService(Context context) {
		return service;
	}

	/**
	 * @return the {@link #isRemote}
	 */
	public Boolean getIsRemote() {
		return isRemote == null || isRemote.booleanValue();
	}

	/**
	 * @param isRemote
	 *                 the {@link #isRemote} to set
	 */
	public void setIsRemote(Boolean isRemote) {
		this.isRemote = isRemote == null || isRemote.booleanValue();
	}

	protected <T> T getDto(final IWsStatusWithDto<T> wsStatusWithDto) throws MercuryException {
		if (WsStatusUtils.checkWsStatus(wsStatusWithDto)) {
			return wsStatusWithDto.getDto();
		}
		return null;
	}

	protected Document loadMrcObjectXML(Context context, MrcObject mrcObject) throws MercuryException {
		try {
			return XMLVariableBuilder.buildObjectVariable(context, XMLReaderHelper.TAG_MAIN_DOCUMENT, mrcObject);
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

	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public MrcObject find(Context context, Long versionId) throws MercuryException {
		WsStatusWithMrcObject result = getService(context).find(context, versionId);
		DtoMrcObject dtoObject = getDto(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcObject) DtoMrcDataUtils.toMrcObject(context, dtoObject,
				collector != null ? collector : new MrcVariableReaderCollector());
	}

	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public MrcPagedResult findByCaseId(Context context, String caseId, Boolean isAsc, IPage page)
			throws MercuryException {
		WsStatusWithMrcObject result = getService(context).findByCaseId(context, caseId, isAsc,
				(PageTransportable) page);
		DtoMrcObject dtoObject = getDto(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject,
				collector != null ? collector : new MrcVariableReaderCollector());

	}

	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public MrcPagedResult findByCaseIdAllVersions(Context context, String caseId, Boolean isAsc, IPage page)
			throws MercuryException {
		WsStatusWithMrcObject result = getService(context).findByCaseIdAllVersions(context, caseId, isAsc,
				(PageTransportable) page);
		DtoMrcObject dtoObject = getDto(result);
		MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils.getMrcVariableReaderCollector(context);
		return (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject,
				collector != null ? collector : new MrcVariableReaderCollector());

	}

	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public Document findXML(Context context, Long versionId) throws MercuryException {
		if (getIsRemote().booleanValue()) {
			WsStatusWithMrcObject result = getService(context).find(context, versionId);
			DtoMrcObject dtoObject = getDto(result);
			MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils
					.getMrcVariableReaderCollector(context);
			MrcObject mrcObject = (MrcObject) DtoMrcDataUtils.toMrcObject(context, dtoObject,
					collector != null ? collector : new MrcVariableReaderCollector());
			return loadMrcObjectXML(context, mrcObject);
		} else {
			WsStatusWithXML result = getService(context).findXML(context, versionId);
			return WsStatusUtils.createDocument(result);
		}
	}

	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public Document findByCaseIdXML(Context context, String caseId, Boolean isAsc, IPage page) throws MercuryException {
		if (getIsRemote().booleanValue()) {
			WsStatusWithMrcObject result = getService(context).findByCaseId(context, caseId, isAsc,
					(PageTransportable) page);
			DtoMrcObject dtoObject = getDto(result);
			MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils
					.getMrcVariableReaderCollector(context);
			MrcPagedResult mrcPagedResult = (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject,
					collector != null ? collector : new MrcVariableReaderCollector());
			return loadMrcPagedResultXML(context, mrcPagedResult);
		} else {
			WsStatusWithXML result = getService(context).findByCaseIdXML(context, caseId, isAsc,
					(PageTransportable) page);
			return WsStatusUtils.createDocument(result);
		}
	}

	/**
	 * Można dodać kolektor zbierający dane do kontekstu!
	 * 
	 * @see MrcVariableReaderCollectorUtils
	 * @see MrcVariableReaderCollector
	 */
	@Override
	public Document findByCaseIdAllVersionsXML(Context context, String caseId, Boolean isAsc, IPage page)
			throws MercuryException {
		if (getIsRemote().booleanValue()) {
			WsStatusWithMrcObject result = getService(context).findByCaseIdAllVersions(context, caseId, isAsc,
					(PageTransportable) page);
			DtoMrcObject dtoObject = getDto(result);
			MrcVariableReaderCollector collector = MrcVariableReaderCollectorUtils
					.getMrcVariableReaderCollector(context);
			MrcPagedResult mrcPagedResult = (MrcPagedResult) DtoMrcDataUtils.toMrcPagedResult(context, dtoObject,
					collector != null ? collector : new MrcVariableReaderCollector());
			return loadMrcPagedResultXML(context, mrcPagedResult);
		} else {
			WsStatusWithXML result = getService(context).findByCaseIdAllVersionsXML(context, caseId, isAsc,
					(PageTransportable) page);
			return WsStatusUtils.createDocument(result);
		}
	}

}
