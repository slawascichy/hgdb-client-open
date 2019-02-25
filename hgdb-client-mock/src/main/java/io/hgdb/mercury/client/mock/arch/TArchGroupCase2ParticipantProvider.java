/**
 * 
 */
package io.hgdb.mercury.client.mock.arch;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.data.TSystemUserProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.arch.ArchGroupCase;
import pro.ibpm.mercury.entities.arch.ArchGroupCase2Participant;
import pro.ibpm.mercury.entities.arch.ArchGroupCase2ParticipantPK;
import pro.ibpm.mercury.entities.data.Participant;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TArchGroupCase2ParticipantProvider extends TAbstractProvider<ArchGroupCase2Participant> {

	public TArchGroupCase2ParticipantProvider() {
		super("/pro/ibpm/mercury/mock/arch/tArchGroupCase2Participant.csv", true);
	}

	private static TArchGroupCase2ParticipantProvider instance = new TArchGroupCase2ParticipantProvider();

	/** pobranie instancji provider'a */
	public static TArchGroupCase2ParticipantProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected ArchGroupCase2Participant rowMapper(String[] row) {
		String[] header = new String[] { "groupCaseId", "participantId", "role" };
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
	protected ArchGroupCase2Participant rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length=" + header.length);

		ArchGroupCase2Participant result = new ArchGroupCase2Participant();
		result.setId(new ArchGroupCase2ParticipantPK());

		if (row != null && header != null && row.length == header.length) {
			for (int index = 0; index < header.length; index++) {
				setPropertyValue(header[index], row[index], result);
			}
		} else {
			String msg = "Nie wczytano danych dla wiersza row=" + row + " oraz naglowka header=" + header;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

		return result;
	}

	private void setPropertyValue(String property, String newValue, ArchGroupCase2Participant entity) {

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("groupCaseId")) {
			entity.getId().setGroupCase(new ArchGroupCase(Long.valueOf(MockDataUtils.convertToLong(newValue))));

		} else if (property.equalsIgnoreCase("participantId")) {
			entity.getId().setParticipant(new Participant(Long.valueOf(MockDataUtils.convertToLong(newValue))));

		} else if (property.equalsIgnoreCase("role")) {
			entity.getId().setRole(newValue);

		} else {
			String msg = "Obiekt " + entity.getClass().getSimpleName() + " nie posiada właściwości property="
					+ property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

		entity.setCreatedBy(TSystemUserProvider.usersMap.get(1L));
		entity.setLastModifiedBy(TSystemUserProvider.usersMap.get(1L));
	}

}
