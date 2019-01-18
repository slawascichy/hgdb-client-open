package io.hgdb.mercury.client.test;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import pro.ibpm.mercury.entities.MEntity;
import pro.ibpm.mercury.registry.RegistrySupport;

public interface ScenarioWithSpring extends Scenario {

	/**
	 * Inicjalizacja podstawowych parametrów scenariusza
	 * 
	 * @param registry
	 *            rejestr bean'ów Spring'a
	 * @param datasourceDefault
	 *            źródło danych pozwalające na nawiązanie bezpośredniego
	 *            połączenia z testową domyślną bazą danych
	 * @param datasourceCommon
	 *            źródło danych pozwalające na nawiązanie bezpośredniego
	 *            połączenia z testową ogólną bazą danych
	 * @param skipTest
	 *            flaga, czy test ma być pominięty
	 */
	void init(RegistrySupport registry, DataSource datasourceDefault,
			DataSource datasourceCommon, boolean skipTest);

	RegistrySupport getRegistry();

	Map<Class<? extends MEntity>, List<EntityObjectWithDbStatus>> getUsedValues();

	boolean isForceCleanAllData();

	void setForceCleanAllData(boolean forceCleanAllData);
}
