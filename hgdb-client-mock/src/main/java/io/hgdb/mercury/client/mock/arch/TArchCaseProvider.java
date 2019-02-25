/**
 * 
 */
package io.hgdb.mercury.client.mock.arch;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.data.TSystemUserProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.arch.ArchCase;
import pro.ibpm.mercury.entities.arch.ArchGroupCase;
import pro.ibpm.mercury.entities.attr.TypeCase;
import pro.ibpm.mercury.entities.data.Store;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TArchCaseProvider extends TAbstractProvider<ArchCase> {

	public TArchCaseProvider() {
		super("/pro/ibpm/mercury/mock/arch/tArchCase.csv", true);
	}

	private static TArchCaseProvider instance = new TArchCaseProvider();

	/** pobranie instancji provider'a */
	public static TArchCaseProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected ArchCase rowMapper(String[] row) {
		String[] header = new String[] { "id", "bpmProcessId", "caseGroupId", "caseTypeId", "storeId", "createDate",
				"createdBy", "modifyDate", "modifiedBy", "endDate", "dueDate", "status", "parameter1", "parameter2",
				"parameter3", "parameter4", "parameter5", "parameter6", "parameter7", "parameter8", "parameter9",
				"parameter10", "parameter11", "parameter12", "parameter13", "parameter14", "parameter15",
				"previousVersionId" };
		return rowMapper(row, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[],
	 * java.lang.String[])
	 */
	@Override
	protected ArchCase rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length={}, header.length={} ", row.length, header.length);

		ArchCase result = new ArchCase();
		result.setGroup(new ArchGroupCase());
		result.setType(new TypeCase());
		result.setStore(new Store());

		if (row.length == header.length) {
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

	private void setPropertyValue(String property, String newValue, ArchCase entuty) {
		// id;bpmProcessId;caseGroupId;caseTypeId;createDate;createdBy;modifyDate;modifiedBy;endDate;dueDate;status;parameter1;parameter2;parameter3;parameter4;parameter5;parameter6;parameter7;parameter8;parameter9;parameter10;parameter11;parameter12;parameter13;parameter14;parameter15

		this.logger.trace("property={}, newValue={}", property, newValue);

		if (property.equalsIgnoreCase("id")) {
			entuty.setId(Long.parseLong(newValue));

		} else if (property.equalsIgnoreCase("bpmProcessId")) {
			entuty.setBpmProcessId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("caseGroupId")) {
			entuty.getGroup().setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("caseTypeId")) {
			entuty.getType().setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("storeId")) {
			entuty.getStore().setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("createDate")) {
			entuty.setCreateDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("createdBy")) {
			Long id = MockDataUtils.convertToLong(newValue);
			entuty.setCreatedBy(TSystemUserProvider.usersMap.get(id));

		} else if (property.equalsIgnoreCase("modifyDate")) {
			entuty.setLastModifyDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("modifiedBy")) {
			Long id = Long.parseLong(newValue);
			entuty.setLastModifiedBy(TSystemUserProvider.usersMap.get(id));

		} else if (property.equalsIgnoreCase("endDate")) {
			entuty.setEndDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("dueDate")) {
			entuty.setDueDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("status")) {
			entuty.setStatus(newValue);

		} else if (property.equalsIgnoreCase("parameter1")) {
			entuty.setParameter1(newValue);
		} else if (property.equalsIgnoreCase("parameter2")) {
			entuty.setParameter2(newValue);
		} else if (property.equalsIgnoreCase("parameter3")) {
			entuty.setParameter3(newValue);
		} else if (property.equalsIgnoreCase("parameter4")) {
			entuty.setParameter4(newValue);
		} else if (property.equalsIgnoreCase("parameter5")) {
			entuty.setParameter5(newValue);
		} else if (property.equalsIgnoreCase("parameter6")) {
			entuty.setParameter6(newValue);
		} else if (property.equalsIgnoreCase("parameter7")) {
			entuty.setParameter7(newValue);
		} else if (property.equalsIgnoreCase("parameter8")) {
			entuty.setParameter8(newValue);
		} else if (property.equalsIgnoreCase("parameter9")) {
			entuty.setParameter9(newValue);
		} else if (property.equalsIgnoreCase("parameter10")) {
			entuty.setParameter10(newValue);
		} else if (property.equalsIgnoreCase("parameter11")) {
			entuty.setParameter11(newValue);
		} else if (property.equalsIgnoreCase("parameter12")) {
			entuty.setParameter12(newValue);
		} else if (property.equalsIgnoreCase("parameter13")) {
			entuty.setParameter13(newValue);
		} else if (property.equalsIgnoreCase("parameter14")) {
			entuty.setParameter14(newValue);
		} else if (property.equalsIgnoreCase("parameter15")) {
			entuty.setParameter15(newValue);
		} else if (property.equalsIgnoreCase("previousVersionId")) {
			entuty.setPreviousVersionId(MockDataUtils.convertToLong(newValue));
		} else {
			String msg = "Obiekt " + entuty.getClass().getSimpleName() + " nie posiada właściwości property="
					+ property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}
