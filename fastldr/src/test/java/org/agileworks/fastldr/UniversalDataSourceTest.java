package org.agileworks.fastldr;


import org.junit.Before;
import org.junit.Test;

public class UniversalDataSourceTest {
    @Before
    public void setUp() throws Exception {
        System.getProperties().remove(UniversalDataSource.DRIVER_CLASS);
        System.getProperties().remove(UniversalDataSource.URL);
        System.getProperties().remove(UniversalDataSource.USERNAME);
        System.getProperties().remove(UniversalDataSource.PASSWORD);
    }

    @Test(expected = FastLdrException.class)
    public void testDataSourceWithoutParameters() {
        UniversalDataSource universalDataSource = new UniversalDataSource();
    }

    @Test
    public void testDataSourceWithEmptyPassword() {
        System.setProperty(UniversalDataSource.DRIVER_CLASS, java.lang.Object.class.getName());
        System.setProperty(UniversalDataSource.URL, "jdbc://foo");
        System.setProperty(UniversalDataSource.USERNAME, "guest");
        UniversalDataSource universalDataSource = new UniversalDataSource();
    }

    @Test(expected = FastLdrException.class)
    public void testDataSourceWithEmptyDriver() {
        System.setProperty(UniversalDataSource.URL, "jdbc://foo");
        System.setProperty(UniversalDataSource.USERNAME, "guest");
        UniversalDataSource universalDataSource = new UniversalDataSource();
    }
}
