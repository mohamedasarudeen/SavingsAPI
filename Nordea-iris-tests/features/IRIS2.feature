@Iris2
Feature: IRIS-Fetch and verify list of currencylists and Post new record 

Scenario: Verifying the new Arrangement Activity is created using post method 

    Given I create a request with path verAaArrangementActivity_AaNewTsts()/new
    And I set query parameter Activity=LENDING-NEW-ARRANGEMENT&Product=MARGIN.LOAN
    When i execute POST request
    Then the HTTP status is 201
    And set bundle aaId with session property ArrSequence value
    And Create test Customer
    And set bundle customerId with session property CustomerMvGroup(0)/Customer value
    And set property Currency with value USD
    And i execute POST request for REL aapopulate
    And i reuse Interaction Session
    And set property AaArrTermAmount(0)/Amount with value 26000
    And set property AaArrTermAmount(0)/Term with value 1Y
    And i execute POST request for REL input
    Then the HTTP status is 201 
    And Authorise the created record
    Then the HTTP status is 200
    Then the response is an entity
    
    
    
    
    
    
    