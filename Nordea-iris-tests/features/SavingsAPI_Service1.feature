@Service1 
Feature: Savings API-Service1 - Get list of arrangements 

#Background: 
#Given Initialize the Interaction session with mediatype application/atom+xml 

Scenario: Get list of arrangements for the given customer 

    Given a scenario Verifying list of arrangements for the given customer 
    And a description is To validate a list of arrangements for the given customer 
    Given Set Interaction Session path as v1/customers/ 
    And Set Interaction Session query parameters as 1014/arrangements 
    When i execute GET request 
    Then the HTTP status is 200 
    Then the response is not empty 
    Then property CustomerId should be 1014 in all entities on Holder 
    Then property CustomerId should be 1014 in at least one entity on Holder 
    Then property CustomerId should be 1014 in entity Z on Holder 
    Then property Role should be OWNER in entity Z on Holder 
    
Scenario: Get list of arrangements for the given invalid customer 

    Given a scenario Verifying list of arrangements for the given invalid customer 
    And a description is To validate a list of arrangements for the given invalid customer 
    Given Set Interaction Session path as v1/customers/ 
    And Set Interaction Session query parameters as foo/arrangements 
    When i execute GET request 
    Then the HTTP status is 200 
    Then the response is empty 
    
Scenario: Get list of arrangements for the given empty customer 

    Given a scenario Verifying list of arrangements for the given empty customer 
    And a description is To validate a list of arrangements for the given empty customer 
    Given Set Interaction Session path as v1/customers/ 
    And Set Interaction Session query parameters as "" 
    When i execute GET request 
    Then the HTTP status is 405 
    Then the response is empty 
    
Scenario: Get list of arrangements for the given customer and validate property 

    Given a scenario Verifying list of arrangements for the given customer and validate property 
    And a description is To validate a list of arrangements for the given customer and validate property 
    Given Set Interaction Session path as v1/customers/ 
    And Set Interaction Session query parameters as 1014/arrangements 
    When i execute GET request 
    Then the HTTP status is 200 
    Then the response of each property values is not empty AccountIBAN 
    
