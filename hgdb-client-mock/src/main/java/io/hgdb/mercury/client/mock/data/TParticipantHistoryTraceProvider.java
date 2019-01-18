/**
 * 
 */
package io.hgdb.mercury.client.mock.data;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.data.Participant;
import pro.ibpm.mercury.entities.data.ParticipantHistoryTrace;
import pro.ibpm.mercury.entities.dict.ParticipantKind;
import pro.ibpm.mercury.entities.dict.Source;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TParticipantHistoryTraceProvider extends TAbstractProvider<ParticipantHistoryTrace> {

	public TParticipantHistoryTraceProvider() {
		super("/pro/ibpm/mercury/mock/data/tParticipantHistoryTrace.csv", true);
	}

	private static TParticipantHistoryTraceProvider instance = new TParticipantHistoryTraceProvider();

	/** pobranie instancji provider'a */
	public static TParticipantHistoryTraceProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected ParticipantHistoryTrace rowMapper(String[] row) {
		String[] header = new String[] { "id", "old_vlue", "new_vlue", "store_id", "parameter_def_name",
				"parameter_def_version" };
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
	protected ParticipantHistoryTrace rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length=" + header.length);

		ParticipantHistoryTrace result = new ParticipantHistoryTrace();
		result.setMatter(new Participant());
		result.setKind(new ParticipantKind());
		result.setSource(new Source());

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

	private void setPropertyValue(String property, String newValue, ParticipantHistoryTrace e) {

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			e.setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("identity")) {
			e.setIdentity(newValue);

		} else if (property.equalsIgnoreCase("fullname")) {
			e.setFullname(newValue);

		} else if (property.equalsIgnoreCase("name")) {
			e.setParticipantName(newValue);

		} else if (property.equalsIgnoreCase("contact_person")) {
			e.setContactPerson(newValue);

		} else if (property.equalsIgnoreCase("phone_1")) {
			e.setPhone1(newValue);

		} else if (property.equalsIgnoreCase("phone_2")) {
			e.setPhone2(newValue);

		} else if (property.equalsIgnoreCase("email")) {
			e.setEmail(newValue);

		} else if (property.equalsIgnoreCase("is_active")) {
			e.setIsActive(MockDataUtils.convertToBoolean(newValue));

		} else if (property.equalsIgnoreCase("pagecode")) {
			e.setPageCode(newValue);

		} else if (property.equalsIgnoreCase("task_subject")) {
			e.setChangeSubject(newValue);

		} else if (property.equalsIgnoreCase("participant_id")) {
			e.getMatter().setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("type")) {
			e.getKind().setName(newValue);

		} else if (property.equalsIgnoreCase("source_value")) {
			e.getSource().setValue(newValue);

		} else {
			String msg = "Obiekt " + ParticipantHistoryTrace.class.getSimpleName()
					+ " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}
