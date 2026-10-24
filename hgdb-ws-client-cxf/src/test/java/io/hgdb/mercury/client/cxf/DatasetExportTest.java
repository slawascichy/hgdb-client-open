package io.hgdb.mercury.client.cxf;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;

import pro.ibpm.mercury.business.data.api.ICaseBusiness;
import pro.ibpm.mercury.business.data.api.ICaseBusinessXML;
import pro.ibpm.mercury.business.data.api.IMrcPagedResult;
import pro.ibpm.mercury.business.data.api.MrcCaseHeader;
import pro.ibpm.mercury.business.data.api.MrcObject;
import pro.ibpm.mercury.business.data.api.MrcPagedResult;
import pro.ibpm.mercury.business.data.api.MrcSimpleProperty;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.attr.TypeCode;
import pro.ibpm.mercury.logic.api.attr.ITypeCodeLogic;
import pro.ibpm.mercury.xml.w3c.dom.XMLUtils;

/**
 * DatasetExportTest - eksport zestawu danych testowych z działającego serwera do plików XML (zadanie 116). Dla
 * każdego typu spraw zapisuje najwyżej <code>dataset.export.max</code> spraw (domyślnie 20) w formacie, który
 * ładuje <code>XMLFileLoader</code> z <code>mercury-srv</code>. Uruchamiany tylko z <code>-Ddataset.export=true</code>
 * (inaczej nic nie robi), np.:
 *
 * <pre>
 * mvn test -pl hgdb-ws-client-cxf -Duser=scichy -Dtest=DatasetExportTest -Ddataset.export=true
 *     -Ddataset.export.dir=D:/tmp/dataset -Ddataset.export.skipTypes=POLISA_LIST_DOK
 * </pre>
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("spring-test.xml")
public class DatasetExportTest extends SpringClientTestSupport {

	private final Context context = new Context("admin", "DatasetExportTest", "001", "10", "1000");

	public DatasetExportTest() {
		super(false);
		context.setMaxResults(1000);
	}

	@Test
	public void testExport() throws Exception {
		if (!Boolean.getBoolean("dataset.export")) {
			logger.info("Eksport pominięty (brak -Ddataset.export=true)");
			return;
		}
		int max = Integer.getInteger("dataset.export.max", 20);
		File dir = new File(System.getProperty("dataset.export.dir", "target/dataset-export"));
		Set<String> skipTypes = new HashSet<>();
		for (String t : System.getProperty("dataset.export.skipTypes", "").split(",")) {
			if (!t.trim().isEmpty()) {
				skipTypes.add(t.trim());
			}
		}
		Files.createDirectories(dir.toPath());

		ICaseBusiness caseBusiness = (ICaseBusiness) applicationContext.getBean("caseBusiness");
		ICaseBusinessXML caseBusinessXml = (ICaseBusinessXML) caseBusiness;
		ITypeCodeLogic typeCodeLogic = (ITypeCodeLogic) applicationContext.getBean("typeCodeLogic");

		List<String> typeCodes = new ArrayList<>();
		for (TypeCode typeCode : typeCodeLogic.findAllCaseTypeCodes(context)) {
			typeCodes.add(typeCode.getId());
		}
		logger.info("Typów spraw: {}", typeCodes.size());
		int exported = 0;
		List<String> failed = new ArrayList<>();
		for (String typeCode : typeCodes) {
			if (skipTypes.contains(typeCode)) {
				logger.info("Pomijam typ {}", typeCode);
				continue;
			}
			List<String> caseIds = findCaseIds(caseBusiness, typeCode, max);
			int number = 0;
			for (String caseId : caseIds) {
				number++;
				try {
					Document doc = caseBusinessXml.findXML(context, caseId);
					if (doc.getDocumentElement() == null || !doc.getDocumentElement().hasChildNodes()) {
						failed.add(typeCode + "/" + caseId + ": serwer zwrócił pusty dokument");
						continue;
					}
					write(new File(dir, typeCode + "." + String.format("%03d", number) + ".xml"),
							XMLUtils.nodeToString(doc));
					exported++;
				} catch (Exception e) {
					/* pojedyncza sprawa, której serwer nie umie zapisać jako XML, nie przerywa eksportu */
					failed.add(typeCode + "/" + caseId + ": " + e.getMessage());
				}
			}
			logger.info("Typ {}: zapisano {} spraw", typeCode, caseIds.size());
		}
		logger.info("Zapisano {} spraw do {}", exported, dir.getAbsolutePath());
		for (String f : failed) {
			logger.warn("Nie zapisano sprawy: {}", f);
		}
	}

	private List<String> findCaseIds(ICaseBusiness caseBusiness, String typeCode, int max) throws Exception {
		PageTransportable page = new PageTransportable();
		page.setNumber(1);
		page.setSize(max);
		MrcPagedResult paged = caseBusiness.searchByQuery(context, "mrc_typeCodeValue:\"" + typeCode + "\"", page,
				/* sortClause */ null, /* additionalDateRange */ null);
		List<String> ids = new ArrayList<>();
		Object result = paged.getPropertyValue(IMrcPagedResult.MrcPagedResultField.result.name());
		Collection<?> items = result instanceof pro.ibpm.mercury.business.data.api.MrcList
				? ((pro.ibpm.mercury.business.data.api.MrcList) result).getUnmodifiableArray()
				: new ArrayList<Object>();
		for (Object item : items) {
			Object header = ((MrcObject) item).getPropertyValue("mrcCaseHeader");
			if (header instanceof MrcCaseHeader) {
				ids.add(((MrcCaseHeader) header).getCaseId());
			} else if (header instanceof MrcObject) {
				Object caseId = ((MrcObject) header).getPropertyValue("caseId");
				ids.add(caseId instanceof MrcSimpleProperty ? ((MrcSimpleProperty) caseId).getValue()
						: String.valueOf(caseId));
			}
		}
		return ids;
	}

	private void write(File file, String content) throws IOException {
		Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));
	}
}
