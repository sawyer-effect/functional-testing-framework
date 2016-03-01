@Search
Feature: Search Functionality

  Scenario: Google Search should work
    Given User is in google main page
    When She enters fly me to the moon
    Then Verify Frank Sinatra results are displayed
    And Click on the first link from text results
    And Verify the header of the link is the title in the opened page