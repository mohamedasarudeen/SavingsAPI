@Iris1 
Feature: IRIS-Fetch and verify list of enquiries and Post new version 

Scenario Outline: Verifying the list currency with valid request 

    Given I create a request with path 'enqCurrencyListTsts()' 
    When I execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    
    Examples: 
        | responseCode|
        |         200 |
        
Scenario Outline: Verifying the list currency with valid request Arrangement 

    Given I create a request with path 'enqlistAaProductLines()' 
    When I execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    
    Examples: 
        | responseCode|
        |         200 |
        
        
Scenario: Verifying the test currency enquiry with operators EQ 

    Given I create a request with path 'enqCurrencyListTsts()' 
    And I set query parameter '$filter=NoOfDecimals+eq+2+and+NumericCcyCode+eq+752' 
    When I execute GET request 
    Then the HTTP status is 200 
    Then the response is not empty 
    Then property NoOfDecimals should be equalTo '2' in entity 
    Then property Id should be equalTo 'SEK' in entity 
    
Scenario Outline: Verifying the test currency enquiry with operators OR 

    Given I create a request with path 'enqCurrencyListTsts()' 
    And I set query parameter '$filter=Id+eq+%27EUR%27+or+Id+eq+%27USD%27' 
    When I execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    Then property <currencyId> matcher <methodMatcher> method <paramMatcher> value '<currencyName>' in entity 
    
    Examples: 
        | responseCode| |currencyId||methodMatcher| |paramMatcher||currencyName|
        |         200 | |Id        ||equalTo      | |anyOf       ||EUR,USD|
        
Scenario Outline: Verifying the test currency enquiry with operators top 

    Given I create a request with path 'enqCurrencyListTsts()' 
    And I set query parameter '$top=1' 
    When I execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    Then entity size should be not,equalTo '2' in entity 
    
    Examples: 
        | responseCode|
        |         200 | 
        
Scenario: Verifying the test currency enquiry with operators skip 

    Given I create a request with path 'enqCurrencyListTsts()' 
    And I set query parameter '$skip=4' 
    When I execute GET request 
    Then the HTTP status is 200 
    Then the response is not empty 
    Then entity size should be equalTo '35' in entity 
    
Scenario: Verifying the test customerinfos enquiry 

    Given I create a request with path 'enqCustomerInfos()' 
    And I set query parameter '$filter=CusNo+eq+100106' 
    When I execute GET request 
    Then the HTTP status is 200 
    Then the response is not empty 
    Then propertyindex 0 and CusNo should be equalTo '100106' in entity 
    
Scenario Outline: Verifying the test currency enquiry with unknown field 

    Given I create a request with path 'enqCurrencyListTsts()' 
    And I set query parameter '<uri>' 
    When I execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    
    Examples: 
        |uri|            | responseCode|
        |$orderBy=foo||         200 |        
        
Scenario: Verifying the test customerinfos with zero records 

    Given I create a request with path 'enqCustomerInfos()' 
    And I set query parameter '$filter=CusNo+eq+%27foo%27' 
    When I execute GET request 
    Then the HTTP status is 200 
    Then the response is empty 
    
Scenario Outline: 
    Verifying the test customerinfos enquiry to display and selection field with same name 
 
    Given I create a request with path 'enqCustomerInfos()' 
    And I set query parameter '$filter=Sect+eq+1000' 
    When I execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    Then property Sect should be equalTo '1000' in entity 
    
    Examples: 
        | responseCode|
        |         200 |     
        
Scenario: Verifying the test currency enquiry with operators OR 

    Given I create a request with path 'enqCurrencyListTsts()' 
    And I set query parameter '$filter=Id+eq+%27EUR%27+or+Id+eq+%27USD%27' 
    When I execute GET request 
    Then the HTTP status is 200 
    Then the response is not empty 
    Then property Id matcher startsWith method anyOf value 'E,U' in entity 
    Then property Id matcher containsString method anyOf value 'EUR,USD' in entity 
    Then property Id matcher equalTo method anyOf value 'EUR,USD' in entity 
    Then check the property Id should be notNullValue in entity response 
    Then entity property Id with value 'USD' and should have NoOfDecimals value equalTo '2' 
    Then entity size should be equalTo '2' in entity 
    
Scenario Outline: 
    Verifying the test currencylists enquiry to display for specific filter 

    Given I create a request with path 'enqCurrencyListTsts()' 
    And I set query parameter '$filter=Id+eq+%27EUR%27' 
    When I execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    Then entity size should be greaterThanOrEqualTo '1' in entity 
    Then entity size should be not,equalTo '2' in entity 
    Then property Id should be equalTo 'EUR' in entity 
    Then property Id should be not,equalTo 'USD' in entity 
    Then property Id matcher equalTo method allOf value 'EUR' in entity 
    Then property Id matcher not method allOf value 'USD' in entity
        
    Examples: 
        | responseCode|
        |         200 |  
        
Scenario: Verifying the new test customer is hold using post method 

    Given I create a request with path 'verCustomer_InputTsts()/new' 
    When I execute POST request 
    Then the HTTP status is 201 
    And I set bundle customerId with session property 'CustomerCode' value 
    And I reuse my session 
    And I set property Mnemonic with bundle customerId appended to value 'C' 
    And I set property Name1MvGroup(0)/Name1 with value 'Mr Robin Peterson' 
    And I set property ShortNameMvGroup(0)/ShortName with value 'Rob' 
    And I set property Sector with value '1001' 
    And I set property Gender with value 'MALE' 
    And I set property Title with value 'MR' 
    And I set property FamilyName with value 'Peterson' 
    And I set property Language with value '1' 
    And I execute POST request for REL hold 
    Then the HTTP status is 201 
    Then the response is an entity
      
Scenario: Verifying the new test customer is created using post method 

    Given I create a request with path 'verCustomer_InputTsts()/new' 
    When I execute POST request 
    Then the HTTP status is 201 
    And I set bundle customerId with session property 'CustomerCode' value 
    And I reuse my session 
    And I set property Mnemonic with bundle customerId appended to value 'C' 
    And I set property Name1MvGroup(0)/Name1 with value 'Mr Robin Peterson' 
    And I set property ShortNameMvGroup(0)/ShortName with value 'Rob' 
    And I set property Sector with value '1001' 
    And I set property Gender with value 'MALE' 
    And I set property Title with value 'MR' 
    And I set property FamilyName with value 'Peterson' 
    And I set property Language with value '1' 
    And I execute POST request for REL input 
    Then the HTTP status is 201 
    Then property RecordStatus matcher equalTo method allOf value 'INAU' in entity 
    And Authorise the created record by user AUTHOR 
    Then the HTTP status is 200 
    Then property RecordStatus should be equalTo '' in entity 
    Then the response is an entity 
    
Scenario: Verifying the new test customer is deleted using delete method 

    Given I create a request with path 'verCustomer_InputTsts()/new' 
    When I execute POST request 
    Then the HTTP status is 201 
    And I set bundle customerId with session property 'CustomerCode' value 
    And I reuse my session 
    And I set property Mnemonic with bundle customerId appended to value 'C' 
    And I set property Name1MvGroup(0)/Name1 with value 'Mr Robin Peterson' 
    And I set property ShortNameMvGroup(0)/ShortName with value 'Rob' 
    And I set property Sector with value '1001' 
    And I set property Gender with value 'MALE' 
    And I set property Title with value 'MR' 
    And I set property FamilyName with value 'Peterson' 
    And I set property Language with value '1' 
    And I execute POST request for REL input 
    Then the HTTP status is 201 
    Then property RecordStatus matcher equalTo method allOf value 'INAU' in entity 
    And I set request header If-Match with session header ETag 
    And I execute DELETE request for REL delete 
    Then the HTTP status is 200 
    Then the response is an entity 
    
Scenario: Verifying the new test FT is reverse using reverse method 

# Given I set Interaction Session BASE_URI as http://10.93.24.60:9089/hothouse-iris/Hothouse.svc/GB0010001
    Given I create a request with path 'verFundsTransfer_Inputs()/new' 
    When I set request header Content-Type with value 'application/atom+xml' 
    When I set request header Accept with value 'application/atom+xml' 
    #When I set request header Content-Type with value 'application/json'
    #When I set request header Accept with value 'application/json'
    #And I set media-type application/json with handler com.temenos.useragent.generic.mediatype.HalJsonPayloadHandler 
    When I execute POST request 
    Then the HTTP status is 201 
    And I reuse my session 
    And I set property TransactionType with value 'AC' 
    And I set property CreditAcctNo with value '20737' 
    And I set property CreditAmount with value '1000' 
    And I set property CreditCurrency with value 'USD' 
    And I set property DebitAcctNo with value '21261' 
    And I execute POST request for REL validate 
    Then the HTTP status is 200 
    And I execute POST request for REL input 
    Then the HTTP status is 201 
    Then property RecordStatus matcher equalTo method allOf value 'INAU' in entity 
    And Authorise the created record by user AUTHOR 
    Then the HTTP status is 200 
    Then property RecordStatus should be equalTo '' in entity 
    Then the response is an entity 
    And I set request header If-Match with session header ETag 
    And I execute PUT request for REL reverse 
    Then the HTTP status is 200 
    When I set username 'INPUTT' with password '123456' 
    And I set request header If-Match with session header ETag 
    And I execute PUT request for REL authorise 
    Then the HTTP status is 200 
    Then property RecordStatus should be equalTo 'REVE' in entity 
    Then the response is an entity 

    
    