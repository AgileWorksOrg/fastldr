/**
 * 
 */
package org.agileworks.fastldr.domain;

/**
 * @author tajzivit
 * 
 */
public class Options {

	private boolean direct = false;
	private int rows = 0;
	private int skip = -1;
	/** -1 equals to no-limit */
	private int load =  -1;

	public void setDirect(boolean direct) {
		this.direct = direct;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	public void setLoad(int load) {
		this.load = load;
	}

	public boolean isDirect() {
		return direct;
	}

	public int getRows() {
		return rows;
	}

	public int getSkip() {
		return skip;
	}

	public int getLoad() {
		return load;
	}
}
