/**
 * 
 */
package io.hgdb.mercury.client.mock.attr;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import pro.ibpm.mercury.entities.attr.TypeCase;
import pro.ibpm.mercury.entities.attr.TypeParam;
import pro.ibpm.mercury.entities.attr.TypeParamAction;
import pro.ibpm.mercury.entities.attr.TypeParamPK;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TTypeParamActionProvider extends TAbstractProvider<TypeParamAction> {

	public TTypeParamActionProvider() {
		super("/pro/ibpm/mercury/mock/attr/tTypeParamAction.csv", true);
	}

	private static TTypeParamActionProvider instance = new TTypeParamActionProvider();

	/** pobranie instancji provider'a */
	public static TTypeParamActionProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected TypeParamAction rowMapper(String[] row) {
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
	protected TypeParamAction rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length=" + header.length);

		TypeParamAction result = new TypeParamAction();
		result.setTypeParam(new TypeParam());
		result.getTypeParam().setId(new TypeParamPK());
		result.getTypeParam().getId().setType(new TypeCase());

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

	private void setPropertyValue(String property, String newValue, TypeParamAction typeParamAction) {
		// caseTypeId;position;jsEventName;jsFunctionBody
		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			typeParamAction.setId(Long.parseLong(newValue));

		} else if (property.equalsIgnoreCase("caseTypeId")) {
			typeParamAction.getTypeParam().getId().getType().setId(Long.parseLong(newValue));
			typeParamAction.getTypeParam().getId().getType().setVersionMinor(0.0);
		} else if (property.equalsIgnoreCase("position")) {
			typeParamAction.getTypeParam().getId().setPosition(Integer.parseInt(newValue));

		} else if (property.equalsIgnoreCase("jsEventName")) {
			typeParamAction.setJsEventName(newValue);

		} else if (property.equalsIgnoreCase("jsFunctionBody")) {
			typeParamAction.setJsFunctionBody(newValue);

		} else {
			String msg = "Obiekt typeParamAction nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}