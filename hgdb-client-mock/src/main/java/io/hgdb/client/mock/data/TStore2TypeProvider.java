/**
 * 
 */
package io.hgdb.client.mock.data;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.client.mock.TAbstractProvider;
import io.hgdb.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.attr.TypeCode;
import pro.ibpm.mercury.entities.data.Comment;
import pro.ibpm.mercury.entities.data.Store;
import pro.ibpm.mercury.entities.data.Store2Type;
import pro.ibpm.mercury.entities.data.Store2TypePK;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TStore2TypeProvider extends TAbstractProvider<Store2Type> {

	public TStore2TypeProvider() {
		super("/pro/ibpm/mercury/mock/data/tStore2Type.csv", true);
	}

	private static TStore2TypeProvider instance = new TStore2TypeProvider();

	/** pobranie instancji provider'a */
	public static TStore2TypeProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected Store2Type rowMapper(String[] row) {
		String[] header = new String[] { "id", "caseId", "createDate",
				"username", "content" };
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
	protected Store2Type rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length="
				+ header.length);

		Store2Type result = new Store2Type();
		result.setId(new Store2TypePK());

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
			Store2Type store2Type) {
		// storeId;typeCode

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("storeId")) {
			store2Type.getId().setStore(
					new Store(MockDataUtils.convertToLong(newValue)));

		} else if (property.equalsIgnoreCase("typeCode")) {
			store2Type.getId().setTypeCode(new TypeCode(newValue));

		} else {
			String msg = "Obiekt " + Comment.class.getSimpleName()
					+ " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}
