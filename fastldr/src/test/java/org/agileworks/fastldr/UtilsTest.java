package org.agileworks.fastldr;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void testGetSubstring() {
		assertEquals("asdf", Utils.getSubstring("name=asdf"));
		assertEquals("asdf=asdf", Utils.getSubstring("name=asdf=asdf"));
		assertEquals("", Utils.getSubstring("name="));
		assertNull("", Utils.getSubstring("name"));
	}

}
