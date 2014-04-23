/**
 *
 */
package org.agileworks.fastldr.parser;

import org.agileworks.fastldr.domain.Control;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * @author tajzivit
 */
public class FastLdrTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Test
    public void testCtlDirect() throws Exception {
        InputStream input = FastLdrTest.class.getResourceAsStream("/Sample1.ctl");

        assertNotNull(input);

        FastLdr fastldr = new FastLdr(input);

        Control control = fastldr.Start();

        assertNotNull(control);
        assertNotNull(control.getOptions());

        assertFalse(control.getOptions().isDirect());
        assertEquals(0, control.getOptions().getRows());
        assertEquals(-1, control.getOptions().getSkip());
        assertEquals(-1, control.getOptions().getLoad());

        assertEquals("AL32UTF8", control.getCharset());

        assertEquals("SampleTable1", control.getTable());
        assertTrue(control.isTruncate());

        assertEquals("/sample/data/Sample1.ctl", control.getFile().getPath());

        assertFalse(control.getFields().isEmpty());

        checkFields(control);
    }

    private void checkFields(Control control) {
        assertTrue(4 == control.getFields().size());
        assertEquals("Fld0", control.getFields().get(0).getName());
        assertEquals("Fld1", control.getFields().get(1).getName());
        assertEquals("Fld2", control.getFields().get(2).getName());
        assertEquals("Fld3", control.getFields().get(3).getName());

        assertEquals("CHAR", control.getFields().get(0).getType());
        assertEquals("CHAR", control.getFields().get(1).getType());
        assertEquals(null, control.getFields().get(2).getType());
        assertEquals("DATE", control.getFields().get(3).getType());
    }

    @Test
    public void testCtlRows() throws Exception {
        InputStream input = FastLdrTest.class.getResourceAsStream("/Sample2.ctl");

        assertNotNull(input);

        FastLdr fastldr = new FastLdr(input);

        Control control = fastldr.Start();

        assertNotNull(control);
        assertNotNull(control.getOptions());

        assertFalse(control.getOptions().isDirect());
        assertEquals(5000, control.getOptions().getRows());
        assertEquals(-1, control.getOptions().getSkip());
        assertEquals(-1, control.getOptions().getLoad());

        assertEquals("AL32UTF8", control.getCharset());

        assertEquals("SampleTable2", control.getTable());
        assertTrue(control.isTruncate());

        assertEquals("/sample/data/Sample2.ctl", control.getFile().getPath());

        assertFalse(control.getFields().isEmpty());

        checkFields(control);
    }

    @Test
    public void testCtlSkip() throws Exception {
        InputStream input = FastLdrTest.class.getResourceAsStream("/Sample3.ctl");

        assertNotNull(input);

        FastLdr fastldr = new FastLdr(input);

        Control control = fastldr.Start();

        assertNotNull(control);
        assertNotNull(control.getOptions());

        assertFalse(control.getOptions().isDirect());
        assertEquals(5000, control.getOptions().getRows());
        assertEquals(1, control.getOptions().getSkip());
        assertEquals(-1, control.getOptions().getLoad());

        assertEquals("AL32UTF8", control.getCharset());

        assertEquals("SampleTable3", control.getTable());
        assertTrue(control.isTruncate());

        assertEquals("/sample/data/Sample3.ctl", control.getFile().getPath());


        assertFalse(control.getFields().isEmpty());

        checkFields(control);
    }

    @Test
    public void testCtlLoad() throws Exception {
        InputStream input = FastLdrTest.class.getResourceAsStream("/Sample4.ctl");

        assertNotNull(input);

        FastLdr fastldr = new FastLdr(input);

        Control control = fastldr.Start();

        assertNotNull(control);
        assertNotNull(control.getOptions());

        assertFalse(control.getOptions().isDirect());
        assertEquals(5000, control.getOptions().getRows());
        assertEquals(1, control.getOptions().getSkip());
        assertEquals(123, control.getOptions().getLoad());

        assertEquals("AL32UTF8", control.getCharset());

        assertEquals("SampleTable4", control.getTable());
        assertTrue(control.isTruncate());

        assertEquals("/sample/data/Sample4.ctl", control.getFile().getPath());

        assertFalse(control.getFields().isEmpty());

        checkFields(control);
    }

    @Test
    public void testUntypical8VersionAFIFile() throws Exception {


        InputStream input = FastLdrTest.class.getResourceAsStream("/Sample5.ctl");

        assertNotNull(input);

        FastLdr fastldr = new FastLdr(input);

        Control control = fastldr.Start();

        assertNotNull(control);
        assertNotNull(control.getOptions());

        assertFalse(control.getOptions().isDirect());
        assertEquals(5000, control.getOptions().getRows());

        assertEquals("AL32UTF8", control.getCharset());

        assertEquals("SampleTable5", control.getTable());
        assertTrue(control.isTruncate());

        assertEquals("/sample/data/Sample5.ctl", control.getFile().getPath());

        assertFalse(control.getFields().isEmpty());

        checkFields(control);
    }
}
