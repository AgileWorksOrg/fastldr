package org.agileworks.fastldr;

import java.sql.Driver;

import javax.sql.DataSource;

import org.agileworks.fastldr.args.UserID;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import org.agileworks.fastldr.args.CmdArguments;

public class OracleDataSource {

	private final CmdArguments cmdArguments;

	public OracleDataSource(CmdArguments cmdArguments) {
		this.cmdArguments = cmdArguments;
	}

	public DataSource getDataSource() throws FastLdrException {
		String url = createUrl();
		try {
			Class<? extends Driver> driver = (Class<? extends Driver>) Class.forName("oracle.jdbc.OracleDriver");
			SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
			dataSource.setDriverClass(driver);
			dataSource.setUrl(url);
			UserID userID = cmdArguments.getUserID();
			dataSource.setUsername(userID.getUser());
			dataSource.setPassword(userID.getPassword());
			return dataSource;
		} catch (ClassNotFoundException e) {
			throw new FastLdrException(e);
		}
	}

	private String createUrl() {
		StringBuilder sb = new StringBuilder();
		sb.append("jdbc:oracle:thin:@");
		UserID userID = cmdArguments.getUserID();
		sb.append("//");
		sb.append(userID.getHostname()).append(":1521");
		sb.append('/').append(userID.getService());
		return sb.toString();
	}
}
