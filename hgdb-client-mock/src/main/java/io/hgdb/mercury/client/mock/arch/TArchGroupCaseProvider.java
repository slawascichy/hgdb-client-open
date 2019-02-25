/**
 * 
 */
package io.hgdb.mercury.client.mock.arch;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.data.TSystemUserProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.arch.ArchGroupCase;
import pro.ibpm.mercury.entities.arch.ArchKtmNumber;
import pro.ibpm.mercury.entities.dict.Source;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TArchGroupCaseProvider extends TAbstractProvider<ArchGroupCase> {

	public TArchGroupCaseProvider() {
		super("/pro/ibpm/mercury/mock/arch/tArchGroupCase.csv", true);
	}

	private static TArchGroupCaseProvider instance = new TArchGroupCaseProvider();

	/** pobranie instancji provider'a */
	public static TArchGroupCaseProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected ArchGroupCase rowMapper(String[] row) {
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
	protected ArchGroupCase rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length="
				+ header.length);

		ArchGroupCase result = new ArchGroupCase();
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
			ArchGroupCase entity) {

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			entity.setId(Long.parseLong(newValue));

		} else if (property.equalsIgnoreCase("createDate")) {
			entity.setCreateDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("createdBy")) {
			Long id = Long.parseLong(newValue);
			entity.setCreatedBy(TSystemUserProvider.usersMap.get(id));

		} else if (property.equalsIgnoreCase("modifyDate")) {
			entity.setLastModifyDate(MockDataUtils
					.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("modifiedBy")) {
			Long id = Long.parseLong(newValue);
			entity.setLastModifiedBy(TSystemUserProvider.usersMap.get(id));

		} else if (property.equalsIgnoreCase("sourceValue")) {
			entity.getSource().setValue(newValue);

		} else if (property.equalsIgnoreCase("ktmId")) {
			entity.setKtmNumber(new ArchKtmNumber(MockDataUtils
					.convertToLong(newValue)));

		} else {
			String msg = "Obiekt " + entity.getClass().getSimpleName()
					+ " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}
