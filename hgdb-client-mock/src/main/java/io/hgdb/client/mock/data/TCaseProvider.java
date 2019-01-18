/**
 * 
 */
package io.hgdb.client.mock.data;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.client.mock.TAbstractProvider;
import io.hgdb.client.mock.helpers.MockDataUtils;
import io.hgdb.client.mock.helpers.MockType;
import pro.ibpm.mercury.entities.attr.TypeCase;
import pro.ibpm.mercury.entities.data.Case;
import pro.ibpm.mercury.entities.data.GroupCase;
import pro.ibpm.mercury.entities.data.Store;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TCaseProvider extends TAbstractProvider<Case> {

	private static TCaseProvider simpleInstance = null;

	private static TCaseProvider bigDataInstance = null;

	private final MockType mockType;

	public TCaseProvider(MockType mockType, String sourceFileName) {
		super(sourceFileName, true);
		this.mockType = mockType;
	}

	/** pobranie instancji provider'a */
	public static TCaseProvider getInstance() {
		if (simpleInstance == null) {
			simpleInstance = new TCaseProvider(MockType.simpleData, "/pro/ibpm/mercury/mock/data/tCase.csv");
		}
		return simpleInstance;
	}

	/** pobranie instancji provider'a dla dużej ilości danych */
	public static TCaseProvider getInstanceBigData() {
		if (bigDataInstance == null) {
			bigDataInstance = new TCaseProvider(MockType.bigData, "/pro/ibpm/mercury/mock/data/tCase1000.csv");
		}
		return bigDataInstance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected Case rowMapper(String[] row) {
		String[] header = new String[] { "id", "bpmProcessId", "caseGroupId", "caseTypeId", "storeId", "createDate",
				"createdBy", "modifyDate", "modifiedBy", "endDate", "dueDate", "status", "parameter1", "parameter2",
				"parameter3", "parameter4", "parameter5", "parameter6", "parameter7", "parameter8", "parameter9",
				"parameter10", "parameter11", "parameter12", "parameter13", "parameter14", "parameter15",
				"piervousVersionId" };
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
	protected Case rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length=" + header.length);

		Case result = new Case();
		result.setGroup(new GroupCase());
		result.setType(new TypeCase());
		result.setStore(new Store());

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

	private void setPropertyValue(String property, String newValue, Case caseO) {
		// id;bpmProcessId;caseGroupId;caseTypeId;createDate;createdBy;modifyDate;modifiedBy;endDate;dueDate;status;parameter1;parameter2;parameter3;parameter4;parameter5;parameter6;parameter7;parameter8;parameter9;parameter10;parameter11;parameter12;parameter13;parameter14;parameter15

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			caseO.setId(Long.parseLong(newValue));

		} else if (property.equalsIgnoreCase("bpmProcessId")) {
			caseO.setBpmProcessId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("caseGroupId")) {
			caseO.getGroup().setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("caseTypeId")) {
			caseO.getType().setId(MockDataUtils.convertToLong(newValue));
			caseO.getType().setVersionMinor(0.0);

		} else if (property.equalsIgnoreCase("storeId")) {
			caseO.getStore().setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("createDate")) {
			caseO.setCreateDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("createdBy")) {
			Long id = MockDataUtils.convertToLong(newValue);
			caseO.setCreatedBy(TSystemUserProvider.usersMap.get(id));

		} else if (property.equalsIgnoreCase("modifyDate")) {
			caseO.setLastModifyDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("modifiedBy")) {
			Long id = Long.parseLong(newValue);
			caseO.setLastModifedBy(TSystemUserProvider.usersMap.get(id));

		} else if (property.equalsIgnoreCase("endDate")) {
			caseO.setEndDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("dueDate")) {
			caseO.setDueDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("status")) {
			caseO.setStatus(newValue);

		} else if (property.equalsIgnoreCase("parameter1")) {
			caseO.setParameter1(newValue);
		} else if (property.equalsIgnoreCase("parameter2")) {
			caseO.setParameter2(newValue);
		} else if (property.equalsIgnoreCase("parameter3")) {
			caseO.setParameter3(newValue);
		} else if (property.equalsIgnoreCase("parameter4")) {
			caseO.setParameter4(newValue);
		} else if (property.equalsIgnoreCase("parameter5")) {
			caseO.setParameter5(newValue);
		} else if (property.equalsIgnoreCase("parameter6")) {
			caseO.setParameter6(newValue);
		} else if (property.equalsIgnoreCase("parameter7")) {
			caseO.setParameter7(newValue);
		} else if (property.equalsIgnoreCase("parameter8")) {
			caseO.setParameter8(newValue);
		} else if (property.equalsIgnoreCase("parameter9")) {
			caseO.setParameter9(newValue);
		} else if (property.equalsIgnoreCase("parameter10")) {
			caseO.setParameter10(newValue);
		} else if (property.equalsIgnoreCase("parameter11")) {
			caseO.setParameter11(newValue);
		} else if (property.equalsIgnoreCase("parameter12")) {
			caseO.setParameter12(newValue);
		} else if (property.equalsIgnoreCase("parameter13")) {
			caseO.setParameter13(newValue);
		} else if (property.equalsIgnoreCase("parameter14")) {
			caseO.setParameter14(newValue);
		} else if (property.equalsIgnoreCase("parameter15")) {
			caseO.setParameter15(newValue);

		} else if (property.equalsIgnoreCase("piervousVersionId")) {
			caseO.setPiervousVersionId(MockDataUtils.convertToLong(newValue));

		} else {
			String msg = "Obiekt " + Case.class.getSimpleName() + " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}
		caseO.setStoreCount(1);
	}

	/**
	 * @return the {@link #mockType}
	 */
	@Override
	public MockType getMockType() {
		return mockType;
	}
}
