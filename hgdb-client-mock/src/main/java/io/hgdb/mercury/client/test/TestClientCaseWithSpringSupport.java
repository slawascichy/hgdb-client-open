package io.hgdb.mercury.client.test;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import pl.slawas.helpers.Strings;
import pl.slawas.twl4j.Logger;
import pl.slawas.twl4j.LoggerFactory;
import pro.ibpm.mercury.registry.RegistrySupport;

/**
 * TestClientCaseWithSpringSupport - abstrakcja dla realizacji scenariuszy
 * testowych ze Spring'iem dla klienta.
 * 
 * @author Sławomir Cichy &lt;slawas@slawas.pl&gt;
 * @version $Revision: 1.1 $
 * 
 */
public abstract class TestClientCaseWithSpringSupport extends TestCaseSupport implements BeanFactoryAware {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private final RegistrySupport registry;

	private boolean firstClean = true;

	protected TestClientCaseWithSpringSupport(RegistrySupport registry, boolean skipPackageTest) {
		super(skipPackageTest);
		this.registry = registry;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
	}

	@Test
	@Override
	public void testScenarios() {

		if (isSkipPackageTest()) {
			return;
		}

		ScenarioStatus scenarioStatus;
		Scenario currScenario = null;
		String propName;
		final Scenario[] scenarios = createScenarioList();
		try {
			for (Scenario scenario : scenarios) {
				currScenario = scenario;
				propName = TEST_SKIP_PREFIX + scenario.getLabel();
				String propVal = getTestProperties().getProperty(propName);
				boolean skipTest = false;
				if (StringUtils.isNotBlank(propVal)) {
					skipTest = Boolean.parseBoolean(Strings.lrtrim(getTestProperties().getProperty(propName)));
				}
				logger.trace("{}: skipTest={}", new Object[] { propName, skipTest });
				if (scenario instanceof ScenarioWithSpring) {
					((ScenarioWithSpring) scenario).init(getRegistry(), null, null, skipTest);
					propName = TestPropertiesLoader.STATUS_PROP_ALL;
					String lastAllStatus = getTestProperties().getProperty(propName);
					propName = TestPropertiesLoader.STATUS_PROP_PREFIX + scenario.getLabel();
					String lastStatus = getTestProperties().getProperty(propName);
					/**
					 * Parametr ten jest ustawiany tylko wtedy gdy scenariusz
					 * zakończył się porażką/błędem. Zobacz
					 * {@link TestPropertiesLoader#saveErrorStatus}.
					 */
					boolean forceCleanAllData = StringUtils.isNotBlank(lastStatus)
							|| StringUtils.isNotBlank(lastAllStatus);
					logger.info("{}: forceCleanAllData={}", new Object[] { propName, forceCleanAllData && firstClean });
					((ScenarioWithSpring) scenario).setForceCleanAllData(forceCleanAllData && firstClean);
					if (!skipTest) {
						firstClean = false;
					}
				} else {
					scenario.init(skipTest);
				}
				scenarioStatus = scenario.execute();
				assertEquals(scenario.getLabel(), (!skipTest ? ScenarioStatus.OK : ScenarioStatus.SKIPED),
						scenarioStatus);
			}
		} finally {
			printResult2Log(scenarios, currScenario);
			if (currScenario != null) {
				TestPropertiesLoader.saveErrorStatus(currScenario.getLabel(), currScenario.getStatus(), logger);
			}
		}

	}

	/**
	 * @return the {@link #registry}
	 */
	public RegistrySupport getRegistry() {
		return registry;
	}

}
