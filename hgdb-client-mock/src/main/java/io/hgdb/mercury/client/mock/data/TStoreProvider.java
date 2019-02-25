/**
 * 
 */
package io.hgdb.mercury.client.mock.data;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.config.MercuryConfig;
import pro.ibpm.mercury.entities.data.Comment;
import pro.ibpm.mercury.entities.data.Store;
import pro.ibpm.mercury.entities.data.SystemUser;
import pro.ibpm.mercury.entities.dict.Role;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TStoreProvider extends TAbstractProvider<Store> {

	public TStoreProvider() {
		super("/pro/ibpm/mercury/mock/data/tStore.csv", true);
	}

	private static TStoreProvider instance = new TStoreProvider();

	/** pobranie instancji provider'a */
	public static TStoreProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected Store rowMapper(String[] row) {
		String[] header = new String[] { "id", "caseId", "createDate", "username", "content" };
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
	protected Store rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length=" + header.length);

		Store result = new Store();

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

	private void setPropertyValue(String property, String newValue, Store store) {
		// id;name;manager;role;isActive;arg1;arg2;arg3;arg4;arg5

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			store.setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("name")) {
			store.setName(newValue);

		} else if (property.equalsIgnoreCase("manager")) {
			store.setManager(new SystemUser(MockDataUtils.convertToLong(newValue)));

		} else if (property.equalsIgnoreCase("role")) {
			store.setRole(new Role(newValue, ""));

		} else if (property.equalsIgnoreCase("isActive")) {
			store.setIsActive(MockDataUtils.convertToBoolean(newValue));

		} else if (property.equalsIgnoreCase("arg1")) {
			store.setArg1(newValue);

		} else if (property.equalsIgnoreCase("arg2")) {
			store.setArg2(newValue);

		} else if (property.equalsIgnoreCase("arg3")) {
			store.setArg3(newValue);

		} else if (property.equalsIgnoreCase("arg4")) {
			store.setArg4(newValue);

		} else if (property.equalsIgnoreCase("arg5")) {
			store.setArg5(newValue);

		} else {
			String msg = "Obiekt " + Comment.class.getSimpleName() + " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

		store.setCreatedBy(TSystemUserProvider.usersMap.get(1L));
		store.setLastModifiedBy(TSystemUserProvider.usersMap.get(4L));
		store.setManager(TSystemUserProvider.usersMap.get(5L));
		store.setSourceOfObject(MercuryConfig.getDefaultSourceOfObject());

	}

}