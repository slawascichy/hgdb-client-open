/*
 * Copyright 2014 Sci Software Sławomir Cichy
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.	
 */
package io.hgdb.client.test;

import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;

import org.junit.Test;

import pl.slawas.helpers.Strings;
import pl.slawas.twl4j.Logger;
import pl.slawas.twl4j.LoggerFactory;

/**
 * TestCaseSupport - abstrakcja dla realizacji prostych scenariuszy testowych
 * 
 * @author Sławomir Cichy &lt;slawas@slawas.pl&gt;
 * @version $Revision: 1.1 $
 * 
 */
public abstract class TestCaseSupport extends TestCase {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	public static final String TEST_SKIP_PREFIX = "test.skip.";

	private static final Properties testProperties = new Properties();

	private final boolean skipPackageTest;

	static {
		Map<String, String> _Properties = TestPropertiesLoader.loadProperties();
		testProperties.putAll(_Properties);
	}

	protected TestCaseSupport(boolean skipPackageTest) {
		super();
		this.skipPackageTest = skipPackageTest;
	}

	@Test
	public void testScenarios() {

		if (isSkipPackageTest()) {
			return;
		}

		ScenarioStatus scenarioStatus;
		Scenario currScenario = null;
		final Scenario[] scenarios = createScenarioList();
		try {
			for (Scenario scenario : scenarios) {
				currScenario = scenario;
				boolean skipTest = Boolean.parseBoolean(testProperties
						.getProperty(TEST_SKIP_PREFIX + scenario.getLabel()));
				scenario.init(skipTest);
				scenarioStatus = scenario.execute();
				TestPropertiesLoader.saveErrorStatus(scenario.getLabel(),
						scenarioStatus, logger);
				assertEquals(
						scenario.getLabel(),
						(!skipTest ? ScenarioStatus.OK : ScenarioStatus.SKIPED),
						scenarioStatus);
			}
		} finally {
			printResult2Log(scenarios, currScenario);
		}

	}

	private static final int row1size = 75;
	private static final int row2size = 15;
	private static final int row3size = 17;

	protected void printResult2Log(Scenario[] result, Scenario lastExecuted) {
		StringBuffer out = new StringBuffer("\n Statystyka " + result.length
				+ " testów");
		out.append("\n+-" + Strings.lpad("-", "-", row1size));
		out.append("-+-" + Strings.rpad("-", "-", row2size));
		out.append("-+-" + Strings.lpad("-", "-", row3size));
		out.append("-+");
		out.append(printRow("name", "status", "executeTime [ms]"));
		out.append("\n+-" + Strings.lpad("-", "-", row1size));
		out.append("-+-" + Strings.rpad("-", "-", row2size));
		out.append("-+-" + Strings.lpad("-", "-", row3size));
		out.append("-+");
		Long summaryExecutionTime = 0L;
		for (Scenario scenario : result) {
			out.append(printRow(scenario.getLabel(), scenario.getStatus()
					.name(), Long.toString(scenario.getExecutionTime())));
			summaryExecutionTime = summaryExecutionTime
					+ scenario.getExecutionTime();
		}
		out.append("\n+-" + Strings.lpad("-", "-", row1size));
		out.append("-+-" + Strings.rpad("-", "-", row2size));
		out.append("-+-" + Strings.lpad("-", "-", row3size));
		out.append("-+");
		out.append(printRow(
				"Summary Report",
				(lastExecuted != null ? (lastExecuted.getStatus().equals(
						ScenarioStatus.OK)
						|| lastExecuted.getStatus().equals(
								ScenarioStatus.SKIPED) ? "BUILD SUCCESS"
						: lastExecuted.getStatus().name()) : "n/a"), Long
						.toString(summaryExecutionTime)));
		out.append("\n+-" + Strings.lpad("-", "-", row1size));
		out.append("-+-" + Strings.rpad("-", "-", row2size));
		out.append("-+-" + Strings.lpad("-", "-", row3size));
		out.append("-+");

		logger.info("{}", out.toString());
	}

	private String printRow(String name, String status, String executionTime) {
		StringBuffer out = new StringBuffer();
		out.append("\n| " + Strings.rpad(name, " ", row1size));
		out.append(" | " + Strings.lpad(status, " ", row2size));
		out.append(" | " + Strings.lpad(executionTime, " ", row3size));
		out.append(" |");
		return out.toString();
	}

	abstract protected Scenario[] createScenarioList();

	/**
	 * @return the {@link #testProperties}
	 */
	public static Properties getTestProperties() {
		return testProperties;
	}

	/**
	 * @return the {@link #skipPackageTest}
	 */
	public boolean isSkipPackageTest() {
		return skipPackageTest;
	}
}
