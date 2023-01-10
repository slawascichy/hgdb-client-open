package org.mercury.cxf.client.business.data;

import org.w3c.dom.Document;

import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.paging.IPage;

/**
 * 
 * ICaseHistoryTraceBusiness interfejs dla usługi pobierania kolejnych wersji
 * sprawy
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public interface ICaseHistoryTraceBusinessXML {

	/**
	 * Pobranie wpisy historycznego sprawy (obrazu sprawy) na podstawie jego
	 * identyfikatora
	 * 
	 * @param context
	 *            kontekst wykonania operacji ładowania danych zawierający nazwę i
	 *            komentarz użytkownika, oraz maksymalną liczbę wyników
	 * @param versionId
	 *            identyfikator wersji
	 * @return znaleziona instancja sprawy
	 * @throws MercuryException
	 */
	Document findXML(Context context, Long versionId) throws MercuryException;

	/**
	 * Załadowanie wpisów historycznych sprawy (obrazów sprawy) dla danej sprawy
	 * 
	 * @param context
	 *            kontekst wykonania operacji ładowania danych zawierający nazwę i
	 *            komentarz użytkownika
	 * @param caseId
	 *            identyfikator sprawy
	 * @param isAsc
	 *            kierunek sortowania po dacie
	 * @param page
	 *            strona wyniku
	 * @return strumień zmian związany z daną wersją sprawy
	 * @throws MercuryException
	 */
	Document findByCaseIdXML(Context context, String caseId, Boolean isAsc, IPage page) throws MercuryException;

	/**
	 * Załadowanie wpisów historycznych sprawy (obrazów sprawy) dla wszystkich
	 * wersji danej sprawy.
	 * 
	 * @param context
	 *            kontekst wykonania operacji ładowania danych zawierający nazwę i
	 *            komentarz użytkownika
	 * @param caseId
	 *            identyfikator sprawy
	 * @param isAsc
	 *            kierunek sortowania po dacie
	 * @param page
	 *            strona wyniku
	 * @return strumień zmian związany z daną wersją sprawy
	 * @throws MercuryException
	 */
	Document findByCaseIdAllVersionsXML(Context context, String caseId, Boolean isAsc, IPage page)
			throws MercuryException;

}
