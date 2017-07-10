@Iris1 
Feature: IRIS-Fetch and verify list of currencylists and Post new record 

#Background: 
#Given Initialize the Interaction session with mediatype application/atom+xml

Scenario Outline: Verifying the list currency with valid request 

    Given I create a request with path enqCurrencyListTsts() 
    When i execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    
    Examples: 
        | responseCode|
        |         200 |
        
Scenario Outline: Verifying the list currency with valid request Arrangement 

    Given I create a request with path enqlistAaProductLines() 
    When i execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    
    Examples: 
        | responseCode|
        |         200 |
        
        
Scenario: Verifying the test currency enquiry with operators EQ 

    Given I create a request with path enqCurrencyListTsts() 
    And I set query parameter $filter=NoOfDecimals+eq+2+and+NumericCcyCode+eq+752 
    When i execute GET request 
    Then the HTTP status is 200 
    Then the response is not empty 
    Then property NoOfDecimals should be equalTo '2' in entity 
    Then property Id should be equalTo 'SEK' in entity 
    
Scenario Outline: Verifying the test currency enquiry with operators OR 

    Given I create a request with path enqCurrencyListTsts() 
    And I set query parameter $filter=Id+eq+%27EUR%27+or+Id+eq+%27USD%27 
    When i execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    Then property <currencyId> matcher <methodMatcher> method <paramMatcher> value '<currencyName>' in entity 
    
    Examples: 
        | responseCode| |currencyId||methodMatcher| |paramMatcher||currencyName|
        |         200 | |Id        ||equalTo      | |anyOf       ||EUR,USD|
        
Scenario Outline: Verifying the test currency enquiry with operators top 

    Given I create a request with path enqCurrencyListTsts() 
    And I set query parameter $top=1 
    When i execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    Then entity size should be 2 not,equalTo in entity 
    
    Examples: 
        | responseCode|
        |         200 | 
        
Scenario: Verifying the test currency enquiry with operators skip 

    Given I create a request with path enqCurrencyListTsts() 
    And I set query parameter $skip=4 
    When i execute GET request 
    Then the HTTP status is 200 
    Then the response is not empty 
    Then entity size should be 35 equalTo in entity 
    
Scenario: Verifying the test customerinfos enquiry 

    Given I create a request with path enqCustomerInfos() 
    And I set query parameter $filter=CusNo+eq+100106 
    When i execute GET request 
    Then the HTTP status is 200 
    Then the response is not empty 
    Then propertyindex 0 and CusNo should be equalTo 100106 in entity 
    
Scenario Outline: Verifying the test currency enquiry with unknown field 

    Given I create a request with path enqCurrencyListTsts() 
    And I set query parameter <uri> 
    When i execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    
    Examples: 
        |uri|            | responseCode|
        |$orderBy=foo||         200 |        
        
Scenario: Verifying the test customerinfos with zero records 

    Given I create a request with path enqCustomerInfos() 
    And I set query parameter $filter=CusNo+eq+%27foo%27 
    When i execute GET request 
    Then the HTTP status is 200 
    Then the response is empty 
    
Scenario Outline: 
    Verifying the test customerinfos enquiry to display and selection field with same name 
 
    Given I create a request with path enqCustomerInfos() 
    And I set query parameter $filter=Sect+eq+1000 
    When i execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    Then property Sect should be equalTo '1000' in entity 
    
    Examples: 
        | responseCode|
        |         200 |     
        
Scenario: Verifying the test currency enquiry with operators OR 

    Given I create a request with path enqCurrencyListTsts() 
    And I set query parameter $filter=Id+eq+%27EUR%27+or+Id+eq+%27USD%27 
    When i execute GET request 
    Then the HTTP status is 200 
    Then the response is not empty 
    Then property Id matcher startsWith method anyOf value 'E,U' in entity 
    Then property Id matcher containsString method anyOf value 'EUR,USD' in entity 
    Then property Id matcher equalTo method anyOf value 'EUR,USD' in entity 
    Then check the property Id should be notNullValue in entity response 
    Then entity property Id with value 'USD', should have NoOfDecimals value equalTo '2' 
    Then entity size should be 2 equalTo in entity 
    
Scenario Outline: 
    Verifying the test currencylists enquiry to display for specific filter 

    Given I create a request with path enqCurrencyListTsts() 
    And I set query parameter $filter=Id+eq+%27EUR%27 
    When i execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    Then entity size should be 1 greaterThanOrEqualTo in entity 
    Then entity size should be 2 not,equalTo in entity 
    Then property Id should be equalTo 'EUR' in entity 
    Then property Id should be not,equalTo 'USD' in entity 
    Then property Id matcher equalTo method allOf value 'EUR' in entity 
    Then property Id matcher not method allOf value 'USD' in entity 
    
    Examples: 
        | responseCode|
        |         200 |  
        
Scenario: Verifying the new test customer is hold using post method 

    Given I create a request with path verCustomer_InputTsts()/new 
    When i execute POST request 
    Then the HTTP status is 201 
    And set bundle customerId with session property CustomerCode value 
    And i reuse my session 
    And set property Mnemonic with bundle customerId appended to value C 
    And set property Name1MvGroup(0)/Name1 with value Mr Robin Peterson 
    And set property ShortNameMvGroup(0)/ShortName with value Rob 
    And set property Sector with value 1001 
    And set property Gender with value MALE 
    And set property Title with value MR 
    And set property FamilyName with value Peterson 
    And set property Language with value 1 
    And i execute POST request for REL hold 
    Then the HTTP status is 201 
    Then the response is an entity
    
Scenario: Verifying the new test customer is created using post method 

    Given I create a request with path verCustomer_InputTsts()/new 
    When i execute POST request 
    Then the HTTP status is 201 
    And set bundle customerId with session property CustomerCode value 
    And i reuse my session 
    And set property Mnemonic with bundle customerId appended to value C 
    And set property Name1MvGroup(0)/Name1 with value Mr Robin Peterson 
    And set property ShortNameMvGroup(0)/ShortName with value Rob 
    And set property Sector with value 1001 
    And set property Gender with value MALE 
    And set property Title with value MR 
    And set property FamilyName with value Peterson 
    And set property Language with value 1 
    And i execute POST request for REL input 
    Then the HTTP status is 201 
    Then property RecordStatus matcher equalTo method allOf value 'INAU' in entity 
    And Authorise the created record 
    Then the HTTP status is 200 
    Then property RecordStatus should be equalTo '' in entity 
    Then the response is an entity 
    
Scenario: Verifying the new test customer is deleted using delete method 

    Given I create a request with path verCustomer_InputTsts()/new 
    When i execute POST request 
    Then the HTTP status is 201 
    And set bundle customerId with session property CustomerCode value 
    And i reuse my session 
    And set property Mnemonic with bundle customerId appended to value C 
    And set property Name1MvGroup(0)/Name1 with value Mr Robin Peterson 
    And set property ShortNameMvGroup(0)/ShortName with value Rob 
    And set property Sector with value 1001 
    And set property Gender with value MALE 
    And set property Title with value MR 
    And set property FamilyName with value Peterson 
    And set property Language with value 1 
    And i execute POST request for REL input 
    Then the HTTP status is 201 
    Then property RecordStatus matcher equalTo method allOf value 'INAU' in entity 
    And I set request header If-Match with session header ETag 
    And i execute DELETE request for REL delete 
    Then the HTTP status is 200 
    Then the response is an entity 
    
Scenario: Verifying the new test FT is reverse using reverse method 

    Given I create a request with path verFundsTransfer_Inputs()/new 
    When i execute POST request 
    Then the HTTP status is 201 
    And i reuse my session 
    And set property CreditAcctNo with value 20737 
    And set property CreditAmount with value 1000 
    And set property CreditCurrency with value USD 
    And set property DebitAcctNo with value 21261 
    And set property TransactionType with value AC 
    And i execute POST request for REL validate 
    Then the HTTP status is 200 
    And i execute POST request for REL input 
    Then the HTTP status is 201 
    Then property RecordStatus matcher equalTo method allOf value 'INAU' in entity 
    And Authorise the created record 
    Then the HTTP status is 200 
    Then property RecordStatus should be equalTo '' in entity 
    Then the response is an entity 
    And I set request header If-Match with session header ETag 
    And i execute PUT request for REL reverse 
    Then the HTTP status is 200 
    And Authorise the created record with different user 
    Then the HTTP status is 200 
    Then property RecordStatus should be equalTo 'REVE' in entity 
    Then the response is an entity 
    
    
    # TODO - Need to do JSON PArser changes on USer-Agent Generic Library for AA POST multiple entity            
    #Scenario: Verifying the new Arrangement Activity is created using post method 
    #
    #    Given I create a request with path verAaArrangementActivity_AaNewTsts()/new
    #    And I set query parameter Activity=LENDING-NEW-ARRANGEMENT&Product=MARGIN.LOAN
    #    When i execute POST request
    #    Then the HTTP status is 201
    #    And set bundle aaId with session property ArrSequence value
    #    And Create test Customer
    #    And set bundle customerId with session property CustomerMvGroup(0)/Customer value
    #    And set property Currency with value USD
    #    And i execute POST request for REL aapopulate
    #    And i reuse my session
    #    And set property AaArrTermAmount(0)/Amount with value 26000
    #    And set property AaArrTermAmount(0)/Term with value 1Y
    #    And i execute POST request for REL input
    #    Then the HTTP status is 201 
    #    And Authorise the created record
    #    Then the HTTP status is 200
    #    Then the response is an entity
    
    
    # TODO - DRILL DOWN Enquiry scripts        
    #Scenario: Verifying the drilldown enquiry for customerinfos 
    #
    #    Given a scenario Verifying the drilldown enquiry for customerinfos 
    #    And a description is To validate a drilldown enquiry for customerinfos 
    #    When i execute GET request with resource enqCustomerInfo 
    #    Then the response for drilldown enquiry is not empty for parameters 100460,self,enqCustomerInfo,enqCustomerInfos,Record View,http://temenostech.temenos.com/rels/see,CusNo/verCustomer,verCustomers,/see?t24Intent=View 