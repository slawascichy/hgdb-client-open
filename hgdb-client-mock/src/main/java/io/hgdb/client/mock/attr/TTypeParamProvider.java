/**
 * 
 */
package io.hgdb.client.mock.attr;

import java.util.Arrays;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;

import io.hgdb.client.mock.TAbstractProvider;
import io.hgdb.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.attr.ParamDefinition;
import pro.ibpm.mercury.entities.attr.ParamDefinitionPK;
import pro.ibpm.mercury.entities.attr.TypeCase;
import pro.ibpm.mercury.entities.attr.TypeParam;
import pro.ibpm.mercury.entities.attr.TypeParamPK;
import pro.ibpm.mercury.entities.beans.Updatable;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TTypeParamProvider extends TAbstractProvider<TypeParam> {

	/**
	 * @param sourceFile
	 */
	public TTypeParamProvider() {
		super("/pro/ibpm/mercury/mock/attr/tTypeParam.csv", true);
	}

	private static TTypeParamProvider instance = new TTypeParamProvider();

	/** pobranie instancji provider'a */
	public static TTypeParamProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected TypeParam rowMapper(String[] row) {
		String[] header = new String[] { "caseTypeId", "position", "parameterDefinitionName", "paramDefinitionVersion",
				"label", "tooltip", "isRequired", "isSearchData", "arg1", "arg2", "arg3", "arg4", "arg5" };
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
	protected TypeParam rowMapper(String[] row, String[] header) {
		TypeParam result = new TypeParam();
		result.setId(new TypeParamPK(new TypeCase(), null));
		result.setParamDefinition(new ParamDefinition(new ParamDefinitionPK()));

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

	private void setPropertyValue(String property, String newValue, TypeParam typeParam) {
		// caseTypeId;position;parameterDefinitionName;paramDefinitionVersion;label;tooltip;isRequired;isSearchData;arg1;arg2;arg3;arg4;arg5
		this.logger.trace("" + typeParam.getClass().getName() + " : " + property + " : " + newValue);
		if (property.equalsIgnoreCase("caseTypeId")) {
			typeParam.getId().getType().setId(MockDataUtils.convertToLong(newValue));
			typeParam.getId().getType().setVersionMinor(0.0);
		} else if (property.equalsIgnoreCase("position")) {
			typeParam.getId().setPosition(MockDataUtils.convertToInteger(newValue));
			typeParam.setXmlId("p:" + newValue);

		} else if (property.equalsIgnoreCase("parameterDefinitionName")) {
			typeParam.getParamDefinition().getId().setDefinitionName(newValue);

		} else if (property.equalsIgnoreCase("paramDefinitionVersion")) {
			typeParam.getParamDefinition().getId().setVersion(MockDataUtils.convertToLong(newValue));
		} else if (property.equalsIgnoreCase("label")) {
			typeParam.setLabel(newValue);

		} else if (property.equalsIgnoreCase("tooltip")) {
			typeParam.setTooltip(newValue);

		} else if (property.equalsIgnoreCase("isRequired")) {
			typeParam.setIsRequired(Boolean.parseBoolean(newValue));

		} else if (property.equalsIgnoreCase("isSearchData")) {
			typeParam.setIsSearchData(Boolean.parseBoolean(newValue));

		} else if (property.equalsIgnoreCase("arg1")) {
			typeParam.setArg1(newValue);

		} else if (property.equalsIgnoreCase("arg2")) {
			typeParam.setArg2(newValue);

		} else if (property.equalsIgnoreCase("arg3")) {
			typeParam.setArg3(newValue);

		} else if (property.equalsIgnoreCase("arg4")) {
			typeParam.setArg4(newValue);

		} else if (property.equalsIgnoreCase("arg5")) {
			typeParam.setArg5(newValue);

		} else {
			String msg = "Obiekt typeParam nie posiada właściwości property=" + property;
			this.logger.error(msg);

			throw new NotImplementedException(msg);
		}
		if (typeParam.getWithEmptyOption() == null) {
			typeParam.setWithEmptyOption(Boolean.FALSE);
		}
		if (typeParam.getIsRequired() == null) {
			typeParam.setIsRequired(Boolean.FALSE);
		}
		if (typeParam.getIsSearchData() == null) {
			typeParam.setIsSearchData(Boolean.TRUE);
		}
		if (typeParam.getHasMultiValues() == null) {
			typeParam.setHasMultiValues(Boolean.FALSE);
		}
		if (StringUtils.isBlank(typeParam.getUpdateable())) {
			typeParam.setUpdateable(Updatable.READWRITE.name());
		}
	}

}
