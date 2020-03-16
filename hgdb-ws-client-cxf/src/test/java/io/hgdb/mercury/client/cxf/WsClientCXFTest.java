package io.hgdb.mercury.client.cxf;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mercury.business.data.api.ICaseHistoryTraceBusiness;
import org.mercury.cxf.client.business.data.ICaseHistoryTraceBusinessXML;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import io.hgdb.mercury.client.cxf.business.data.CaseBusiness;
import io.hgdb.mercury.client.cxf.test.helpers.WsCaseStreamHistoryHelper;
import io.hgdb.mercury.client.mock.helpers.MockType;
import pl.scisoftware.filter.api.ICopyable;
import pl.slawas.entities.NameValuePair;
import pro.ibpm.mercury.business.SecretaryManager;
import pro.ibpm.mercury.business.attr.api.IType2TypeWithLastVersionBusiness;
import pro.ibpm.mercury.business.attr.api.ITypeCodeWithLastVersionBusiness;
import pro.ibpm.mercury.business.data.api.Case2CaseRelationship;
import pro.ibpm.mercury.business.data.api.ICase2CaseBusiness;
import pro.ibpm.mercury.business.data.api.ICaseBusiness;
import pro.ibpm.mercury.business.data.api.ICaseBusinessXML;
import pro.ibpm.mercury.business.data.api.ICaseHistoryStreamBusiness;
import pro.ibpm.mercury.business.data.api.IMrcObject;
import pro.ibpm.mercury.business.data.api.IMrcPagedResult;
import pro.ibpm.mercury.business.data.api.IParticipant2TypeStatsBusiness;
import pro.ibpm.mercury.business.data.api.IStore2TypeLastVersionBusiness;
import pro.ibpm.mercury.business.data.api.IStore2TypeStatsBusiness;
import pro.ibpm.mercury.business.data.api.MrcCaseHistoryStream;
import pro.ibpm.mercury.business.data.api.MrcDataUtils;
import pro.ibpm.mercury.business.data.api.MrcList;
import pro.ibpm.mercury.business.data.api.MrcObject;
import pro.ibpm.mercury.business.data.api.MrcPagedResult;
import pro.ibpm.mercury.business.data.api.MrcSimpleProperty;
import pro.ibpm.mercury.business.data.utils.MrcObjectUtils;
import pro.ibpm.mercury.business.entities.Store2TypeLastVersion;
import pro.ibpm.mercury.business.entities.Type2TypeWithLastVersion;
import pro.ibpm.mercury.business.entities.TypeCodeWithLastVersion;
import pro.ibpm.mercury.config.MercuryConfig;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.cse.CredentialsMode;
import pro.ibpm.mercury.dao.PageingHelper;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.MEntity;
import pro.ibpm.mercury.entities.MEntityToString;
import pro.ibpm.mercury.entities.MIdModifier;
import pro.ibpm.mercury.entities.arch.ArchCase;
import pro.ibpm.mercury.entities.arch.ArchCaseDocument;
import pro.ibpm.mercury.entities.arch.ArchCaseDocumentPK;
import pro.ibpm.mercury.entities.arch.ArchCaseHistoryStream;
import pro.ibpm.mercury.entities.arch.ArchCaseHistoryTrace;
import pro.ibpm.mercury.entities.arch.ArchComment;
import pro.ibpm.mercury.entities.arch.ArchGroupCase;
import pro.ibpm.mercury.entities.arch.ArchGroupCase2Participant;
import pro.ibpm.mercury.entities.arch.ArchGroupCase2ParticipantPK;
import pro.ibpm.mercury.entities.arch.ArchGroupCaseHistoryStream;
import pro.ibpm.mercury.entities.arch.ArchKtmNumber;
import pro.ibpm.mercury.entities.arch.ArchQuickTask;
import pro.ibpm.mercury.entities.attr.CaseViewDefinition;
import pro.ibpm.mercury.entities.attr.ParamDefinition;
import pro.ibpm.mercury.entities.attr.ParamDefinitionPK;
import pro.ibpm.mercury.entities.attr.TypeCase;
import pro.ibpm.mercury.entities.attr.TypeCode;
import pro.ibpm.mercury.entities.attr.TypeParam;
import pro.ibpm.mercury.entities.attr.TypeParamAction;
import pro.ibpm.mercury.entities.attr.TypeParamPK;
import pro.ibpm.mercury.entities.attr.TypeParamRole;
import pro.ibpm.mercury.entities.data.Case;
import pro.ibpm.mercury.entities.data.Case2Case;
import pro.ibpm.mercury.entities.data.CaseDocument;
import pro.ibpm.mercury.entities.data.CaseDocumentPK;
import pro.ibpm.mercury.entities.data.CaseHistoryStream;
import pro.ibpm.mercury.entities.data.CaseHistoryTrace;
import pro.ibpm.mercury.entities.data.Comment;
import pro.ibpm.mercury.entities.data.GroupCase;
import pro.ibpm.mercury.entities.data.GroupCase2Participant;
import pro.ibpm.mercury.entities.data.GroupCase2ParticipantPK;
import pro.ibpm.mercury.entities.data.KtmNumber;
import pro.ibpm.mercury.entities.data.LoggerEvent;
import pro.ibpm.mercury.entities.data.Participant;
import pro.ibpm.mercury.entities.data.ParticipantHistoryStream;
import pro.ibpm.mercury.entities.data.ParticipantHistoryTrace;
import pro.ibpm.mercury.entities.data.QuickTask;
import pro.ibpm.mercury.entities.data.Store;
import pro.ibpm.mercury.entities.data.Store2Type;
import pro.ibpm.mercury.entities.data.Store2TypePK;
import pro.ibpm.mercury.entities.data.StoreHistoryStream;
import pro.ibpm.mercury.entities.data.SystemUser;
import pro.ibpm.mercury.entities.dict.InitStatus;
import pro.ibpm.mercury.entities.dict.Role;
import pro.ibpm.mercury.entities.dict.Source;
import pro.ibpm.mercury.entities.dict.TypeKind;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.MBigDataLogic;
import pro.ibpm.mercury.logic.MCatalogLogic;
import pro.ibpm.mercury.logic.MDataLogic;
import pro.ibpm.mercury.logic.MDictLogic;
import pro.ibpm.mercury.logic.api.INotyficationLogic;
import pro.ibpm.mercury.logic.api.NotyficationType;
import pro.ibpm.mercury.logic.api.arch.IArchCaseDocumentLogic;
import pro.ibpm.mercury.logic.api.arch.IArchCaseHistoryStreamLogic;
import pro.ibpm.mercury.logic.api.arch.IArchCaseHistoryTraceLogic;
import pro.ibpm.mercury.logic.api.arch.IArchCaseLogic;
import pro.ibpm.mercury.logic.api.arch.IArchCommentLogic;
import pro.ibpm.mercury.logic.api.arch.IArchGroupCase2ParticipantLogic;
import pro.ibpm.mercury.logic.api.arch.IArchGroupCaseHistoryStreamLogic;
import pro.ibpm.mercury.logic.api.arch.IArchGroupCaseLogic;
import pro.ibpm.mercury.logic.api.arch.IArchKtmNumberLogic;
import pro.ibpm.mercury.logic.api.arch.IArchQuickTaskLogic;
import pro.ibpm.mercury.logic.api.attr.ICaseViewDefinitionLogic;
import pro.ibpm.mercury.logic.api.attr.IParamDefinitionLogic;
import pro.ibpm.mercury.logic.api.attr.ITypeCaseLogic;
import pro.ibpm.mercury.logic.api.attr.ITypeCodeLogic;
import pro.ibpm.mercury.logic.api.attr.ITypeParamActionLogic;
import pro.ibpm.mercury.logic.api.attr.ITypeParamLogic;
import pro.ibpm.mercury.logic.api.attr.ITypeParamRoleLogic;
import pro.ibpm.mercury.logic.api.data.ICase2CaseLogic;
import pro.ibpm.mercury.logic.api.data.ICaseDocumentLogic;
import pro.ibpm.mercury.logic.api.data.ICaseHistoryStreamLogic;
import pro.ibpm.mercury.logic.api.data.ICaseHistoryTraceLogic;
import pro.ibpm.mercury.logic.api.data.ICaseLogic;
import pro.ibpm.mercury.logic.api.data.ICommentLogic;
import pro.ibpm.mercury.logic.api.data.IGroupCase2ParticipantLogic;
import pro.ibpm.mercury.logic.api.data.IGroupCaseLogic;
import pro.ibpm.mercury.logic.api.data.IKtmNumberLogic;
import pro.ibpm.mercury.logic.api.data.ILoggerEventLogic;
import pro.ibpm.mercury.logic.api.data.IParticipantHistoryStreamLogic;
import pro.ibpm.mercury.logic.api.data.IParticipantHistoryTraceLogic;
import pro.ibpm.mercury.logic.api.data.IParticipantLogic;
import pro.ibpm.mercury.logic.api.data.IQuickTaskLogic;
import pro.ibpm.mercury.logic.api.data.IStore2TypeLogic;
import pro.ibpm.mercury.logic.api.data.IStoreHistoryStreamLogic;
import pro.ibpm.mercury.logic.api.data.IStoreLogic;
import pro.ibpm.mercury.logic.api.data.ISystemUserLogic;
import pro.ibpm.mercury.logic.api.dict.IInitStatusLogic;
import pro.ibpm.mercury.logic.api.dict.IRoleLogic;
import pro.ibpm.mercury.logic.api.dict.ISourceLogic;
import pro.ibpm.mercury.logic.api.dict.ITypeKindLogic;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.business.bpm.IBpmBPDInstanceBufferSecretaryManagerAction;
import pro.ibpm.mercury.ws.server.api.actions.business.bpm.IBpmTaskBufferSecretaryManagerAction;
import pro.ibpm.mercury.ws.server.api.actions.business.bpm.IBpmTaskNarrativeBufferSecretaryManagerAction;
import pro.ibpm.mercury.xml.w3c.dom.XMLUtils;

/**
 * 
 * WsClientCXFTest
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("spring-test.xml")
public class WsClientCXFTest extends AWsClientCXFAnyTest {

	private static final long CASE_ID = 2004L;
	private final Context context = new Context("ttesteusz", "WsClientCXFTest", "001", "10", "1000");

	public WsClientCXFTest() {
		super(Boolean.parseBoolean(props.getProperty("test.skip.package." + WsClientCXFTest.class.getSimpleName())));
		context.setMaxResults(100);
	}

	@Test
	public void testNotyfication() throws MercuryException, Exception {

		final String methodName = "testNotyfication";
		final String beanName = "notyficationLogic";

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

	@Test
	public void testStore2TypeStats() throws MercuryException, Exception {

		final String methodName = "testStore2TypeStats";
		final String beanName = "store2TypeStatsBusiness";

		logger.info("Start testu... " + "\n************************************" + "\n*  SCENARIUSZ {}()  *"
				+ "\n************************************", new Object[] { methodName });

		String result = "OK";

		try {

			final IStore2TypeStatsBusiness business = (IStore2TypeStatsBusiness) applicationContext.getBean(beanName);
			assert business != null : "Nie znaleziono beana " + beanName;

			business.repairStats(context);

		} catch (Exception ex) {
			logger.error("ERROR : wyjatek", ex);
			result = "BAD";
			throw ex;
		}

		assert result.equals("OK") : "Test zakończył się porażką";
	}

	@Test
	public void testParticipant2TypeStats() throws MercuryException, Exception {

		final String methodName = "testParticipant2TypeStats";
		final String beanName = "participant2TypeStatsBusiness";

		logger.info("Start testu... " + "\n************************************" + "\n*  SCENARIUSZ {}()  *"
				+ "\n************************************", new Object[] { methodName });

		String result = "OK";

		try {

			final IParticipant2TypeStatsBusiness business = (IParticipant2TypeStatsBusiness) applicationContext
					.getBean(beanName);
			assert business != null : "Nie znaleziono beana " + beanName;

			business.repairStats(context);

		} catch (Exception ex) {
			logger.error("ERROR : wyjatek", ex);
			result = "BAD";
			throw ex;
		}

		assert result.equals("OK") : "Test zakończył się porażką";
	}

	@Test
	public void testCaseHistoryStreamBusiness() throws MercuryException, Exception {

		final String methodName = "testCaseHistoryStreamBusiness";
		final String beanName = "caseHistoryStreamBusiness";
		logger.info("Start testu... " + "\n************************************" + "\n*  SCENARIUSZ {}()  *"
				+ "\n************************************", new Object[] { methodName });
		String result = "OK";
		try {

			final ICaseHistoryStreamBusiness business = (ICaseHistoryStreamBusiness) applicationContext
					.getBean(beanName);
			assert business != null : "Nie znaleziono beana " + beanName;
			IPagedResult<MrcCaseHistoryStream, IPage> pagedResult = business.loadCaseHistoryStream(context, CASE_ID,
					/* isAsc */ true, /* page */ null);
			WsCaseStreamHistoryHelper.printResult2Log(logger, pagedResult.getResult());
		} catch (Exception ex) {
			logger.error("ERROR : wyjatek", ex);
			result = "BAD";
			throw ex;
		}
		assert result.equals("OK") : "Test zakończył się porażką";
	}

	@Test
	public void testCaseHistoryTraceBusiness() throws MercuryException, Exception {

		final String methodName = "testCaseHistoryTraceBusiness";
		String beanName = "caseHistoryTraceBusiness";
		logger.info("Start testu... " + "\n************************************" + "\n*  SCENARIUSZ {}()  *"
				+ "\n************************************", new Object[] { methodName });
		String result = "OK";
		try {

			final ICaseHistoryTraceBusiness business = (ICaseHistoryTraceBusiness) applicationContext.getBean(beanName);
			assert business != null : "Nie znaleziono beana " + beanName;
			MrcPagedResult pagedResult = business.findByCaseIdAllVersions(context, CASE_ID, /* isAsc */ true,
					/* page */ null);
			logger.info("-->testCaseHistoryTraceBusiness: resultSize: {}", ((MrcSimpleProperty) pagedResult
					.getPropertyValue(IMrcPagedResult.MrcPagedResultField.resultSize.name())).getValue());
			assertNotNull(pagedResult.getPropertyValue(IMrcPagedResult.MrcPagedResultField.resultSize.name()));
			Document xmlResult = ((ICaseHistoryTraceBusinessXML) business).findByCaseIdAllVersionsXML(context, CASE_ID,
					/* isAsc */ true, /* page */ null);
			logger.info("-->testCaseHistoryTraceBusiness: result: \n{}", XMLUtils.nodeToString(xmlResult));
			beanName = "caseBusiness";
			final ICaseBusiness caseBusiness = (ICaseBusiness) applicationContext.getBean(beanName);
			assert business != null : "Nie znaleziono beana " + beanName;
			MrcList list = caseBusiness.loadCaseHistoryTraces(context, CASE_ID, /* isAsc */ true);
			logger.info("-->testCaseHistoryTraceBusiness: caseBusiness.loadCaseHistoryTraces: \n{}",
					MrcObjectUtils.printMrcList("list", list, CaseBusiness.FIRST_ITERATION));
		} catch (Exception ex) {
			logger.error("ERROR : wyjatek", ex);
			result = "BAD";
			throw ex;
		}
		assert result.equals("OK") : "Test zakończył się porażką";
	}

	@Test
	public void testRole() throws MercuryException, Exception {
		new AnyTest<Role, String, IRoleLogic>("testRole", "roleLogic", null, null);
	}

	@Test
	public void testInitStatus() throws MercuryException, Exception {
		new AnyTest<InitStatus, String, IInitStatusLogic>("testInitStatus", "initStatusLogic", null, null);
	}

	@Test
	public void testTypeKind() throws MercuryException, Exception {

		new AnyTest<TypeKind, String, ITypeKindLogic>("testTypeKind", "typeKindLogic", null, null);
	}

	@Test
	public void testSource() throws MercuryException, Exception {

		new AnyTest<Source, String, ISourceLogic>("testSource", "sourceLogic", null, null);

	}

	@Test
	public void testCaseViewDefinition() throws MercuryException, Exception {

		Set<String> viewColumns;
		CaseViewDefinition created;
		Calendar dateFrom = Calendar.getInstance();
		dateFrom.set(Calendar.YEAR, 2000);
		Calendar dateTo = Calendar.getInstance();
		dateTo.set(Calendar.YEAR, 4000);
		String viewName;
		CaseViewDefinition founded;

		ICaseViewDefinitionLogic caseViewDefinitionLogic = (ICaseViewDefinitionLogic) applicationContext
				.getBean("caseViewDefinitionLogic");

		viewName = "Test" + Calendar.getInstance().getTimeInMillis();
		logger.info("\n************************************" + "\n*  BUDOWANIE WIDOKU {}..."
				+ "\n************************************", new Object[] { viewName });
		viewColumns = new HashSet<String>();
		viewColumns.add("NazwiskoKlienta");
		viewColumns.add("RodzajPolisy");

		created = caseViewDefinitionLogic.createView(context, viewName, viewColumns, dateFrom, dateTo,
				CredentialsMode.AND.name(), /* skipHeaderFields */ true, /* ignoreAlternateFields */ false,
				/* baseTypeCodes */ null);
		assertNotNull(created);

		founded = caseViewDefinitionLogic.find(context, viewName);
		assertNotNull(founded);
		assertNull(founded.getGeneratedVersion());
		assertNotNull(founded.getPublishVersion());

		founded = caseViewDefinitionLogic.generateView(context, viewName, /* force */ true);
		assertNotNull(founded);

		/* trzeba poczekać na realizację zadania utworzenia widoku */
		Thread.sleep(2000L);
		founded = caseViewDefinitionLogic.find(context, viewName);
		assertNotNull(founded);
		assertNotNull(founded.getGeneratedVersion());
		assertNotNull(founded.getPublishVersion());
		assertEquals(founded.getPublishVersion(), founded.getGeneratedVersion());

		// founded = caseViewDefinitionLogic.dropView(context, viewName);
		// assertNotNull(founded);
		// assertNull(founded.getGeneratedVersion());
		// assertNotNull(founded.getPublishVersion());

	}

	@Test
	public void testParamDefinition() throws MercuryException, Exception {

		new AnyTest<ParamDefinition, ParamDefinitionPK, IParamDefinitionLogic>("testParamDefinition",
				"paramDefinitionLogic", null, null);

		ParamDefinitionPK id;
		ParamDefinition pd;
		List<ParamDefinition> result;
		IParamDefinitionLogic l = (IParamDefinitionLogic) applicationContext.getBean("paramDefinitionLogic");

		id = new ParamDefinitionPK("Number", 1L);
		l.getValue(this.context, new ParamDefinition(id), "0.234");

		id = new ParamDefinitionPK();
		id.setDefinitionName("Number");
		pd = new ParamDefinition(id);
		pd.setIsLatestVersion(true);
		result = l.filter(context, pd);

		assertNotNull(result);
		assertTrue(result.size() > 0);

	}

	@Test
	public void testCaseSearch() throws MercuryException, Exception {

		String methodName = "testCaseSearch";

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
		Integer year;

		/*
		 * XXX Wyszukiwanie po atrybucie "NazwiskoKlienta": 'Nowak' po zakresie dat
		 * ważności typów.
		 */
		paramsMap.clear();
		paramsMap.put("NazwiskoKlienta", "Nowak");
		fromDate = Calendar.getInstance();
		fromDate.set(2012, 11, 1);
		toDate = Calendar.getInstance();
		toDate.set(2013, 0, 10);

		searchResult = caseLogic.search(context, paramsMap, CredentialsMode.AND, fromDate, toDate, defaultPage);
		caseList = searchResult.getResult();

		List<String> idS = new ArrayList<String>();
		for (Case caseObj : caseList) {
			idS.add(caseObj.getId().toString());
		}
		assertEquals("Niepoprawna ilosc elementow", 0, idS.size());

		/*
		 * XXX Wyszukiwanie po atrybucie "NazwiskoKlienta": 'Kowalski' dla typów z
		 * danego roku.
		 */
		paramsMap.clear();
		paramsMap.put("NazwiskoKlienta", "Kowalski");
		year = 2013;

		searchResult = caseLogic.search(context, paramsMap, CredentialsMode.AND, year, defaultPage);
		caseList = searchResult.getResult();

		idS = new ArrayList<String>();
		for (Case caseObj : caseList) {
			idS.add(caseObj.getId().toString());
		}
		assertTrue("Niepoprawne elementy na liscie " + idS, idS.contains("9"));
		assertTrue("Niepoprawne elementy na liscie " + idS, idS.contains("8"));
		assertTrue("Niepoprawne elementy na liscie " + idS, idS.contains("7"));
		assertTrue("Niepoprawne elementy na liscie " + idS, idS.contains("4"));
	}

	@Test
	public void testType2Type() throws MercuryException, Exception {

		new AnyTest<TypeCase, Long, ITypeCaseLogic>("testType2Type", "type2TypeLogic", null, null);

	}

	@Test
	public void testType2TypeWithLastVersion() throws MercuryException, Exception {

		new AnyTest<Type2TypeWithLastVersion, String, IType2TypeWithLastVersionBusiness>("testType2TypeWithLastVersion",
				"type2TypeWithLastVersionBusiness", null,
				new IAnyTest<Type2TypeWithLastVersion, String, IType2TypeWithLastVersionBusiness>() {

					public void run(final IType2TypeWithLastVersionBusiness logic, final Type2TypeWithLastVersion e)
							throws MercuryException, Exception {

						if (!haveType(e.getParent())) {
							assert false : "Brak Type2TypeWithLastVersion.parent.lastType";
						}
						if (!haveType(e.getChild())) {
							assert false : "Brak Type2TypeWithLastVersion.child.lastType";
						}

						final IPage page = new PageTransportable();
						TypeCase parent = new TypeCase();
						TypeCase child = null;
						parent.setCode(new TypeCode("POLISA1")); // POLISA1
																	// AGENT1
						final Type2TypeWithLastVersion type2Type = new Type2TypeWithLastVersion(parent, child);
						// symulacja mapera Type2Type2EntityMapper
						Context ctx = context.copy();
						ctx.setMaxResults(Integer.valueOf(page.getSize()));
						final IPagedResult<Type2TypeWithLastVersion, IPage> iPagedResult = logic.filterPaged(ctx,
								type2Type, page);
						logger.info("[{}] iPagedResult={} size={}",
								new Object[] { getName(), iPagedResult, iPagedResult.getResult().size() });

						assertFalse(iPagedResult.getResult().isEmpty());
						for (Type2TypeWithLastVersion r : iPagedResult.getResult()) {
							logger.debug("[{}] test after search type2type={}...",
									new Object[] { getName(), r.getId() });
							if (!haveType(r.getParent())) {
								assert false : "Brak Type2TypeWithLastVersion.parent.lastType";
							}
							if (!haveType(r.getChild())) {
								assert false : "Brak Type2TypeWithLastVersion.child.lastType";
							}
						}
					}

					private boolean haveType(final TypeCodeWithLastVersion e) {
						TypeCase eType = e.getTypeLastVersion();
						return ((eType != null) && (eType.getCode().getName() != null));
					}

				});

	}

	@Test
	public void testTypeCodeWithLastVersion() throws MercuryException, Exception {

		new AnyTest<TypeCodeWithLastVersion, String, ITypeCodeWithLastVersionBusiness>("testTypeCodeWithLastVersion",
				"typeCodeWithLastVersionBusiness", null,
				new IAnyTest<TypeCodeWithLastVersion, String, ITypeCodeWithLastVersionBusiness>() {

					public void run(final ITypeCodeWithLastVersionBusiness logic, final TypeCodeWithLastVersion e)
							throws MercuryException, Exception {

						if (!haveType(e)) {
							assert false : "Brak TypeCodeWithLastVersion.lastType";
						}

						final IPage page = new PageTransportable();
						Context ctx = context.copy();
						ctx.setMaxResults(Integer.valueOf(page.getSize()));
						final List<TypeCodeWithLastVersion> iResult = logic.findAll(ctx);
						logger.info("[{}] iResult size={}", new Object[] { getName(), iResult.size() });
						assertFalse(iResult.isEmpty());
						for (TypeCodeWithLastVersion r : iResult) {
							logger.debug("[{}] test after search typeCode={}...",
									new Object[] { getName(), r.getId() });
							if (!haveType(r)) {
								assert false : "Brak TypeCodeWithLastVersion.parent.lastType";
							}
						}

					}

					private boolean haveType(final TypeCodeWithLastVersion e) {
						TypeCase eType = e.getTypeLastVersion();
						return ((eType != null) && (eType.getCode().getName() != null));
					}

				});

	}

	@Test
	public void testStore2TypeWithLastVersion() throws MercuryException, Exception {

		new AnyTest<Store2TypeLastVersion, Store2TypePK, IStore2TypeLastVersionBusiness>(
				"testStore2TypeWithLastVersion", "store2TypeLastVersionBusiness", null,
				new IAnyTest<Store2TypeLastVersion, Store2TypePK, IStore2TypeLastVersionBusiness>() {

					public void run(final IStore2TypeLastVersionBusiness logic, final Store2TypeLastVersion e)
							throws MercuryException, Exception {

						if (!haveType(e)) {
							assert false : "Brak Store2TypeLastVersion.lastType";
						}
						final IPage page = new PageTransportable();
						TypeCase parent = new TypeCase();
						parent.setCode(new TypeCode("POLISA1"));
						final Store2TypeLastVersion store2Type = new Store2TypeLastVersion();
						store2Type.setTypeLastVersion(parent);
						// symulacja mapera Type2Type2EntityMapper
						Context ctx = context.copy();
						ctx.setMaxResults(Integer.valueOf(page.getSize()));
						final List<Store2TypeLastVersion> iResult = logic.filter(ctx, store2Type);
						logger.info("[{}] iResult size={}", new Object[] { getName(), iResult.size() });
						assertFalse(iResult.isEmpty());
						for (Store2TypeLastVersion r : iResult) {
							logger.debug("[{}] test after search store2Type={}...",
									new Object[] { getName(), r.getId() });
							if (!haveType(r)) {
								assert false : "Brak TypeCodeWithLastVersion.parent.lastType";
							}
						}
					}

					private boolean haveType(final Store2TypeLastVersion e) {
						TypeCase eType = e.getTypeLastVersion();
						return ((eType != null) && (eType.getCode().getName() != null));
					}

				});

	}

	@Test
	public void testTypeCase() throws MercuryException, Exception {

		new AnyTest<TypeCase, Long, ITypeCaseLogic>("testTypeCase", "typeCaseLogic", null, null);

	}

	@Test
	public void testTypeParamAction() throws MercuryException, Exception {

		new AnyTest<TypeParamAction, Long, ITypeParamActionLogic>("testTypeParamAction", "typeParamActionLogic", null,
				null);

	}

	@Test
	public void testTypeParam() throws MercuryException, Exception {
		new AnyTest<TypeParam, TypeParamPK, ITypeParamLogic>("testTypeParam", "typeParamLogic", null, null);
	}

	@Test
	public void testTypeParamRole() throws MercuryException, Exception {
		new AnyTest<TypeParamRole, Long, ITypeParamRoleLogic>("testTypeParamRole", "typeParamRoleLogic", null, null);
	}

	@Test
	public void testTypeCode() throws MercuryException, Exception {
		new AnyTest<TypeCode, String, ITypeCodeLogic>("testTypeCode", "typeCodeLogic", null, null);

		context.setMaxResults(1000);
		ITypeCodeLogic typeCodeLogic = (ITypeCodeLogic) applicationContext.getBean("typeCodeLogic");
		List<TypeCode> all = typeCodeLogic.findAll(context);
		for (TypeCode a : all) {
			logger.info("--> testTypeCode: typeCode={}", new Object[] { a.getName() });
		}

	}

	@Test
	public void testCase2Case() throws MercuryException, Exception {
		new AnyTest<Case2Case, String, ICase2CaseLogic>("testCase2Case", "case2CaseLogic", null,
				new IAnyTest<Case2Case, String, ICase2CaseLogic>() {
					public void run(ICase2CaseLogic logic, Case2Case e) throws MercuryException, Exception {
						Case2Case e4Update = new Case2Case();
						e.copyTo(e4Update);
						final String comment = "&<ampersand>&";
						e4Update.setModifyComment(comment);
						context.setComment(comment);
						e = logic.find(context, e.getId());
						assert e != null : "Nie znaleziono case2Case ponownie";

						final ICase2CaseBusiness busines = (ICase2CaseBusiness) applicationContext
								.getBean("case2CaseBusiness");
						IPagedResult<Case2CaseRelationship, IPage> pagedResult = busines.getAllByPathStartsWith(context,
								"%", null, new PageTransportable());
						Collection<Case2CaseRelationship> list = pagedResult.getResult();
						assert (list != null) && (!list.isEmpty()) : "Nie znaleziono case2Cases";

						List<Case2Case> bag = logic.getAllByPathStartsWith(context, "%", null);
						assert (bag != null) && (!bag.isEmpty()) : "Nie znaleziono case2Cases";
						final List<String> pks = new ArrayList<String>();
						for (Case2Case t : bag) {
							pks.add(t.getId());
							assertNotNull(t.getChild());
							assertNotNull(t.getParent());
							assertNotNull(t.getChild().getRootVersionId());
							assertNotNull(t.getParent().getRootVersionId());
							t.setModifyComment(comment);
						}
						bag = logic.findByIdList(context, pks);
						assert (bag != null) && (!bag.isEmpty()) : "Nie znaleziono ponownie case2Cases";
						for (Case2Case t : bag) {
							assertNotNull(t.getChild());
							assertNotNull(t.getParent());
							assertNotNull(t.getChild().getRootVersionId());
							assertNotNull(t.getParent().getRootVersionId());
						}

						Context context = MercuryConfig.createDefaultContext();
						context.setMaxResults(1);

						Case2Case filter = new Case2Case();
						filter.setDepth(2);
						Case filterParent = new Case(1L);
						filter.setParent(filterParent);
						final IPagedResult<Case2Case, IPage> iPagedResult = logic.filterPaged(context, filter,
								new PageTransportable());
						assertEquals(Integer.valueOf(1), Integer.valueOf(iPagedResult.getResult().size()));
						logger.info("[{}] iPagedResult={} size={}",
								new Object[] { getName(), iPagedResult, iPagedResult.getResult().size() });
					}
				});
	}

	@Test
	public void testCase() throws MercuryException, Exception {
		new AnyTest<Case, Long, ICaseLogic>("testCase", "caseLogic", null, new IAnyTest<Case, Long, ICaseLogic>() {
			@SuppressWarnings({ "deprecation" })
			public void run(ICaseLogic logic, Case e) throws MercuryException, Exception {
				final String text = "&ampersand&";

				final ICaseDocumentLogic cdl = (ICaseDocumentLogic) applicationContext.getBean("caseDocumentLogic");
				CaseDocument cd = new CaseDocument();
				final String groupingCode = "<ampersand>/" + text;
				cd.setId(new CaseDocumentPK(e, 99912L, 99L));
				String comment = "[" + Calendar.getInstance().getTimeInMillis()
						+ "] <ampersand> Niepotrzebny zapis w teście";
				context.setComment(comment);
				context.setEager4omdBuilder("false");

				cdl.remove(context, cd);
				cd.setGroupingCode(groupingCode);
				cd.setIsLatestVersion(false);
				cd.setInitStatus(((IInitStatusLogic) applicationContext.getBean("initStatusLogic")).findFirst(context));
				cd.setType(((ITypeCaseLogic) applicationContext.getBean("typeCaseLogic")).findFirst(context));
				cd.setSource(((ISourceLogic) applicationContext.getBean("sourceLogic")).findFirst(context));
				cdl.insert(context, cd);
				cd = cdl.find(context, cd.getId());
				assert cd != null : "Nie znaleziono caseDocument";
				assert groupingCode.equals(cd.getGroupingCode()) : "Nie zmieniono caseDocument.groupingCode";
				assert comment.equals(cd.getModifyComment()) : "Nie zmieniono caseDocument.comment";

				Case e4Update = new Case();
				e.copyTo(e4Update);
				String parameterNewValue = "" + Calendar.getInstance().getTimeInMillis() + ": " + text;
				e4Update.setParameter1(parameterNewValue);
				comment = "[" + Calendar.getInstance().getTimeInMillis() + "] Case: Niepotrzebny zapis w teście";
				context.setComment(comment);
				logic.update(context, e4Update);
				e = logic.find(context, e.getId());
				assert e != null : "Nie znaleziono case ponownie";
				assert parameterNewValue.equals(e.getParameter(1)) : "Nie zmieniono case.parameter1";
				assert comment.equals(e.getModifyComment()) : "Nie zmieniono case.comment";
				assert groupingCode.equals(e.getDocuments().iterator().next()
						.getGroupingCode()) : "Nie znaleziono case.documents.first.groupingCode";
				logger.info("text={} comment={} documents.first.groupingCode={}", new Object[] { e.getParameter(5),
						e.getModifyComment(), e.getDocuments().iterator().next().getGroupingCode() });

				final Long id = e.getId();
				List<Long> pks = new ArrayList<Long>();
				pks.add(id);

				List<Case> bag = logic.findByIdList(context, pks);
				assert (bag != null) && (!bag.isEmpty()) : "Nie znaleziono cases";
				for (Case t : bag) {
					t.setModifyComment(comment);
				}
				bag = logic.updateList(context, bag);
				assert (bag != null) && (!bag.isEmpty()) : "Nie zmieniono cases";
				pks = new ArrayList<Long>();
				int i = 0;
				for (Case t : bag) {
					pks.add(t.getId());
					i++;
					assert comment.equals(t.getModifyComment()) : "Nie zmieniono case[" + i + "].comment";
					logger.info("case.comment[{}]={}", new Object[] { i, e.getModifyComment() });
				}
				bag = logic.findByIdList(context, pks);
				assert (bag != null) && (!bag.isEmpty()) : "Nie znaleziono ponownie cases";
				i = 0;
				for (Case t : bag) {
					i++;
					assert comment.equals(t.getModifyComment()) : "Inny case[" + i + "].comment";
					logger.info("case.comment[{}]={}", new Object[] { i, e.getModifyComment() });
				}

			}
		});
	}

	public void testCaseDocument() throws MercuryException, Exception {
		new AnyTest<CaseDocument, CaseDocumentPK, ICaseDocumentLogic>("testCaseDocument", "caseDocumentLogic",
				new IAnyBeforeTest<CaseDocument, CaseDocumentPK, ICaseDocumentLogic>() {
					@Override
					public void run(ICaseDocumentLogic logic) throws MercuryException, Exception {
						final CaseDocumentPK pk = new CaseDocumentPK();
						pk.setObjectId("10012");
						pk.setVersionSeriesId("1");
						final CaseDocument cd = new CaseDocument();
						cd.setId(pk);
						Context context = MercuryConfig.createDefaultContext();
						context.setMaxResults(1);
						final IPagedResult<CaseDocument, IPage> iPagedResult = logic.filterPaged(context, cd,
								new PageTransportable());

						logger.info("iPagedResult={} size={}",
								new Object[] { iPagedResult, iPagedResult.getResult().size() });
					}
				}, new IAnyTest<CaseDocument, CaseDocumentPK, ICaseDocumentLogic>() {
					@SuppressWarnings("unused")
					@Override
					public void run(ICaseDocumentLogic logic, CaseDocument e) throws MercuryException, Exception {

						logger.info("e.id={} e.versionLabel={} e.comment={} e.author={}" + " e.id.versionSeries={}",
								new Object[] { e.getId(), e.getVersionLabel(), e.getModifyComment(), e.getAuthor(),
										e.getId().getVersionSeriesId() });
						List<CaseDocument> bag = logic.findAllByVersionSeries(context, e);
						final CaseDocument e4Update = new CaseDocument();
						e.copyTo(e4Update);
						final String text = "[" + Calendar.getInstance().getTimeInMillis() + "] &ampersand&";
						String comment = "[" + Calendar.getInstance().getTimeInMillis()
								+ "] <ampersand> Niepotrzebny zapis w teście";
						e4Update.setVersionLabel(text);
						context.setComment(comment);
						e = logic.update(context, e4Update);
						assert comment.equals(e.getModifyComment()) : "Nie zmieniono caseDocument.comment";
						assert text.equals(e.getVersionLabel()) : "Nie zmieniono caseDocument.versionLabel";
						logger.info("e4Update.id={} e.id={} e.versionLabel={} e.comment={} e.author={}",
								new Object[] { e4Update.getId(), e.getId(), e.getVersionLabel(), e.getModifyComment(),
										e.getAuthor() });
						e = logic.find(context, e.getId());
						assert e != null : "Nie znaleziono caseDocument ponownie";
						assert e.getId().equals(e4Update.getId()) : "Zmienił się caseDocument.pk";

						logger.info("e.id={} e.versionLabel={} e.comment={} e.author={}",
								new Object[] { e.getId(), e.getVersionLabel(), e.getModifyComment(), e.getAuthor() });

						CaseDocument e4Insert = new CaseDocument();
						e.copyTo(e4Insert);
						final CaseDocumentPK pk = e4Insert.getId();
						pk.setObjectId("99911");
						e4Insert.setId(pk);
						e4Insert.setVersionLabel(text);
						context.setComment(comment);
						logic.remove(context, e4Insert);
						logger.info("before insert: user={} comment={}",
								new Object[] { context.getUserName(), comment });

						comment = "[" + Calendar.getInstance().getTimeInMillis() + "] <ampersand> Nowy zapis w teście";
						context.setComment(comment);
						e = logic.insert(context, e4Insert);

						logger.info("e4Insert.id={} e.id={} e.versionLabel={} e.comment={} e.author={}",
								new Object[] { e4Insert.getId(), e.getId(), e.getVersionLabel(), e.getModifyComment(),
										e.getAuthor() });
						e = logic.find(context, e.getId());
						assert e != null : "Nie znaleziono caseDocument ponownie";
						assert e.getId().equals(e4Insert.getId()) : "Nie znaleziono się caseDocument.pk ponownie";

						logger.info("e.id={} e.versionLabel={} e.comment={} e.author={}",
								new Object[] { e.getId(), e.getVersionLabel(), e.getModifyComment(), e.getAuthor() });

						assert comment.equals(e.getModifyComment()) : "Nie zmieniono caseDocument.comment3";
						assert text.equals(e.getVersionLabel()) : "Nie zmieniono caseDocument.versionLabel3";
						bag = logic.findAllVersionsByCaseId(context, e.getId().getCaseId());
						assert (bag != null) && (!bag.isEmpty()) : "Nie znaleziono caseDocuments";
						List<CaseDocumentPK> pks = new ArrayList<CaseDocumentPK>();
						int i = 0;
						for (CaseDocument t : bag) {
							pks.add(t.getId());
						}
						bag = logic.findByIdList(context, pks);
						assert (bag != null) && (!bag.isEmpty()) : "Nie znaleziono ponownie caseDocuments";
						i = 0;
						for (CaseDocument t : bag) {
							i++;
							logger.info("caseDocument.comment[{}]={}", new Object[] { i, e.getModifyComment() });
						}
					}
				});
	}

	@Test
	public void testCaseHistoryStream() throws MercuryException, Exception {
		Set<String> skipMethods = new HashSet<String>();
		skipMethods.add(AnyTest.FIND_ALL_METHOD);
		new AnyTest<CaseHistoryStream, Long, ICaseHistoryStreamLogic>("testCaseHistoryStream", "caseHistoryStreamLogic",
				null, null, skipMethods);
	}

	@Test
	public void testCaseHistoryTrace() throws MercuryException, Exception {
		new AnyTest<CaseHistoryTrace, Long, ICaseHistoryTraceLogic>("testCaseHistoryTrace", "caseHistoryTraceLogic",
				null, null);
	}

	@Test
	public void testKtmNumber() throws MercuryException, Exception {
		new AnyTest<KtmNumber, Long, IKtmNumberLogic>("testKtmNumber", "ktmNumberLogic", null, null);
	}

	@Test
	public void testStoreHistoryStream() throws MercuryException, Exception {
		Set<String> skipMethods = new HashSet<String>();
		skipMethods.add(AnyTest.FIND_ALL_METHOD);
		new AnyTest<StoreHistoryStream, Long, IStoreHistoryStreamLogic>("testStoreHistoryStream",
				"storeHistoryStreamLogic", null, null, skipMethods);
	}

	@Test
	public void testParticipantHistoryStream() throws MercuryException, Exception {
		Set<String> skipMethods = new HashSet<String>();
		skipMethods.add(AnyTest.FIND_ALL_METHOD);
		new AnyTest<ParticipantHistoryStream, Long, IParticipantHistoryStreamLogic>("testParticipantHistoryStream",
				"participantHistoryStreamLogic", null, null, skipMethods);
	}

	@Test
	public void testParticipantHistoryTrace() throws MercuryException, Exception {
		new AnyTest<ParticipantHistoryTrace, Long, IParticipantHistoryTraceLogic>("testParticipantHistoryTrace",
				"participantHistoryTraceLogic", null, null);
	}

	@Test
	public void testLoggerEvent() throws MercuryException, Exception {
		new AnyTest<LoggerEvent, Long, ILoggerEventLogic>("testLoggerEvent", "loggerEventLogic", null, null);
	}

	@Test
	public void testParticipant() throws MercuryException, Exception {
		new AnyTest<Participant, Long, IParticipantLogic>("testParticipant", "participantLogic", null, null);
	}

	@Test
	public void testSystemUser() throws MercuryException, Exception {
		new AnyTest<SystemUser, Long, ISystemUserLogic>("testSystemUser", "systemUserLogic", null, null);
	}

	@Test
	public void testStore() throws MercuryException, Exception {
		new AnyTest<Store, Long, IStoreLogic>("testStore", "storeLogic", null, null);
	}

	@Test
	public void testStore2Type() throws MercuryException, Exception {
		new AnyTest<Store2Type, Store2TypePK, IStore2TypeLogic>("testStore2Type", "store2TypeLogic", null,
				new IAnyTest<Store2Type, Store2TypePK, IStore2TypeLogic>() {
					public void run(final IStore2TypeLogic logic, final Store2Type e)
							throws MercuryException, Exception {

						boolean notHaveType = false;
						if (!haveType(e)) {
							notHaveType = true;
						} else {
							for (final Store2Type s2t : logic.findAll(context)) {
								if (!haveType(s2t)) {
									notHaveType = true;
									break;
								}
							}
						}
						assert !notHaveType : "Brak Store2Type.id.type i Store2Type.id.store";
					}

					private boolean haveType(final Store2Type e) {
						final TypeCode eTypeCode = e.getId().getTypeCode();
						final Store eStore = e.getId().getStore();
						return ((eTypeCode != null) && (eTypeCode.getName() != null && (eStore.getId() != null)));
					}

				});
	}

	@Test
	public void testComment() throws MercuryException, Exception {
		new AnyTest<Comment, Long, ICommentLogic>("testComment", "commentLogic", null, null);
	}

	@Test
	public void testQuickTask() throws MercuryException, Exception {
		new AnyTest<QuickTask, Long, IQuickTaskLogic>("testQuickTask", "quickTaskLogic", null, null);
	}

	@Test
	public void testArchCaseDocument() throws MercuryException, Exception {
		new AnyTest<ArchCaseDocument, ArchCaseDocumentPK, IArchCaseDocumentLogic>("testArchCaseDocument",
				"archCaseDocumentLogic", null, null);
	}

	@Test
	public void testArchCaseHistoryStream() throws MercuryException, Exception {
		new AnyTest<ArchCaseHistoryStream, Long, IArchCaseHistoryStreamLogic>("testArchCaseHistoryStream",
				"archCaseHistoryStreamLogic", null, null);
	}

	@Test
	public void testArchCaseHistoryTrace() throws MercuryException, Exception {
		new AnyTest<ArchCaseHistoryTrace, Long, IArchCaseHistoryTraceLogic>("testArchCaseHistoryTrace",
				"archCaseHistoryTraceLogic", null, null);
	}

	@Test
	public void testArchCase() throws MercuryException, Exception {
		new AnyTest<ArchCase, Long, IArchCaseLogic>("testArchCase", "archCaseLogic", null, null);
	}

	@Test
	@Ignore
	public void testArchComment() throws MercuryException, Exception {
		new AnyTest<ArchComment, Long, IArchCommentLogic>("testArchComment", "archCommentLogic", null, null);
	}

	@Test
	public void testArchGropCaseHistoryStream() throws MercuryException, Exception {
		new AnyTest<ArchGroupCaseHistoryStream, Long, IArchGroupCaseHistoryStreamLogic>(
				"testArchGroupCaseHistoryStream", "archGroupCaseHistoryStreamLogic", null, null);
	}

	@Test
	public void testArchKtmNumber() throws MercuryException, Exception {
		new AnyTest<ArchKtmNumber, Long, IArchKtmNumberLogic>("testArchKtmNumber", "archKtmNumberLogic", null, null);
	}

	@Test
	public void testArchQuickTask() throws MercuryException, Exception {
		new AnyTest<ArchQuickTask, Long, IArchQuickTaskLogic>("testArchQuickTask", "archQuickTaskLogic", null, null);
	}

	@Test
	public void testArchGroupCase2Participant() throws MercuryException, Exception {
		new AnyTest<ArchGroupCase2Participant, ArchGroupCase2ParticipantPK, IArchGroupCase2ParticipantLogic>(
				"testArchGroupCase2Participant", "archGroupCase2ParticipantLogic", null, null);
	}

	@Test
	public void testGroupCase2Participant() throws MercuryException, Exception {
		new AnyTest<GroupCase2Participant, GroupCase2ParticipantPK, IGroupCase2ParticipantLogic>(
				"testGroupCase2Participant", "groupCase2ParticipantLogic", null, null);
	}

	@Test
	public void testArchGroupCase() throws MercuryException, Exception {
		new AnyTest<ArchGroupCase, Long, IArchGroupCaseLogic>("testArchGroupCase", "archGroupCaseLogic", null, null);
	}

	@Test
	public void testGroupCase() throws MercuryException, Exception {
		new AnyTest<GroupCase, Long, IGroupCaseLogic>("testGroupCase", "groupCaseLogic", null, null);
	}

	@Test
	public void testBpmBPDInstanceBufferSecretaryManager() throws MercuryException, Exception {
		new SecretaryTest<IBpmBPDInstanceBufferSecretaryManagerAction>("testBpmBPDInstanceBufferSecretaryManager",
				"bpmBPDInstanceBufferSecretaryManager", null);
	}

	@Test
	public void testBpmTaskBufferSecretaryManager() throws MercuryException, Exception {
		new SecretaryTest<IBpmTaskBufferSecretaryManagerAction>("testBpmTaskBufferSecretaryManager",
				"bpmTaskBufferSecretaryManager", null);
	}

	@Test
	public void testBpmTaskNarrativeBufferSecretaryManager() throws MercuryException, Exception {
		new SecretaryTest<IBpmTaskNarrativeBufferSecretaryManagerAction>("testBpmTaskNarrativeBufferSecretaryManager",
				"bpmTaskNarrativeBufferSecretaryManager", null);
	}

	@Test
	public void testCaseBusiness() throws MercuryException, Exception {

		final String methodName = "testCaseBusiness";
		final String beanName = "caseBusiness";

		logger.info("Start testu... " + "\n************************************" + "\n*  SCENARIUSZ {}()  *"
				+ "\n************************************", new Object[] { methodName });

		String result = "OK";

		try {

			final ICaseBusiness business = (ICaseBusiness) applicationContext.getBean(beanName);
			assert business != null : "Nie znaleziono beana " + beanName;

			final MrcObject first = business.findFirst(context);
			assert first != null : "Nie znaleziono pierwszej encji";

			logger.info("\nMrcObject {}:{}", new Object[] { ((IMrcObject) first).getMrcClassName(),
					MrcObjectUtils.printMrcObject((IMrcObject) first, CaseBusiness.FIRST_ITERATION).toString() });

			String doc = "<dto type=\"TestRole\">" + " <mrcCaseHeader type=\"TestRole\" version=\"1\">"
					+ "    <caseId type=\"Integer\">2004</caseId>"
					+ "    <bpmProcessId type=\"Integer\">1213</bpmProcessId>" + "    <inventoryCode type=\"String\"/>"
					+ "    <groupId type=\"Integer\">7018</groupId>"
					+ "    <typeCode type=\"String\">TestRole</typeCode>" + "    <endDate type=\"Date\"/>"
					+ "    <dueDate type=\"Date\"/>" + "    <status type=\"String\">A</status>"
					+ "    <previousVersionId type=\"Integer\"/>"
					+ "    <priceValue type=\"Decimal\">2000.0</priceValue>"
					+ "    <storeCount type=\"Integer\">1</storeCount>" + "    <storeId type=\"Integer\">7016</storeId>"
					+ "    <createDate isEncoded=\"false\" type=\"Date\">2017/03/04 01:00:40.552 CET</createDate>"
					+ "    <createdBy type=\"String\">ttesteusz</createdBy>"
					+ "    <lastModifyDate isEncoded=\"false\" type=\"Date\">2017/03/04 01:00:40.552 CET</lastModifyDate>"
					+ "    <lastModifiedBy type=\"String\">ttesteusz</lastModifiedBy>"
					+ "    <modifyComment type=\"String\">WsServerAbstract</modifyComment>"
					+ "    <createdByRoleName type=\"String\"/>" + "    <lastModifiedByRoleName type=\"String\"/>"
					+ "    <subCaseReferenceId type=\"Integer\"/>"
					+ "    <className type=\"String\">TestRole</className>"
					+ "    <objectID type=\"String\">5a2eed95-5975-42ff-8c4a-1527f38266fb</objectID>"
					+ "    <rootVersionContextID type=\"String\">2064.895ead8d-6fab-4eb8-b54b-b94cd07bb548T</rootVersionContextID>"
					+ "    <version type=\"String\">1</version>" + "    <dirty type=\"Boolean\">true</dirty>"
					+ "    <pkPropertyName type=\"String\">name</pkPropertyName>" + " </mrcCaseHeader>"
					+ " <name id=\"1\" position=\"1\" type=\"String\" version=\"1\"><![CDATA[TestRole2]]></name>"
					+ " <priv id=\"2\" position=\"2\" type=\"String\" version=\"1\">RW</priv>"
					+ " <priv id=\"3\" position=\"3\" type=\"String\" version=\"1\">RO</priv>"
					+ " <users id=\"4\" position=\"4\" type=\"String[]\" version=\"1\">" + "    <item>slawas</item>"
					+ "    <item>ttesteusz</item>" + "    <item>nowosz</item>" + " </users>" + "</dto>  ";
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new InputSource(new ByteArrayInputStream(doc.getBytes("UTF-8"))));
			Document returnDoc = ((ICaseBusinessXML) business).saveXML(context, document.getDocumentElement(),
					/* forceAddStore2Type */ true);
			String nodeStr = MrcDataUtils.nodeToString(returnDoc);
			logger.info("ReturnedDocument: \n{}", nodeStr);

		} catch (Exception ex) {
			logger.error("ERROR : wyjatek", ex);
			result = "BAD";
			throw ex;
		}

		assert result.equals("OK") : "Test zakończył się porażką";

	}

	@Test
	public void testLoadBigMockData() throws MercuryException, Exception {

		final String methodName = "testLoadBigMockData";
		final String beanName = "caseLogic";

		if (skipPackageTest || Boolean.parseBoolean(props.getProperty("test.skip.method." + methodName))) {
			return;
		}

		logger.info("Start testu... " + "\n************************************" + "\n*  SCENARIUSZ {}()  *"
				+ "\n************************************", new Object[] { methodName });

		String result = "OK";

		try {

			final ICaseLogic cL = (ICaseLogic) applicationContext.getBean(beanName);
			assert cL != null : "Nie znaleziono beana " + beanName;

			TCaseMockProvider p = new TCaseMockProvider(MockType.bigData, "/pro/ibpm/mercury/mock/data/tCase_1000.csv");
			SortedMap<Object, Case> mocks = p.loadFromFile();
			for (Case c : mocks.values()) {
				c.setId(null);
				Case inserted = cL.insert(context, c, true);
				logger.debug("inserted case: {}", new Object[] { inserted.getId() });
			}

		} catch (Exception ex) {
			logger.error("ERROR : wyjatek", ex);
			result = "BAD";
			throw ex;
		}

		assert result.equals("OK") : "Test zakończył się porażką";

	}

	interface ISecretaryTest {
		void run(SecretaryManager secretary) throws MercuryException, Exception;
	}

	class SecretaryTest<L> {
		SecretaryTest(final String methodName, final String beanName, final ISecretaryTest test)
				throws MercuryException, Exception {

			if (skipPackageTest || Boolean.parseBoolean(props.getProperty("test.skip.method." + methodName))) {
				return;
			}

			logger.info("Start testu... " + "\n************************************" + "\n*  SCENARIUSZ {}()  *"
					+ "\n************************************", new Object[] { methodName });

			String result = "OK";

			try {

				final SecretaryManager secretary = (SecretaryManager) applicationContext.getBean(beanName);
				assert secretary != null : "Nie znaleziono beana " + beanName;

				final boolean isWorking = secretary.isWorking();
				assert isWorking == false : "Nieoczekiwanie działa sekretarz";

				secretary.wakeup();

				if (test != null) {
					test.run(secretary);
				}

			} catch (Exception ex) {
				logger.error("ERROR : wyjatek", ex);
				result = "BAD";
				throw ex;
			}

			assert result.equals("OK") : "Test zakończył się porażką";
		}
	}

	interface IAnyTest<E extends MEntity & MIdModifier<Pk>, Pk, L extends MDictLogic<E, Pk>> {
		void run(L logic, E e) throws MercuryException, Exception;
	}

	interface IAnyBeforeTest<E extends MEntity & MIdModifier<Pk>, Pk, L extends MDictLogic<E, Pk>> {
		void run(L logic) throws MercuryException, Exception;
	}

	/**
	 * 
	 * AnyTest - uruchomienie scenariusza testowego, który testuje następujące
	 * metody usług:
	 * <ul>
	 * <li>findFirst</li>
	 * <li>find (po kluczu głównym)</li>
	 * <li>findByIdList</li>
	 * <li>findAll</li>
	 * </ul>
	 *
	 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
	 * @version $Revision: 1.1 $
	 *
	 * @param <E>
	 * @param <Pk>
	 * @param <L>
	 */
	@SuppressWarnings({ "unchecked" })
	class AnyTest<E extends MIdModifier<Pk> & ICopyable<E> & MEntity, Pk, L extends MDictLogic<E, Pk>> {

		/** nazwa reprezentujące testowaną metodę 'findFirst' */
		public static final String FIND_FIRST_METHOD = "findFirst";
		/** nazwa reprezentujące testowaną metodę 'find' */
		public static final String FIND_METHOD = "find";
		/** nazwa reprezentujące testowaną metodę 'findByIdList' */
		public static final String FIND_BY_ID_LIST_METHOD = "findByIdList";
		/** nazwa reprezentujące testowaną metodę 'findAll' */
		public static final String FIND_ALL_METHOD = "findAll";

		/**
		 * Konstruktor tworzący scenariusz testowania wszystkich wskazanych metod.
		 * 
		 * @param methodName
		 *            nazwa metody uruchamiająca test
		 * @param beanName
		 *            nazwa bean'a spring'owego z usługą
		 * @param beforeTest
		 *            operacje wykonywane przed testem
		 * @param test
		 *            dodatkowy test uzupełniający scenariusz testowy
		 * @throws MercuryException
		 * @throws Exception
		 */
		AnyTest(final String methodName, final String beanName, final IAnyBeforeTest<E, Pk, L> beforeTest,
				final IAnyTest<E, Pk, L> test) throws MercuryException, Exception {
			this(methodName, beanName, beforeTest, test, /* skipMethods */ null);
		}

		/**
		 * 
		 * Konstruktor tworzący scenariusz testowania wskazanych metod, z możliwością
		 * pominięcia jednej z nich, np. w przypadku gdy metoda nie jest wspierana przez
		 * usługę.
		 * 
		 * @param methodName
		 *            nazwa metody uruchamiająca test
		 * @param beanName
		 *            nazwa bean'a spring'owego z usługą
		 * @param beforeTest
		 *            operacje wykonywane przed testem
		 * @param test
		 *            dodatkowy test uzupełniający scenariusz testowy
		 * @param skipMethods
		 *            lista nazw metod, które mają być pominięte w scenariuszu. Możliwe
		 *            do pominięcia metody to 'findByIdList' oraz 'findAll'
		 * @throws MercuryException
		 * @throws Exception
		 */
		AnyTest(final String methodName, final String beanName, final IAnyBeforeTest<E, Pk, L> beforeTest,
				final IAnyTest<E, Pk, L> test, Set<String> skipMethods) throws MercuryException, Exception {

			if (skipPackageTest || Boolean.parseBoolean(props.getProperty("test.skip.method." + methodName))) {
				return;
			}

			logger.info("Start testu... " + "\n************************************" + "\n*  SCENARIUSZ {}()  *"
					+ "\n************************************", new Object[] { methodName });

			String result = "OK";
			boolean skip;
			try {

				final L logic = (L) applicationContext.getBean(beanName);
				assert logic != null : "Nie znaleziono beana " + beanName;

				if (beforeTest != null) {
					beforeTest.run(logic);
				}

				final E first = logic.findFirst(context);
				assert first != null : "Nie znaleziono pierwszej encji";

				if (first instanceof MEntityToString) {
					// FIXME System.out - usunąć
					System.out.println("znaleziony: " + ((MEntityToString) first).toString(null));
				}

				final Pk pk = getPk(first);
				assert pk != null : "Znaleziona encja nie ma klucza głównego";
				logger.info("beanName:{} pk={}", new Object[] { beanName, pk });

				final E byPk = logic.find(context, pk);
				assert byPk != null : "Nie znaleziono encji wg pk";
				assert getPk(first).equals(getPk(byPk)) : "Znaleziona encja wg pk jest inna";

				skip = skipMethods != null && skipMethods.contains(FIND_BY_ID_LIST_METHOD);
				if (!skip && (logic instanceof MDataLogic) && !(logic instanceof MCatalogLogic)) {
					List<Pk> pkBag = new ArrayList<Pk>();
					pkBag.add(pk);
					final MDataLogic<E, Pk> dataLogic = (MDataLogic<E, Pk>) logic;
					final List<E> byPkBag = dataLogic.findByIdList(context, pkBag);
					assert byPkBag != null : "Nie znaleziono encji wg listy pk";
					assert getPk((E) byPkBag.get(0)).equals(getPk(byPk)) : "Znaleziona encja wg listy pk jest inna";

				}

				skip = skipMethods != null && skipMethods.contains(FIND_ALL_METHOD);
				if (!skip && !(logic instanceof MBigDataLogic)) {
					final List<E> bag = logic.findAll(context);
					assert (bag != null) && (!bag.isEmpty()) : "Nie znaleziono wszystkich encji";
				}
				if (test != null) {
					test.run(logic, byPk);
				}

			} catch (Exception ex) {
				logger.error("ERROR : wyjatek", ex);
				result = "BAD";
				throw ex;
			}

			assert result.equals("OK") : "Test zakończył się porażką";
		}

		private Pk getPk(final E e) {
			if (e instanceof MIdModifier) {
				return (Pk) ((MIdModifier<Pk>) e).getId();
			}
			if (e instanceof NameValuePair) {
				return (Pk) ((NameValuePair) e).getName();
			}
			assert false : "Nieznana implementacja encji";
			return null;
		}
	}
}
