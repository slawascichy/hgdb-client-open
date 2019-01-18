/**
 * 
 */
package io.hgdb.client.mock.arch;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.client.mock.TAbstractProvider;
import io.hgdb.client.mock.data.TSystemUserProvider;
import io.hgdb.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.arch.ArchCase;
import pro.ibpm.mercury.entities.arch.ArchCase2Case;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TArchCase2CaseProvider extends TAbstractProvider<ArchCase2Case> {

	public TArchCase2CaseProvider() {
		super("/pro/ibpm/mercury/mock/arch/tArchCase2Case.csv", true);
	}

	private static TArchCase2CaseProvider instance = new TArchCase2CaseProvider();

	/** pobranie instancji provider'a */
	public static TArchCase2CaseProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected ArchCase2Case rowMapper(String[] row) {
		String[] header = new String[] { "parentId", "childId", "depth", "path" };
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
	protected ArchCase2Case rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length="
				+ header.length);

		ArchCase2Case result = new ArchCase2Case();
		result.setParent(new ArchCase());
		result.setChild(new ArchCase());

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

		if (this.isLogicTest() && result.getDepth() != 2) {
			return null;
		} else {
			return result;
		}
	}

	private void setPropertyValue(String property, String newValue,
			ArchCase2Case entity) {

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("parentId")) {
			entity.getParent().setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("childId")) {
			entity.getChild().setId(MockDataUtils.convertToLong(newValue));
			;

		} else if (property.equalsIgnoreCase("depth")) {
			entity.setDepth(MockDataUtils.convertToInteger(newValue));

		} else if (property.equalsIgnoreCase("path")) {
			entity.setPath(newValue);

		} else {
			String msg = "Obiekt " + entity.getClass().getSimpleName()
					+ " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

		entity.setCreatedBy(TSystemUserProvider.usersMap.get(1L));
		entity.setLastModifedBy(TSystemUserProvider.usersMap.get(1L));

	}

}
