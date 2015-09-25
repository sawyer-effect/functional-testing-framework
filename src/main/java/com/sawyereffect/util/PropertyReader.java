package com.sawyereffect.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    public static final int DESKTOP_DEFAULT_WIDTH = 1200;
    private static final int DESKTOP_DEFAULT_HEIGHT = 800;
    private static final String ENVIRONMENT = "environment";
    private static final String VIEWPORT = "viewport";
    private static final String BROWSER = "browser";
    private static final String USER_AGENT = "user.agent";
    private static final String WINDOW_WIDTH = "window.width";
    private static final String WINDOW_HEIGHT = "window.height";
    private static final String REMOTE_EXECUTION = "remote.execution";
    private static final String SELENIUM_GRID_HUB_URL = "selenium.grid.hub.url";
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
        String configurationFile = environment + "-" + viewport + ".properties";

        logger.info("Configuration file to be used {}", configurationFile);

        InputStream inputStream = new FileInputStream(configurationFile);
        properties.load(inputStream);
    }

    public String readProperty(String key) {
        return properties.getProperty(key);
    }


    public String getBrowser() {
        return readProperty(BROWSER);
    }

    public String getUserAgent() {
        return readProperty(USER_AGENT);
    }

    public int getWindowWidth() {
        String widthString = readProperty(WINDOW_WIDTH);
        int width = 0;
        try {
            width = Integer.parseInt(widthString);
        } catch (NumberFormatException nfe) {
            width = DESKTOP_DEFAULT_WIDTH;
        }
        return width;
    }

    public int getWindowHeight() {
        String heightString = readProperty(WINDOW_HEIGHT);
        int height = 0;
        try {
            height = Integer.parseInt(heightString);
        } catch (NumberFormatException nfe) {
            height = DESKTOP_DEFAULT_HEIGHT;
        }
        return height;
    }

    public String getEnvironment() {
        return readProperty(ENVIRONMENT);
    }

    public boolean isRemoteExecution() {
        return "true".equals(System.getProperty(REMOTE_EXECUTION));
    }


    public String getSeleniumHubUrl() {
        return readProperty(SELENIUM_GRID_HUB_URL);
    }

    public String getProperty(String property) {
        return readProperty(property);
    }
}
