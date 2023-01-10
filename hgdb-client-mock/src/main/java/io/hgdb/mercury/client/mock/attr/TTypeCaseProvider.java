/**
 * 
 */
package io.hgdb.mercury.client.mock.attr;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.attr.TypeCase;
import pro.ibpm.mercury.entities.attr.TypeCode;
import pro.ibpm.mercury.entities.dict.TypeKind;

/**
 * 
 * TTypeCaseProvider
 *
 * @author Mariusz Barwikowski
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class TTypeCaseProvider extends TAbstractProvider<TypeCase> {

	/**
	 * Nazwa licznika typów dokumentów załadowanych z plików Mock (tylko ostatnie wersje!)
	 */
	public static String DOCUMENT_TYPES_COUNTER = "documentTypesCounter";
	/**
	 * Nazwa licznika typów spraw załadowanych z plików Mock (tylko ostatnie wersje!)
	 */
	public static String CASE_TYPES_COUNTER = "caseTypesCounter";
	/**
	 * Nazwa licznika typów załadowanych z plików Mock (tylko ostatnie wersje!)
	 */
	public static String ALL_TYPES_COUNTER = "allLastVersionsTypesCounter";

	public TTypeCaseProvider() {
		super("/pro/ibpm/mercury/mock/attr/tTypeCase.csv", true);
	}

	private static TTypeCaseProvider instance = new TTypeCaseProvider();

	/** pobranie instancji provider'a */
	public static TTypeCaseProvider getInstance() {
		return instance;
	}

	/**
	 * @param sourceFile
	 */
	public TTypeCaseProvider(String sourceFile) {
		super(sourceFile, true);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected TypeCase rowMapper(String[] row) {
		String[] header = { "id", "typeName", "version", "description", "isEditable", "code", "isActive", "kind",
				"isLatestVersion", "fromDate", "toDate", "code", "isDocumentType" };

		return rowMapper(row, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[], java.lang.String[])
	 */
	@Override
	protected TypeCase rowMapper(String[] row, String[] header) {
		TypeCase result = new TypeCase();
		result.setKind(new TypeKind());
		result.setVersionMinor(0.0);
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

	private void setPropertyValue(String property, String newValue, TypeCase typeCase) {
		// id;typeName;version;description;isEditable;code;isActive;kind

		if (property.equalsIgnoreCase("id")) {
			typeCase.setId(Long.parseLong(newValue));
		} else if (property.equalsIgnoreCase("name")) {
			typeCase.setTypeName(newValue);
		} else if (property.equalsIgnoreCase("version")) {
			typeCase.setVersion(Long.parseLong(newValue));
		} else if (property.equalsIgnoreCase("description")) {
			typeCase.setDescription(newValue);
		} else if (property.equalsIgnoreCase("isEditable")) {
			typeCase.setIsEditable(Boolean.parseBoolean(newValue));
		} else if (property.equalsIgnoreCase("code")) {
			typeCase.setCode(new TypeCode(newValue));
		} else if (property.equalsIgnoreCase("isActive")) {
			typeCase.setIsActive(Boolean.parseBoolean(newValue));
		} else if (property.equalsIgnoreCase("kind")) {
			typeCase.getKind().setName(newValue);
		} else if (property.equalsIgnoreCase("isLatestVersion")) {
			typeCase.setIsLatestVersion(Boolean.parseBoolean(newValue));
		} else if (property.equalsIgnoreCase("fromDate")) {
			typeCase.setFromDate(MockDataUtils.convertToCalendar(newValue, MockDataUtils.EXCEL_DATE_FORMAT));
		} else if (property.equalsIgnoreCase("toDate")) {
			if (StringUtils.isNotBlank(newValue)) {
				typeCase.setToDate(MockDataUtils.convertToCalendar(newValue, MockDataUtils.EXCEL_DATE_FORMAT));
			}
		} else if (property.equalsIgnoreCase("isDocumentType")) {
			typeCase.setIsDocumentType(Boolean.parseBoolean(newValue));
		} else {
			throw new NotImplementedException("Obiekt typeCase nie posiada właściwości property=" + property);
		}

	}

}
