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
package io.hgdb.client.mock;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.hgdb.client.mock.helpers.MockType;
import pl.slawas.entities.NameValuePair;
import pl.slawas.helpers.MockLoader;
import pro.ibpm.mercury.entities.MEntity;
import pro.ibpm.mercury.entities.MEntityWithPublishVersion;

/**
 * TAbstractProvider
 * 
 * @author Sławomir Cichy &lt;slawas@slawas.pl&gt;
 * @version $Revision: 1.1 $
 * 
 */
public abstract class TAbstractProvider<T extends MEntity> implements TProvider<T> {

	final protected Logger logger = LoggerFactory.getLogger(getClass());

	private SortedMap<Object, T> rows = null;

	private static final Object rowsLoaderLock = new Object();

	private final Class<T> entityClass;

	private final String sourceFile;

	private boolean isLogicTest = false;

	/** Czy plik CSV posiada nagłówek z oznaczeniem kolumn. */
	private final boolean fileHasHeader;

	public TAbstractProvider(String sourceFile) {
		this(sourceFile, false);
	}

	@SuppressWarnings("unchecked")
	public TAbstractProvider(String sourceFile, boolean fileHasHeader) {
		super();
		this.sourceFile = sourceFile;
		this.fileHasHeader = fileHasHeader;
		Class<?> tmpClass = this.getClass();
		while (!(tmpClass.getGenericSuperclass() instanceof ParameterizedType)) {
			tmpClass = tmpClass.getSuperclass();
		}
		this.entityClass = (Class<T>) ((ParameterizedType) tmpClass.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected SortedMap<Object, T> loadFromFile() {
		synchronized (rowsLoaderLock) {
			if (rows == null) {
				this.logger.debug("Ładowanie danych testowych z pliku {}...", sourceFile);
				/* Inicjalizacja mapy wierszy */
				rows = new TreeMap<Object, T>();
				List<String[]> readedRows = MockLoader.loadCSV(TAbstractProvider.class, this.sourceFile);
				if (readedRows != null && !readedRows.isEmpty()) {
					/* plik nie jest pusty - START */
					setNULL(readedRows);
					String[] header = new String[0];
					/* jeśli plik CVS zawiera nagłówek to wczytaj nagłówek */
					if (fileHasHeader) {
						/* plik zawiera nagłówek */
						header = readedRows.remove(0);
						for (String[] row : readedRows) {
							T val = rowMapper(row, header);
							if (val != null) {
								if (val instanceof MEntityWithPublishVersion) {
									if (StringUtils.isBlank(((MEntityWithPublishVersion) val).getPublishVersion())) {
										String versionUUID = UUID.randomUUID().toString();
										((MEntityWithPublishVersion) val).setPublishVersion(versionUUID);
									}
								}
								if (StringUtils.isNotBlank(((NameValuePair) val).getValue())) {
									rows.put(((NameValuePair) val).getValue(), val);
								}
							}
						}
					} else {
						/* plik nie zawiera nagłówka */
						for (String[] row : readedRows) {
							T val = rowMapper(row);
							if (val != null) {
								if (val instanceof MEntityWithPublishVersion) {
									if (StringUtils.isBlank(((MEntityWithPublishVersion) val).getPublishVersion())) {
										String versionUUID = UUID.randomUUID().toString();
										((MEntityWithPublishVersion) val).setPublishVersion(versionUUID);
									}
								}
								rows.put(((NameValuePair) val).getValue(), val);
							}
						}
					}
					logger.debug("Załadowano {} wierszy", rows.size());
					/* plik nie jest pusty - KONIEC */
				} else {
					logger.warn("Plik {} jest pusty lub dane są błędne", this.sourceFile);
				}
			}
			return rows;
		}
	}

	protected abstract T rowMapper(String[] row);

	protected abstract T rowMapper(String[] row, String[] header);

	/* Overridden (non-Javadoc) */
	@Override
	public SortedMap<Object, T> getRows() {
		return rows;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Collection<T> getCollection() {
		return rows != null ? rows.values() : new ArrayList<T>();
	}

	/**
	 * Budowanie liczników. Podczas nadpisywania implementacji należy wyjście
	 * ustawić na {@code true}.
	 * 
	 * @param object
	 *            załadowana encja
	 * @param counters
	 *            mapa liczników
	 * @return {@code true} jeżeli metoda jest zaimplementowana poza transakcją,
	 */
	protected boolean incrementCounters(T object, Map<String, Long> counters) {
		return false;
	}

	private void setNULL(List<String[]> readedRows) {
		for (String[] row : readedRows) {
			setNULL(row);
		}
	}

	private void setNULL(String[] rows) {
		for (int i = 0; i < rows.length; i++) {
			if ("null".equalsIgnoreCase(rows[i])) {
				rows[i] = null;
			}
		}
	}

	/* Overridden (non-Javadoc) */
	@Override
	public boolean isLogicTest() {
		return isLogicTest;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public void setLogicTest(boolean isLogicTest) {
		this.isLogicTest = isLogicTest;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Class<T> getEntityClass() {
		return entityClass;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public MockType getMockType() {
		return MockType.dualData;
	}

}
