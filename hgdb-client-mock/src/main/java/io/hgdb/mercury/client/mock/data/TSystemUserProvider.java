/**
 * 
 */
package io.hgdb.mercury.client.mock.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import pro.ibpm.mercury.entities.data.SystemUser;

/**
 * 
 * TSystemUserProvider
 * 
 * @author Sławomir Cichy &lt;slawas@slawas.pl&gt;
 * @version $Revision: 1.1 $
 * 
 */
public class TSystemUserProvider extends TAbstractProvider<SystemUser> {

	public TSystemUserProvider() {
		super("/pro/ibpm/mercury/mock/data/tSystemUser.csv", true);
	}

	public static Map<Long, SystemUser> usersMap = new HashMap<Long, SystemUser>();

	private static TSystemUserProvider instance = new TSystemUserProvider();

	/** pobranie instancji provider'a */
	public static TSystemUserProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected SystemUser rowMapper(String[] row) {
		return rowMapper(row, new String[] { "id", "username", "fullname", "isActive", "isTechnical" });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[],
	 * java.lang.String[])
	 */
	@Override
	protected SystemUser rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length=" + header.length);

		SystemUser result = new SystemUser();

		if (row != null && header != null && row.length == header.length) {
			for (int index = 0; index < header.length; index++) {
				setPropertyValue(header[index], row[index], result);
			}
			Long key = result.getId();
			usersMap.put(key, result);
		} else {
			String msg = "Nie wczytano danych dla wiersza row=" + row + " oraz naglowka header=" + header;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

		return result;
	}

	private void setPropertyValue(String property, String newValue, SystemUser user) {
		// id;username;fullname;isActive;isTechnical
		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			user.setId(Long.parseLong(newValue));
		} else if (property.equalsIgnoreCase("username")) {
			user.setUserName(newValue);
		} else if (property.equalsIgnoreCase("fullname")) {
			user.setFullname(newValue);
		} else if (property.equalsIgnoreCase("locale")) {
			user.setLocale(newValue);
		} else if (property.equalsIgnoreCase("timeZone")) {
			user.setTimeZone(newValue);
		} else if (property.equalsIgnoreCase("isActive")) {
			user.setIsActive(Boolean.valueOf(newValue));
		} else if (property.equalsIgnoreCase("isTechnical")) {
			user.setIsTechnical(Boolean.valueOf(newValue));
		} else {
			String msg = "Obiekt " + SystemUser.class.getSimpleName() + " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}