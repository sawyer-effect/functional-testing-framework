package com.sawyereffect.steps;

import com.sawyereffect.page.ResultPage;
import com.sawyereffect.util.DriverFactory;
import com.sawyereffect.util.StateHolder;
import cucumber.api.java.en.And;

public class ResultPageSteps extends DriverFactory {

    private final StateHolder stateHolder = StateHolder.getInstance();
    private ResultPage resultPage = new ResultPage(getDriver());

    /**
     * Verifies title in page is similar to the clicked result.
     */
    @And("^Verify the header of the link is the title in the opened page$")
    public void verify_the_header_of_the_link_is_the_title_in_the_opened_page() throws Throwable {
        String title = (String) stateHolder.get("clickedResult");
        resultPage.hasTitle(title);
    }
}
