package com.sawyereffect.steps;

import com.sawyereffect.util.DriverFactory;
import com.sawyereffect.util.PropertyReader;
import cucumber.api.java.en.Given;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class StartingSteps extends DriverFactory{

    private final Logger logger = LoggerFactory.getLogger(StartingSteps.class);
    private final PropertyReader reader = PropertyReader.getPropertyReader();
    private final WebDriver driver = getDriver();


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

}
