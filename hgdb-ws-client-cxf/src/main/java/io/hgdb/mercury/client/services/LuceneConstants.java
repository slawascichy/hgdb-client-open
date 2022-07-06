package io.hgdb.mercury.client.services;

import pro.ibpm.mercury.config.MercuryConfig;
import pro.ibpm.mercury.entities.data.Case;
import pro.ibpm.mercury.lucene.LuceneModelVersion;

/**
 * 
 * LuceneConstants - stałe, które mogą być pomocne do budowani zaawansowanych mechanizmów wyszukiwania.
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class LuceneConstants {

	private LuceneConstants() {
	}

	/** Wersja modelu budowania nazewnictwa pól indeksu Lucene */
	public static final LuceneModelVersion LUCENE_MODEL_VERSION = MercuryConfig.getLuceneModelVersion();

	/**
	 * pole w indeksie przechowujące informację o tym czy pole z identyfikatorem procesu jest uzupełnione. Pole
	 * przyjmować będzie wartości tekstowe {@code true|false} https://support.ibpm.pro:8443/browse/MERC-68
	 */
	public static final String LUCENE_BPM_PROCESS_ID_NOT_NULL;
	public static final String LUCENE_BPM_PROCESS_ID;
	/** Pole przechowujące odwrócony numer inwentaryzacyjny */
	public static final String LUCENE_INVENTORY_CODE;

	/**
	 * Pole przechowujące odwrócony numer inwentaryzacyjny do wyszukiwania po "końcówce" kodu - lucene nie lubi gwiazdek
	 * na początku wyszukiwanej wartości. Dla kryterium wyszukiwania po kodzie inwentaryzacyjnym będziemy budować
	 * zapytanie (pseudo kod): {@code inventoryCode like '<wartość>*' MUST inventoryCodeReverse like '<wartość>*'}
	 */
	public static final String LUCENE_INVENTORY_CODE_REVERSE;
	/** Pole z ceną przeliczoną do wartości waluty systemowej */
	public static final String LUCENE_PRICE_VALUE;
	/**
	 * Sufiks dodawany do nazwy pola przechowującego identyfikatory spraw zależnych dla modelu w wersji 3.
	 */
	public static final String LUCENE_SUBCASE_SUFFIX = "_" + LuceneHelper.getLuceneIdFieldName(Case.class);
	/**
	 * nazwy pól spraw nadrzędnych, w których występuje sprawa.
	 */
	public static final String LUCENE_PARENT_FIELDS;
	/**
	 * kody typów spraw nadrzędnych, w których występuje sprawa.
	 */
	public static final String LUCENE_PARENT_TYPES;
	/**
	 * pole opisowe, konkatenacja wszystkich wartości tekstowych w celu ogólnego wyszukiwania. Pole obejmuje (na razie)
	 * tylko wartości związane bezpośrednio z obiektem sprawy, czyli nie dodawane są do niego wartości pochodzące np. z
	 * powiązanych komentarzy, czy QickTask'ów.
	 */
	public static final String LUCENE_MEMO_FIELD;

	public static final String LUCENE_CREATE_DATE;
	public static final String LUCENE_MODIFY_DATE;

	/**
	 * Prefiks dodawany do nazw pól indeksu lucene, który dodawany jest tylko do pól będących składowymi encji. NIE JEST
	 * DODAWANY DO NAZW ATRYBUTÓW!
	 */
	public static final String LUCENE_BASE_PREFIX;

	/** Prefiks dodawany do pól pochodzących od grupy sprawy */
	public static final String LUCENE_GROUP_PREFIX;
	/** Prefiks dodawany do pól pochodzących od typu sprawy */
	public static final String LUCENE_TYPE_PREFIX;
	/** Prefiks dodawany do pól pochodzących od komentarzy sprawy */
	public static final String LUCENE_COMMENT_PREFIX;
	/** Prefiks dodawany do pól pochodzących od szybkich zadań sprawy */
	public static final String LUCENE_QUICK_TASK_PREFIX;
	/** Prefiks dodawany do pól pochodzących od dokumentów sprawy */
	public static final String LUCENE_CASE_DOCUMENT_PREFIX;

	public static final String INVENTORY_CODE_GETTER_NAME = "getInventoryCode";
	public static final String BPM_PROCESS_ID_GETTER_NAME = "getBpmProcessId";

	static {
		LUCENE_BASE_PREFIX = LuceneHelper.getLucenePrefixFieldName(Case.class);
		LUCENE_BPM_PROCESS_ID_NOT_NULL = LUCENE_BASE_PREFIX + "bpmProcessIdNotNull";
		LUCENE_BPM_PROCESS_ID = LUCENE_BASE_PREFIX + "bpmProcessId";
		LUCENE_INVENTORY_CODE = LUCENE_BASE_PREFIX + "inventoryCode";
		LUCENE_INVENTORY_CODE_REVERSE = LUCENE_BASE_PREFIX + "inventoryCodeReverse";
		LUCENE_PRICE_VALUE = LUCENE_BASE_PREFIX + "priceValueSys";
		LUCENE_MEMO_FIELD = LUCENE_BASE_PREFIX + "luceneDocumentMemo";
		LUCENE_PARENT_FIELDS = LUCENE_BASE_PREFIX + "parentFields";
		LUCENE_PARENT_TYPES = LUCENE_BASE_PREFIX + "parentTypes";
		LUCENE_GROUP_PREFIX = LUCENE_BASE_PREFIX + "gr";
		LUCENE_TYPE_PREFIX = LUCENE_BASE_PREFIX + "type";
		LUCENE_COMMENT_PREFIX = LUCENE_BASE_PREFIX + "comm";
		LUCENE_QUICK_TASK_PREFIX = LUCENE_BASE_PREFIX + "qt";
		LUCENE_CASE_DOCUMENT_PREFIX = LUCENE_BASE_PREFIX + "c2doc";
		LUCENE_CREATE_DATE = LUCENE_BASE_PREFIX + "createDate";
		LUCENE_MODIFY_DATE = LUCENE_BASE_PREFIX + "lastModifyDate";
	}
}
