/**
 * 
 */
package io.hgdb.client.mock.arch;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.client.mock.TAbstractProvider;
import io.hgdb.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.arch.ArchKtmNumber;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TArchKtmNumberProvider extends TAbstractProvider<ArchKtmNumber> {

	public TArchKtmNumberProvider() {
		super("/pro/ibpm/mercury/mock/arch/tArchKTMNumber.csv", true);
	}

	private static TArchKtmNumberProvider instance = new TArchKtmNumberProvider();

	/** pobranie instancji provider'a */
	public static TArchKtmNumberProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected ArchKtmNumber rowMapper(String[] row) {
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
	protected ArchKtmNumber rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length="
				+ header.length);

		ArchKtmNumber result = new ArchKtmNumber();

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
			ArchKtmNumber entity) {

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			entity.setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("description")) {
			entity.setDescription(newValue);

		} else if (property.equalsIgnoreCase("groupCode")) {
			entity.setGroupCode(newValue);

		} else if (property.equalsIgnoreCase("ktmCode")) {
			entity.setKtmCode(newValue);

		} else if (property.equalsIgnoreCase("priceValue")) {
			entity.setPriceValue(MockDataUtils.convertToDouble(newValue));

		} else {
			String msg = "Obiekt " + entity.getClass().getSimpleName()
					+ " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}

	}
	
}
