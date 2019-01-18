package io.hgdb.mercury.client.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.config.PropertyPrinter;

import pl.slawas.entities.NameValuePair;
import pl.slawas.helpers.Configurations;
import pl.slawas.twl4j.Logger;
import pl.slawas.twl4j.config.LoggerConfig;
import pro.ibpm.mercury.config.MercuryConfig;

public class TestPropertiesLoader {

	private static boolean LOG4J_PRINT_FLAG = true;
	private static final String STATUS_FILE_NAME = "testStatus.properties";
	public static final String STATUS_PROP_PREFIX = "test.status.";
	public static final String STATUS_PROP_ALL = "test.status.all";

	/**
	 * Ładowanie parametrów testów.
	 * 
	 * @return
	 */
	public static Map<String, String> loadProperties() {

		if (LOG4J_PRINT_FLAG) {
			System.out.println("#log4j Config");
			PrintWriter pw = new PrintWriter(System.out);
			PropertyPrinter pp = new PropertyPrinter(pw);
			pp.print(pw);
			System.out.println("#End of Config");
			System.out.println("#sclogger Config");
			for (NameValuePair nvp : LoggerConfig.getInstance().getPropertyList()) {
				System.out.println(nvp.getName() + "=" + nvp.getValue());
			}
			System.out.println("#sclogger End of Config");
			LOG4J_PRINT_FLAG = false;
		}

		Map<String, String> props = new Hashtable<String, String>();

		final String propkFileName = "/test.properties";
		Map<String, String> _Properties = null;
		_Properties = Configurations.loadHashtable(TestPropertiesLoader.class, propkFileName);
		props.putAll(_Properties);

		/** załadowanie ustawień */
		final String mockFileName = _Properties.get("test.file.ext");
		System.out.println("Loading test properties from " + mockFileName + "....");
		_Properties = Configurations.loadHashtable(TestPropertiesLoader.class, mockFileName);
		props.putAll(_Properties);
		MercuryConfig.getInstance();

		/** załadowanie statusu wykonania ostatniego testu */
		File statusFile = new File(STATUS_FILE_NAME);
		if (statusFile.exists()) {
			System.out.println("Loading test properties from " + mockFileName + "....");
			_Properties = Configurations.loadHashtable(TestPropertiesLoader.class, STATUS_FILE_NAME);
			props.putAll(_Properties);
		}
		return props;
	}

	public static void saveErrorStatus(String testLabel, ScenarioStatus status, Logger logger) {
		File statusFile = new File(STATUS_FILE_NAME);
		if (statusFile.exists()) {
			statusFile.delete();
		}
		if (status.equals(ScenarioStatus.ERROR) || status.equals(ScenarioStatus.FAILED)) {
			FileWriter fw = null;
			try {
				fw = new FileWriter(statusFile);
				fw.append((new StringBuilder()).append(STATUS_PROP_PREFIX).append(testLabel)
						.append(MercuryConfig.EQUALS).append(status.name()).append("\n").toString());
				fw.append((new StringBuilder()).append(STATUS_PROP_ALL).append(MercuryConfig.EQUALS)
						.append(status.name()).toString());
				fw.flush();
				System.out.println("Saved test status in " + statusFile.getCanonicalPath());
			} catch (IOException e) {
				logger.error("Błąd zapisu statusu wykonania testu.", e);
			} finally {
				if (fw != null) {
					try {
						fw.close();
					} catch (IOException e) {
						logger.error("Błąd zapisu statusu wykonania testu.", e);
					}
				}
			}
		}
	}
}
