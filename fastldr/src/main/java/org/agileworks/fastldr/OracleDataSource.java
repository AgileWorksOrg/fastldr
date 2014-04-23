package org.agileworks.fastldr;

import java.sql.Driver;

import javax.sql.DataSource;

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
			dataSource.setUsername(cmdArguments.getUserID().getUser());
			dataSource.setPassword(cmdArguments.getUserID().getPassword());
			return dataSource;
		} catch (ClassNotFoundException e) {
			throw new FastLdrException(e);
		}
	}

	private String createUrl() {
		StringBuilder sb = new StringBuilder();
		sb.append("jdbc:oracle:thin:@");
		sb.append(cmdArguments.getUserID().getHostname()).append(":1521:")
				.append(cmdArguments.getUserID().getInstance());
		return sb.toString();
	}
}
