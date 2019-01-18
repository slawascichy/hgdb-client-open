/**
 * 
 */
package io.hgdb.mercury.client.mock.data;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.attr.TypeCode;
import pro.ibpm.mercury.entities.data.Participant;
import pro.ibpm.mercury.entities.data.Participant2TypeStats;
import pro.ibpm.mercury.entities.data.Participant2TypeStatsPK;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TParticipant2TypeStatsProvider extends
		TAbstractProvider<Participant2TypeStats> {

	public TParticipant2TypeStatsProvider() {
		super("/pro/ibpm/mercury/mock/data/tParticipant2TypeStats.csv", true);
	}

	private static TParticipant2TypeStatsProvider instance = new TParticipant2TypeStatsProvider();

	/** pobranie instancji provider'a */
	public static TParticipant2TypeStatsProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected Participant2TypeStats rowMapper(String[] row) {
		String[] header = new String[] { "participant_id", "type_code", "role" };
		return rowMapper(row, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[],
	 * java.lang.String[])
	 */
	@Override
	protected Participant2TypeStats rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length="
				+ header.length);

		Participant2TypeStats result = new Participant2TypeStats();
		result.setId(new Participant2TypeStatsPK());

		result.getId().setParticipant(new Participant());
		result.getId().setTypeCode(new TypeCode());

		if (row != null && header != null && row.length == header.length) {
			for (int index = 0; index < header.length; index++) {
				setPropertyValue(header[index], row[index], result);
			}
		} else {
			String msg = "Nie wczytano danych dla wiersza row=" + row
					+ " oraz naglowka header=" + header;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

		return result;
	}

	private void setPropertyValue(String property, String newValue,
			Participant2TypeStats e) {

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("participant_id")) {
			e.getId().getParticipant()
					.setId(MockDataUtils.convertToLong(newValue));
		} else if (property.equalsIgnoreCase("type_code")) {
			e.getId().getTypeCode().setId(newValue);
		} else if (property.equalsIgnoreCase("role")) {
			e.getId().setRole(newValue);
		} else {
			String msg = "Obiekt "
					+ Participant2TypeStats.class.getSimpleName()
					+ " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}
	}

}
