package org.agileworks.fastldr;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.Test;

public class ParFileParserTest {

	@Test
	public void testParseSingleLine() throws FastLdrException {
		String[] filePaths = ParFileParser.parse(new ByteArrayInputStream("CONTROL=/home/a/b".getBytes()));
		assertEquals(1, filePaths.length);
		assertEquals("/home/a/b", filePaths[0]);
	}

	@Test
	public void testParseMultipleLines() throws FastLdrException {
		String[] filePaths = ParFileParser.parse(new ByteArrayInputStream("CONTROL=/home/a/b\nCONTROL=/home/c/d\n"
				.getBytes()));
		assertEquals(2, filePaths.length);
		assertEquals("/home/a/b", filePaths[0]);
		assertEquals("/home/c/d", filePaths[1]);
	}

	@Test
	public void testParseWithComments() throws FastLdrException {
		String[] filePaths = ParFileParser.parse(new ByteArrayInputStream(
				"CONTROL=/home/a/b\n#CONTROL=/home/c/b\nCONTROL=/home/d/b\n".getBytes()));
		assertEquals(2, filePaths.length);
		assertEquals("/home/a/b", filePaths[0]);
		assertEquals("/home/d/b", filePaths[1]);
	}

}
