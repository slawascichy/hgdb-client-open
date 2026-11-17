package io.hgdb.mercury.client.cxf.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import pro.ibpm.mercury.context.Context;

/**
 * SaveRequestFlagsTest - ustawianie flagi zapisu w kontekście klienta (zadanie 56), bez serwera.
 */
public class SaveRequestFlagsTest {

	private static Context newContext() {
		return new Context("Test", "1.0", "user", "label", 10, 10, "pl_PL", "Europe/Warsaw", "comment");
	}

	@Test
	public void flagIsNotSetByDefault() {
		assertNull(SaveRequestFlags.isNullIfFieldNotInRequest(newContext()));
	}

	@Test
	public void setterStoresTheFlagUnderTheRequestPropertyAndReturnsTheContext() {
		Context context = newContext();
		assertSame(context, SaveRequestFlags.nullIfFieldNotInRequest(context, true));
		assertEquals(Boolean.TRUE, SaveRequestFlags.isNullIfFieldNotInRequest(context));
		assertEquals("true", context.getRequestPropertyValue("saveRequestContext.valueIsNullIfFieldNotExisisInRequest"));
		SaveRequestFlags.nullIfFieldNotInRequest(context, false);
		assertEquals(Boolean.FALSE, SaveRequestFlags.isNullIfFieldNotInRequest(context));
	}

	@Test
	public void clearRestoresTheServerDefault() {
		Context context = SaveRequestFlags.nullIfFieldNotInRequest(newContext(), true);
		SaveRequestFlags.clearNullIfFieldNotInRequest(context);
		assertNull(SaveRequestFlags.isNullIfFieldNotInRequest(context));
		assertNull(context.getRequestPropertyValue("saveRequestContext.valueIsNullIfFieldNotExisisInRequest"));
	}
}
