package com.sawyereffect.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SearchPage {

    private Logger logger = LoggerFactory.getLogger(SearchPage.class);

    private final WebDriver driver;
    @FindBy(id = "lst-ib")
    private WebElement searchInput;

    @FindBy(css = "button.lsb")
    private WebElement submitButton;

    @FindBy(id = "ires")
    private WebElement resultsSection;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public void search(String searchTerm) {
        searchInput.sendKeys(searchTerm);
        submitButton.click();
    }

    public boolean isTextPresent(String results) {

        String text = driver.findElement(By.id("ires")).getAttribute("innerHTML");
        logger.debug("Results text is: {} ", text);
        return text.contains(results);

    }
}
