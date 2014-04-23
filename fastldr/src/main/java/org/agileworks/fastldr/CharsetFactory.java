package org.agileworks.fastldr;

import java.nio.charset.Charset;

public final class CharsetFactory {

	private CharsetFactory() {
	}

	public static Charset getCharset(String dbCharsetName) {
		if (dbCharsetName.equalsIgnoreCase("AL32UTF8")) {
			return Charset.forName("UTF-8");
		}
		return Charset.forName(dbCharsetName);
	}
}
