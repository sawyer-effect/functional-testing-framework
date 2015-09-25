@Search
Feature: Search Functionality

  Scenario: Google Search should work
    Given User is in google main page
    When She enters fly me to the moon
    Then Frank Sinatra results should be displayed