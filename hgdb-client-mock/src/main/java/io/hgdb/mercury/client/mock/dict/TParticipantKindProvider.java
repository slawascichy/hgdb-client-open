package io.hgdb.mercury.client.mock.dict;

import java.util.HashMap;
import java.util.Map;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import pro.ibpm.mercury.entities.dict.ParticipantKind;
import pro.ibpm.mercury.entities.dict.Source;

public class TParticipantKindProvider extends TAbstractProvider<ParticipantKind> {

	public static Map<String, ParticipantKind> kindMap = new HashMap<String, ParticipantKind>();

	public TParticipantKindProvider() {
		super("/pro/ibpm/mercury/mock/dict/tParticipantKinds.csv");
	}

	private static TParticipantKindProvider instance = new TParticipantKindProvider();

	/** pobranie instancji provider'a */
	public static TParticipantKindProvider getInstance() {
		return instance;
	}

	public static enum TParticipantKind {
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
	protected ParticipantKind rowMapper(String[] row) {
		ParticipantKind val = new ParticipantKind();
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
	protected ParticipantKind rowMapper(String[] row, String[] header) {
		ParticipantKind kind = rowMapper(row);
		kindMap.put(kind.getId(), kind);
		return kind;
	}

}
