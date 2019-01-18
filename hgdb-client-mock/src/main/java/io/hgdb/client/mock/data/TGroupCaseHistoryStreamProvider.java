/**
 * 
 */
package io.hgdb.client.mock.data;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.client.mock.TAbstractProvider;
import io.hgdb.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.attr.ParamDefinition;
import pro.ibpm.mercury.entities.attr.ParamDefinitionPK;
import pro.ibpm.mercury.entities.data.GroupCase;
import pro.ibpm.mercury.entities.data.GroupCaseHistoryStream;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TGroupCaseHistoryStreamProvider extends
		TAbstractProvider<GroupCaseHistoryStream> {

	public TGroupCaseHistoryStreamProvider() {
		super("/pro/ibpm/mercury/mock/data/tGroupCaseHistoryStream.csv", true);
	}

	private static TGroupCaseHistoryStreamProvider instance = new TGroupCaseHistoryStreamProvider();

	/** pobranie instancji provider'a */
	public static TGroupCaseHistoryStreamProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected GroupCaseHistoryStream rowMapper(String[] row) {
		String[] header = new String[] { "id", "groupCaseId", "modifyComment",
				"modifyDate", "modifyUserId", "newValue", "oldValue",
				"paramDefinitionName", "paramDefinitionVersion" };
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
	protected GroupCaseHistoryStream rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length="
				+ header.length);

		GroupCaseHistoryStream result = new GroupCaseHistoryStream();
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
			GroupCaseHistoryStream groupCaseHistoryStream) {

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			groupCaseHistoryStream.setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("groupCaseId")) {
			groupCaseHistoryStream.setMatter(new GroupCase(MockDataUtils
					.convertToLong(newValue)));

		} else if (property.equalsIgnoreCase("modifyComment")) {
			groupCaseHistoryStream.setModifyComment(newValue);

		} else if (property.equalsIgnoreCase("modifyDate")) {
			groupCaseHistoryStream.setModifyDate(MockDataUtils
					.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("modifyUserId")) {
			groupCaseHistoryStream.setModifyUser(TSystemUserProvider.usersMap
					.get(MockDataUtils.convertToLong(newValue)));

		} else if (property.equalsIgnoreCase("newValue")) {
			groupCaseHistoryStream.setNewValue(newValue);

		} else if (property.equalsIgnoreCase("oldValue")) {
			groupCaseHistoryStream.setOldValue(newValue);

		} else if (property.equalsIgnoreCase("paramDefinitionName")) {

			groupCaseHistoryStream.getParamDefinition().getId()
					.setDefinitionName(newValue);

		} else if (property.equalsIgnoreCase("paramDefinitionVersion")) {

			groupCaseHistoryStream.getParamDefinition().getId()
					.setVersion(MockDataUtils.convertToLong(newValue));

		} else {
			String msg = "Obiekt "
					+ GroupCaseHistoryStream.class.getSimpleName()
					+ " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}
