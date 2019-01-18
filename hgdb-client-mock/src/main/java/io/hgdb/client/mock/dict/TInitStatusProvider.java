package io.hgdb.client.mock.dict;

import io.hgdb.client.mock.TAbstractProvider;
import pro.ibpm.mercury.entities.dict.InitStatus;
import pro.ibpm.mercury.entities.dict.Source;

public class TInitStatusProvider extends TAbstractProvider<InitStatus> {

	public TInitStatusProvider() {
		super("/pro/ibpm/mercury/mock/dict/tInitStatus.csv");
	}

	private static TInitStatusProvider instance = new TInitStatusProvider();

	/** pobranie instancji provider'a */
	public static TInitStatusProvider getInstance() {
		return instance;
	}

	public static enum TInitStatus {
		name, decription;

		public Object getValue(Source row) {
			switch (this) {
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
	protected InitStatus rowMapper(String[] row) {
		InitStatus val = new InitStatus();
		/* ID */
		val.setName(row[0]);
		val.setDescription(row[1]);
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
	protected InitStatus rowMapper(String[] row, String[] header) {
		return rowMapper(row);
	}
	
}
