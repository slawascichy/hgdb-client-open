package io.hgdb.multi.client.cxf.business.data.api;

import org.w3c.dom.Document;

import pro.ibpm.mercury.business.data.api.MrcObject;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.ws.server.api.actions.business.data.ICaseSearchAction;

/**
 * 
 * ICaseSearchBusinessXML - interfejs odwzorowuje metody z {@link ICaseSearchAction}, które zwracają obiekty spraw jako
 * dowolne obiekty XML.
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public interface ICaseSearchBusinessXML {

	/**
	 * Wyszukiwanie spraw na podstawie danych zgromadzonych w indeksie Lucene gdzie kryterium jest zapytanie Lucene.
	 * Występuje dodatkowy argument pozwalający na podanie kryterium sortowania wyniku.
	 * 
	 * @param context
	 *                            kontekst wykonania operacji ładowania danych zawierający nazwę i komentarz
	 *                            użytkownika, oraz maksymalną liczbę wyników
	 * @param query
	 *                            zapytanie lucene np.
	 *                            {@code (grParticipantFullname:\"Kowalski Marek\" OR grParticipantFullname:\"Kowalski Maciek\") AND NazwiskoKlienta:Kowalski2}
	 * @param page
	 *                            obiekt pobieranej strony
	 * @param sortClause
	 *                            kryterium sortowania np.
	 *                            {@code grParticipantFullname ASC, NazwiskoKlienta:Kowalski2 DESC}
	 * @param additionalDateRange
	 *                            dodatkowe kryterium zawężające wyniki wyszukiwania oparte do datę np.
	 *                            "mrc_createDate:[946681200000 TO 1564681107093]"
	 * @return stronicowany wynik zapytania w postaci XML
	 * @throws MercuryException
	 */
	Document searchByQueryXML(Context context, String query, IPage page, String sortClause, String additionalDateRange)
			throws MercuryException;

	/**
	 * Wyszukiwanie spraw na podstawie danych zgromadzonych w indeksie Lucene gdzie kryterium jest zapytanie Lucene.
	 * Występuje dodatkowy argument pozwalający na podanie kryterium konwersji wyniku do typu o podanej nazwie.
	 * 
	 * @param context
	 *                            kontekst wykonania operacji ładowania danych zawierający nazwę i komentarz
	 *                            użytkownika, oraz maksymalną liczbę wyników
	 * @param query
	 *                            zapytanie lucene np.
	 *                            {@code (grParticipantFullname:\"Kowalski Marek\" OR grParticipantFullname:\"Kowalski Maciek\") AND NazwiskoKlienta:Kowalski2}
	 * @param page
	 *                            obiekt pobieranej strony
	 * @param sortClause
	 *                            kryterium sortowania np.
	 *                            {@code grParticipantFullname ASC, NazwiskoKlienta:Kowalski2 DESC}
	 * @param additionalDateRange
	 *                            dodatkowe kryterium zawężające wyniki wyszukiwania oparte do datę np.
	 *                            "mrc_createDate:[946681200000 TO 1564681107093]"
	 * @param resultTypeName
	 *                            nazwa reprezentująca typ danych wyniku wyszukiwania
	 * @return stronicowany wynik zapytania w postaci XML
	 * @throws MercuryException
	 */
	Document searchByQueryWithResultTypeXML(Context context, String query, IPage page, String sortClause,
			String additionalDateRange, String resultTypeName) throws MercuryException;

	/**
	 * Uruchomienie zapytania z klauzulą "GROUP BY".
	 * 
	 * @param context
	 *                             kontekst wykonania operacji ładowania danych zawierający nazwę i komentarz
	 *                             użytkownika, oraz maksymalną liczbę wyników
	 * @param query
	 *                             zapytanie lucene np.
	 *                             {@code (grParticipantFullname:\"Kowalski Marek\" OR grParticipantFullname:\"Kowalski Maciek\") AND NazwiskoKlienta:Kowalski2}
	 * @param groupByClause
	 *                             klauzula "GROUP BY" np.
	 *                             {@code trunc(createDate, DD) as createDatePerMonth, count(1) as count, sum(price) as sum, max(price) as max, min(price) as min, avg(price) as avg}
	 * @param filterClause
	 *                             dodatkowa klauzula filtrowania wyniku agregacji.
	 * @param additionalDateRange
	 *                             dodatkowe kryterium zawężające wyniki wyszukiwania oparte do datę np.
	 *                             "mrc_createDate:[946681200000 TO 1564681107093]"
	 * @param page
	 *                             obiekt pobieranej strony
	 * @param resultTypeName
	 *                             nazwa reprezentująca typ danych. Opcjonalna, jeżeli nie zostanie podana typ obiektu
	 *                             {@link MrcObject} zostanie zdefiniowany jako {@code QueryResult_&lt;query_id>}
	 * @param resultPkPropertyName
	 *                             nazwa pola, która definiuje wartość unikalną wyniku. Opcjonalnie, jeżeli nie zostanie
	 *                             podana zostanie zdefiniowana jako pole {@code rowId}
	 * @return wyniki wyszukiwania w postaci stronicowanego wyniku obiektów jako dokument XML
	 * 
	 * @throws MercuryException
	 */
	Document groupByQueryXML(Context context, String query, String groupByClause, String filterClause,
			String additionalDateRange, IPage page, String resultTypeName, String resultPkPropertyName)
			throws MercuryException;

}
