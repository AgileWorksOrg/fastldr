package org.agileworks.fastldr;

public class FastLdrException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 950271249318597100L;

	public FastLdrException(Throwable e) {
		super(e);
	}

	public FastLdrException(String string) {
		super(string);
	}

}
