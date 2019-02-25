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
import pro.ibpm.mercury.entities.arch.ArchCaseHistoryTrace;
import pro.ibpm.mercury.entities.arch.ArchGroupCase;
import pro.ibpm.mercury.entities.attr.TypeCase;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TArchCaseHistoryTraceProvider extends TAbstractProvider<ArchCaseHistoryTrace> {

	public TArchCaseHistoryTraceProvider() {
		super("/pro/ibpm/mercury/mock/arch/tArchCaseHistoryTrace.csv", true);
	}

	private static TArchCaseHistoryTraceProvider instance = new TArchCaseHistoryTraceProvider();

	/** pobranie instancji provider'a */
	public static TArchCaseHistoryTraceProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected ArchCaseHistoryTrace rowMapper(String[] row) {
		String[] header = new String[] { "id", "taskSubject", "caseId", "bpmProcessId", "caseGroupId", "caseTypeId",
				"createDate", "createdBy", "modifyDate", "modifiedBy", "endDate", "dueDate", "status", "parameter1",
				"parameter2", "parameter3", "parameter4", "parameter5", "parameter6", "parameter7", "parameter8",
				"parameter9", "parameter10", "parameter11", "parameter12", "parameter13", "parameter14", "parameter15",
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
	protected ArchCaseHistoryTrace rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length={}, header.length={} ", row.length, header.length);

		ArchCaseHistoryTrace result = new ArchCaseHistoryTrace();
		result.setMatter(new ArchCase());
		result.setGroup(new ArchGroupCase());
		result.setType(new TypeCase());

		if (row.length == header.length) {
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

	private void setPropertyValue(String property, String newValue, ArchCaseHistoryTrace entity) {
		// id;taskSubject;caseId;bpmProcessId;caseGroupId;caseTypeId;createDate;createdBy;modifyDate;modifiedBy;endDate;dueDate;status;parameter1;parameter2;parameter3;parameter4;parameter5;parameter6;parameter7;parameter8;parameter9;parameter10;parameter11;parameter12;parameter13;parameter14;parameter15

		this.logger.trace("property={}, newValue={}", property, newValue);

		if (property.equalsIgnoreCase("id")) {
			entity.setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("taskSubject")) {
			entity.setChangeSubject(newValue);

		} else if (property.equalsIgnoreCase("caseId")) {
			entity.getMatter().setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("bpmProcessId")) {
			entity.setBpmProcessId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("caseGroupId")) {
			entity.getGroup().setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("caseTypeId")) {
			entity.getType().setId(MockDataUtils.convertToLong(newValue));
		} else if (property.equalsIgnoreCase("createDate")) {
			entity.setTraceDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("createdBy")) {
			Long id = Long.parseLong(newValue);
			entity.setCreatedBy(TSystemUserProvider.usersMap.get(id));

		} else if (property.equalsIgnoreCase("modifyDate")) {
			// FIXME Nie ma już takiego pola

		} else if (property.equalsIgnoreCase("modifiedBy")) {
			// FIXME Nie ma już takiego pola

		} else if (property.equalsIgnoreCase("endDate")) {
			entity.setEndDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("dueDate")) {
			entity.setDueDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("status")) {
			entity.setStatus(newValue);

		} else if (property.equalsIgnoreCase("parameter1")) {
			entity.setParameter1(newValue);

		} else if (property.equalsIgnoreCase("parameter2")) {
			entity.setParameter2(newValue);

		} else if (property.equalsIgnoreCase("parameter3")) {
			entity.setParameter3(newValue);

		} else if (property.equalsIgnoreCase("parameter4")) {
			entity.setParameter4(newValue);

		} else if (property.equalsIgnoreCase("parameter5")) {
			entity.setParameter5(newValue);

		} else if (property.equalsIgnoreCase("parameter6")) {
			entity.setParameter6(newValue);

		} else if (property.equalsIgnoreCase("parameter7")) {
			entity.setParameter7(newValue);

		} else if (property.equalsIgnoreCase("parameter8")) {
			entity.setParameter8(newValue);

		} else if (property.equalsIgnoreCase("parameter9")) {
			entity.setParameter9(newValue);

		} else if (property.equalsIgnoreCase("parameter10")) {
			entity.setParameter10(newValue);

		} else if (property.equalsIgnoreCase("parameter11")) {
			entity.setParameter11(newValue);

		} else if (property.equalsIgnoreCase("parameter12")) {
			entity.setParameter12(newValue);

		} else if (property.equalsIgnoreCase("parameter13")) {
			entity.setParameter13(newValue);

		} else if (property.equalsIgnoreCase("parameter14")) {
			entity.setParameter14(newValue);

		} else if (property.equalsIgnoreCase("parameter15")) {
			entity.setParameter15(newValue);

		} else if (property.equalsIgnoreCase("previousVersionId")) {
			entity.setPreviousVersionId(MockDataUtils.convertToLong(newValue));

		} else {
			String msg = "Obiekt " + entity.getClass().getSimpleName() + " nie posiada właściwości property="
					+ property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}
