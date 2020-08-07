package io.hgdb.multi.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pro.ibpm.mercury.context.Context;

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
	public void testApp() {
		assertTrue(true);
	}
}
