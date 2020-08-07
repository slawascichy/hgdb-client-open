package io.hgdb.multi.client;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.hgdb.multi.client.context.ClientContextParams;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.cse.CredentialsMode;
import pro.ibpm.mercury.dao.PageingHelper;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.data.Case;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.INotyficationLogic;
import pro.ibpm.mercury.logic.api.NotyficationType;
import pro.ibpm.mercury.logic.api.data.ICaseLogic;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("spring-test.xml")
public class MultiClientTest extends SpringClientTestSupport {

	private final Context context = new Context("ttesteusz", "WsClientCXFTest", "001", "10", "1000");

	public MultiClientTest() {
		super(Boolean.parseBoolean(props.getProperty("test.skip.package." + MultiClientTest.class.getSimpleName())));
		context.setMaxResults(100);

	}

	/**
	 * Rigourous Test :-)
	 */
	@Test
	public void testCaseSearch() throws MercuryException, Exception {

		String methodName = "testCaseSearch";
		context.setAdditionalPropertyValue(ClientContextParams.HTTP_INVOCER_PROXY_FACTORY_INSTANCE_NAME, "testcluster");

		logger.info("Start testu... " + "\n************************************" + "\n*  SCENARIUSZ {}()  *"
				+ "\n************************************", new Object[] { methodName });

		IPage defaultPage = new PageTransportable(PageingHelper.getDefaultPagingParams().getFirstPage());
		context.setMaxResults(1000);
		ICaseLogic caseLogic = (ICaseLogic) applicationContext.getBean("caseLogic");
		Collection<Case> caseList;
		IPagedResult<Case, IPage> searchResult;
		Map<String, String> paramsMap = new HashMap<String, String>();
		Calendar fromDate;
		Calendar toDate;

		/*
		 * XXX Wyszukiwanie po atrybucie "priv": 'RO' po zakresie dat ważności typów.
		 */
		paramsMap.clear();
		paramsMap.put("priv", "RO");
		fromDate = Calendar.getInstance();
		fromDate.set(2012, 11, 1);
		toDate = Calendar.getInstance();
		toDate.set(2022, 10, 10);

		searchResult = caseLogic.search(context, paramsMap, CredentialsMode.AND, fromDate, toDate, defaultPage);
		caseList = searchResult.getResult();

		List<String> idS = new ArrayList<String>();
		for (Case caseObj : caseList) {
			idS.add(caseObj.getId().toString());
		}
		assertEquals("Niepoprawna ilosc elementow", 3, idS.size());

	}

	@Test
	public void testNotyfication() throws MercuryException, Exception {

		final String methodName = "testNotyfication";
		final String beanName = "notyficationLogic";
		context.setAdditionalPropertyValue(ClientContextParams.HTTP_INVOCER_PROXY_FACTORY_INSTANCE_NAME, "testcluster");

		logger.info("Start testu... " + "\n************************************" + "\n*  SCENARIUSZ {}()  *"
				+ "\n************************************", new Object[] { methodName });
		String result = "OK";
		try {

			final INotyficationLogic logic = (INotyficationLogic) applicationContext.getBean(beanName);
			assert logic != null : "Nie znaleziono beana " + beanName;
			logic.sendNotyfication(context, NotyficationType.USER_REQUEST, "Proszę o potwierdzenie zmian.");
		} catch (Exception ex) {
			logger.error("ERROR : wyjatek", ex);
			result = "BAD";
			throw ex;
		}
		assert result.equals("OK") : "Test zakończył się porażką";
	}
}
