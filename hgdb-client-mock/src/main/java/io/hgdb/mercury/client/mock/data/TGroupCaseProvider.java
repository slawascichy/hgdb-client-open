/**
 * 
 */
package io.hgdb.mercury.client.mock.data;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.data.GroupCase;
import pro.ibpm.mercury.entities.data.KtmNumber;
import pro.ibpm.mercury.entities.dict.Source;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TGroupCaseProvider extends TAbstractProvider<GroupCase> {

	public TGroupCaseProvider() {
		super("/pro/ibpm/mercury/mock/data/tGroupCase.csv", true);
	}

	private static TGroupCaseProvider instance = new TGroupCaseProvider();

	/** pobranie instancji provider'a */
	public static TGroupCaseProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected GroupCase rowMapper(String[] row) {
		String[] header = new String[] { "id", "createDate", "createdBy",
				"modifyDate", "modifiedBy", "sourceValue", "ktmId" };
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
	protected GroupCase rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length="
				+ header.length);

		GroupCase result = new GroupCase();
		result.setSource(new Source());

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
			GroupCase groupCase) {

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			groupCase.setId(Long.parseLong(newValue));

		} else if (property.equalsIgnoreCase("createDate")) {
			groupCase.setCreateDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("createdBy")) {
			Long id = Long.parseLong(newValue);
			groupCase.setCreatedBy(TSystemUserProvider.usersMap.get(id));

		} else if (property.equalsIgnoreCase("modifyDate")) {
			groupCase.setLastModifyDate(MockDataUtils
					.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("modifiedBy")) {
			Long id = Long.parseLong(newValue);
			groupCase.setLastModifiedBy(TSystemUserProvider.usersMap.get(id));

		} else if (property.equalsIgnoreCase("sourceValue")) {
			groupCase.getSource().setValue(newValue);

		} else if (property.equalsIgnoreCase("ktmId")) {
			groupCase.setKtmNumber(new KtmNumber(MockDataUtils
					.convertToLong(newValue)));

		} else {
			String msg = "Obiekt " + GroupCase.class.getSimpleName()
					+ " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}
