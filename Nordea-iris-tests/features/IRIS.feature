@Iris 
Feature: IRIS-Fetch and verify list of currencylists and Post new record 

#Background: 
#Given Initialize the Interaction session with mediatype application/atom+xml

Scenario Outline: Verifying the list currency with valid request 
    Given a scenario Verifying the list currency enquiry 
    And a description is To validate a currency list enquiry with valid request 
    And Set Interaction Session path as enqCurrencyListTsts() 
    When i execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    
    Examples: 
        | responseCode|
        |         200 |
        
Scenario Outline: Verifying the list currency with valid request Arrangement 
    Given a scenario Verifying the list currency enquiry 
    And a description is To validate a currency list enquiry with valid request 
    And Set Interaction Session path as enqlistAaProductLines() 
    When i execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    
    Examples: 
        | responseCode|
        |         200 |
        
        
Scenario: Verifying the test currency enquiry with operators EQ 

    Given a scenario Verifying the test currency enquiry with operators EQ 
    And a description is To validate a currency list enquiry with Equal operators 
    And Set Interaction Session path as enqCurrencyListTsts() 
    Given Set Interaction Session query parameters as $filter=NoOfDecimals+eq+2+and+NumericCcyCode+eq+752 
    When i execute GET request 
    Then the HTTP status is 200 
    Then the response is not empty 
    Then property NoOfDecimals should be equalTo 2 in entity Z 
    Then property NumericCcyCode should be equalTo 752 in entity Z 
    Then property Id should be equalTo SEK in entity Z 
    
Scenario: Verifying the new test customer is hold using post method 

    Given a scenario Verifying the new test customer creation 
    And a description is To validate a new customer is created with mandatory fields and values 
    Given Set Interaction Session path as verCustomer_InputTsts()/new 
    When i execute POST request 
    Then the HTTP status is 201 
    And set bundle customerId with session property CustomerCode value
    And i reuse Interaction Session 
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
    
Scenario: Verifying the new test customer is created using post method 

    Given a scenario Verifying the new test customer creation 
    And a description is To validate a new customer is created with mandatory fields and values 
    Given Set Interaction Session path as verCustomer_InputTsts()/new 
    When i execute POST request 
    Then the HTTP status is 201
    And set bundle customerId with session property CustomerCode value
    And i reuse Interaction Session 
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
    And Authorise the created record 
    Then the HTTP status is 200 
    Then the response is an entity 
    
Scenario Outline: Verifying the test currency enquiry with operators OR 

    Given a scenario Verifying the test currency enquiry with operators OR 
    And a description is To validate a currency list enquiry with OR operators 
    And Set Interaction Session path as enqCurrencyListTsts() 
    Given Set Interaction Session query parameters as $filter=Id+eq+%27EUR%27+or+Id+eq+%27USD%27 
    When i execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    Then property <currencyId> should be <methodMatcher> anyofparam matcher <currencyName> in entity Y 
    
    Examples: 
        | responseCode| |currencyId||currencyName||methodMatcher|
        |         200 | |Id      ||EUR,USD|   |equalTo|   
        
Scenario Outline: Verifying the test currency enquiry with operators top 

    Given a scenario Verifying the test currency enquiry with operators top 
    And a description is To validate a currency list enquiry with top operators 
    And Set Interaction Session path as enqCurrencyListTsts() 
    Given Set Interaction Session query parameters as $top=1 
    When i execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    Then entity size should be 2 not,equalTo in entity Z 
    
    Examples: 
        | responseCode|
        |         200 | 
        
Scenario: Verifying the test currency enquiry with operators skip 

    Given a scenario Verifying the test currency enquiry with operators skip 
    And a description is To validate a currency list enquiry with skip operators 
    And Set Interaction Session path as enqCurrencyListTsts() 
    Given Set Interaction Session query parameters as $skip=4 
    When i execute GET request 
    Then the HTTP status is 200 
    Then the response is not empty 
    Then entity size should be 35 equalTo in entity Z 
    
Scenario: Verifying the test customerinfos enquiry 

    Given a scenario Verifying the test customerinfos enquiry 
    And a description is To validate a customerinfos enquiry with parameters customerid 
    And Set Interaction Session path as enqCustomerInfos() 
    Given Set Interaction Session query parameters as $filter=CusNo+eq+100106 
    When i execute GET request 
    Then the HTTP status is 200 
    Then the response is not empty 
    Then propertyindex 0 and CusNo should be equalTo 100106 in entity X 
    
Scenario Outline: Verifying the test currency enquiry with unknown field 

    Given a scenario Verifying the test currency enquiry with unknown field 
    And a description is To validate a currency list enquiry with unknown fields 
    And Set Interaction Session path as enqCurrencyListTsts() 
    Given Set Interaction Session query parameters as <uri> 
    When i execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    
    Examples: 
        |uri|            | responseCode|
        |$orderBy=foo||         200 |        
        
Scenario: Verifying the test customerinfos with zero records 

    Given a scenario Verifying the test test customerinfos with zero records 
    And a description is To validate a customerinfos with zero records 
    And Set Interaction Session path as enqCustomerInfos() 
    Given Set Interaction Session query parameters as $filter=CusNo+eq+%27foo%27 
    When i execute GET request 
    Then the HTTP status is 200 
    Then the response is empty 
    
Scenario Outline:
Verifying the test customerinfos enquiry to display and selection field with same name 

    Given a scenario Verifying the customerinfos enquiry to display and selection field with same name 
    And a description is To validate CUSTOMER.INFO Enquiry has been modified to have Sect field, which has same name as Custom-Selection having SECTOR 
    And Set Interaction Session path as enqCustomerInfos() 
    Given Set Interaction Session query parameters as $filter=Sect+eq+1000 
    When i execute GET request 
    Then the HTTP status is <responseCode> 
    Then the response is not empty 
    Then property Sect should be equalTo 1000 in entity Z 
    
    Examples: 
        | responseCode|
        |         200 |     
        
        # TODO - DRILL DOWN Enquiry scripts        
        #Scenario: Verifying the drilldown enquiry for customerinfos 
        #
        #    Given a scenario Verifying the drilldown enquiry for customerinfos 
        #    And a description is To validate a drilldown enquiry for customerinfos 
        #    When i execute GET request with resource enqCustomerInfo 
        #    Then the response for drilldown enquiry is not empty for parameters 100460,self,enqCustomerInfo,enqCustomerInfos,Record View,http://temenostech.temenos.com/rels/see,CusNo/verCustomer,verCustomers,/see?t24Intent=View 