@Service2 
Feature: Savings API-Service2 - Get details of specific Arrangements 

#Background: 
#Given Initialize the Interaction session with mediatype application/atom+xml 

Scenario: Get details of specific Arrangements for invalid IBAN Account Number 

    Given a scenario Verifying details of specific Arrangements for invalid IBAN Account Number 
    And a description is To validate a details of specific Arrangements for invalid IBAN Account Number 
    Given Set Interaction Session path as v1/savings/ 
    And Set Interaction Session query parameters as foo 
    When i execute GET request 
    Then the HTTP status is 200 
    Then the response is empty 