package org.agileworks.fastldr;

import javax.sql.DataSource;

import org.agileworks.fastldr.args.CmdArguments;

public class DataSourceFactory {

	public static final String DB_TYPE = "FASTLDR_DB_TYPE";

	public static DataSource getDataSource(CmdArguments cmdArguments) throws FastLdrException {
		String sqlType = System.getProperty(DB_TYPE);
		if (sqlType == null || sqlType.equalsIgnoreCase("ORACLE")) {
			return new OracleDataSource(cmdArguments).getDataSource();
		} else if (sqlType.equals("UNIVERSAL")) {
			return new UniversalDataSource().getDataSource();
		} else {
			throw new FastLdrException("Unknown database type:" + sqlType);
		}
	}

}
