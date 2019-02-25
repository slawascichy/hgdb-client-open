/**
 * 
 */
package io.hgdb.mercury.client.mock.data;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.data.GroupCase;
import pro.ibpm.mercury.entities.data.GroupCase2Participant;
import pro.ibpm.mercury.entities.data.GroupCase2ParticipantPK;
import pro.ibpm.mercury.entities.data.Participant;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TGroupCase2ParticipantProvider extends
		TAbstractProvider<GroupCase2Participant> {

	public TGroupCase2ParticipantProvider() {
		super("/pro/ibpm/mercury/mock/data/tGroupCase2Participant.csv", true);
	}

	private static TGroupCase2ParticipantProvider instance = new TGroupCase2ParticipantProvider();

	/** pobranie instancji provider'a */
	public static TGroupCase2ParticipantProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected GroupCase2Participant rowMapper(String[] row) {
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
	protected GroupCase2Participant rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length="
				+ header.length);

		GroupCase2Participant result = new GroupCase2Participant();
		result.setId(new GroupCase2ParticipantPK());

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
			GroupCase2Participant groupCase2Participant) {

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("groupCaseId")) {
			groupCase2Participant.getId().setGroupCase(
					new GroupCase(Long.valueOf(MockDataUtils
							.convertToLong(newValue))));

		} else if (property.equalsIgnoreCase("participantId")) {
			groupCase2Participant.getId().setParticipant(
					new Participant(Long.valueOf(MockDataUtils
							.convertToLong(newValue))));

		} else if (property.equalsIgnoreCase("role")) {
			groupCase2Participant.getId().setRole(newValue);

		} else {
			String msg = "Obiekt " + GroupCase.class.getSimpleName()
					+ " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

		groupCase2Participant
				.setCreatedBy(TSystemUserProvider.usersMap.get(1L));
		groupCase2Participant.setLastModifiedBy(TSystemUserProvider.usersMap
				.get(1L));
	}

}
