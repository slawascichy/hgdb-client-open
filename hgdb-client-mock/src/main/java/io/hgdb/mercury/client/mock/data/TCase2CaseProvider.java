/**
 * 
 */
package io.hgdb.mercury.client.mock.data;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.data.Case;
import pro.ibpm.mercury.entities.data.Case2Case;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TCase2CaseProvider extends TAbstractProvider<Case2Case> {

	public TCase2CaseProvider() {
		super("/pro/ibpm/mercury/mock/data/tCase2Case.csv", true);
	}

	private static TCase2CaseProvider instance = new TCase2CaseProvider();

	/** pobranie instancji provider'a */
	public static TCase2CaseProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected Case2Case rowMapper(String[] row) {
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
	protected Case2Case rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length="
				+ header.length);

		Case2Case result = new Case2Case();
		result.setParent(new Case());
		result.setChild(new Case());

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
			Case2Case case2Case) {

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("parentId")) {
			case2Case.getParent().setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("childId")) {
			case2Case.getChild().setId(MockDataUtils.convertToLong(newValue));
			;

		} else if (property.equalsIgnoreCase("depth")) {
			case2Case.setDepth(MockDataUtils.convertToInteger(newValue));

		} else if (property.equalsIgnoreCase("path")) {
			case2Case.setPath(newValue);

		} else {
			String msg = "Obiekt " + Case2Case.class.getSimpleName()
					+ " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

		case2Case.setCreatedBy(TSystemUserProvider.usersMap.get(1L));
		case2Case.setLastModifiedBy(TSystemUserProvider.usersMap.get(1L));

	}

}
