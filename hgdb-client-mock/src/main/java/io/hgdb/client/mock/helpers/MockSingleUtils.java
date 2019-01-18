package io.hgdb.client.mock.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.slawas.twl4j.Logger;
import pl.slawas.twl4j.LoggerFactory;
import pro.ibpm.mercury.entities.MIdModifier;

/**
 * 
 * MockSingleUtils klasa narzędziowa wspierająca przygotowanie "indywidualnych"
 * danych testowych w bazie danych oraz ich usunięcie po wykonaniu testów.
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
public class MockSingleUtils {

	private static transient Logger logger = LoggerFactory.getLogger(MockSingleUtils.class);

	/**
	 * Metoda pomocnicza do przygotowania odpowiedniej mapy encji
	 * 
	 * @param entities
	 *            lista encji (różnych typów)
	 * @return mapa encji gdzie kluczem jest nazwa klasy encji a obiektem jest
	 *         lista encji danego typu.
	 */
	@SuppressWarnings("rawtypes")
	public static Map<Class<?>, List<Object>> prepareEntitiesMap(List<Object> entities) {
		Map<Class<?>, List<Object>> map = new HashMap<Class<?>, List<Object>>();
		if (entities == null || entities.isEmpty()) {
			return map;
		}
		for (Object entity : entities) {
			if (entity instanceof MIdModifier) {
				Class<?> key = entity.getClass();
				List<Object> currEntities = map.get(key);
				if (currEntities == null) {
					currEntities = new ArrayList<Object>();
					map.put(key, currEntities);
				}
				currEntities.add(entity);
				if (logger.isTraceEnabled()) {
					logger.trace("prepareEntitiesMap: entity {}: id={}, list.size={}",
							new Object[] { key, ((MIdModifier) entity).getId(), currEntities.size() });
				}
			}
		}
		return map;
	}

}
