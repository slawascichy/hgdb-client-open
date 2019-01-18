package io.hgdb.mercury.client.test;

import java.util.Calendar;
import java.util.Map;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;

import pl.slawas.twl4j.Logger;
import pl.slawas.twl4j.LoggerFactory;

public abstract class ScenarioSupport extends TestCase implements Scenario {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private final String label;

	private boolean skipTest;

	private ScenarioStatus status = ScenarioStatus.SKIPED;

	private Long executionTime = 0L;

	public ScenarioSupport(String label) {
		this.label = label;
	}

	public ScenarioSupport(String label, boolean skipTest) {
		super();
		this.label = label;
		init(skipTest);
	}

	public void init(boolean skipTest) {
		this.skipTest = skipTest;
	}

	public ScenarioStatus execute() {

		if (skipTest) {
			status = ScenarioStatus.SKIPED;
			return status;
		}

		logger.info(
				"\n************************************"
						+ "\n*  START SCENARIUSZA {}..."
						+ "\n************************************",
				new Object[] { StringUtils.isBlank(label) ? getClass() : label });

		Long startTime = Calendar.getInstance().getTimeInMillis();
		try {

			/* Inicjalizacja oczekiwanych parametrów */
			Map<String, Object> expectedValues = step0InitExpectedParams();
			/* Uruchomienie testów */
			step1RunScenario(expectedValues);
			status = ScenarioStatus.OK;
		} catch (AssertionFailedError e) {
			status = ScenarioStatus.FAILED;
			logger.error("Błąd wyniku testu.", e);
		} catch (Exception e) {
			status = ScenarioStatus.ERROR;
			logger.error("Błąd wykonania scenariusza.", e);
		} finally {
			finallyAction();
		}
		Long endTime = Calendar.getInstance().getTimeInMillis();
		this.executionTime = endTime - startTime;
		logger.info("\n************************************"
				+ "\n*  KONIEC SCENARIUSZA {}: {}"
				+ "\n************************************", new Object[] {
				StringUtils.isBlank(label) ? getClass() : label, status });
		return status;

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	protected abstract Map<String, Object> step0InitExpectedParams()
			throws Exception;

	/**
	 * 
	 * @param expectedValues
	 * @throws AssertionFailedError
	 * @throws Exception
	 */
	protected abstract void step1RunScenario(Map<String, Object> expectedValues)
			throws AssertionFailedError, Exception;

	/**
	 * 
	 * @param em
	 */
	protected abstract void finallyAction();

	/**
	 * @return the {@link #status}
	 */
	public ScenarioStatus getStatus() {
		return status;
	}

	/**
	 * @return the {@link #executionTime}
	 */
	public Long getExecutionTime() {
		return executionTime;
	}

	/**
	 * @return the {@link #label}
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param status
	 *            the {@link #status} to set
	 */
	protected void setStatus(ScenarioStatus status) {
		this.status = status;
	}

	/**
	 * @param executionTime
	 *            the {@link #executionTime} to set
	 */
	protected void setExecutionTime(Long executionTime) {
		this.executionTime = executionTime;
	}

	/**
	 * @return the {@link #skipTest}
	 */
	protected boolean isSkipTest() {
		return skipTest;
	}

}
