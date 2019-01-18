/**
 * 
 */
package io.hgdb.mercury.client.mock.arch;

import java.util.Arrays;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.data.TSystemUserProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.arch.ArchCase;
import pro.ibpm.mercury.entities.arch.ArchCaseHistoryStream;
import pro.ibpm.mercury.entities.attr.ParamDefinition;
import pro.ibpm.mercury.entities.attr.ParamDefinitionPK;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TArchCaseHistoryStreamProvider extends
		TAbstractProvider<ArchCaseHistoryStream> {

	public TArchCaseHistoryStreamProvider() {
		super("/pro/ibpm/mercury/mock/arch/tArchCaseHistoryStream.csv", true);
	}

	private static TArchCaseHistoryStreamProvider instance = new TArchCaseHistoryStreamProvider();

	/** pobranie instancji provider'a */
	public static TArchCaseHistoryStreamProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected ArchCaseHistoryStream rowMapper(String[] row) {
		String[] header = new String[] { "id", "caseId", "modifyComment",
				"modifyDate", "systemUserId", "newValue", "oldValue",
				"definitionName", "version" };
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
	protected ArchCaseHistoryStream rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length="
				+ header.length);

		ArchCaseHistoryStream result = new ArchCaseHistoryStream();
		result.setMatter(new ArchCase());
		result.setParamDefinition(new ParamDefinition(new ParamDefinitionPK()));

		if (row != null && header != null && row.length == header.length) {
			for (int index = 0; index < header.length; index++) {
				setPropertyValue(header[index], row[index], result);
			}
		} else {
			String msg = "Nie wczytano danych dla wiersza row="
					+ Arrays.asList(row) + " oraz naglowka header="
					+ Arrays.asList(header);
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

		return result;
	}

	private void setPropertyValue(String property, String newValue,
			ArchCaseHistoryStream entity) {
		// id;caseId;modifyComment;modifyDate;systemUserId;newValue;oldValue;definitionName;version

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			entity.setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("caseId")) {
			entity.getMatter().setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("modifyComment")) {
			entity.setModifyComment(newValue);

		} else if (property.equalsIgnoreCase("modifyDate")) {
			entity.setModifyDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("systemUserId")) {
			entity.setModifyUser(TSystemUserProvider.usersMap.get(MockDataUtils
					.convertToLong(newValue)));

		} else if (property.equalsIgnoreCase("newValue")) {
			entity.setNewValue(newValue);

		} else if (property.equalsIgnoreCase("oldValue")) {
			entity.setOldValue(newValue);

		} else if (property.equalsIgnoreCase("definitionName")) {
			entity.getParamDefinition().getId().setDefinitionName(newValue);

		} else if (property.equalsIgnoreCase("version")) {
			entity.getParamDefinition().getId()
					.setVersion(MockDataUtils.convertToLong(newValue));

		} else {
			String msg = "Obiekt " + entity.getClass().getSimpleName()
					+ " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}
	
}
