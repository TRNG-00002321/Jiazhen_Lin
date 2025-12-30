@visual
Feature: Visual Regression Testing
  As a QA engineer
  I want to verify pages haven't changed visually
  So that I can catch unintended UI changes

  @baseline
  Scenario: Homepage visual baseline
    Given I navigate to the homepage
    When the page has fully loaded
    Then the page should visually match "homepage" baseline

  @baseline
  Scenario: Login page visual baseline
    Given I am on the login page
    When the page has fully loaded
    Then the page should visually match "login-page" baseline

  @element
  Scenario: Login form element baseline
    Given I am on the login page
    When the page has fully loaded
    Then the login form should visually match "login-form" baseline