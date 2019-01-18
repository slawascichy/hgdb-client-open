/*
 * Copyright 2014 Sci Software Sławomir Cichy
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.	
 */
package io.hgdb.mercury.client.mock.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import io.hgdb.mercury.client.mock.TProvider;
import io.hgdb.mercury.client.mock.arch.TArchCase2CaseProvider;
import io.hgdb.mercury.client.mock.arch.TArchCaseDocumentProvider;
import io.hgdb.mercury.client.mock.arch.TArchCaseHistoryStreamProvider;
import io.hgdb.mercury.client.mock.arch.TArchCaseHistoryTraceProvider;
import io.hgdb.mercury.client.mock.arch.TArchCaseProvider;
import io.hgdb.mercury.client.mock.arch.TArchCommentProvider;
import io.hgdb.mercury.client.mock.arch.TArchGroupCase2ParticipantProvider;
import io.hgdb.mercury.client.mock.arch.TArchGroupCaseHistoryStreamProvider;
import io.hgdb.mercury.client.mock.arch.TArchGroupCaseProvider;
import io.hgdb.mercury.client.mock.arch.TArchKtmNumberProvider;
import io.hgdb.mercury.client.mock.arch.TArchQuickTaskProvider;
import io.hgdb.mercury.client.mock.attr.TParamDefinitionProvider;
import io.hgdb.mercury.client.mock.attr.TType2TypeProvider;
import io.hgdb.mercury.client.mock.attr.TTypeCaseProvider;
import io.hgdb.mercury.client.mock.attr.TTypeCodeProvider;
import io.hgdb.mercury.client.mock.attr.TTypeParamActionProvider;
import io.hgdb.mercury.client.mock.attr.TTypeParamProvider;
import io.hgdb.mercury.client.mock.attr.TTypeParamRoleProvider;
import io.hgdb.mercury.client.mock.data.TCase2CaseProvider;
import io.hgdb.mercury.client.mock.data.TCaseDocumentProvider;
import io.hgdb.mercury.client.mock.data.TCaseHistoryStreamProvider;
import io.hgdb.mercury.client.mock.data.TCaseHistoryTraceProvider;
import io.hgdb.mercury.client.mock.data.TCaseProvider;
import io.hgdb.mercury.client.mock.data.TCommentProvider;
import io.hgdb.mercury.client.mock.data.TGroupCase2ParticipantProvider;
import io.hgdb.mercury.client.mock.data.TGroupCaseHistoryStreamProvider;
import io.hgdb.mercury.client.mock.data.TGroupCaseProvider;
import io.hgdb.mercury.client.mock.data.TKtmNumberProvider;
import io.hgdb.mercury.client.mock.data.TLoggerEventProvider;
import io.hgdb.mercury.client.mock.data.TParticipant2TypeStatsProvider;
import io.hgdb.mercury.client.mock.data.TParticipantHistoryStreamProvider;
import io.hgdb.mercury.client.mock.data.TParticipantHistoryTraceProvider;
import io.hgdb.mercury.client.mock.data.TParticipantProvider;
import io.hgdb.mercury.client.mock.data.TQuickTaskProvider;
import io.hgdb.mercury.client.mock.data.TStore2TypeProvider;
import io.hgdb.mercury.client.mock.data.TStoreHistoryStreamProvider;
import io.hgdb.mercury.client.mock.data.TStoreProvider;
import io.hgdb.mercury.client.mock.data.TSystemUserProvider;
import io.hgdb.mercury.client.mock.dict.TInitStatusProvider;
import io.hgdb.mercury.client.mock.dict.TParticipantKindProvider;
import io.hgdb.mercury.client.mock.dict.TRoleProvider;
import io.hgdb.mercury.client.mock.dict.TSourcesProvider;
import io.hgdb.mercury.client.mock.dict.TTypeKindProvider;

/**
 * MockDataUtils
 * 
 * @author Sławomir Cichy &lt;slawas@slawas.pl&gt;
 * @version $Revision: 1.1 $
 * 
 */
public class MockDataUtils {

	/** Formatowanie daty w mock'ach */
	private static final String DATE_FORMAT = "dd-MM-yyyy";

	/**
	 * Pobranie obiektu formater'a daty
	 * 
	 * @return obiekt formater'a daty
	 */
	public static SimpleDateFormat getSDF() {
		return new SimpleDateFormat(DATE_FORMAT);
	}

	/**
	 * Hierarchia dostawców testowych obiektów tożsama z hierarchią encji
	 * 
	 * FIXME Jeżeli dodajemy nową encję/dostawcę trzeba go umieścić na tej
	 * liście.
	 */
	private static final TProvider<?>[] hierarchy = {
			/*************** dict ***********************/
			/** Typy partycypantów */
			TParticipantKindProvider.getInstance(),
			/** Źródła */
			TSourcesProvider.getInstance(),
			/** Słownik TypeKind */
			TTypeKindProvider.getInstance(),
			/** Słownik InitStatus */
			TInitStatusProvider.getInstance(),
			/** Słownik ról */
			TRoleProvider.getInstance(), TSystemUserProvider.getInstance(),
			/*************** attr ***********************/
			/** Kody typów */
			TTypeCodeProvider.getInstance(),
			/** ParamDefinitiopn */
			TParamDefinitionProvider.getInstance(),
			/** Typy spraw */
			TTypeCaseProvider.getInstance(),
			/** Typy parametrów */
			TTypeParamProvider.getInstance(),
			/** TypeParamAction */
			TTypeParamActionProvider.getInstance(),
			/** Type2Type */
			TType2TypeProvider.getInstance(),
			/** TypeParamRole */
			TTypeParamRoleProvider.getInstance(),
			/*************** data ***********************/
			/** KTMNumber */
			TKtmNumberProvider.getInstance(),
			/** Store */
			TStoreProvider.getInstance(),
			/** Store2Type */
			TStore2TypeProvider.getInstance(),
			/** StoreHistoryStream */
			TStoreHistoryStreamProvider.getInstance(),
			/** Petenci */
			TParticipantProvider.getInstance(),
			/** ParticipantHistoryStream */
			TParticipantHistoryStreamProvider.getInstance(),
			/** ParticipantHistoryTrace */
			TParticipantHistoryTraceProvider.getInstance(),
			/** Participant2TypeStats */
			TParticipant2TypeStatsProvider.getInstance(),
			/** Grupy spraw */
			TGroupCaseProvider.getInstance(),
			/** Grupy spraw do użytkowników */
			TGroupCase2ParticipantProvider.getInstance(),
			/** Sprawy */
			TCaseProvider.getInstanceBigData(), TCaseProvider.getInstance(),
			/** HistoryStream dla grup */
			TGroupCaseHistoryStreamProvider.getInstance(),
			/** Zależności między sprawami */
			TCase2CaseProvider.getInstance(),
			/** Dokumenty sprawy */
			TCaseDocumentProvider.getInstance(),
			/** Komentarz */
			TCommentProvider.getInstance(),
			/** Zadania */
			TQuickTaskProvider.getInstance(),
			/** Historia sprawy - Trace */
			TCaseHistoryTraceProvider.getInstance(),
			/** Historia sprawy - Stream */
			TCaseHistoryStreamProvider.getInstance(),
			/** LoggerEvent */
			TLoggerEventProvider.getInstance(),
			/*************** arch ***********************/
			/** ArchKTMNumber */
			TArchKtmNumberProvider.getInstance(),
			/** Grupy spraw */
			TArchGroupCaseProvider.getInstance(),
			/** HistoryStream dla grup */
			TArchGroupCaseHistoryStreamProvider.getInstance(),
			/** Grupy spraw do użytkowników */
			TArchGroupCase2ParticipantProvider.getInstance(),
			/** Grupy spraw do użytkowników */
			TArchCaseProvider.getInstance(),
			/** Zależności między sprawami */
			TArchCase2CaseProvider.getInstance(),
			/** Dokumenty sprawy */
			TArchCaseDocumentProvider.getInstance(),
			/** Zadania */
			TArchQuickTaskProvider.getInstance(),
			/** Historia sprawy - Trace */
			TArchCaseHistoryTraceProvider.getInstance(),
			/** Historia sprawy - Stream */
			TArchCaseHistoryStreamProvider.getInstance(),
			/** Komentarz */
			TArchCommentProvider.getInstance(), };

	public static Calendar convertToCalendar(String str) {
		Calendar result = null;

		if (isNotNull(str)) {
			result = Calendar.getInstance();
			try {
				result.setTime(getSDF().parse(str));
			} catch (ParseException e) {
				throw new UnsupportedOperationException("ParseException : Nie moge sparsować daty, dataValue=" + str
						+ ", przyjęty format daty=" + DATE_FORMAT);
			}
		}

		return result;
	}

	public static Long convertToLong(String str) {
		Long result = null;
		if (isNotNull(str)) {
			result = Long.parseLong(str);
		}

		return result;
	}

	public static Integer convertToInteger(String str) {
		Integer result = null;
		if (isNotNull(str)) {
			result = Integer.parseInt(str);
		}
		return result;
	}

	public static Boolean convertToBoolean(String str) {
		Boolean result = null;
		if (isNotNull(str)) {
			result = Boolean.parseBoolean(str);
		}

		return result;
	}

	public static boolean isNotNull(String str) {
		return StringUtils.isNotBlank(str) && !"null".equalsIgnoreCase(str);
	}

	/**
	 * @param newValue
	 * @return
	 */
	public static Double convertToDouble(String str) {
		Double result = null;
		if (isNotNull(str)) {
			result = Double.parseDouble(str);
		}

		return result;
	}

	/**
	 * @return the {@link #hierarchy}
	 */
	public static TProvider<?>[] getHierarchy(MockType mockType) {
		TProvider<?>[] result = new TProvider<?>[] {};
		for (int i = 0; i < hierarchy.length; i++) {
			TProvider<?> provider = hierarchy[i];
			if (provider.getMockType().equals(mockType) || provider.getMockType().equals(MockType.dualData)) {
				result = (TProvider<?>[]) ArrayUtils.add(result, provider);
			}
		}
		return result;
	}
}
