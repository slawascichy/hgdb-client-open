package io.hgdb.mercury.client.cxf.test.helpers;

import java.util.Collection;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import pl.slawas.helpers.Strings;
import org.slf4j.Logger;
import pro.ibpm.mercury.business.data.api.MrcCaseHistoryStream;

public class WsCaseStreamHistoryHelper {

	private WsCaseStreamHistoryHelper() {
	}

	private static final int row0size = 10;
	private static final int row1size = 30;
	private static final int row2size = 20;
	private static final int row3size = 20;
	private static final int row4size = 60;

	public static void printResult2Log(final Logger logger, Collection<MrcCaseHistoryStream> result) {
		StringBuffer out = new StringBuffer("\n Strumień sprawy " + result.size() + " wierszy");
		out.append("\n+-" + Strings.lpad("-", "-", row0size));
		out.append("-+-" + Strings.rpad("-", "-", row0size));
		out.append("-+-" + Strings.rpad("-", "-", row1size));
		out.append("-+-" + Strings.rpad("-", "-", row2size));
		out.append("-+-" + Strings.lpad("-", "-", row3size));
		out.append("-+-" + Strings.lpad("-", "-", row4size));
		out.append("-+");
		out.append(printRow("id", "CaseId", "field", "stara", "nowa", "komentarz"));
		out.append("\n+-" + Strings.lpad("-", "-", row0size));
		out.append("-+-" + Strings.rpad("-", "-", row0size));
		out.append("-+-" + Strings.rpad("-", "-", row1size));
		out.append("-+-" + Strings.rpad("-", "-", row2size));
		out.append("-+-" + Strings.lpad("-", "-", row3size));
		out.append("-+-" + Strings.lpad("-", "-", row4size));
		out.append("-+");
		for (MrcCaseHistoryStream scenario : result) {
			String fieldName = (scenario.getParamDefinitionId() != null
					? scenario.getParamDefinitionId().getDefinitionName()
					: "n/a");
			if (scenario.getSubCaseFields() != null && !scenario.getSubCaseFields().isEmpty()) {
				fieldName = ArrayUtils.toString(scenario.getSubCaseFields().toArray()) + "." + fieldName;
			}
			String id = Long.toString(scenario.getId());
			String caseId = scenario.getCaseId() != null ? scenario.getCaseId().toString() : "n/a";
			out.append(printRow(id, caseId, fieldName == null ? StringUtils.EMPTY : fieldName,
					scenario.getOldValue() == null ? StringUtils.EMPTY : scenario.getOldValue(),
					scenario.getNewValue() == null ? StringUtils.EMPTY : scenario.getNewValue(),
					scenario.getModifyComment()));
		}
		out.append("\n+-" + Strings.lpad("-", "-", row0size));
		out.append("-+-" + Strings.rpad("-", "-", row0size));
		out.append("-+-" + Strings.rpad("-", "-", row1size));
		out.append("-+-" + Strings.rpad("-", "-", row2size));
		out.append("-+-" + Strings.lpad("-", "-", row3size));
		out.append("-+-" + Strings.lpad("-", "-", row4size));
		out.append("-+");
		logger.info("{}", out.toString());
	}

	private static String printRow(String id, String caseId, String definitionName, String oldVal, String newVal,
			String comment) {
		StringBuffer out = new StringBuffer();
		out.append("\n| " + Strings.rpad(id, " ", row0size));
		out.append(" | " + Strings.lpad(caseId, " ", row0size));
		out.append(" | " + Strings.lpad(definitionName, " ", row1size));
		out.append(" | " + Strings.lpad(oldVal, " ", row2size));
		out.append(" | " + Strings.lpad(newVal, " ", row3size));
		out.append(" | " + Strings.lpad(comment, " ", row4size));
		out.append(" |");
		return out.toString();
	}

}
