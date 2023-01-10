package io.hgdb.mercury.client.utils;

import pro.ibpm.mercury.business.data.utils.MrcVariableReaderCollector;
import pro.ibpm.mercury.context.Context;

/**
 * 
 * MrcVariableReaderCollectorUtils wsparcie dla obsługi kolektorów wartości zbierających dane podczas parsowania DTO lub
 * XML. Kolektor wstawiany będzie do kontekstu i przesyłany do odpowiedniej metody.
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class MrcVariableReaderCollectorUtils {

	/**
	 * Nazwa dodatkowego parametr w kontekście zawierającego instancję kolektora.
	 */
	public static final String MRC_COLLECTOR_OBJECT = "Object.MrcVariableReaderCollector";

	private MrcVariableReaderCollectorUtils() {
	}

	/**
	 * Ustawianie kolektora jako dodatkowy parametr w kontekście.
	 * 
	 * @param context
	 *                      kontekst wykonywanej operacji, do którego zostanie dodany kolektor
	 * @param variableNames
	 *                      nazwy parametrów, których wartości mają być zbierane
	 * @return zmieniony obiekt kontekstu
	 */
	public static Context setMrcVariableReaderCollector(Context context, String... variableNames) {
		context.setAdditionalPropertyValue(MRC_COLLECTOR_OBJECT, new MrcVariableReaderCollector(variableNames));
		return context;
	}

	/**
	 * Pobieranie ustawionego w kontekście kolektora danych
	 * 
	 * @param context
	 *                kontekst wykonywanej operacji
	 * @return instancja obiektu kolektora, albo {@code null} gdy nie jest ustawiony.
	 */
	public static MrcVariableReaderCollector getMrcVariableReaderCollector(Context context) {
		return (MrcVariableReaderCollector) context.getAdditionalPropertyValue(MRC_COLLECTOR_OBJECT);
	}

}
