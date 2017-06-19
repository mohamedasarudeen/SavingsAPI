@Service2 
Feature: Savings API-Service2 - Get details of specific Arrangements 

#Background: 
#Given Initialize the Interaction session with mediatype application/atom+xml 

Scenario: Get details of specific Arrangements for invalid IBAN Account Number 

    Given I create a request with path v1/savings/ 
    And I set query parameter foo 
    When i execute GET request 
    Then the HTTP status is 200 
    Then the response is empty 