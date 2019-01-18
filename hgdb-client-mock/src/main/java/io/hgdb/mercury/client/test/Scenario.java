package io.hgdb.mercury.client.test;


public interface Scenario {

	/**
	 * Inicjalizacja podstawowych parametrów scenariusza
	 * 
	 * @param skipTest
	 *            flaga, czy test ma być pominięty
	 */
	void init(boolean skipTest);

	/**
	 * Uruchomienie testu
	 * 
	 * @return status testu
	 */
	ScenarioStatus execute();

	ScenarioStatus getStatus();

	Long getExecutionTime();

	String getLabel();

}
