package io.hgdb.mercury.client.cxf;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mercury.cxf.client.WsStatusUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.ibpm.mercury.config.MercuryConfig;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.ws.server.api.actions.IActionRoot;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatus;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithBag;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithDto;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithDtos;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithMap;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithValue;

/**
 * 
 * WsClient
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 * @param <W>
 *            interfejs zdalnej usługi
 */
public abstract class WsClient<W extends IActionRoot> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * instancja zdalnej usługi spełniającej odpowiedni interfejs zdefiniowany w
	 * parametrze abstrakcji.
	 */
	private W service;

	/**
	 * Ustawia instancję zdalnej usługi.
	 * 
	 * @param service
	 *            instancja zdalnej usługi
	 */
	public void setService(W service) {
		this.service = service;
	}

	/**
	 * Zwraca instancję zdalnej usługi.
	 * 
	 * @return instancja zdalnej usługi
	 */
	public W getService() {
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
		return WsStatusUtils.checkWsStatus(wsStatus);
	}

	protected <T> T getValue(Context context, final IWsStatusWithValue<T> wsStatusWithValue) throws MercuryException {
		if (checkWsStatus((IWsStatus) wsStatusWithValue)) {
			return wsStatusWithValue.getValue();
		}
		return null;
	}

	protected <T> T getDto(final IWsStatusWithDto<T> wsStatusWithDto) throws MercuryException {
		if (checkWsStatus((IWsStatus) wsStatusWithDto)) {
			return wsStatusWithDto.getDto();
		}
		return null;
	}

	protected <T> Collection<T> getBag(final IWsStatusWithBag<T> wsStatusWithBag) throws MercuryException {
		if (checkWsStatus((IWsStatus) wsStatusWithBag)) {
			return wsStatusWithBag.getBag();
		}
		return Collections.emptyList();
	}

	protected <T> T getValue(final IWsStatusWithValue<T> wsStatusWithValue) throws MercuryException {
		if (checkWsStatus((IWsStatus) wsStatusWithValue)) {
			return wsStatusWithValue.getValue();
		}
		return null;
	}

	protected <T> Collection<T> getDtos(final IWsStatusWithDtos<T> wsStatusWithDtos) throws MercuryException {
		if (checkWsStatus((IWsStatus) wsStatusWithDtos)) {
			return wsStatusWithDtos.getDtos();
		}
		return Collections.emptyList();
	}

	protected <K, T> Map<K, T> getMap(final IWsStatusWithMap<K, T> wsStatusWithMap) throws MercuryException {
		if (checkWsStatus((IWsStatus) wsStatusWithMap)) {
			return wsStatusWithMap.getMap();
		}
		return Collections.emptyMap();
	}

	protected Map<String, String> getParamsMap(final Map<String, Object> paramsMap) {
		final Map<String, String> newMap = new HashMap<>();
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
		Collection<String> ret = new ArrayList<>();
		for (final Object id : idList) {
			ret.add(id.toString());
		}
		return ret;
	}

}
