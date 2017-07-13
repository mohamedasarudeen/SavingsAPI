package com.temenos.interaction.cucumber.rest.step;

import static com.temenos.interaction.cucumber.core.MatcherUtility.*;
import static com.temenos.interaction.cucumber.rest.step.StepDefinitonBase.executeRequest;
import static com.temenos.interaction.cucumber.rest.step.StepDefinitonBase.getInteractionSession;
import static com.temenos.interaction.cucumber.rest.step.StepDefinitonBase.getInteractionSessionURL;
import static com.temenos.interaction.cucumber.rest.step.StepDefinitonBase.getScenarioBundle;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matcher;

import com.temenos.interaction.cucumber.core.ScenarioBundle;
import com.temenos.interaction.cucumber.test.EndpointConfig;
import com.temenos.interaction.cucumber.test.T24Helper;
import com.temenos.useragent.generic.Entity;
import com.temenos.useragent.generic.InteractionSession;
import com.temenos.useragent.generic.PayloadHandler;
import com.temenos.useragent.generic.internal.ActionableLink;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Step definition generic methods with assertions to validate the corresponding
 * feature file scenarios
 * 
 * @author mohamedasarudeen
 *
 */

public class StepDefinitionCommonITCase {

    InteractionSession session;
    Scenario scenario;
    ScenarioBundle bundle;

    @Before
    public void setup(Scenario scenario) {
        this.scenario = scenario;
        this.session = getInteractionSession(scenario);
        this.bundle = getScenarioBundle(scenario);
    }

    /**
     * Set Base uri params passed from feature file
     * 
     * @param baseURI
     */
    @Given("I set BASE_URI as '(.*)'$")
    public void interactionSession_setBaseUri(String baseURI) {
        getInteractionSessionURL(scenario, session).baseuri(baseURI);
    }

    /**
     * Set version or enquiry sub url params
     * 
     * @param postServicePath
     */

    @Given("I create a request with path '(.*)'$")
    public void interactionSession_setPath(String postServicePath) {
        getInteractionSessionURL(scenario, session).path(postServicePath);
    }

    /**
     * Set filter or query params encode with base url and version/enquiry url
     * 
     * @param servicePath
     */
    @Given("I set query parameter '(.*)'$")
    public void interactionSession_setUrlParams(String servicePath) {
        getInteractionSessionURL(scenario, session).queryParam(servicePath);
    }

    /**
     * Set Content-Type/Accept Header params
     * 
     * @param key
     * @param value
     * @throws Throwable
     */
    @Given("^I set request header (.*) with value '(.*)'$")
    public void givenRequestHeader(String key, String value) throws Throwable {
        session.header(key, value);
    }

    /**
     * Set username and password from basicAuth params
     * 
     * @param username
     * @param password
     * @throws Throwable
     */

    @Given("^I set username '(.*)' with password '(.*)'$")
    public void givenRequestBasicAuth(String username, String password) throws Throwable {
        session.basicAuth(username, password);
    }

    /**
     * Set media-type format as params example application/json Example -And I
     * set media-type application/json with handler
     * com.temenos.useragent.generic.mediatype.HalJsonPayloadHandler
     * 
     * @param mediaType
     * @param klassName
     */
    @Given("^I set media-type (.*) with handler (.*)$")
    public void givenRegisterHandler(String mediaType, String klassName) {
        Class<?> klass = null;
        try {
            klass = this.getClass().forName(klassName);
        } catch (ClassNotFoundException e) {
            fail(e.getMessage());
        }
        assertNotNull(klassName);
        session.registerHandler(mediaType, (Class<? extends PayloadHandler>) klass);
    }

    /**
     * To Set ETag value from session in header
     * 
     * @param key
     * @param headerKey
     * @throws Throwable
     */
    @Given("^I set request header (.*) with session header (.*)$")
    public void givenRequestHeaderSession(String key, String headerKey) throws Throwable {
        session.header(key, session.header(headerKey));
    }

    /**
     * To reuse the interactionSession
     * 
     */
    @And("^I reuse my session$")
    public void interactionSession_reuse() {
        session.reuse();
    }

    /**
     * Set bundle value with session property value, ex. customerId with session
     * property 'CustomerCode' value
     * 
     * @param propertyName
     * @param sessionProperty
     */

    @When("^I set bundle (.*) with session property '(.*)' value$")
    public void interactionSession_setBundleValue(String propertyName, String sessionProperty) {
        bundle.put(propertyName, session.entities().item().get(sessionProperty));
    }

    /**
     * Set mandatory values for post new record
     * 
     * @param propertyName
     * @param propertyValue
     */

    @When("^I set property (.*) with value '(.*)'$")
    public void interactionSession_setPropertyValue(String propertyName, String propertyValue) {
        session.set(propertyName, propertyValue);
    }

    /**
     * TODO - methods to use created test customer using for other application
     * like ArrangementActivity
     * 
     * @param propertyName
     * @param bundleId
     */
    /*
     * @When("^set property (.*) with test (.*)$") public void
     * interactionSession_setPropertyBundleValue(String propertyName, String
     * bundleId) { assertTrue(bundle.containsKey(bundleId)); String bundleValue
     * = bundle.getString(bundleId); assertNotNull(bundleValue);
     * session.set(propertyName, bundleValue); }
     */

    /**
     * Set mandatory values used from bundle and appended to another property
     * (Ex. Customer id is appended to Mnemonic)
     * 
     * @param propertyName
     * @param bundleId
     * @param propertyValue
     */

    @When("^I set property (.*) with bundle (.*) appended to value '(.*)'$")
    public void interactionSession_setPropertyValue(String propertyName, String bundleId, String propertyValue) {
        assertTrue(bundle.containsKey(bundleId));
        String bundleValue = bundle.getString(bundleId);
        assertNotNull(bundleValue);
        session.set(propertyName, propertyValue.concat(bundleValue));
    }

    /**
     * Execute different HTTP methods like GET,POST,PUT,DELETE
     * 
     * @param httpMethod
     */

    @When("^I execute (GET|POST|PUT|DELETE) request$")
    public void executeRequest_new(String httpMethod) throws Throwable {
        executeRequest(scenario, session, httpMethod);

    }

    /**
     * Execute different HTTP methods like GET,POST,PUT,DELETE for REL urls REL
     * and override handling validate,input,authorise,reverse,delete etc.
     * 
     * @param httpMethod
     * @param rel
     */
    @When("^I execute (GET|POST|PUT|DELETE) request for REL (.*)$")
    public void executeRequest_rel(String httpMethod, String rel) throws Throwable {
        if (StringUtils.isEmpty(rel)) {
            throw new IllegalArgumentException("REL not provided");
        }
        ActionableLink link = session.entities().item().links().byRel(EndpointConfig.getRelPrefix() + rel);
        assertNotNull(link);
        executeRequest(scenario, link.url(), httpMethod);
        T24Helper.handleOverrideAcceptance(session, "", link.url(), T24Helper.getOverrideCodes(session));
    }

    /**
     * Check response is an entity or item
     */

    @Then("^the response is an entity$")
    public void the_response_is_an_entity() throws Throwable {
        assertTrue(session.entities().isItem());
        Entity response = session.entities().item();
        assertNotNull(response);
    }

    /**
     * Check HTTP Status Code
     * 
     * @param responseCode
     */
    @Then("^the HTTP status is (.*)$")
    public void the_HTTP_status_is(int responseCode) throws Throwable {

        int responseCodeBVar = session.result().code();
        assertEquals(responseCode, responseCodeBVar);
    }

    /**
     * Check response is an collection or multiple entity
     */
    @Then("^the response is an collection$")
    public void the_response_is_collection() throws Throwable {
        assertTrue(session.entities().isCollection());
        List<? extends Entity> response = session.entities().collection();
        assertNotNull(response);
    }

    /**
     * Check response is not empty and collection size is greater than zero
     */

    @Then("^the response is not empty$")
    public void the_response_is_not_empty() throws Throwable {

        int responseCollectionSize = session.entities().collection().size();
        assertThat(responseCollectionSize, greaterThan(0));
    }

    /**
     * Check response is empty and collection size is equalTo zero
     */

    @Then("^the response is empty$")
    public void the_response_is_empty() throws Throwable {
        assertThat(session.entities().collection().size(), equalTo(0));
    }

    /**
     * Check response of each property is not empty (Ex. propertyId's value id
     * Currency should have value like USD)
     * 
     * @param propertyId
     */

    @Then("^the response of each property values is not empty '(.*)'$")
    public void the_response_of_each_property_values_is_not_empty(String propertyId) throws Throwable {

        assertThat(session.entities().collection().size(), greaterThan(0));
        for (Entity entity : session.entities().collection()) {
            assertFalse(StringUtils.isEmpty(entity.get(propertyId)));
        }
    }

    /**
     * Check property id and value matches in collection response Example - Then
     * property Id matcher equalTo method anyOf value 'USD' in entity
     * 
     * @param propertyId
     * @param matchMethod
     * @param matcherArg
     * @param propertyValue
     * @throws Throwable
     */
    @Then("^property (.*) matcher (.*) method (.*) value '(.*)' in entity$")
    public void property_value_validate_collection(String propertyId, String matchMethod, String matcherArg,
            String propertyValue) throws Throwable {
        String[] paramSplit = propertyValue.split(",");
        // assertThat(session.entities().collection().size(),
        // equalTo(paramSplit.length));
        Matcher<String>[] params = new Matcher[paramSplit.length];
        int count = 0;
        while (paramSplit.length > count
                && (params[count] = (Matcher<String>) getMatcherFunction(Arrays.asList(matchMethod),
                        paramSplit[count++])) != null)
            ;
        Matcher matcher = getMatcherFunction(Arrays.asList(matcherArg), params);
        if (session.entities().isCollection()) {
            for (Entity entity : session.entities().collection()) {
                assertThat(entity.get(propertyId), matcher);
            }
        } else {
            Entity entity = session.entities().item();
            assertThat(entity.get(propertyId), matcher);
        }
    }

    /**
     * Check property id and value matches in entity response Example - Then
     * property Id should be equalTo 'EUR' in entity
     * 
     * @param propertyId
     * @param matchMethod
     * @param propertyValue
     * @throws Throwable
     */
    @Then("^property (.*) should be (.*) '(.*)' in entity$")
    public void property_value_validate_in_entity(String propertyId, ArrayList<String> matchMethod, String propertyValue)
            throws Throwable {
        Matcher<String> matcher = (Matcher<String>) getMatcherFunction(matchMethod, propertyValue);
        runMatchAssertion(session, propertyId, matcher);
    }

    /**
     * Check the property is null or not in response Example - Then check the
     * property Id should be notNullValue in entity response
     * 
     * @param propertyId
     * @param matchMethod
     * @throws Throwable
     */
    @Then("^check the property (.*) should be (.*) in entity response$")
    public void property_nullfield_validate_entity(String propertyId, ArrayList<String> matchMethod) throws Throwable {
        Matcher<String> matcher = (Matcher<String>) getMatcherFunction(matchMethod, "");
        runMatchAssertion(session, propertyId, matcher);
    }

    /**
     * Check if one property is asserted fine then another property value is
     * validated in response Example - Then entity property Id with value 'USD'
     * and should have NoOfDecimals value equalTo '2'
     * 
     * @param key
     * @param value
     * @param targetKey
     * @param matchMethod
     * @param targetValue
     * @throws Throwable
     */
    @Then("^entity property (.*) with value '(.*)' and should have (.*) value (.*) '(.*)'$")
    public void entity_property_condition_validate(String key, String value, String targetKey,
            ArrayList<String> matchMethod, String targetValue) throws Throwable {
        Entity targetEntity = null;
        for (Entity entity : session.entities().collection()) {
            if (entity.get(key) != null && entity.get(key).equals(value)) {
                targetEntity = entity;
                break;
            }
        }
        assertNotNull(targetEntity);
        assertThat(targetEntity.get(targetKey), getMatcherFunction(matchMethod, targetValue));
    }

    /**
     * Checking property value assertion based on index position in response
     * Example - Then propertyindex 0 and CusNo should be equalTo '100106' in
     * entity
     * 
     * @param index
     * @param propertyId
     * @param matchMethod
     * @param propertyValue
     * @throws Throwable
     */
    @Then("^propertyindex (.*) and (.*) should be (.*) '(.*)' in entity$")
    public void property_index_value_validate_entity(String index, String propertyId, String matchMethod,
            String propertyValue) throws Throwable {
        Matcher<String> matcher = (Matcher<String>) getMatcherFunction(Arrays.asList(matchMethod), propertyValue);
        assertThat(session.entities().collection().get(Integer.parseInt(index)).get(propertyId), matcher);
    }

    /**
     * Check property vaue assertion based on total collection size Example -
     * Then entity size should be equalTo '35' in entity
     * 
     * @param matchMethod
     * @param propertyValue
     * @throws Throwable
     */
    @Then("^entity size should be (.*) '(\\d+)' in entity$")
    public void entity_size_validate(ArrayList<String> matchMethod, int propertyValue) throws Throwable {
        int entitiesCount = session.entities().collection().size();
        Matcher<Integer> matcher = getMatcherFunction(matchMethod, propertyValue);
        assertThat(entitiesCount, matcher);
    }

    /**
     * Checking one property value is asserted in any item in collection Example
     * - Then property Product should be equalTo value 'A.FIXED.TAKING' in any
     * item in collection
     * 
     * @param propertyId
     * @param matchMethod
     * @param propertyValue
     * @throws Throwable
     */
    @Then("^property (.*) should be (.*) value '(.*)' in any item in collection$")
    public void property_in_any_item_in_collection(String propertyId, String matchMethod, String propertyValue)
            throws Throwable {
        Matcher matcher = getMatcherFunction(Arrays.asList(matchMethod.split(",")), propertyValue);
        if (session.entities().isCollection()) {
            boolean hasMatched = false;
            for (Entity entity : session.entities().collection()) {
                if (hasMatched = matcher.matches(entity.get(propertyId))) {
                    break;
                }
            }
            assertThat("Expected propertyValue " + propertyValue
                    + " is not matched with Actual Value in response for propertyId " + propertyId, true,
                    equalTo(hasMatched));
        } else {
            Entity entity = session.entities().item();
            assertThat(entity.get(propertyId), matcher);
        }
    }

    /**
     * Below three methods are created for only Savings API enquiry response
     */

    /**
     * It will validate in SavingsAPI scenarios, Example - Then property
     * CustomerId should be '1014' in all entities on Holder params - Key is the
     * Holder
     * 
     * @param propertyId
     * @param propertyValue
     * @param key
     * @throws Throwable
     */
    @Then("^property (.*) should be '(.*)' in all entities on (.*)$")
    public void property_should_be_in_all_entities(String propertyId, String propertyValue, String key)
            throws Throwable {
        for (Entity entity : session.entities().collection()) {
            int keyCount = entity.count(key);
            if (keyCount <= 0) {
                fail("PropertyId got from all entities is mandatory but not found in response");
            }
            for (int i = 0; i < keyCount; i++) {
                assertFalse(StringUtils.isEmpty(entity.get("" + key + "(" + i + ")" + "/" + propertyId + "")));
                assertEquals(propertyValue, entity.get("" + key + "(" + i + ")" + "/" + propertyId + ""));
            }
        }
    }

    /**
     * It will validate in SavingsAPI scenarios, Example - Then property
     * CustomerId should be '1014' in entity on Holder params - Key is the
     * Holder
     * 
     * @param propertyId
     * @param propertyValue
     * @param key
     * @throws Throwable
     */
    @Then("^property (.*) should be '(.*)' in entity on (.*)$")
    public void property_should_be_in_entity_on(String propertyId, String propertyValue, String key) throws Throwable {
        for (Entity entity : session.entities().collection()) {
            int keyCount = entity.count(key);
            if (keyCount <= 0) {
                fail("PropertyId got from particular entity is mandatory but not found in response");
            }
            for (int i = 0; i < keyCount; i++) {
                assertFalse(StringUtils.isEmpty(entity.get("" + key + "(" + i + ")" + "/" + propertyId + "")));
                assertEquals(propertyValue, entity.get("" + key + "(" + 0 + ")" + "/" + propertyId + ""));
            }
        }
    }

    /**
     * It will validate in SavingsAPI scenarios, Example - Then property
     * CustomerId should be '1014' in at least one entity on Holder params - Key
     * is the Holder
     * 
     * @param propertyId
     * @param propertyValue
     * @param key
     * @throws Throwable
     */
    @Then("^property (.*) should be '(.*)' in at least one entity on (.*)$")
    public void property_should_be_in_at_least_one_entity(String propertyId, String propertyValue, String key)
            throws Throwable {
        for (Entity entity : session.entities().collection()) {
            int keyCount = entity.count(key);
            if (keyCount <= 0) {
                fail("propertyId got from atleast one entity is mandatory but not found in response");
            }
            for (int i = 0; i < keyCount; i++) {
                assertFalse(StringUtils.isEmpty(entity.get("" + key + "(" + i + ")" + "/" + propertyId + "")));
                assertNotEquals(propertyValue, entity.get("" + key + "(" + i + ")" + "/" + propertyId + "").isEmpty());
            }
        }
    }

    /**
     * Code commented below used for HTML Dashboard report
     */
    /*
     * @After public static void cleanup() throws Exception {
     * aftercase(testCaseName, testCaseDescription); }
     * 
     * @Given("^a scenario (.*)$") public void a_scenario(String scenarioName)
     * throws Throwable { testCaseName = scenarioName; beforecase(testCaseName);
     * }
     * 
     * @Given("^a description is (.*)$") public void a_description(String
     * scenarioDescription) { testCaseDescription = scenarioDescription; }
     */

}