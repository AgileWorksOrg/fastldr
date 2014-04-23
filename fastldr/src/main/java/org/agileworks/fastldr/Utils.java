package org.agileworks.fastldr;

public class Utils {

	private Utils() {
	}

	public static final String getSubstring(String string) {
		if (string.contains("=")) {
			return string.substring(string.indexOf("=") + 1);
		}
		return null;
	}
}
