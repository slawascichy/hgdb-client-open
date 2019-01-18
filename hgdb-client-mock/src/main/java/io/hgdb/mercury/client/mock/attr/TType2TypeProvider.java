/**
 * 
 */
package io.hgdb.mercury.client.mock.attr;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import pro.ibpm.mercury.entities.attr.Type2Type;
import pro.ibpm.mercury.entities.attr.TypeCode;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TType2TypeProvider extends TAbstractProvider<Type2Type> {

	public TType2TypeProvider() {
		super("/pro/ibpm/mercury/mock/attr/tType2Type.csv", true);
	}

	private static TType2TypeProvider instance = new TType2TypeProvider();

	/** pobranie instancji provider'a */
	public static TType2TypeProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected Type2Type rowMapper(String[] row) {
		// TODO Auto-generated method stub
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
	protected Type2Type rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length=" + header.length);

		Type2Type result = new Type2Type();
		result.setParent(new TypeCode());
		result.setChild(new TypeCode());

		if (row != null && header != null && row.length == header.length) {
			for (int index = 0; index < header.length; index++) {
				setPropertyValue(header[index], row[index], result);
			}
		} else {
			String msg = "Nie wczytano danych dla wiersza row=" + row + " oraz naglowka header=" + header;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}
		if (this.isLogicTest() && result.getDepth() != 2) {
			return null;
		} else {
			return result;
		}

	}

	private void setPropertyValue(String property, String newValue, Type2Type type2Type) {
		// parentId;childId;depth;path
		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("parentId")) {
			type2Type.getParent().setId(newValue);

		} else if (property.equalsIgnoreCase("childId")) {
			type2Type.getChild().setId(newValue);

		} else if (property.equalsIgnoreCase("depth")) {
			type2Type.setDepth(Integer.parseInt(newValue));

		} else if (property.equalsIgnoreCase("path")) {
			type2Type.setPath(newValue);

		} else {
			String msg = "Obiekt type2Type nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}