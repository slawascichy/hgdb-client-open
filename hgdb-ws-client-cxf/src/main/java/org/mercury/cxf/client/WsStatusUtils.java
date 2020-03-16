package org.mercury.cxf.client;

import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pro.ibpm.mercury.exceptions.InternalErrorException;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.exceptions.SQLNoDataFoundException;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatus;
import pro.ibpm.mercury.ws.server.api.returns.WsErrorCode;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithXML;

/**
 * 
 * WsStatusWithXMLUtils biblioteka wspierająca odczyt obiektów XML z odpowiedzi
 * serwera.
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class WsStatusUtils {

	protected static final Logger logger = LoggerFactory.getLogger(WsStatusUtils.class);

	private WsStatusUtils() {
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
	public static boolean checkWsStatus(final IWsStatus wsStatus) throws MercuryException {
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

	/**
	 * Przeczytanie Elementu XML z przesłanej wiadomości od serwera.
	 * 
	 * @param wsStatusWithXML
	 *            odpowiedź od serwera
	 * @return obiekt elementu XML
	 * @throws MercuryException
	 */
	public static Element getDtoXMLElement(final WsStatusWithXML wsStatusWithXML) throws MercuryException {
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

	/**
	 * Przeczytanie dokumentu XML z przesłanej wiadomości od serwera
	 * 
	 * @param result
	 *            odpowiedź od serwera
	 * @return obiekt dokumentu XML
	 * @throws MercuryException
	 * @throws FactoryConfigurationError
	 */
	public static Document createDocument(WsStatusWithXML result) throws MercuryException, FactoryConfigurationError {
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

}
