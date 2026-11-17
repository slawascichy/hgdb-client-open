package io.hgdb.mercury.client.cxf.context;

import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.context.ContextRequestProperties.SaveRequestContext;

/**
 * SaveRequestFlags - wygodne ustawianie flag zapisu sprawy w kontekście żądania klienta (zadanie 56).
 * <p>
 * Flagi zapisu są parametrami żądania (grupa {@link SaveRequestContext}, nazwy z prefiksem
 * {@code saveRequestContext.}), które klient przesyła w kontekście. Klasa ukrywa nazwy parametrów i zapis wartości
 * tekstowych: metody ustawiające zwracają kontekst, więc można je łączyć w łańcuch.
 *
 * <pre>
 * SaveRequestFlags.nullIfFieldNotInRequest(context, true);
 * caseBusiness.save(context, mrcObject, true, typeCodes);
 * </pre>
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 */
public final class SaveRequestFlags {

	private SaveRequestFlags() {
	}

	/**
	 * Czy pola nieustawione w żądaniu zapisu sprawy mają zostać puste ({@code null}), zamiast dostać wartości zapisane w
	 * bazie danych. Dotyczy aktualizacji, która przywraca poprzednią wersję typu sprawy (żądanie z mniejszą liczbą pól
	 * niż zapisana sprawa). Bez ustawienia obowiązuje wartość domyślna serwera
	 * ({@code mercury.saveRequestContext.valueIsNullIfFieldNotExisisInRequest.default}, domyślnie {@code false}).
	 *
	 * @param context
	 *            kontekst żądania
	 * @param nullIfFieldNotInRequest
	 *            {@code true}: pola nieustawione w żądaniu stają się puste; {@code false}: dostają wartości z bazy
	 * @return ten sam kontekst
	 */
	public static Context nullIfFieldNotInRequest(Context context, boolean nullIfFieldNotInRequest) {
		SaveRequestContext.VALUE_IS_NULL_IF_FIELD_NOT_EXISTS_IN_REQUEST.setContextValue(context,
				Boolean.toString(nullIfFieldNotInRequest));
		return context;
	}

	/**
	 * @param context
	 *            kontekst żądania
	 * @return wartość flagi ustawiona w kontekście; {@code null}, gdy flagi nie ustawiono (wtedy decyduje wartość domyślna
	 *         serwera)
	 */
	public static Boolean isNullIfFieldNotInRequest(Context context) {
		String value = context.getRequestPropertyValue(
				SaveRequestContext.VALUE_IS_NULL_IF_FIELD_NOT_EXISTS_IN_REQUEST.getPropertyName());
		return value == null ? null : Boolean.valueOf(value);
	}

	/**
	 * Usuwa flagę z kontekstu (wraca wartość domyślna serwera).
	 *
	 * @param context
	 *            kontekst żądania
	 * @return ten sam kontekst
	 */
	public static Context clearNullIfFieldNotInRequest(Context context) {
		context.getRequestProperties()
				.remove(SaveRequestContext.VALUE_IS_NULL_IF_FIELD_NOT_EXISTS_IN_REQUEST.getPropertyName());
		return context;
	}
}
