/**
 * 
 */
package io.hgdb.mercury.client.mock.arch;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.arch.ArchCase;
import pro.ibpm.mercury.entities.arch.ArchComment;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TArchCommentProvider extends TAbstractProvider<ArchComment> {

	public TArchCommentProvider() {
		super("/pro/ibpm/mercury/mock/arch/tArchComment.csv", true);
	}

	private static TArchCommentProvider instance = new TArchCommentProvider();

	/** pobranie instancji provider'a */
	public static TArchCommentProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected ArchComment rowMapper(String[] row) {
		String[] header = new String[] { "id", "caseId", "createDate", "username", "content" };
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
	protected ArchComment rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length=" + header.length);

		ArchComment result = new ArchComment();
		result.setCaseObj(new ArchCase());

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

	private void setPropertyValue(String property, String newValue, ArchComment entity) {
		// id;caseId;createDate;username;content

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			entity.setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("caseId")) {
			entity.getCaseObj().setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("createDate")) {
			entity.setCreateDate(MockDataUtils.convertToCalendar(newValue));

		} else if (property.equalsIgnoreCase("username")) {
			entity.setUsername(newValue);

		} else if (property.equalsIgnoreCase("content")) {
			entity.setContent(newValue);

		} else {
			String msg = "Obiekt " + entity.getClass().getSimpleName() + " nie posiada właściwości property="
					+ property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}
