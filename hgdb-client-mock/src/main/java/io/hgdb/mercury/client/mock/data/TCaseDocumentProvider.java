/**
 * 
 */
package io.hgdb.mercury.client.mock.data;

import java.util.Arrays;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.attr.TypeCase;
import pro.ibpm.mercury.entities.data.Case;
import pro.ibpm.mercury.entities.data.CaseDocument;
import pro.ibpm.mercury.entities.data.CaseDocumentPK;
import pro.ibpm.mercury.entities.data.SystemUser;
import pro.ibpm.mercury.entities.dict.InitStatus;
import pro.ibpm.mercury.entities.dict.Source;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TCaseDocumentProvider extends TAbstractProvider<CaseDocument> {

	public TCaseDocumentProvider() {
		super("/pro/ibpm/mercury/mock/data/tCaseDocument.csv", true);
	}

	private static TCaseDocumentProvider instance = new TCaseDocumentProvider();

	/** pobranie instancji provider'a */
	public static TCaseDocumentProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected CaseDocument rowMapper(String[] row) {
		String[] header = new String[] { "caseId", "aeObjectId",
				"versionSeriesId", "contentStreamId", "groupingCode",
				"initStatus", "isInput", "isRoot", "docTypeId", "sourceValue",
				"createDate", "createdBy", "isActive", "isValid", "groupSize",
				"versionLabel", "isLatestVersion" };
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
	protected CaseDocument rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length="
				+ header.length);

		CaseDocument result = new CaseDocument();
		CaseDocumentPK pk = new CaseDocumentPK();
		pk.setCaseObj(new Case());
		result.setId(pk);
		result.setInitStatus(new InitStatus());
		result.setType(new TypeCase());
		result.setSource(new Source());

		if (row != null && header != null && row.length == header.length) {
			for (int index = 0; index < header.length; index++) {
				setPropertyValue(header[index], row[index], result);
			}
		} else {
			String msg = "Nie wczytano danych dla wiersza row="
					+ Arrays.asList(row) + " oraz naglowka header="
					+ Arrays.asList(header);
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

		return result;
	}

	private void setPropertyValue(String property, String newValue,
			CaseDocument caseDocument) {
		// caseId;aeObjectId;versionSeriesId;contentStreamId;groupingCode;initStatus;isInput;isRoot;docTypeId;sourceValue;createDate;createdBy;isActive;isValid;groupSize;versionLabel;isLatestVersion;author;receivedDate;receiver;receiverDW;subject;lastModifyDate;lastModifiedBy;modifyComment

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("caseId")) {
			caseDocument.getId().getCaseObj()
					.setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("aeObjectId")) {
			caseDocument.getId().setObjectId(newValue);

		} else if (property.equalsIgnoreCase("versionSeriesId")) {
			caseDocument.getId().setVersionSeriesId(newValue);

		} else if (property.equalsIgnoreCase("contentStreamId")) {
			caseDocument.setContentStreamId(newValue);

		} else if (property.equalsIgnoreCase("groupingCode")) {
			caseDocument.setGroupingCode(newValue);

		} else if (property.equalsIgnoreCase("initStatus")) {
			caseDocument.getInitStatus().setName(newValue);

		} else if (property.equalsIgnoreCase("isInput")) {
			caseDocument.setIsInput(MockDataUtils.convertToBoolean(newValue));

		} else if (property.equalsIgnoreCase("isRoot")) {
			caseDocument.setIsRoot(MockDataUtils.convertToBoolean(newValue));

		} else if (property.equalsIgnoreCase("docTypeId")) {
			caseDocument.getType().setId(MockDataUtils.convertToLong(newValue));
			caseDocument.getType().setVersionMinor(0.0);

		} else if (property.equalsIgnoreCase("sourceValue")) {
			caseDocument.getSource().setValue(newValue);

		} else if (property.equalsIgnoreCase("createDate")) {
			caseDocument.setCreateDate(MockDataUtils
					.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("createdBy")) {
			Long id = Long.parseLong(newValue);
			caseDocument.setCreatedBy(new SystemUser(id));

		} else if (property.equalsIgnoreCase("isActive")) {
			caseDocument.setIsActive(MockDataUtils.convertToBoolean(newValue));

		} else if (property.equalsIgnoreCase("isValid")) {
			caseDocument.setIsValid(MockDataUtils.convertToBoolean(newValue));

		} else if (property.equalsIgnoreCase("groupSize")) {
			caseDocument.setGroupSize(MockDataUtils.convertToInteger(newValue));

		} else if (property.equalsIgnoreCase("versionLabel")) {
			caseDocument.setVersionLabel(newValue);

		} else if (property.equalsIgnoreCase("isLatestVersion")) {
			caseDocument.setIsLatestVersion(MockDataUtils
					.convertToBoolean(newValue));

		} else if (property.equalsIgnoreCase("author")) {
			caseDocument.setAuthor(newValue);

		} else if (property.equalsIgnoreCase("receivedDate")) {
			caseDocument.setReceivedDate(MockDataUtils
					.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("receiver")) {
			caseDocument.setReceiver(newValue);

		} else if (property.equalsIgnoreCase("receiverDW")) {
			caseDocument.setReceiverDW(newValue);

		} else if (property.equalsIgnoreCase("subject")) {
			caseDocument.setSubject(newValue);

		} else if (property.equalsIgnoreCase("lastModifyDate")) {
			caseDocument.setLastModifyDate(MockDataUtils
					.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("lastModifiedBy")) {
			caseDocument.setCreatedBy(TSystemUserProvider.usersMap
					.get(MockDataUtils.convertToLong(newValue)));
			caseDocument.setLastModifedBy(TSystemUserProvider.usersMap
					.get(MockDataUtils.convertToLong(newValue)));

		} else if (property.equalsIgnoreCase("modifyComment")) {
			caseDocument.setModifyComment(newValue);

		} else {
			String msg = "Obiekt " + CaseDocument.class.getSimpleName()
					+ " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}
	
}