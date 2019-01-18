package io.hgdb.mercury.client.mock.data;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.data.Case;
import pro.ibpm.mercury.entities.data.QuickTask;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TQuickTaskProvider extends TAbstractProvider<QuickTask> {

	public TQuickTaskProvider() {
		super("/pro/ibpm/mercury/mock/data/tQuickTask.csv", true);
	}

	private static TQuickTaskProvider instance = new TQuickTaskProvider();

	/** pobranie instancji provider'a */
	public static TQuickTaskProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected QuickTask rowMapper(String[] row) {
		String[] header = new String[] { "id", "caseId", "description",
				"replyText", "priority", "sendDate", "replyDate", "from", "to" };
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
	protected QuickTask rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length="
				+ header.length);

		QuickTask result = new QuickTask();
		result.setCaseObj(new Case());

		if (row != null && header != null && row.length == header.length) {
			for (int index = 0; index < header.length; index++) {
				setPropertyValue(header[index], row[index], result);
			}
		} else {
			String msg = "Nie wczytano danych dla wiersza row=" + row
					+ " oraz naglowka header=" + header;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

		return result;
	}

	private void setPropertyValue(String property, String newValue,
			QuickTask quickTask) {
		// id;caseId;description;replyText;priority;sendDate;replyDate;from;to

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			quickTask.setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("caseId")) {
			quickTask.getCaseObj().setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("description")) {
			quickTask.setDescription(newValue);

		} else if (property.equalsIgnoreCase("replyText")) {
			quickTask.setReplyText(newValue);

		} else if (property.equalsIgnoreCase("priority")) {
			quickTask.setPriority(newValue);

		} else if (property.equalsIgnoreCase("sendDate")) {
			quickTask.setSendDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("replyDate")) {
			quickTask.setReplyDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("from")) {
			quickTask.setFrom(newValue);

		} else if (property.equalsIgnoreCase("to")) {
			quickTask.setTo(newValue);

		} else {
			String msg = "Obiekt " + QuickTask.class.getSimpleName()
					+ " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}