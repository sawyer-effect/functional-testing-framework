package com.sawyereffect.steps;

import com.sawyereffect.util.DriverFactory;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import cucumber.api.java.BeforeStep;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class Hooks {

    private static final String TAKE_SCREENSHOT = "Screenshot";
    private final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private DriverFactory driverFactory;
    private WebDriver driver;

    @Before
    public void setupDriver() throws IOException {
        driverFactory = new DriverFactory();
        driver = driverFactory.getDriver();
    }

    @After
    public void quitDriver(Scenario scenario) throws IOException {

        if (driver != null && (scenario.isFailed() || scenario.getName().contains(TAKE_SCREENSHOT))) {

            logger.debug("Taking screenshot of scenario {}", scenario.getName());
            try {
                final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            } catch (Exception e){
                logger.error("Not able to take screenshot");
                if (driverFactory != null) {
                    driverFactory.resetDriver();
                }
            }
        }

        if (driverFactory != null) {
            driverFactory.destroyDriver();
        }
    }

    @BeforeStep
    public void beforeStep(Scenario scenario) {
        logger.debug("Executing before every step {}", scenario.getName());
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        logger.debug("Executing after every step {}", scenario.getName());
    }
}
