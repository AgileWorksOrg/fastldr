package org.agileworks.fastldr;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;

/**
 * Can be used with any JDBC database.
 * Configuration is done by environment properties
 * FASTLDR_DRIVER_CLASS - driver classname
 * FASTLDR_URL - connection URL
 * FASTLDR_USERNAME - username
 * FASTLDR_PASSWORD - password
 */
public class UniversalDataSource {
    public static final String DRIVER_CLASS = "FASTLDR_DRIVER_CLASS";
    public static final String URL = "FASTLDR_URL";
    public static final String USERNAME = "FASTLDR_USERNAME";
    public static final String PASSWORD = "FASTLDR_PASSWORD";
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    public UniversalDataSource() {
        init();
        validate();
    }

    private void init() {
        driverClassName = System.getProperty(DRIVER_CLASS);
        url = System.getProperty(URL);
        username = System.getProperty(USERNAME);
        password = System.getProperty(PASSWORD);
    }

    private void validate() throws FastLdrException {
        validate(driverClassName, DRIVER_CLASS);
        validate(url, URL);
        validate(username, USERNAME);
    }

    private void validate(String property, String name) {
        if (property == null || property.trim().isEmpty()) {
            throw new FastLdrException("Environment property " + name + " must be defined !");
        }
    }

    public DataSource getDataSource() throws FastLdrException {
        try {
            Class<? extends Driver> driver = (Class<? extends Driver>) Class.forName(driverClassName);
            SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
            dataSource.setDriverClass(driver);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            return dataSource;
        } catch (ClassNotFoundException e) {
            throw new FastLdrException(e);
        }
    }
}
