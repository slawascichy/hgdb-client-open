/**
 * 
 */
package io.hgdb.mercury.client.mock.data;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.data.LoggerEvent;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TLoggerEventProvider extends TAbstractProvider<LoggerEvent> {

	public TLoggerEventProvider() {
		super("/pro/ibpm/mercury/mock/data/tLoggerEvent.csv", true);
	}

	private static TLoggerEventProvider instance = new TLoggerEventProvider();

	/** pobranie instancji provider'a */
	public static TLoggerEventProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected LoggerEvent rowMapper(String[] row) {
		String[] header = new String[] { "participant_id", "type_id" };
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
	protected LoggerEvent rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length="
				+ header.length);

		LoggerEvent result = new LoggerEvent();

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
			LoggerEvent e) {

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			e.setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("document_id")) {
			e.setVersionSeriesId(newValue);

		} else if (property.equalsIgnoreCase("document_version_id")) {
			e.setObjectId(newValue);

		} else if (property.equalsIgnoreCase("bpm_instance_id")) {
			e.setBpmInstanceId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("policy_nr")) {
			e.setLogMessage(newValue);

		} else if (property.equalsIgnoreCase("status_policy_bo")) {
			e.setLogMessageExt(newValue);

		} else if (property.equalsIgnoreCase("document_version_type_id")) {
			e.setObjectId(newValue);

		} else if (property.equalsIgnoreCase("end_status")) {
			e.setEndStatus(newValue);

		} else if (property.equalsIgnoreCase("group_case_id")) {
			e.setGroupCaseId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("create_case_id")) {
			e.setCaseId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("ae_document_code")) {
			e.setAeDocumentCode(newValue);

		} else if (property.equalsIgnoreCase("source")) {
			e.setSource(newValue);

		} else if (property.equalsIgnoreCase("document_role")) {
			e.setDocumentRole(newValue);

		} else {
			String msg = "Obiekt " + LoggerEvent.class.getSimpleName()
					+ " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}
