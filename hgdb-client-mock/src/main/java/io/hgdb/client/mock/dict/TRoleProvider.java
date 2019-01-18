/**
 * 
 */
package io.hgdb.client.mock.dict;

import java.util.Arrays;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.client.mock.TAbstractProvider;
import pro.ibpm.mercury.entities.dict.Role;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TRoleProvider extends TAbstractProvider<Role> {

	public TRoleProvider() {
		super("/pro/ibpm/mercury/mock/dict/tRole.csv", true);
	}

	private static TRoleProvider instance = new TRoleProvider();

	/** pobranie instancji provider'a */
	public static TRoleProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.dict.TAbstractProvider#rowMapper(java.lang.String
	 * [])
	 */
	@Override
	protected Role rowMapper(String[] row) {
		String[] header = new String[] { "name", "description" };
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
	protected Role rowMapper(String[] row, String[] header) {
		Role result = new Role();

		this.logger.trace("header=" + Arrays.asList(header) + "; row="
				+ Arrays.asList(row));
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

	private void setPropertyValue(String property, String newValue, Role role) {
		// name;description
		this.logger.trace("" + role.getClass().getName() + " : " + property
				+ " : " + newValue);
		if (property.equalsIgnoreCase("name")) {
			role.setName(newValue);

		} else if (property.equalsIgnoreCase("description")) {
			role.setDescription(newValue);

		} else {
			String msg = "Obiekt typeParamRole nie posiada właściwości property="
					+ property;
			this.logger.error(msg);

			throw new NotImplementedException(msg);
		}

		this.logger.trace("Row mapper end");

	}

}
