package io.hgdb.client.cxf;

import java.util.SortedMap;

import io.hgdb.client.mock.data.TCaseProvider;
import io.hgdb.client.mock.helpers.MockType;
import pro.ibpm.mercury.entities.data.Case;

/**
 * 
 * TCaseMockProvider
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $ 
 *
 */
public class TCaseMockProvider extends TCaseProvider {

	public TCaseMockProvider(MockType mockType, String sourceFileName) {
		super(mockType, sourceFileName);

	}

	/* Overridden (non-Javadoc) */
	public SortedMap<Object, Case> loadFromFile() {
		return super.loadFromFile();
	}

}
