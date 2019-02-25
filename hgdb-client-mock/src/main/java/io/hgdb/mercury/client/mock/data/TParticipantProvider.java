package io.hgdb.mercury.client.mock.data;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.dict.TParticipantKindProvider;
import pro.ibpm.mercury.entities.data.Participant;
import pro.ibpm.mercury.entities.dict.ParticipantKind;
import pro.ibpm.mercury.entities.dict.Source;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TParticipantProvider extends TAbstractProvider<Participant> {

	public TParticipantProvider() {
		super("/pro/ibpm/mercury/mock/data/tParticipant.csv", true);
	}

	private static TParticipantProvider instance = new TParticipantProvider();

	/** pobranie instancji provider'a */
	public static TParticipantProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected Participant rowMapper(String[] row) {
		return rowMapper(row, new String[] { "id", "kind", "identity", "fullname", "name", "contactPerson", "phone1",
				"phone2", "email" });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[],
	 * java.lang.String[])
	 */
	@Override
	protected Participant rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length=" + header.length);

		Participant result = new Participant();
		result.setSource(new Source("Axapta", "MS Axapta", "System Księgowy"));

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

	private void setPropertyValue(String property, String newValue, Participant participant) {
		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			participant.setId(Long.parseLong(newValue));
		} else if (property.equalsIgnoreCase("kind")) {
			/* trzeba pobrać rodzaj z bazy danych */
			ParticipantKind k = TParticipantKindProvider.kindMap.get(newValue);
			if (k != null) {
				participant.setKind(k);
			}
		} else if (property.equalsIgnoreCase("identity")) {
			participant.setIdentity(newValue);

		} else if (property.equalsIgnoreCase("fullname")) {
			participant.setFullname(newValue);

		} else if (property.equalsIgnoreCase("name")) {
			participant.setParticipantName(newValue);

		} else if (property.equalsIgnoreCase("contactPerson")) {
			participant.setContactPerson(newValue);

		} else if (property.equalsIgnoreCase("phone1")) {
			participant.setPhone1(newValue);

		} else if (property.equalsIgnoreCase("phone2")) {
			participant.setPhone2(newValue);

		} else if (property.equalsIgnoreCase("email")) {
			participant.setEmail(newValue);

		} else {
			String msg = "Obiekt " + Participant.class.getSimpleName() + " nie posiada właściwości property="
					+ property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

		participant.setCreatedBy(TSystemUserProvider.usersMap.get(1L));
		participant.setLastModifiedBy(TSystemUserProvider.usersMap.get(1L));

	}

}
