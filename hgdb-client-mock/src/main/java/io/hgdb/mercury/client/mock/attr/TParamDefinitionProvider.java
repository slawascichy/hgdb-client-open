/**
 * 
 */
package io.hgdb.mercury.client.mock.attr;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.attr.ParamDefinition;
import pro.ibpm.mercury.entities.attr.ParamDefinitionPK;

/**
 * 
 * TParamDefinitionProvider
 *
 * @author Mariusz Barwikowski
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $ 
 *
 */
public class TParamDefinitionProvider extends TAbstractProvider<ParamDefinition> {

	public TParamDefinitionProvider() {
		super("/pro/ibpm/mercury/mock/attr/tParamDefinition.csv", true);
	}

	private static TParamDefinitionProvider instance = new TParamDefinitionProvider();

	/** pobranie instancji provider'a */
	public static TParamDefinitionProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected ParamDefinition rowMapper(String[] row) {
		String[] header = new String[] { "definitionName", "version", "description", "isLatestVersion",
				"htmlControlName", "paramType", "timeToLiveSeconds", "valueDefinition", "sourceType", "source",
				"sourceJndiName", "sourceJ2CName", "presentationUrlPrefix", "defaultValue", "fromDate", "toDate",
				"complexClass", "isIndexable", "alternateName", "recomendedLabel", "subType" };
		return rowMapper(row, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[], java.lang.String[])
	 */
	@Override
	protected ParamDefinition rowMapper(String[] row, String[] header) {
		ParamDefinition result = new ParamDefinition();
		result.setId(new ParamDefinitionPK());

		this.logger.trace("row.length=" + row.length + ", header.length=" + header.length);

		if (row != null && header != null && row.length == header.length) {
			for (int index = 0; index < header.length; index++) {
				setPropertyValue(header[index], row[index], result);
			}
		} else {
			String msg = "Nie wczytano danych dla wiersza row={} oraz naglowka header= {}";
			this.logger.error(msg, new Object[] { ArrayUtils.toString(row), ArrayUtils.toString(header) });
			throw new NotImplementedException("Nie wczytano danych dla wiersza row=" + ArrayUtils.toString(row)
					+ " oraz naglowka header=" + ArrayUtils.toString(header));
		}

		return result;
	}

	private void setPropertyValue(String property, String newValue, ParamDefinition paramDefinition) {
		// definitionName;version;description;isLatestVersion;htmlControlName;paramType;timeToLiveSeconds;valueDefinition;sourceType;source;sourceJndiName;sourceJ2CName;presentationUrlPrefix;defaultValue
		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("definitionName")) {
			paramDefinition.getId().setDefinitionName(newValue);
		} else if (property.equalsIgnoreCase("version")) {
			paramDefinition.getId().setVersion(MockDataUtils.convertToLong(newValue));
		} else if (property.equalsIgnoreCase("description")) {
			paramDefinition.setDescription(newValue);
		} else if (property.equalsIgnoreCase("isLatestVersion")) {
			paramDefinition.setIsLatestVersion(MockDataUtils.convertToBoolean(newValue));
		} else if (property.equalsIgnoreCase("htmlControlName")) {
			paramDefinition.setHtmlControlName(newValue);
		} else if (property.equalsIgnoreCase("paramType")) {
			paramDefinition.setParamType(newValue);
		} else if (property.equalsIgnoreCase("timeToLiveSeconds")) {
			paramDefinition.setTimeToLiveSeconds(MockDataUtils.convertToLong(newValue));
		} else if (property.equalsIgnoreCase("valueDefinition")) {
			paramDefinition.setValueDefinition(newValue);
		} else if (property.equalsIgnoreCase("sourceType")) {
			paramDefinition.setSourceType(newValue);
		} else if (property.equalsIgnoreCase("source")) {
			paramDefinition.setSource(newValue);
		} else if (property.equalsIgnoreCase("sourceJndiName")) {
			paramDefinition.setSourceJndiName(newValue);
		} else if (property.equalsIgnoreCase("sourceJ2CName")) {
			paramDefinition.setSourceJ2CName(newValue);
		} else if (property.equalsIgnoreCase("presentationUrlPrefix")) {
			paramDefinition.setPresentationUrlPrefix(newValue);
		} else if (property.equalsIgnoreCase("defaultValue")) {
			paramDefinition.setDefaultValue(newValue);
		} else if (property.equalsIgnoreCase("fromDate")) {
			paramDefinition.setFromDate(MockDataUtils.convertToCalendar(newValue, MockDataUtils.EXCEL_DATE_FORMAT));
		} else if (property.equalsIgnoreCase("toDate")) {
			if (StringUtils.isNotBlank(newValue)) {
				paramDefinition.setToDate(MockDataUtils.convertToCalendar(newValue, MockDataUtils.EXCEL_DATE_FORMAT));
			}
		} else if (property.equalsIgnoreCase("complexClass")) {
			paramDefinition.setComplexClass(newValue);
		} else if (property.equalsIgnoreCase("isIndexable")) {
			paramDefinition.setIsIndexable(MockDataUtils.convertToBoolean(newValue));
		} else if (property.equalsIgnoreCase("alternateName")) {
			paramDefinition.setAlternateName(newValue);
		} else if (property.equalsIgnoreCase("recomendedLabel")) {
			paramDefinition.setRecomendedLabel(newValue);
		} else if (property.equalsIgnoreCase("subType")) {
			paramDefinition.setSubType(newValue);
		} else {
			String msg = "Obiekt ParamDefinition nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}
