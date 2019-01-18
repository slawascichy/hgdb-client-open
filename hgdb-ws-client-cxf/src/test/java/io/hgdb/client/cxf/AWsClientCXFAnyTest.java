package io.hgdb.client.cxf;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import io.hgdb.client.test.TestCaseSupport;
import junit.framework.TestCase;
import pl.slawas.twl4j.Logger;
import pl.slawas.twl4j.LoggerFactory;

/**
 * 
 * AWsClientCXFAnyTest
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public abstract class AWsClientCXFAnyTest extends TestCase implements BeanFactoryAware {

	protected static final Properties props = new Properties();

	protected final boolean skipPackageTest;
	protected final Logger logger;

	protected AWsClientCXFAnyTest(boolean skipPackageTest) {
		super();
		this.skipPackageTest = skipPackageTest;
		logger = LoggerFactory.getLogger(getClass());
	}

	static {
		/*
		 * Tutaj kod uruchamiany na początek raz
		 */
		props.putAll(TestCaseSupport.getTestProperties());
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
	}

	@Autowired
	protected ApplicationContext applicationContext;

}
