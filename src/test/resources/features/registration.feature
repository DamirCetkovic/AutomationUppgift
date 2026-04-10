Feature: Registration England Basketball
  I want to create an account

  Background:
    Given I prepare the test environment for registration

  Scenario: Create new user - everything goes as expected
    Given I am using "edge" browser
    And I am on the page: CREATE A SUPPORTER ACCOUNT
    When I select "04/02/1986" as my date of birth
    And I fill in first name "Damir" and last name "Cetkovic"
    And I enter email "damir1@test.se" and confirm it as "damir1@test.se"
    And I enter password "Damir86" and retype it as "Damir86"
    And I check all required checkboxes for terms and ethics
    And I click on the CONFIRM AND JOIN button
    Then a new account should be created successfully

  Scenario: Create new user - last name is missing
    Given I am using "chrome" browser
    And I am on the page: CREATE A SUPPORTER ACCOUNT
    When I select "04/02/1986" as my date of birth
    And I fill in first name "Damir" but leave last name empty
    And I enter email "damir2@test.se" and confirm it as "damir2@test.se"
    And I enter password "Damir86" and retype it as "Damir86"
    And I check all required checkboxes for terms and ethics
    And I click on the CONFIRM AND JOIN button
    Then I should see the error message "Last Name is required"

  Scenario: Create new user - terms and condition not accepted
    Given I am using "firefox" browser
    And I am on the page: CREATE A SUPPORTER ACCOUNT
    When I select "04/02/1986" as my date of birth
    And  I fill in first name "Damir" and last name "Cetkovic"
    And I enter email "damir3@test.se" and confirm it as "damir3@test.se"
    And I enter password "Damir86" and retype it as "Damir86"
    And I check only Age and Ethics checkboxes but NOT Terms
    And I click on the CONFIRM AND JOIN button
    Then I should see the error message "You must confirm that you have read and accepted our Terms and Conditions"

  Scenario Outline: Create new user - password do not match
    Given I am using "chrome" browser
    And I am on the page: CREATE A SUPPORTER ACCOUNT
    When I select "04/02/1986" as my date of birth
    And I fill in first name "Damir" and last name "Cetkovic"
    And I enter email "damir4@test.se" and confirm it as "damir4@test.se"
    And I enter password "<password>" and retype it as "<confirmPassword>"
    And I check all required checkboxes for terms and ethics
    And I click on the CONFIRM AND JOIN button
    Then I should see the error message "Password did not match"

    Examples:
      | password | confirmPassword |
      | Damir86  | damir1986       |
      | Damir86  | Damir           |
      | Test123  | Test321         |


  Scenario Outline: Create account on different browser - <browser>
    Given I am using "<browser>" browser
    And I am on the page: CREATE A SUPPORTER ACCOUNT
    When I select "04/02/1986" as my date of birth
    And I fill in first name "Damir" and last name "Cetkovic"
    And I enter email "<email>" and confirm it as "<email>"
    And I enter password "Damir86" and retype it as "Damir86"
    And I check all required checkboxes for terms and ethics
    And I click on the CONFIRM AND JOIN button
    Then a new account should be created successfully

    Examples:
      | browser | email            |
      | chrome  | damir_chrome@test.se |
      | edge    | damir_ed@test.se |
      | firefox | damir_firefox5600@test.se |