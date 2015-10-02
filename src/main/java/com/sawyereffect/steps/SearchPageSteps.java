package com.sawyereffect.steps;


import com.sawyereffect.page.SearchPage;
import com.sawyereffect.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

public class SearchPageSteps extends DriverFactory {


    private final SearchPage searchPage = new SearchPage(getDriver());

    @When("^She enters ([^\"]*)$")
    public void she_enters(String searchTerm) throws Throwable {
        searchPage.search(searchTerm);
    }

    @Then("^([^\"]*) results should be displayed$")
    public void results_should_be_displayed(String results) throws Throwable {
        assertTrue(results + " should have been displayed", searchPage.isTextPresent(results));
    }

    @And("^Click on the first link from text results$")
    public void click_on_the_first_link_from_text_results() throws Throwable {
        searchPage.click_first_link_from_text_results();
    }


}
