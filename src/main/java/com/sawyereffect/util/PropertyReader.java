package com.sawyereffect.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private static final String ENVIRONMENT = "environment";
    private static final String VIEWPORT = "viewport";
    private Properties properties = new Properties();
    private Logger logger = LoggerFactory.getLogger(PropertyReader.class);
    private static final PropertyReader propertyReader = new PropertyReader();

    public static PropertyReader getPropertyReader() {
        return propertyReader;
    }

    private PropertyReader() {
        try {
            loadProperties();
        } catch (IOException e) {
            logger.error("Unable to load configuration file.");
        }
    }

    private void loadProperties() throws IOException {
        String environment = System.getProperty(ENVIRONMENT, ENVIRONMENT);
        String viewport = System.getProperty(VIEWPORT, VIEWPORT);
        String environmentFile = environment + ".properties";
        String viewportFile = viewport + ".properties";

        logger.info("Environment configuration file to be used {}", environmentFile);
        logger.info("Viewport configuration file to be used {}", viewportFile);

        InputStream environmentStream = new FileInputStream(environmentFile);
        properties.load(environmentStream);

        InputStream viewportStream = new FileInputStream(viewportFile);
        properties.load(viewportStream);
    }

    public String getProperty(String key) {
        return readProperty(key);
    }

    public boolean isSystemPropertyTrue(String key) {
        return "true".equals(System.getProperty(key));
    }

    private String readProperty(String key) {
        String value = properties.getProperty(key);

        if (value == null) {
            throw new RuntimeException("Key '" + key + "' is not defined in environment or viewport properties file");
        }

        return value;
    }
}
