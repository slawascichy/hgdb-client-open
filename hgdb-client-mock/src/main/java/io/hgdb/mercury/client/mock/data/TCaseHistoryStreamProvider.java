/**
 * 
 */
package io.hgdb.mercury.client.mock.data;

import java.util.Arrays;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.attr.ParamDefinition;
import pro.ibpm.mercury.entities.attr.ParamDefinitionPK;
import pro.ibpm.mercury.entities.data.Case;
import pro.ibpm.mercury.entities.data.CaseHistoryStream;

/**
 * 
 * TCaseHistoryStreamProvider
 *
 * @author Mariusz Barwikowski
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class TCaseHistoryStreamProvider extends TAbstractProvider<CaseHistoryStream> {

	public TCaseHistoryStreamProvider() {
		super("/pro/ibpm/mercury/mock/data/tCaseHistoryStream.csv", true);
	}

	private static TCaseHistoryStreamProvider instance = new TCaseHistoryStreamProvider();

	/** pobranie instancji provider'a */
	public static TCaseHistoryStreamProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected CaseHistoryStream rowMapper(String[] row) {
		String[] header = new String[] { "id", "caseId", "modifyComment", "modifyDate", "systemUserId", "newValue",
				"oldValue", "definitionName", "version" };
		return rowMapper(row, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[], java.lang.String[])
	 */
	@Override
	protected CaseHistoryStream rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length=" + header.length);

		CaseHistoryStream result = new CaseHistoryStream();
		result.setMatter(new Case());
		result.setParamDefinition(new ParamDefinition(new ParamDefinitionPK()));

		if (row != null && header != null && row.length == header.length) {
			for (int index = 0; index < header.length; index++) {
				setPropertyValue(header[index], row[index], result);
			}
		} else {
			String msg = "Nie wczytano danych dla wiersza row=" + Arrays.asList(row) + " oraz naglowka header="
					+ Arrays.asList(header);
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

		return result;
	}

	private void setPropertyValue(String property, String newValue, CaseHistoryStream caseHistory) {
		// id;caseId;modifyComment;modifyDate;systemUserId;newValue;oldValue;definitionName;version

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			caseHistory.setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("caseId")) {
			caseHistory.getMatter().setId(newValue);

		} else if (property.equalsIgnoreCase("modifyComment")) {
			caseHistory.setModifyComment(newValue);

		} else if (property.equalsIgnoreCase("modifyDate")) {
			caseHistory.setModifyDate(MockDataUtils.convertToCalendar(newValue, MockDataUtils.EXCEL_DATE_FORMAT));

		} else if (property.equalsIgnoreCase("systemUserId")) {
			caseHistory.setModifyUser(TSystemUserProvider.usersMap.get(MockDataUtils.convertToLong(newValue)));

		} else if (property.equalsIgnoreCase("newValue")) {
			caseHistory.setNewValue(newValue);

		} else if (property.equalsIgnoreCase("oldValue")) {
			caseHistory.setOldValue(newValue);

		} else if (property.equalsIgnoreCase("definitionName")) {
			caseHistory.getParamDefinition().getId().setDefinitionName(newValue);

		} else if (property.equalsIgnoreCase("version")) {
			caseHistory.getParamDefinition().getId().setVersion(MockDataUtils.convertToLong(newValue));

		} else {
			String msg = "Obiekt " + CaseHistoryStream.class.getSimpleName() + " nie posiada właściwości property="
					+ property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}
