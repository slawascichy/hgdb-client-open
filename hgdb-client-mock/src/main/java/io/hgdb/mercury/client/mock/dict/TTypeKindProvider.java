package io.hgdb.mercury.client.mock.dict;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import pro.ibpm.mercury.entities.dict.Source;
import pro.ibpm.mercury.entities.dict.TypeKind;

public class TTypeKindProvider extends TAbstractProvider<TypeKind> {

	public TTypeKindProvider() {
		super("/pro/ibpm/mercury/mock/dict/tTypeKinds.csv");
	}

	private static TTypeKindProvider instance = new TTypeKindProvider();

	/** pobranie instancji provider'a */
	public static TTypeKindProvider getInstance() {
		return instance;
	}

	public static enum TTypeKind {
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
	protected TypeKind rowMapper(String[] row) {
		TypeKind val = new TypeKind();
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
	protected TypeKind rowMapper(String[] row, String[] header) {
		return rowMapper(row);
	}

}
