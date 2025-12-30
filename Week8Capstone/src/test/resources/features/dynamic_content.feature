@dynamic
Feature: Dynamic Content Handling
  As a tester
  I want to verify dynamic content loads correctly
  So that I can ensure page functionality

  Scenario: Dynamic loading with hidden element
    Given I am on dynamic loading page 1
    When I click the start button
    Then I should see "Hello World!" after loading

  Scenario: Dynamic loading with added element
    Given I am on dynamic loading page 2
    When I click the start button
    Then I should see "Hello World!" after loading
    And a trace should be captured for this scenario