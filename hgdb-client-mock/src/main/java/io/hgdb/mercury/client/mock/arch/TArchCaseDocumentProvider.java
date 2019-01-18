/**
 * 
 */
package io.hgdb.mercury.client.mock.arch;

import java.util.Arrays;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.data.TSystemUserProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.arch.ArchCase;
import pro.ibpm.mercury.entities.arch.ArchCaseDocument;
import pro.ibpm.mercury.entities.arch.ArchCaseDocumentPK;
import pro.ibpm.mercury.entities.attr.TypeCase;
import pro.ibpm.mercury.entities.data.SystemUser;
import pro.ibpm.mercury.entities.dict.InitStatus;
import pro.ibpm.mercury.entities.dict.Source;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TArchCaseDocumentProvider extends
		TAbstractProvider<ArchCaseDocument> {

	public TArchCaseDocumentProvider() {
		super("/pro/ibpm/mercury/mock/arch/tArchCaseDocument.csv", true);
	}

	private static TArchCaseDocumentProvider instance = new TArchCaseDocumentProvider();

	/** pobranie instancji provider'a */
	public static TArchCaseDocumentProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected ArchCaseDocument rowMapper(String[] row) {
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
	protected ArchCaseDocument rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length="
				+ header.length);

		ArchCaseDocument result = new ArchCaseDocument();
		result.setId(new ArchCaseDocumentPK(new ArchCase(), null, null));
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
			ArchCaseDocument entity) {

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("caseId")) {
			entity.getId().getCaseObj()
					.setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("aeObjectId")) {
			entity.getId().setObjectId(newValue);

		} else if (property.equalsIgnoreCase("versionSeriesId")) {
			entity.getId().setVersionSeriesId(newValue);

		} else if (property.equalsIgnoreCase("contentStreamId")) {
			entity.setContentStreamId(newValue);

		} else if (property.equalsIgnoreCase("groupingCode")) {
			entity.setGroupingCode(newValue);

		} else if (property.equalsIgnoreCase("initStatus")) {
			entity.getInitStatus().setName(newValue);

		} else if (property.equalsIgnoreCase("isInput")) {
			entity.setIsInput(MockDataUtils.convertToBoolean(newValue));

		} else if (property.equalsIgnoreCase("isRoot")) {
			entity.setIsRoot(MockDataUtils.convertToBoolean(newValue));

		} else if (property.equalsIgnoreCase("docTypeId")) {
			entity.getType().setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("sourceValue")) {
			entity.getSource().setValue(newValue);

		} else if (property.equalsIgnoreCase("createDate")) {
			entity.setCreateDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("createdBy")) {
			Long id = Long.parseLong(newValue);
			entity.setCreatedBy(new SystemUser(id));

		} else if (property.equalsIgnoreCase("isActive")) {
			entity.setIsActive(MockDataUtils.convertToBoolean(newValue));

		} else if (property.equalsIgnoreCase("isValid")) {
			entity.setIsValid(MockDataUtils.convertToBoolean(newValue));

		} else if (property.equalsIgnoreCase("groupSize")) {
			entity.setGroupSize(MockDataUtils.convertToInteger(newValue));

		} else if (property.equalsIgnoreCase("versionLabel")) {
			entity.setVersionLabel(newValue);

		} else if (property.equalsIgnoreCase("isLatestVersion")) {
			entity.setIsLatestVersion(MockDataUtils.convertToBoolean(newValue));

		} else if (property.equalsIgnoreCase("author")) {
			entity.setAuthor(newValue);

		} else if (property.equalsIgnoreCase("receivedDate")) {
			entity.setReceivedDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("receiver")) {
			entity.setReceiver(newValue);

		} else if (property.equalsIgnoreCase("receiverDW")) {
			entity.setReceiverDW(newValue);

		} else if (property.equalsIgnoreCase("subject")) {
			entity.setSubject(newValue);

		} else if (property.equalsIgnoreCase("lastModifyDate")) {
			entity.setLastModifyDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("lastModifiedBy")) {
			entity.setCreatedBy(TSystemUserProvider.usersMap.get(MockDataUtils
					.convertToLong(newValue)));
			entity.setLastModifedBy(TSystemUserProvider.usersMap
					.get(MockDataUtils.convertToLong(newValue)));

		} else if (property.equalsIgnoreCase("modifyComment")) {
			entity.setModifyComment(newValue);

		} else {
			String msg = "Obiekt " + entity.getClass().getSimpleName()
					+ " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}
	
}
