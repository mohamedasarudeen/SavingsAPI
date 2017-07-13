@Service2 
Feature: Savings API-Service2 - Get details of specific Arrangements 

Scenario: Get details of specific Arrangements for invalid IBAN Account Number 

    Given I create a request with path 'v1/savings/' 
    And I set query parameter 'foo' 
    When I execute GET request 
    Then the HTTP status is 200 
    Then the response is empty 