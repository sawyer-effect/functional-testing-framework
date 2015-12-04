package com.sawyereffect.steps;

import com.sawyereffect.util.DriverFactory;
import com.sawyereffect.util.PropertyReader;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import cucumber.api.java.BeforeStep;
import cucumber.api.java.en.Given;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class StartingSteps {

    private static final String TAKE_SCREENSHOT = "Screenshot";
    private final Logger logger = LoggerFactory.getLogger(StartingSteps.class);
    private DriverFactory driverFactory;
    private WebDriver driver;
    private final PropertyReader reader = PropertyReader.getPropertyReader();

    @Before
    public void setupDriver() throws IOException {
        driverFactory = new DriverFactory();
        driver = driverFactory.getDriver();
    }

    @Given("^User is in google main page$")
    public void user_is_on_home_page() throws Throwable {
        int retry = 0;
        boolean successfulLoad = false;
        while (retry < 5 && !successfulLoad) {
            try {
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                driver.get(reader.getProperty("host"));
                waitForPageLogoToLoad();
                successfulLoad = true;
            } catch (TimeoutException te) {
                logger.debug("Page did not load retry: {}", retry + 1);
                retry++;
            }
        }

    }

    private void waitForPageLogoToLoad() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(
                By.id("hplogo")));
    }


    @After
    public void quitDriver(Scenario scenario) throws IOException {

        if (driver != null && (scenario.isFailed() || scenario.getName().contains(TAKE_SCREENSHOT))) {

            logger.debug("Taking screenshot of scenario {}", scenario.getName());

            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
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
