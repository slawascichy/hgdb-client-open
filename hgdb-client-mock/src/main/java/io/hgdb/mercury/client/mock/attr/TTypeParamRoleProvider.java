/**
 * 
 */
package io.hgdb.mercury.client.mock.attr;

import java.util.Arrays;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.data.TSystemUserProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.attr.TypeParamRole;
import pro.ibpm.mercury.entities.beans.ParamPrivilege;
import pro.ibpm.mercury.entities.dict.Role;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TTypeParamRoleProvider extends TAbstractProvider<TypeParamRole> {

	/**
	 * @param sourceFile
	 */
	public TTypeParamRoleProvider() {
		super("/pro/ibpm/mercury/mock/attr/tTypeParamRole.csv", true);
	}

	private static TTypeParamRoleProvider instance = new TTypeParamRoleProvider();

	/** pobranie instancji provider'a */
	public static TTypeParamRoleProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected TypeParamRole rowMapper(String[] row) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[],
	 * java.lang.String[])
	 */
	@Override
	protected TypeParamRole rowMapper(String[] row, String[] header) {
		TypeParamRole result = new TypeParamRole();

		result.setRole(new Role());

		this.logger.trace("header=" + Arrays.asList(header) + "; row=" + Arrays.asList(row));
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

	private void setPropertyValue(String property, String newValue, TypeParamRole typeParamRole) {
		// caseTypeId;position;roleName;privilegeName
		this.logger.trace("" + typeParamRole.getClass().getName() + " : " + property + " : " + newValue);

		if (property.equalsIgnoreCase("id")) {
			typeParamRole.setId(Long.parseLong(newValue));

		} else if (property.equalsIgnoreCase("typeCode")) {
			typeParamRole.setTypeCode(newValue);

		} else if (property.equalsIgnoreCase("paramName")) {
			typeParamRole.setParamName(newValue);

		} else if (property.equalsIgnoreCase("roleName")) {
			typeParamRole.getRole().setName(newValue);

		} else if (property.equalsIgnoreCase("privilegeName")) {

			ParamPrivilege pp = ParamPrivilege.valueOf(newValue.toUpperCase());
			this.logger.trace("pp.name()=" + pp.name());

			typeParamRole.setPrivilegeName(newValue);
		} else if (property.equalsIgnoreCase("createDate")) {
			typeParamRole.setCreateDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("createdBy")) {
			Long id = MockDataUtils.convertToLong(newValue);
			typeParamRole.setCreatedBy(TSystemUserProvider.usersMap.get(id));

		} else if (property.equalsIgnoreCase("modifyDate")) {
			typeParamRole.setLastModifyDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("modifiedBy")) {
			Long id = Long.parseLong(newValue);
			typeParamRole.setLastModifiedBy(TSystemUserProvider.usersMap.get(id));
		} else {
			String msg = "Obiekt typeParamRole nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

		this.logger.trace("Row mapper end");

	}

}
