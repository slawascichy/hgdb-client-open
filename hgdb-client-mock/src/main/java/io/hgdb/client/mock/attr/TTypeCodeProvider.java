package io.hgdb.client.mock.attr;

import io.hgdb.client.mock.TAbstractProvider;
import pro.ibpm.mercury.entities.attr.TypeCode;

public class TTypeCodeProvider extends TAbstractProvider<TypeCode> {

	public TTypeCodeProvider() {
		super("/pro/ibpm/mercury/mock/attr/tTypeCode.csv");
	}

	private static TTypeCodeProvider instance = new TTypeCodeProvider();

	/** pobranie instancji provider'a */
	public static TTypeCodeProvider getInstance() {
		return instance;
	}

	public static enum TTypeCode {
		name;

		public Object getValue(TypeCode row) {
			switch (this) {
			case name:
				return row.getName();
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
	protected TypeCode rowMapper(String[] row) {
		return new TypeCode(row[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[],
	 * java.lang.String[])
	 */
	@Override
	protected TypeCode rowMapper(String[] row, String[] header) {
		return rowMapper(row);
	}

}
