package com.sawyereffect.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@Search"},
        glue = {"com.sawyereffect.steps"},
        format = {
                "json:target/searchfeatures.json",
                "pretty",
                "html:target/cucumber/searchfeatures"
        }
)
public class SearchRunnerTest {
}
