/**
 * 
 */
package io.hgdb.client.mock.arch;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.client.mock.TAbstractProvider;
import io.hgdb.client.mock.data.TSystemUserProvider;
import io.hgdb.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.arch.ArchGroupCase;
import pro.ibpm.mercury.entities.arch.ArchGroupCaseHistoryStream;
import pro.ibpm.mercury.entities.attr.ParamDefinition;
import pro.ibpm.mercury.entities.attr.ParamDefinitionPK;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TArchGroupCaseHistoryStreamProvider extends TAbstractProvider<ArchGroupCaseHistoryStream> {

	public TArchGroupCaseHistoryStreamProvider() {
		super("/pro/ibpm/mercury/mock/arch/tArchGroupCaseHistoryStream.csv", true);
	}

	private static TArchGroupCaseHistoryStreamProvider instance = new TArchGroupCaseHistoryStreamProvider();

	/** pobranie instancji provider'a */
	public static TArchGroupCaseHistoryStreamProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected ArchGroupCaseHistoryStream rowMapper(String[] row) {
		String[] header = new String[] { "id", "groupCaseId", "modifyComment", "modifyDate", "modifyUserId", "newValue",
				"oldValue", "paramDefinitionName", "paramDefinitionVersion" };
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
	protected ArchGroupCaseHistoryStream rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length=" + header.length);

		ArchGroupCaseHistoryStream result = new ArchGroupCaseHistoryStream();
		result.setParamDefinition(new ParamDefinition(new ParamDefinitionPK()));

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

	private void setPropertyValue(String property, String newValue, ArchGroupCaseHistoryStream entity) {

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			entity.setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("groupCaseId")) {
			entity.setMatter(new ArchGroupCase(MockDataUtils.convertToLong(newValue)));

		} else if (property.equalsIgnoreCase("modifyComment")) {
			entity.setModifyComment(newValue);

		} else if (property.equalsIgnoreCase("modifyDate")) {
			entity.setModifyDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("modifyUserId")) {
			entity.setModifyUser(TSystemUserProvider.usersMap.get(MockDataUtils.convertToLong(newValue)));

		} else if (property.equalsIgnoreCase("newValue")) {
			entity.setNewValue(newValue);

		} else if (property.equalsIgnoreCase("oldValue")) {
			entity.setOldValue(newValue);

		} else if (property.equalsIgnoreCase("paramDefinitionName")) {

			entity.getParamDefinition().getId().setDefinitionName(newValue);

		} else if (property.equalsIgnoreCase("paramDefinitionVersion")) {

			entity.getParamDefinition().getId().setVersion(MockDataUtils.convertToLong(newValue));

		} else {
			String msg = "Obiekt " + entity.getClass().getSimpleName() + " nie posiada właściwości property="
					+ property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}
