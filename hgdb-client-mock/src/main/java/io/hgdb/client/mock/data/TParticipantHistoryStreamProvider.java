/**
 * 
 */
package io.hgdb.client.mock.data;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.client.mock.TAbstractProvider;
import io.hgdb.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.attr.ParamDefinition;
import pro.ibpm.mercury.entities.attr.ParamDefinitionPK;
import pro.ibpm.mercury.entities.data.Participant;
import pro.ibpm.mercury.entities.data.ParticipantHistoryStream;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TParticipantHistoryStreamProvider extends
		TAbstractProvider<ParticipantHistoryStream> {

	public TParticipantHistoryStreamProvider() {
		super("/pro/ibpm/mercury/mock/data/tParticipantHistoryStream.csv", true);
	}

	private static TParticipantHistoryStreamProvider instance = new TParticipantHistoryStreamProvider();

	/** pobranie instancji provider'a */
	public static TParticipantHistoryStreamProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected ParticipantHistoryStream rowMapper(String[] row) {
		String[] header = new String[] { "id", "old_vlue", "new_vlue",
				"store_id", "parameter_def_name", "parameter_def_version" };
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
	protected ParticipantHistoryStream rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length="
				+ header.length);

		ParticipantHistoryStream result = new ParticipantHistoryStream();
		result.setMatter(new Participant());
		result.setParamDefinition(new ParamDefinition(new ParamDefinitionPK()));

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
			ParticipantHistoryStream e) {

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			e.setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("old_vlue")) {
			e.setOldValue(newValue);

		} else if (property.equalsIgnoreCase("new_vlue")) {
			e.setNewValue(newValue);

		} else if (property.equalsIgnoreCase("participant_id")) {
			e.getMatter().setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("parameter_def_name")) {
			e.getParamDefinition().getId().setDefinitionName(newValue);

		} else if (property.equalsIgnoreCase("parameter_def_version")) {
			e.getParamDefinition().getId()
					.setVersion(MockDataUtils.convertToLong(newValue));

		} else {
			String msg = "Obiekt " + ParticipantHistoryStream.class.getSimpleName()
					+ " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}



	}
	
}
