package io.hgdb.mercury.client.cxf;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import pl.slawas.twl4j.Logger;
import pl.slawas.twl4j.LoggerFactory;
import pro.ibpm.mercury.config.MercuryConfig;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.exceptions.SQLNoDataFoundException;
import pro.ibpm.mercury.ws.server.api.actions.IActionRoot;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatus;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithBag;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithDto;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithDtos;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithMap;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithValue;
import pro.ibpm.mercury.ws.server.api.returns.WsErrorCode;

/**
 * 
 * WsClient
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 * @param <Ws>
 */
public abstract class WsClient<Ws extends IActionRoot> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private Ws service;

	/**
	 * Ustawia instancję zdalnej usługi.
	 */
	public void setService(Ws service) {
		this.service = service;
	}

	/**
	 * Zwraca instancję zdalnej usługi.
	 */
	public Ws getService() {
		return service;
	}

	/**
	 * Sprawdzenie statusu odpowiedzi usługi serwera.
	 * 
	 * @param wsStatus
	 *            obiekt odpowiedzi
	 * @return informacja o tym, czy odpowiedź (argument) jest {@code null}. Jeżeli
	 *         jest {@code null} to nie można kontynuować przetwarzania odpowiedzi
	 *         pod kierunkiem pobrania zwóconych danych.
	 * @throws MercuryException
	 */
	protected boolean checkWsStatus(final IWsStatus wsStatus) throws MercuryException {
		if (wsStatus != null) {
			if (StringUtils.isNotBlank(wsStatus.getErrorCode())) {
				/* Kod błędu jest przesyłany tylko wtedy gdy wystąpił błąd */
				WsErrorCode wsErrorCode = WsErrorCode.decodeErrorCode(wsStatus.getErrorCode());
				String errorCode = wsErrorCode.getErrorCode();
				String errorUUID = wsErrorCode.getErrorUUID();
				if (errorCode.equals(SQLNoDataFoundException.ERROR_CODE)) {
					logger.warn("--> checkStatus: {}", wsStatus.getErrorMessage());
				} else {
					String errorMessage = wsStatus.getErrorMessage();
					String message = (new StringBuilder()).append(errorMessage.endsWith(".") ? StringUtils.EMPTY : '.')
							.append(" Sprawdź logi serwera. Błąd został oznaczony etykietą [").append(errorUUID)
							.append("].").toString();
					throw new MercuryException(errorCode, message);
				}
			}
			return true;
		}
		return false;
	}

	protected <T> T getValue(Context context, final IWsStatusWithValue<T> wsStatusWithValue) throws MercuryException {
		logger.debug("wsStatusWithValue={}", new Object[] { wsStatusWithValue });
		if (checkWsStatus((IWsStatus) wsStatusWithValue)) {
			return wsStatusWithValue.getValue();
		}
		return null;
	}

	protected <T> T getDto(final IWsStatusWithDto<T> wsStatusWithDto) throws MercuryException {
		logger.debug("wsStatusWithDto={}", new Object[] { wsStatusWithDto });
		if (checkWsStatus((IWsStatus) wsStatusWithDto)) {
			return wsStatusWithDto.getDto();
		}
		return null;
	}

	protected <T> Collection<T> getBag(final IWsStatusWithBag<T> wsStatusWithBag) throws MercuryException {
		logger.debug("wsStatusWithBag={}", new Object[] { wsStatusWithBag });
		if (checkWsStatus((IWsStatus) wsStatusWithBag)) {
			return wsStatusWithBag.getBag();
		}
		return Collections.emptyList();
	}

	protected <T> T getValue(final IWsStatusWithValue<T> wsStatusWithValue) throws MercuryException {
		logger.debug("wsStatusWithValue={}", new Object[] { wsStatusWithValue });
		if (checkWsStatus((IWsStatus) wsStatusWithValue)) {
			return wsStatusWithValue.getValue();
		}
		return null;
	}

	protected <T> Collection<T> getDtos(final IWsStatusWithDtos<T> wsStatusWithDtos) throws MercuryException {
		logger.debug("wsStatusWithDtos={}", new Object[] { wsStatusWithDtos });
		if (checkWsStatus((IWsStatus) wsStatusWithDtos)) {
			return wsStatusWithDtos.getDtos();
		}
		return Collections.emptyList();
	}

	protected <K, T> Map<K, T> getMap(final IWsStatusWithMap<K, T> wsStatusWithMap) throws MercuryException {
		logger.debug("wsStatusWithMap={}", new Object[] { wsStatusWithMap });
		if (checkWsStatus((IWsStatus) wsStatusWithMap)) {
			return wsStatusWithMap.getMap();
		}
		return Collections.emptyMap();
	}

	protected Map<String, String> getParamsMap(final Map<String, Object> paramsMap) {
		final Map<String, String> newMap = new HashMap<String, String>();
		if (paramsMap != null) {
			for (final Map.Entry<String, Object> entry : paramsMap.entrySet()) {
				final Object value = entry.getValue();
				newMap.put(entry.getKey(),
						((value == null) || (value instanceof String)) ? (String) value : value.toString());
			}
		}
		return newMap;
	}

	protected String createComment() {
		return new StringBuilder().append(getClass()).append(MercuryConfig.DOT)
				.append(Calendar.getInstance().getTimeInMillis()).toString();
	}

	public static Collection<String> idList2StringBag(List<? extends Object> idList) {
		if (idList == null) {
			return Collections.emptyList();
		}
		Collection<String> ret = new ArrayList<String>();
		for (final Object id : idList) {
			ret.add(id.toString());
		}
		return ret;
	}

}
