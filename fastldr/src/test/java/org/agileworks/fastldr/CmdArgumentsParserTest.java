package org.agileworks.fastldr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.io.StringReader;

import org.junit.Test;

import org.agileworks.fastldr.args.CmdArguments;
import org.agileworks.fastldr.args.parser.CmdArgumentsParser;
import org.agileworks.fastldr.args.parser.ParseException;

public class CmdArgumentsParserTest {

	@Test
	public void testArgs() {
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
		assertEquals("FooUser/FooUserPass@10.211.55.4/AGILEWORKS", cmdArguments.getUserID().toString());
		assertEquals("/development/agileworks/scratch/sqlloader/Return.par", cmdArguments.getParFile());
		assertEquals("/development/agileworks/custom/export/error/Return_1.bad", cmdArguments.getBadFile());
		assertEquals("/development/agileworks/custom/export/error/Return_1.dis", cmdArguments.getDiscardFile());
		assertEquals("/development/agileworks/web/app/log/Return_1.log", cmdArguments.getLogFile());
		assertEquals(1000000, cmdArguments.getLoad());
		assertEquals(1, cmdArguments.getSkip());

		assertEquals("10.211.55.4", cmdArguments.getUserID().getHostname());
		assertEquals("AGILEWORKS", cmdArguments.getUserID().getInstance());
		assertEquals("FooUser", cmdArguments.getUserID().getUser());
		assertEquals("FooUserPass", cmdArguments.getUserID().getPassword());
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
		assertEquals("FooUser/FooUserPass@10.211.55.4/AGILEWORKS", cmdArguments.getUserID().toString());
		assertEquals("/development/agileworks/custom/export/error/Return_1.bad", cmdArguments.getBadFile());
		assertEquals("/development/agileworks/custom/export/error/Return_1.dis", cmdArguments.getDiscardFile());
		assertEquals("/development/agileworks/web/app/log/Return_1.log", cmdArguments.getLogFile());
		assertEquals(1000000, cmdArguments.getLoad());
		assertEquals(1, cmdArguments.getSkip());

		assertEquals("10.211.55.4", cmdArguments.getUserID().getHostname());
		assertEquals("AGILEWORKS", cmdArguments.getUserID().getInstance());
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
		assertEquals("FooUser/FooUserPass@10.211.55.4/AGILEWORKS", cmdArguments.getUserID().toString());
		assertEquals("/development/agileworks/scratch/sqlloader/Return.ctl", cmdArguments.getControlFile());
		assertEquals("/development/agileworks/custom/export/error/Return_1.bad", cmdArguments.getBadFile());
		assertEquals("/development/agileworks/custom/export/error/Return_1.dis", cmdArguments.getDiscardFile());
		assertEquals("/development/agileworks/web/app/log/Return_1.log", cmdArguments.getLogFile());
		assertEquals(1000000, cmdArguments.getLoad());
		assertEquals(1, cmdArguments.getSkip());

		assertEquals("10.211.55.4", cmdArguments.getUserID().getHostname());
		assertEquals("AGILEWORKS", cmdArguments.getUserID().getInstance());
		assertEquals("FooUser", cmdArguments.getUserID().getUser());
		assertEquals("FooUserPass", cmdArguments.getUserID().getPassword());

	}
}
