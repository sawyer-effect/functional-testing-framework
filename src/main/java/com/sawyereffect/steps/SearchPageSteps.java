package com.sawyereffect.steps;


import com.sawyereffect.page.SearchPage;
import com.sawyereffect.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

/**
 * Google search page
 * This page is used to search for terms in the outside world.
 *
 */
public class SearchPageSteps extends DriverFactory {


    private final SearchPage searchPage = new SearchPage(getDriver());

    /**
     * Executes a search given the term provided on the search page.
     * @param searchTerm - term to be searched
     *
     */
    @When("^She enters ([^\"]*)$")
    public void she_enters(String searchTerm) throws Throwable {
        searchPage.search(searchTerm);
    }

    /**
     * Verify from results given an specific text is displayed.
     * @param results - text to be searched for validation.
     */
    @Then("^Verify ([^\"]*) results should be displayed$")
    public void results_should_be_displayed(String results) throws Throwable {
        assertTrue(results + " should have been displayed", searchPage.isTextPresent(results));
    }

    /**
     * User hits the first result available.
     *
     */
    @When("^Click on the first link from text results$")
    public void click_on_the_first_link_from_text_results() throws Throwable {
        searchPage.click_first_link_from_text_results();
    }


}
