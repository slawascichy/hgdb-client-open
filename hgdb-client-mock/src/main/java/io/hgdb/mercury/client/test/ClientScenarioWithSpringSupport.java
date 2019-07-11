package io.hgdb.mercury.client.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import io.hgdb.mercury.client.mock.helpers.MockSingleUtils;
import junit.framework.AssertionFailedError;
import pro.ibpm.mercury.entities.MEntity;
import pro.ibpm.mercury.entities.MIdModifier;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.registry.RegistrySupport;

public abstract class ClientScenarioWithSpringSupport extends ScenarioSupport implements ScenarioWithSpring {

	private Map<Class<? extends MEntity>, List<EntityObjectWithDbStatus>> usedValues = new HashMap<>();

	private RegistrySupport registry;

	private boolean forceCleanAllData = false;

	/**
	 * Flaga z informacją czy było pełne czyszczenie bazy danych, aby nie duplikować
	 * tej operacji
	 */
	private boolean cleanedDB = false;

	public ClientScenarioWithSpringSupport(String label) {
		super(label);
	}

	@Override
	@Deprecated
	public void init(boolean skipTest) {
		throw new IllegalAccessError("Metoda init(String) nie jest wspierana w obiekcie ScenarioWithSpringSupport.");
	}

	/* Overridden (non-Javadoc) */
	@Override
	protected Map<String, Object> step0InitExpectedParams() throws Exception {
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	protected void step1RunScenario(Map<String, Object> expectedValues) throws AssertionFailedError, Exception {
	}

	/* Overridden (non-Javadoc) */
	@Override
	protected void finallyAction() {
	}

	public void init(RegistrySupport registry, boolean skipTest) {
		this.registry = registry;
		super.init(skipTest);
	}

	@Override
	public ScenarioStatus execute() {

		if (isSkipTest()) {
			setStatus(ScenarioStatus.SKIPED);
			return getStatus();
		}

		logger.info(
				"\n************************************" + "\n*  START SCENARIUSZA {}..."
						+ "\n************************************",
				new Object[] { StringUtils.isBlank(getLabel()) ? getClass() : getLabel() });

		Long startTime = Calendar.getInstance().getTimeInMillis();
		try {

			if (forceCleanAllData) {
				cleanAllDataFromDB(logger);
			}
			/*
			 * resetuję czas startu testu bo inicjalizacja kontekstu spring'a nie powinna
			 * być wliczana do czasu wykonania testu.
			 */
			startTime = Calendar.getInstance().getTimeInMillis();
			/* Inicjalizacja oczekiwanych parametrów */
			Map<String, Object> expectedValues = step0InitExpectedParams();
			/* Przygotowanie podstawowych danych testowych */
			List<Object> preparedData = step1PrepareTestData(expectedValues);
			if (preparedData != null) {
				Map<Class<?>, List<Object>> entitiesMap = MockSingleUtils.prepareEntitiesMap(preparedData);
				Map<Class<? extends MEntity>, List<EntityObjectWithDbStatus>> uVals = addTestData(entitiesMap);
				usedValues.putAll(uVals);
			}
			/* Uruchomienie testów */
			step2RunScenario(expectedValues);
			/* Wyczyszczenie dodatkowych danych testowych */
			step3CleanExternalTestData();
			/* Wyczyszczenie podstawowych danych testowych */
			setStatus(step4CleanTestData());
		} catch (AssertionFailedError e) {
			setStatus(ScenarioStatus.FAILED);
			logger.error("Błąd wyniku testu.", e);
			step4CleanTestData();
		} catch (Exception e) {
			setStatus(ScenarioStatus.ERROR);
			logger.error("Błąd wykonania scenariusza.", e);
			step4CleanTestData();
		} finally {
			finallyAction();
		}
		Long endTime = Calendar.getInstance().getTimeInMillis();
		setExecutionTime(endTime - startTime);
		logger.info(
				"\n************************************" + "\n*  KONIEC SCENARIUSZA {}: {}"
						+ "\n************************************",
				new Object[] { StringUtils.isBlank(getLabel()) ? getClass() : getLabel(), getStatus() });
		return getStatus();

	}

	/**
	 * Rejestracja nowych encji używanych/tworzonych podczas testów
	 * 
	 * @param entity
	 *            obiekt encji
	 * @param dbStatus
	 *            statu encji jaki został zrealizowany po operacji testowej.
	 */
	@SuppressWarnings("rawtypes")
	public <T extends MEntity> void registerTestEntity(T entity, EntityDbStatus dbStatus) {
		if (entity instanceof MIdModifier) {
			List<EntityObjectWithDbStatus> list = usedValues.get(entity.getClass());
			if (list == null) {
				logger.debug("--> registerTestEntity: {}, id={}, Nowa lista",
						new Object[] { entity.getClass().getSimpleName(), ((MIdModifier) entity).getId() });
				list = new ArrayList<EntityObjectWithDbStatus>();
				usedValues.put(entity.getClass(), list);
			}
			list.add(new EntityObjectWithDbStatus(entity, dbStatus));
			logger.debug("--> registerTestEntity: {}, id={}, dbStatus: {}",
					new Object[] { entity.getClass().getSimpleName(), ((MIdModifier) entity).getId(), dbStatus });
		} else {
			logger.warn(
					"registerTestEntity: Encja {} nie może zostać zarejestrowana ponieważ nie spełnia interfejsu MIdModifier.",
					entity.getClass());
		}

	}

	/**
	 * @param expectedValues
	 * @throws Exception
	 */
	protected abstract List<Object> step1PrepareTestData(Map<String, Object> expectedValues) throws Exception;

	/**
	 * Metoda czyszczenia bazy danych zaimplementowana tak aby wykonywana była tylko
	 * raz.
	 * 
	 * @see #cleanedDB
	 * 
	 * @param logger
	 * @throws Exception
	 */
	public final void cleanAllDataFromDB(Logger logger) throws Exception {
		if (!cleanedDB) {
			cleanAllDataFromDBImpl(logger);
			cleanedDB = true;
		}
	}

	/**
	 * Implementacja czyszczenia bazy spraw charakterystyczna dla danej warstwy.
	 * 
	 * @param logger
	 * @throws Exception
	 */
	abstract protected void cleanAllDataFromDBImpl(Logger logger) throws Exception;

	/**
	 * @param expectedValues
	 * @throws AssertionFailedError
	 * @throws Exception
	 */
	protected abstract void step2RunScenario(Map<String, Object> expectedValues) throws AssertionFailedError, Exception;

	/**
	 * 
	 * @param em
	 * @throws Exception
	 */
	protected abstract void step3CleanExternalTestData() throws Exception;

	protected abstract void clearTestData(Map<Class<? extends MEntity>, List<EntityObjectWithDbStatus>> usedValues)
			throws Exception;

	protected abstract Map<Class<? extends MEntity>, List<EntityObjectWithDbStatus>> addTestData(
			Map<Class<?>, List<Object>> entitiesMap) throws Exception;

	/**
	 * Wycofanie zmian w bazie danych dokonanych przez test.
	 * 
	 * @throws MercuryException
	 */
	protected ScenarioStatus step4CleanTestData() {
		try {
			clearTestData(usedValues);
		} catch (Exception e) {
			logger.error("Blad czyszczenia danych testu", e);
			return ScenarioStatus.ERROR;
		}
		usedValues.clear();
		return ScenarioStatus.OK;
	}

	/**
	 * @return the {@link #usedValues}
	 */
	public Map<Class<? extends MEntity>, List<EntityObjectWithDbStatus>> getUsedValues() {
		return usedValues;
	}

	/**
	 * @return the {@link #registry}
	 */
	public RegistrySupport getRegistry() {
		return registry;
	}

	@SuppressWarnings("rawtypes")
	protected static <E extends MIdModifier> List<String> convert2IdList(Collection<E> cList) {
		List<String> idList = new ArrayList<String>();
		if (cList != null) {
			for (E e : cList) {
				idList.add(e.getId().toString());
			}
		}
		return idList;
	}

	@SuppressWarnings("rawtypes")
	protected static <E extends MIdModifier> void compare(Collection<E> foundedCases, Collection<String> expectedIdList)
			throws MercuryException {
		List<String> idList = convert2IdList(foundedCases);

		assertEquals("Niepoprawna ilość znalezionych spraw : " + idList, expectedIdList.size(), idList.size());

		assertTrue("Znaleziono niepoprawne sprawy : " + idList + ", a spodziewalem sie : " + expectedIdList,
				idList.containsAll(expectedIdList));

	}

	/**
	 * @return the {@link #forceCleanAllData}
	 */
	public boolean isForceCleanAllData() {
		return forceCleanAllData;
	}

	/**
	 * @param forceCleanAllData
	 *            the {@link #forceCleanAllData} to set
	 */
	public void setForceCleanAllData(boolean forceCleanAllData) {
		this.forceCleanAllData = forceCleanAllData;
	}

	/**
	 * @return the {@link #cleanedDB}
	 */
	public boolean isCleanedDB() {
		return cleanedDB;
	}

}
