package io.hgdb.mercury.client.mock.dict;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import pro.ibpm.mercury.entities.dict.Source;

public class TSourcesProvider extends TAbstractProvider<Source> {

	public TSourcesProvider() {
		super("/pro/ibpm/mercury/mock/dict/tSources.csv");
	}

	private static TSourcesProvider instance = new TSourcesProvider();

	/** pobranie instancji provider'a */
	public static TSourcesProvider getInstance() {
		return instance;
	}

	public static enum TSource {
		value, name, decription;

		public Object getValue(Source row) {
			switch (this) {
			case value:
				return row.getName();
			case name:
				return row.getName();
			case decription:
				return row.getDescription();
			default:
				return null;
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.dict.TAbstractProvider#rowMapper(java.lang.String
	 * [])
	 */
	@Override
	protected Source rowMapper(String[] row) {
		Source val = new Source();
		/* ID */
		val.setValue(row[0]);
		val.setName(row[1]);
		val.setDescription(row[2]);
		return val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[],
	 * java.lang.String[])
	 */
	@Override
	protected Source rowMapper(String[] row, String[] header) {
		return rowMapper(row);
	}

}
