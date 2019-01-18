package io.hgdb.mercury.client.mock;

import java.util.Collection;
import java.util.SortedMap;

import io.hgdb.mercury.client.mock.helpers.MockType;
import pro.ibpm.mercury.entities.MEntity;

public interface TProvider<T extends MEntity> {

	String ALL_ROWS_COUNTER_NAME = "allRows";

	/**
	 * @return the {@link #rows}
	 */
	SortedMap<Object, T> getRows();

	/**
	 * @return the {@link #rows}
	 */
	Collection<T> getCollection();

	/**
	 * @return the {@link #isLogicTest}
	 */
	boolean isLogicTest();

	/**
	 * @param isLogicTest
	 *            the {@link #isLogicTest} to set
	 */
	void setLogicTest(boolean isLogicTest);

	/**
	 * @return the {@link #entityClass}
	 */
	Class<T> getEntityClass();

	MockType getMockType();

}