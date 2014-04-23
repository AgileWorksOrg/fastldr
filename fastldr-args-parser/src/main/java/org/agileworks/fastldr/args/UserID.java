package org.agileworks.fastldr.args;

import org.agileworks.fastldr.args.parser.ParseException;

public class UserID {
	private String user;
	private String password;
	private String hostname;
	private String instance;

	public UserID(String uid) throws ParseException {
		int atSign = uid.indexOf("@");
		int slashSign = -1;
		
		if (atSign == -1)
			throw new ParseException("Invalid format of USERID parameter (missing '@' divider)");
		
		String userPart = uid.substring(0, atSign);
		slashSign = userPart.indexOf("/");
		if (slashSign == -1)
			throw new ParseException("Invalid format of USERID parameter (missing '/' divider of username/password)");

		user = userPart.substring(0, slashSign).trim();
		password = userPart.substring(slashSign + 1).trim();

		String hostPart = uid.substring(uid.indexOf("@") + 1);
		slashSign = hostPart.indexOf("/");
		if (slashSign == -1)
			throw new ParseException("Invalid format of USERID parameter (missing '/' divider of hostname/instance)");

		hostname = hostPart.substring(0, slashSign).trim();
		instance = hostPart.substring(slashSign + 1).trim();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append(user);
		sb.append("/");
		sb.append(password);
		sb.append("@");
		sb.append(hostname);
		sb.append("/");
		sb.append(instance);
		
		return sb.toString();
	}
}
