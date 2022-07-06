package io.hgdb.mercury.client.services;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import pro.ibpm.mercury.entities.attr.MParamDefinition;
import pro.ibpm.mercury.entities.attr.MType;
import pro.ibpm.mercury.entities.attr.MTypeParam;

/**
 * 
 * CaseBusinessHelper - pomocnicze dane do opracji klienta na danych spraw.
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class CaseBusinessHelper {

	private CaseBusinessHelper() {
	}

	/**
	 * To może się przydać do operacji zapisy spraw. Pola, które mogą być zignorowane podczas porównywania wersji
	 * metadanych/ typów pochodzących z repozytorium dokumentów
	 */
	public static final Set<String> IGNORED_TYPE_FIELDS;

	static {

		Set<String> ignoredTypeFields = new HashSet<>();
		/* obiekt definicji parametrów */
		ignoredTypeFields.add(MParamDefinition.FIELD_SOURCE_TYPE);
		ignoredTypeFields.add(MParamDefinition.FIELD_SOURCE);
		ignoredTypeFields.add(MParamDefinition.FIELD_SOURCE_JNDI_NAME);
		ignoredTypeFields.add(MParamDefinition.FIELD_SOURCE_J2C_NAME);
		ignoredTypeFields.add(MParamDefinition.FIELD_DESCRIPTION);
		ignoredTypeFields.add(MParamDefinition.FIELD_DEFAULT_VALUE);
		ignoredTypeFields.add(MParamDefinition.FIELD_PRESENTATION_URL_PREFIX);
		ignoredTypeFields.add(MParamDefinition.FIELD_COMPLEX_CLASS);
		ignoredTypeFields.add(MParamDefinition.FIELD_FROM_DATE);
		ignoredTypeFields.add(MParamDefinition.FIELD_TO_DATE);
		ignoredTypeFields.add(MParamDefinition.FIELD_ALTERNATE_NAME);
		ignoredTypeFields.add(MParamDefinition.FIELD_IS_INDEXABLE);
		ignoredTypeFields.add(MParamDefinition.FIELD_IS_EDITABLE);
		ignoredTypeFields.add(MParamDefinition.FIELD_SOURCE_OF_OBJECT);
		ignoredTypeFields.add(MParamDefinition.FIELD_MIME_TYPE);
		ignoredTypeFields.add(MParamDefinition.FIELD_METADATA_INFO);
		ignoredTypeFields.add(MParamDefinition.FIELD_HTML_CONTROL_NAME);
		ignoredTypeFields.add(MParamDefinition.FIELD_TIME_TO_LIVE);
		ignoredTypeFields.add(MParamDefinition.FIELD_VALUE_DEF);
		ignoredTypeFields.add(MParamDefinition.FIELD_RECOMENDED_LABEL);
		ignoredTypeFields.add(MParamDefinition.FIELD_IS_PROTECTED);

		/* obiekt Typu */
		ignoredTypeFields.add(MType.FIELD_KIND);
		ignoredTypeFields.add(MType.FIELD_FROM_DATE);
		ignoredTypeFields.add(MType.FIELD_DESCRIPTION);
		ignoredTypeFields.add(MType.FIELD_TO_DATE);
		ignoredTypeFields.add(MType.FIELD_CHECK_STORE_COUNTER);
		ignoredTypeFields.add(MType.FIELD_ACCOUNT_NUMBER);
		ignoredTypeFields.add(MType.FIELD_IS_ACTIVE);
		ignoredTypeFields.add(MType.FIELD_IS_EDITABLE);
		ignoredTypeFields.add(MType.FIELD_CMIS_TYPE_NAME);
		ignoredTypeFields.add(MType.FIELD_SOURCE_OF_OBJECT);

		/* obiekty parametrów typu */
		ignoredTypeFields.add(MTypeParam.FIELD_LABEL);
		ignoredTypeFields.add(MTypeParam.FIELD_TOOLTIP);
		ignoredTypeFields.add(MTypeParam.FIELD_IS_SEARCH_DATA);
		ignoredTypeFields.add(MTypeParam.FIELD_ARG1);
		ignoredTypeFields.add(MTypeParam.FIELD_ARG2);
		ignoredTypeFields.add(MTypeParam.FIELD_ARG3);
		ignoredTypeFields.add(MTypeParam.FIELD_ARG4);
		ignoredTypeFields.add(MTypeParam.FIELD_ARG5);
		ignoredTypeFields.add(MTypeParam.FIELD_HAS_MULTI_VALUES);
		ignoredTypeFields.add(MTypeParam.FIELD_ACTIONS);
		ignoredTypeFields.add(MTypeParam.FIELD_WITH_EMPTY_OPTION);
		ignoredTypeFields.add(MTypeParam.FIELD_IS_REQUIRED);
		ignoredTypeFields.add(MTypeParam.FIELD_CMIS_FIELD_NAME);
		ignoredTypeFields.add(MTypeParam.FIELD_UPDATEABLE);

		/* ustawiam domyślną listę argumentów ignorowanych */
		IGNORED_TYPE_FIELDS = Collections.unmodifiableSet(ignoredTypeFields);
	}
}
