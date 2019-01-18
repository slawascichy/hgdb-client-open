/**
 * 
 */
package io.hgdb.client.mock.data;

import org.apache.commons.lang.NotImplementedException;

import io.hgdb.client.mock.TAbstractProvider;
import io.hgdb.client.mock.helpers.MockDataUtils;
import pro.ibpm.mercury.entities.attr.ParamDefinition;
import pro.ibpm.mercury.entities.attr.ParamDefinitionPK;
import pro.ibpm.mercury.entities.data.Store;
import pro.ibpm.mercury.entities.data.StoreHistoryStream;

/**
 * @author Mariusz Barwikowski
 * 
 */
public class TStoreHistoryStreamProvider extends
		TAbstractProvider<StoreHistoryStream> {

	public TStoreHistoryStreamProvider() {
		super("/pro/ibpm/mercury/mock/data/tStoreHistoryStream.csv", true);
	}

	private static TStoreHistoryStreamProvider instance = new TStoreHistoryStreamProvider();

	/** pobranie instancji provider'a */
	public static TStoreHistoryStreamProvider getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pro.ibpm.mercury.mock.TAbstractProvider#rowMapper(java.lang.String[])
	 */
	@Override
	protected StoreHistoryStream rowMapper(String[] row) {
		String[] header = new String[] { "id", "old_vlue", "new_vlue",
				"store_id", "parameter_def_name", "parameter_def_version" };
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
	protected StoreHistoryStream rowMapper(String[] row, String[] header) {

		this.logger.trace("row.length=" + row.length + ", header.length="
				+ header.length);

		StoreHistoryStream result = new StoreHistoryStream();
		result.setMatter(new Store());
		result.setParamDefinition(new ParamDefinition(new ParamDefinitionPK()));

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
			StoreHistoryStream e) {

		this.logger.trace("property=" + property + ", newValue=" + newValue);

		if (property.equalsIgnoreCase("id")) {
			e.setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("old_vlue")) {
			e.setOldValue(newValue);

		} else if (property.equalsIgnoreCase("new_vlue")) {
			e.setNewValue(newValue);

		} else if (property.equalsIgnoreCase("store_id")) {
			e.getMatter().setId(MockDataUtils.convertToLong(newValue));

		} else if (property.equalsIgnoreCase("parameter_def_name")) {
			e.getParamDefinition().getId().setDefinitionName(newValue);

		} else if (property.equalsIgnoreCase("parameter_def_version")) {
			e.getParamDefinition().getId()
					.setVersion(MockDataUtils.convertToLong(newValue));

		} else {
			String msg = "Obiekt " + StoreHistoryStream.class.getSimpleName()
					+ " nie posiada właściwości property=" + property;
			this.logger.error(msg);
			throw new NotImplementedException(msg);
		}


	}

}
