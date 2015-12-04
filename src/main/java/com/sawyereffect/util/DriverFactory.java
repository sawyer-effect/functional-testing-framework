package com.sawyereffect.util;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private static Map<String, WebDriver> driverMap = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    public static final String[] PHANTOM_JS_ARGS = new String[]{"--web-security=false",
            "--ssl-protocol=any",
            "--local-to-remote-url-access=true",
            "--disk-cache=true",
            "--ignore-ssl-errors=true"
    };

    private int width;
    private int height;

    private WebDriver createNewDriverInstance() throws IOException {

        final WebDriver driver;
        final PropertyReader propertyReader = PropertyReader.getPropertyReader();
        if (propertyReader.isPropertyTrue("remote_execution")) {
            driver = createRemoteDriver(propertyReader);
        } else {
            driver = createLocalDriver(propertyReader);
        }

        width = Integer.parseInt(propertyReader.getProperty("window_width"));
        height = Integer.parseInt(propertyReader.getProperty("window_height"));

        driver.manage().window().setSize(new Dimension(width, height));

        return driver;
    }

    private WebDriver createLocalDriver(PropertyReader propertyReader) {
        final String browser = propertyReader.getProperty("browser");
        final WebDriver driver;
        if ("chrome".equals(browser)) {
            driver = new ChromeDriver();
        } else if ("firefox".equals(browser)) {
            driver = new FirefoxDriver();
        } else {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setJavascriptEnabled(true);
            capabilities.setCapability("phantomjs.cli.args", PHANTOM_JS_ARGS);
            capabilities.setCapability("phantomjs.page.settings.userAgent", propertyReader.getProperty("user_agent"));
            driver = new PhantomJSDriver(capabilities);
        }

        return driver;
    }

    private WebDriver createRemoteDriver(PropertyReader propertyReader) throws MalformedURLException {
        final WebDriver driver;
        final String browser = propertyReader.getProperty("browser");
        final String seleniumHubUrl = propertyReader.getProperty("selenium_grid_hub_url");
        final URL seleniumHubRemoteAddress = new URL(seleniumHubUrl);

        if ("chrome".equals(browser)) {

            driver = new RemoteWebDriver(seleniumHubRemoteAddress, DesiredCapabilities.chrome());

        } else if ("firefox".equals(browser)) {

            driver = new RemoteWebDriver(seleniumHubRemoteAddress, DesiredCapabilities.firefox());

        } else {

            final DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
            capabilities.setCapability("phantomjs.cli.args", PHANTOM_JS_ARGS);
            capabilities.setCapability("phantomjs.page.settings.userAgent", propertyReader.getProperty("user_agent"));

            driver = new RemoteWebDriver(seleniumHubRemoteAddress, capabilities);

        }

        return driver;
    }

    public WebDriver getDriver() {
        String identifier = Thread.currentThread().getName();
        WebDriver driver = driverMap.get(identifier);
        if (driver == null) {
            try {
                driver = createNewDriverInstance();
                driverMap.put(identifier, driver);
            } catch (IOException e) {
                logger.error("unable to create driver");
            }

        }
        return driver;
    }

    public void destroyDriver() {
        String identifier = Thread.currentThread().getName();
        WebDriver driver = driverMap.remove(identifier);
        if (driver != null) {
            driver.quit();
        }
    }
}
