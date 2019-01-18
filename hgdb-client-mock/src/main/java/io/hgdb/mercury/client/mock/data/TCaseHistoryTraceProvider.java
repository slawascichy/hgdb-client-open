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
import pro.ibpm.mercury.entities.data.CaseHistoryTrace;
import pro.ibpm.mercury.entities.data.GroupCase;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TCaseHistoryTraceProvider extends TAbstractProvider<CaseHistoryTrace> {

	public TCaseHistoryTraceProvider() {
		super("/pro/ibpm/mercury/mock/data/tCaseHistoryTrace.csv", true);
	}

	private static TCaseHistoryTraceProvider instance = new TCaseHistoryTraceProvider();

	/** pobranie instancji provider'a */
	public static TCaseHistoryTraceProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected CaseHistoryTrace rowMapper(String[] row) {
		String[] header = new String[] { "id", "taskSubject", "caseId", "bpmProcessId", "caseGroupId", "caseTypeId",
				"createDate", "createdBy", "modifyDate", "modifiedBy", "endDate", "dueDate", "status", "parameter1",
				"parameter2", "parameter3", "parameter4", "parameter5", "parameter6", "parameter7", "parameter8",
				"parameter9", "parameter10", "parameter11", "parameter12", "parameter13", "parameter14", "parameter15",
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
	protected CaseHistoryTrace rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length=" + header.length);

		CaseHistoryTrace result = new CaseHistoryTrace();
		result.setMatter(new Case());
		result.setGroup(new GroupCase());
		result.setType(new TypeCase());

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

	private void setPropertyValue(String property, String newValue, CaseHistoryTrace caseHistoryTrace) {
		// id;taskSubject;caseId;bpmProcessId;caseGroupId;caseTypeId;createDate;createdBy;modifyDate;modifiedBy;endDate;dueDate;status;parameter1;parameter2;parameter3;parameter4;parameter5;parameter6;parameter7;parameter8;parameter9;parameter10;parameter11;parameter12;parameter13;parameter14;parameter15

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			caseHistoryTrace.setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("taskSubject")) {
			caseHistoryTrace.setChangeSubject(newValue);

		} else if (property.equalsIgnoreCase("caseId")) {
			caseHistoryTrace.getMatter().setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("bpmProcessId")) {
			caseHistoryTrace.setBpmProcessId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("caseGroupId")) {
			caseHistoryTrace.getGroup().setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("caseTypeId")) {
			caseHistoryTrace.getType().setId(MockDataUtils.convertToLong(newValue));
			caseHistoryTrace.getType().setVersionMinor(0.0);

		} else if (property.equalsIgnoreCase("createDate")) {
			caseHistoryTrace.setTraceDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("createdBy")) {
			Long id = Long.parseLong(newValue);
			caseHistoryTrace.setCreatedBy(TSystemUserProvider.usersMap.get(id));

		} else if (property.equalsIgnoreCase("modifyDate")) {
			// FIXME Nie ma już takiego pola

		} else if (property.equalsIgnoreCase("modifiedBy")) {
			// FIXME Nie ma już takiego pola

		} else if (property.equalsIgnoreCase("endDate")) {
			caseHistoryTrace.setEndDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("dueDate")) {
			caseHistoryTrace.setDueDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("status")) {
			caseHistoryTrace.setStatus(newValue);

		} else if (property.equalsIgnoreCase("parameter1")) {
			caseHistoryTrace.setParameter1(newValue);

		} else if (property.equalsIgnoreCase("parameter2")) {
			caseHistoryTrace.setParameter2(newValue);

		} else if (property.equalsIgnoreCase("parameter3")) {
			caseHistoryTrace.setParameter3(newValue);

		} else if (property.equalsIgnoreCase("parameter4")) {
			caseHistoryTrace.setParameter4(newValue);

		} else if (property.equalsIgnoreCase("parameter5")) {
			caseHistoryTrace.setParameter5(newValue);

		} else if (property.equalsIgnoreCase("parameter6")) {
			caseHistoryTrace.setParameter6(newValue);

		} else if (property.equalsIgnoreCase("parameter7")) {
			caseHistoryTrace.setParameter7(newValue);

		} else if (property.equalsIgnoreCase("parameter8")) {
			caseHistoryTrace.setParameter8(newValue);

		} else if (property.equalsIgnoreCase("parameter9")) {
			caseHistoryTrace.setParameter9(newValue);

		} else if (property.equalsIgnoreCase("parameter10")) {
			caseHistoryTrace.setParameter10(newValue);

		} else if (property.equalsIgnoreCase("parameter11")) {
			caseHistoryTrace.setParameter11(newValue);

		} else if (property.equalsIgnoreCase("parameter12")) {
			caseHistoryTrace.setParameter12(newValue);

		} else if (property.equalsIgnoreCase("parameter13")) {
			caseHistoryTrace.setParameter13(newValue);

		} else if (property.equalsIgnoreCase("parameter14")) {
			caseHistoryTrace.setParameter14(newValue);

		} else if (property.equalsIgnoreCase("parameter15")) {
			caseHistoryTrace.setParameter15(newValue);

		} else if (property.equalsIgnoreCase("piervousVersionId")) {
			caseHistoryTrace.setPiervousVersionId(MockDataUtils.convertToLong(newValue));

		} else {
			String msg = "Obiekt " + CaseHistoryTrace.class.getSimpleName() + " nie posiada właściwości property="
					+ property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}
