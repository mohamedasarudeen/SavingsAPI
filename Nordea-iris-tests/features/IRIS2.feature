@Iris2 
Feature: IRIS-Fetch and verify list of currencylists and Post new record 

Scenario: Verifying the test currency enquiry with operators OR 

    Given I create a request with path enqCurrencyListTsts() 
    And I set query parameter $filter=Id+eq+%27EUR%27+or+Id+eq+%27USD%27 
    When i execute GET request 
    Then the HTTP status is 200 
    Then the response is not empty 
    Then property Id matcher startsWith method anyOf value E,U in entity 
    Then property Id matcher containsString method anyOf value EUR,USD in entity 
    Then property Id matcher equalTo method anyOf value EUR,USD in entity 
    Then check the property Id should be notNullValue in entity response 
    Then entity property Id with value USD, should have NoOfDecimals value equalTo 2 
    Then entity size should be 2 equalTo in entity 
    Then property Id matcher not method anyOf value EUR,USD in entity 
    
Scenario Outline: 
    Verifying the test currencylists enquiry to display for specific filter 

    Given I create a request with path enqCurrencyListTsts() 
    And I set query parameter $filter=Id+eq+%27EUR%27 
    When i execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    Then entity size should be 1 greaterThanOrEqualTo in entity 
    Then entity size should be 2 not,equalTo in entity 
    Then property Id should be equalTo EUR in entity 
    Then property Id should be not,equalTo USD in entity 
    Then property Id matcher equalTo method allOf value EUR in entity
    Then property Id matcher not method allOf value EUR in entity 
    
    Examples: 
        | responseCode|
        |         200 |  
        
        
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
    And i reuse my session
    And set property AaArrTermAmount(0)/Amount with value 26000
    And set property AaArrTermAmount(0)/Term with value 1Y
    And i execute POST request for REL input
    Then the HTTP status is 201 
    And Authorise the created record
    Then the HTTP status is 200
    Then the response is an entity