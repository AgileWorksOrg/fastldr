package org.agileworks.fastldr;

import org.junit.Test;

import org.agileworks.fastldr.args.CmdArguments;
import org.agileworks.fastldr.args.UserID;
import org.agileworks.fastldr.args.parser.ParseException;

public class DataSourceFactoryTest {

	@Test
	public void testGetDataSourceOracle() throws FastLdrException, ParseException {
		System.setProperty(DataSourceFactory.DB_TYPE, "Oracle");
		CmdArguments cmdArguments = new CmdArguments();
		cmdArguments.setUserID(new UserID("uid/pwd@host/instance"));
		DataSourceFactory.getDataSource(cmdArguments);
	}

	@Test(expected = FastLdrException.class)
	public void testGetDataSourceH2() throws FastLdrException, ParseException {
		System.setProperty(DataSourceFactory.DB_TYPE, "H2");
		CmdArguments cmdArguments = new CmdArguments();
		cmdArguments.setUserID(new UserID("uid/pwd@host/instance"));
		DataSourceFactory.getDataSource(cmdArguments);
	}

	@Test(expected = FastLdrException.class)
	public void testGetDataSourceUnknown() throws FastLdrException {
		System.setProperty(DataSourceFactory.DB_TYPE, "MySQL");
		DataSourceFactory.getDataSource(new CmdArguments());
	}

}
