package org.agileworks.fastldr;

import java.io.StringReader;

import org.junit.Test;

import org.agileworks.fastldr.args.CmdArguments;
import org.agileworks.fastldr.args.parser.CmdArgumentsParser;
import org.agileworks.fastldr.args.parser.ParseException;

import static org.junit.Assert.*;

public class CmdArgumentsParserTest {

	@Test
	public void testArgsDefaultPort() {
		String args = "USERID=FooUser/FooUserPass@10.211.55.4/AGILEWORKS,PARFILE=/development/agileworks/scratch/sqlloader/Return.par,LOG=/development/agileworks/web/app/log/Return_1.log,BAD=/development/agileworks/custom/export/error/Return_1.bad,DISCARD=/development/agileworks/custom/export/error/Return_1.dis,SKIP=1,LOAD=1000000";

		CmdArgumentsParser p = new CmdArgumentsParser(new StringReader(args));
		CmdArguments cmdArguments = null;
		try {
			cmdArguments = p.Start();
		} catch (ParseException e) {
			//e.printStackTrace();
		}

		assertNotNull(cmdArguments);
		assertEquals(CmdArguments.TYPE_PAR, cmdArguments.getType());
		assertEquals("FooUser/FooUserPass@10.211.55.4:1521/AGILEWORKS", cmdArguments.getUserID().toString());
		assertEquals("/development/agileworks/scratch/sqlloader/Return.par", cmdArguments.getParFile());
		assertEquals("/development/agileworks/custom/export/error/Return_1.bad", cmdArguments.getBadFile());
		assertEquals("/development/agileworks/custom/export/error/Return_1.dis", cmdArguments.getDiscardFile());
		assertEquals("/development/agileworks/web/app/log/Return_1.log", cmdArguments.getLogFile());
		assertEquals(1000000, cmdArguments.getLoad());
		assertEquals(1, cmdArguments.getSkip());

		assertEquals("10.211.55.4", cmdArguments.getUserID().getHostname());
		assertEquals("1521", cmdArguments.getUserID().getPort()); // default port
		assertEquals("AGILEWORKS", cmdArguments.getUserID().getService());
		assertEquals("FooUser", cmdArguments.getUserID().getUser());
		assertEquals("FooUserPass", cmdArguments.getUserID().getPassword());
	}

	@Test
	public void testArgsCustomPort() {
		String args = "USERID=FooUser1/FooUserPass1@myHost:1234/orcl1";

		CmdArgumentsParser p = new CmdArgumentsParser(new StringReader(args));
		CmdArguments cmdArguments = null;
		try {
			cmdArguments = p.Start();
		} catch (ParseException e) {
			//e.printStackTrace();
		}

		assertNotNull(cmdArguments);
		assertEquals("FooUser1/FooUserPass1@myHost:1234/orcl1", cmdArguments.getUserID().toString());

		assertEquals("myHost", cmdArguments.getUserID().getHostname());
		assertEquals("1234", cmdArguments.getUserID().getPort());
		assertEquals("orcl1", cmdArguments.getUserID().getService());
		assertEquals("FooUser1", cmdArguments.getUserID().getUser());
		assertEquals("FooUserPass1", cmdArguments.getUserID().getPassword());
	}

	@Test
	public void testArgsCustomPortAndSlashes() {
		String args = "USERID=FooUser2/FooUserPass2@//myHost:5678/orcl2";

		CmdArgumentsParser p = new CmdArgumentsParser(new StringReader(args));
		CmdArguments cmdArguments = null;
		try {
			cmdArguments = p.Start();
		} catch (ParseException e) {
			//e.printStackTrace();
		}

		assertNotNull(cmdArguments);
		assertEquals("FooUser2/FooUserPass2@myHost:5678/orcl2", cmdArguments.getUserID().toString());

		assertEquals("myHost", cmdArguments.getUserID().getHostname());
		assertEquals("5678", cmdArguments.getUserID().getPort());
		assertEquals("orcl2", cmdArguments.getUserID().getService());
		assertEquals("FooUser2", cmdArguments.getUserID().getUser());
		assertEquals("FooUserPass2", cmdArguments.getUserID().getPassword());
	}

	@Test
	public void testInvalidArgs1() {
		String args = "USERID1234567=FooUser/FooUserPass@10.211.55.4/AGILEWORKS,PARFILE=/development/agileworks/scratch/sqlloader/Return.par,LOG=/development/agileworks/web/app/log/Return_1.log,BAD=/development/agileworks/custom/export/error/Return_1.bad,DISCARD=/development/agileworks/custom/export/error/Return_1.dis,SKIP=1,LOAD=1000000";

		CmdArgumentsParser p = new CmdArgumentsParser(new StringReader(args));
		CmdArguments cmdArguments = null;
		try {
			cmdArguments = p.Start();
		} catch (ParseException e) {
			//e.printStackTrace();
		}

		assertNull(cmdArguments);
	}

	@Test
	public void testInvalidArgs2() {
		String args = "USERID==FooUser/FooUserPass@10.211.55.4/AGILEWORKS,PARFILE=/development/agileworks/scratch/sqlloader/Return.par,LOG=/development/agileworks/web/app/log/Return_1.log,BAD=/development/agileworks/custom/export/error/Return_1.bad,DISCARD=/development/agileworks/custom/export/error/Return_1.dis,SKIP=1,LOAD=1000000";

		CmdArgumentsParser p = new CmdArgumentsParser(new StringReader(args));
		CmdArguments cmdArguments = null;
		try {
			cmdArguments = p.Start();
		} catch (ParseException e) {
			//e.printStackTrace();
		}

		assertNull(cmdArguments);
	}

	@Test
	public void testInvalidArgs3() {
		String args = "USERID=FooUser/FooUserPass@10.211.55.4/AGILEWORKS,,PARFILE=/development/agileworks/scratch/sqlloader/Return.par,LOG=/development/agileworks/web/app/log/Return_1.log,BAD=/development/agileworks/custom/export/error/Return_1.bad,DISCARD=/development/agileworks/custom/export/error/Return_1.dis,SKIP=1,LOAD=1000000";

		CmdArgumentsParser p = new CmdArgumentsParser(new StringReader(args));
		CmdArguments cmdArguments = null;
		try {
			cmdArguments = p.Start();
		} catch (ParseException e) {
			//e.printStackTrace();
		}

		assertNull(cmdArguments);
	}

	@Test
	public void testInvalidArgs4() {
		String args = "USERID==FooUser/FooUserPass@10.211.55.4/AGILEWORKS,a=b";

		CmdArgumentsParser p = new CmdArgumentsParser(new StringReader(args));
		CmdArguments cmdArguments = null;
		try {
			cmdArguments = p.Start();
		} catch (ParseException e) {
			//e.printStackTrace();
		}

		assertNull(cmdArguments);
	}

	@Test
	public void testInvalidType() {
		String args = "USERID=FooUser/FooUserPass@10.211.55.4/AGILEWORKS,LOG=/development/agileworks/web/app/log/Return_1.log,BAD=/development/agileworks/custom/export/error/Return_1.bad,DISCARD=/development/agileworks/custom/export/error/Return_1.dis,SKIP=1,LOAD=1000000";

		CmdArgumentsParser p = new CmdArgumentsParser(new StringReader(args));
		CmdArguments cmdArguments = null;
		try {
			cmdArguments = p.Start();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		assertNotNull(cmdArguments);
		assertEquals(CmdArguments.TYPE_UNKNOWN, cmdArguments.getType());
		assertEquals("FooUser/FooUserPass@10.211.55.4:1521/AGILEWORKS", cmdArguments.getUserID().toString());
		assertEquals("/development/agileworks/custom/export/error/Return_1.bad", cmdArguments.getBadFile());
		assertEquals("/development/agileworks/custom/export/error/Return_1.dis", cmdArguments.getDiscardFile());
		assertEquals("/development/agileworks/web/app/log/Return_1.log", cmdArguments.getLogFile());
		assertEquals(1000000, cmdArguments.getLoad());
		assertEquals(1, cmdArguments.getSkip());

		assertEquals("10.211.55.4", cmdArguments.getUserID().getHostname());
		assertEquals("AGILEWORKS", cmdArguments.getUserID().getService());
		assertEquals("FooUser", cmdArguments.getUserID().getUser());
		assertEquals("FooUserPass", cmdArguments.getUserID().getPassword());
	}

	@Test
	public void testArgsCONTROL() {
		String args = "USERID=FooUser/FooUserPass@10.211.55.4/AGILEWORKS,CONTROL=/development/agileworks/scratch/sqlloader/Return.ctl,LOG=/development/agileworks/web/app/log/Return_1.log,BAD=/development/agileworks/custom/export/error/Return_1.bad,DISCARD=/development/agileworks/custom/export/error/Return_1.dis,SKIP=1,LOAD=1000000";

		CmdArgumentsParser p = new CmdArgumentsParser(new StringReader(args));
		CmdArguments cmdArguments = null;
		try {
			cmdArguments = p.Start();
		} catch (ParseException e) {
			//e.printStackTrace();
		}

		assertNotNull(cmdArguments);
		assertEquals(CmdArguments.TYPE_CONTROL, cmdArguments.getType());
		assertEquals("FooUser/FooUserPass@10.211.55.4:1521/AGILEWORKS", cmdArguments.getUserID().toString());
		assertEquals("/development/agileworks/scratch/sqlloader/Return.ctl", cmdArguments.getControlFile());
		assertEquals("/development/agileworks/custom/export/error/Return_1.bad", cmdArguments.getBadFile());
		assertEquals("/development/agileworks/custom/export/error/Return_1.dis", cmdArguments.getDiscardFile());
		assertEquals("/development/agileworks/web/app/log/Return_1.log", cmdArguments.getLogFile());
		assertEquals(1000000, cmdArguments.getLoad());
		assertEquals(1, cmdArguments.getSkip());

		assertEquals("10.211.55.4", cmdArguments.getUserID().getHostname());
		assertEquals("1521", cmdArguments.getUserID().getPort());
		assertEquals("AGILEWORKS", cmdArguments.getUserID().getService());
		assertEquals("FooUser", cmdArguments.getUserID().getUser());
		assertEquals("FooUserPass", cmdArguments.getUserID().getPassword());

	}
}
