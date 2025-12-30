@login @smoke
Feature: User Login
  As a registered user
  I want to log in to the application
  So that I can access protected features

  @positive
  Scenario: Successful login with valid credentials
    Given I am on the login page
    When I enter username "tomsmith"
    And I enter password "SuperSecretPassword!"
    And I click the login button
    Then I should be on the secure area
    And I should see success message "You logged into a secure area!"
    And the page should visually match "secure-page" baseline

  @negative
  Scenario Outline: Failed login with invalid credentials
    Given I am on the login page
    When I enter username "<username>"
    And I enter password "<password>"
    And I click the login button
    Then I should see error message "<error>"

    Examples:
      | username    | password    | error                      |
      | tomsmith    | wrong       | Your password is invalid!  |
      | invalid     | password    | Your username is invalid!  |

  @logout
  Scenario: Complete login and logout flow
    Given I am on the login page
    When I login as "tomsmith" with password "SuperSecretPassword!"
    And I click logout
    Then I should be back on the login page
    And I should see message "You logged out"