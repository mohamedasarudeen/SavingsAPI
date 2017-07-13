@Service1 
Feature: Savings API-Service1 - Get list of arrangements 

Scenario: Get list of arrangements for the given customer 

    Given I create a request with path 'v1/customers/' 
    And I set query parameter '1014/arrangements' 
    When I execute GET request 
    Then the HTTP status is 200 
    Then the response is not empty 
    Then property CustomerId should be '1014' in all entities on Holder 
    Then property CustomerId should be '1014' in at least one entity on Holder 
    Then property CustomerId should be '1014' in entity on Holder 
    Then property Role should be 'OWNER' in entity on Holder 
    
Scenario: Get list of arrangements for the given invalid customer 

    Given I create a request with path 'v1/customers/' 
    And I set query parameter 'foo/arrangements' 
    When I execute GET request 
    Then the HTTP status is 200 
    Then the response is empty 
    
Scenario: Get list of arrangements for the given empty customer 

    Given I create a request with path 'v1/customers/' 
    And I set query parameter '' 
    When I execute GET request 
    Then the HTTP status is 405 
    Then the response is empty 
    
Scenario: Get list of arrangements for the given customer and validate property 

    Given I create a request with path 'v1/customers/' 
    And I set query parameter '1014/arrangements' 
    When I execute GET request 
    Then the HTTP status is 200 
    Then the response of each property values is not empty 'AccountIBAN' 
