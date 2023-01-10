/**
 * 
 */
package io.hgdb.mercury.client.mock.data;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.mercury.client.mock.TAbstractProvider;
import io.hgdb.mercury.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.data.Case;
import pro.ibpm.mercury.entities.data.Comment;

/**
 * 
 * TCommentProvider
 *
 * @author Mariusz Barwikowski
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class TCommentProvider extends TAbstractProvider<Comment> {

	public TCommentProvider() {
		super("/pro/ibpm/mercury/mock/data/tComment.csv", true);
	}

	private static TCommentProvider instance = new TCommentProvider();

	/** pobranie instancji provider'a */
	public static TCommentProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected Comment rowMapper(String[] row) {
		String[] header = new String[] { "id", "caseId", "createDate", "username", "content" };
		return rowMapper(row, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[], java.lang.String[])
	 */
	@Override
	protected Comment rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length=" + header.length);

		Comment result = new Comment();
		result.setCaseObj(new Case());

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

	private void setPropertyValue(String property, String newValue, Comment comment) {
		// id;caseId;createDate;username;content

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			comment.setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("caseId")) {
			comment.getCaseObj().setId(newValue);

		} else if (property.equalsIgnoreCase("createDate")) {
			comment.setCreateDate(MockDataUtils.convertToCalendar(newValue, MockDataUtils.EXCEL_DATE_FORMAT));

		} else if (property.equalsIgnoreCase("username")) {
			comment.setUsername(newValue);

		} else if (property.equalsIgnoreCase("content")) {
			comment.setContent(newValue);

		} else {
			String msg = "Obiekt " + Comment.class.getSimpleName() + " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}