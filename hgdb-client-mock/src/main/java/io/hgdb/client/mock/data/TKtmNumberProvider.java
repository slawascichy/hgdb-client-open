/**
 * 
 */
package io.hgdb.client.mock.data;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.client.mock.TAbstractProvider;
import io.hgdb.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.data.KtmNumber;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TKtmNumberProvider extends TAbstractProvider<KtmNumber> {

	public TKtmNumberProvider() {
		super("/pro/ibpm/mercury/mock/data/tKTMNumber.csv", true);
	}

	private static TKtmNumberProvider instance = new TKtmNumberProvider();

	/** pobranie instancji provider'a */
	public static TKtmNumberProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected KtmNumber rowMapper(String[] row) {
		String[] header = new String[] { "id", "description", "groupCode",
				"ktmCode", "priceValue" };
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
	protected KtmNumber rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length="
				+ header.length);

		KtmNumber result = new KtmNumber();

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
			KtmNumber kTMNumber) {

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			kTMNumber.setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("description")) {
			kTMNumber.setDescription(newValue);

		} else if (property.equalsIgnoreCase("groupCode")) {
			kTMNumber.setGroupCode(newValue);

		} else if (property.equalsIgnoreCase("ktmCode")) {
			kTMNumber.setKtmCode(newValue);

		} else if (property.equalsIgnoreCase("priceValue")) {
			kTMNumber.setPriceValue(MockDataUtils.convertToDouble(newValue));

		} else {
			String msg = "Obiekt " + KtmNumber.class.getSimpleName()
					+ " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}

}
