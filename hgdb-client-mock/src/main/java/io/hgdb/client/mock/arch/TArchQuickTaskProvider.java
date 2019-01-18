package io.hgdb.client.mock.arch;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.client.mock.TAbstractProvider;
import io.hgdb.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.arch.ArchCase;
import pro.ibpm.mercury.entities.arch.ArchQuickTask;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TArchQuickTaskProvider extends TAbstractProvider<ArchQuickTask> {

	public TArchQuickTaskProvider() {
		super("/pro/ibpm/mercury/mock/arch/tArchQuickTask.csv", true);
	}

	private static TArchQuickTaskProvider instance = new TArchQuickTaskProvider();

	/** pobranie instancji provider'a */
	public static TArchQuickTaskProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected ArchQuickTask rowMapper(String[] row) {
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
	protected ArchQuickTask rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length="
				+ header.length);

		ArchQuickTask result = new ArchQuickTask();
		result.setCaseObj(new ArchCase());

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
			ArchQuickTask entity) {
		// id;caseId;description;replyText;priority;sendDate;replyDate;from;to

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			entity.setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("caseId")) {
			entity.getCaseObj().setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("description")) {
			entity.setDescription(newValue);

		} else if (property.equalsIgnoreCase("replyText")) {
			entity.setReplyText(newValue);

		} else if (property.equalsIgnoreCase("priority")) {
			entity.setPriority(newValue);

		} else if (property.equalsIgnoreCase("sendDate")) {
			entity.setSendDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("replyDate")) {
			entity.setReplyDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("from")) {
			entity.setFrom(newValue);

		} else if (property.equalsIgnoreCase("to")) {
			entity.setTo(newValue);

		} else {
			String msg = "Obiekt " + entity.getClass().getSimpleName()
					+ " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}
