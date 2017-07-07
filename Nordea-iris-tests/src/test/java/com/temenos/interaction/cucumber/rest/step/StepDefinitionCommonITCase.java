package com.temenos.interaction.cucumber.rest.step;

import static com.temenos.interaction.cucumber.core.MatcherUtility.*;
import static com.temenos.interaction.cucumber.core.MatcherUtility.runMatchAssertion;
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
import com.temenos.useragent.generic.internal.ActionableLink;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Step definition generic methods to validate the corresponding feature file
 * scenarios
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

    /*    @After
    public static void cleanup() throws Exception {
        aftercase(testCaseName, testCaseDescription);
    }

   @Given("^a scenario (.*)$")
    public void a_scenario(String scenarioName) throws Throwable {
        testCaseName = scenarioName;
        beforecase(testCaseName);
    }

    @Given("^a description is (.*)$")
    public void a_description(String scenarioDescription) {
        testCaseDescription = scenarioDescription;
    }*/

    @Given("Set Interaction Session BASE_URI as (.*)$")
    public void interactionSession_setBaseUri(String baseURI) {
        getInteractionSessionURL(scenario, session).baseuri(baseURI);
    }

    @Given("I create a request with path (.*)$")
    public void interactionSession_setPath(String postServicePath) {
        getInteractionSessionURL(scenario, session).path(postServicePath);
    }

    @Given("I set query parameter (.*)$")
    public void interactionSession_setUrlParams(String servicePath) {
        getInteractionSessionURL(scenario, session).queryParam(servicePath);
    }
    
    @Given("^I set request header (.*) with value (.*)$")
    public void givenRequestHeader(String key, String value) throws Throwable {
        session.header(key, value);
    }
    
    @And("^i reuse my session$")
    public void interactionSession_reuse() {
        session.reuse();
    }
    
    @When("^set bundle (.*) with session property (.*) value$")
    public void interactionSession_setBundleValue(String propertyName, String sessionProperty) {
        bundle.put(propertyName, session.entities().item().get(sessionProperty));
    }

    @When("^set property (.*) with value (.*)$")
    public void interactionSession_setPropertyValue(String propertyName, String propertyValue) {
        session.set(propertyName, propertyValue);
    }
    
/*    @When("^set property (.*) with test (.*)$")
    public void interactionSession_setPropertyBundleValue(String propertyName, String bundleId) {
        assertTrue(bundle.containsKey(bundleId));
        String bundleValue = bundle.getString(bundleId);
        assertNotNull(bundleValue);
        session.set(propertyName, bundleValue);
    }
*/       
    
    @When("^set property (.*) with bundle (.*) appended to value (.*)$")
    public void interactionSession_setPropertyValue(String propertyName, String bundleId, String propertyValue) {
        assertTrue(bundle.containsKey(bundleId));
        String bundleValue = bundle.getString(bundleId);
        assertNotNull(bundleValue);
        session.set(propertyName, propertyValue.concat(bundleValue));
    }

    @When("^i execute (GET|POST|PUT|DELETE) request$")
    public void executeRequest_new(String httpMethod) throws Throwable {
        executeRequest(scenario, session, httpMethod);

    }

    @When("^i execute (GET|POST|PUT|DELETE) request for REL (.*)$")
    public void executeRequest_rel(String httpMethod, String rel) throws Throwable {
        if (StringUtils.isEmpty(rel)) {
            throw new IllegalArgumentException("REL not provided");
        }
        ActionableLink link = session.entities().item().links().byRel(EndpointConfig.getRelPrefix() + rel);
        assertNotNull(link);
        executeRequest(scenario, link.url(), httpMethod);
        // TODO: 2nd params required for XML, whereas JSON doesn't require this
        T24Helper.handleOverrideAcceptance(session, "", link.url(), T24Helper.getOverrideCodes(session));
    }
    
    @Then("^the response is an entity$")
    public void the_response_is_an_entity() throws Throwable {
        assertTrue(session.entities().isItem());
        Entity response = session.entities().item();
        assertNotNull(response);
    }

    @Then("^the HTTP status is (.*)$")
    public void the_HTTP_status_is(int responseCode) throws Throwable {

        int responseCodeBVar = session.result().code();
        assertEquals(responseCode, responseCodeBVar);
    }

    @Then("^the response is an collection$")
    public void the_response_is_collection() throws Throwable {
        assertTrue(session.entities().isCollection());
        List<? extends Entity> response = session.entities().collection();
        assertNotNull(response);
    }
   
    @Then("^the response is not empty$")
    public void the_response_is_not_empty() throws Throwable {

        int responseCollectionSize = session.entities().collection().size();
        assertThat(responseCollectionSize, greaterThan(0));
    }

    @Then("^the response is empty$")
    public void the_response_is_empty() throws Throwable {
        assertThat(session.entities().collection().size(), equalTo(0));
    }

    @Then("^the response of each property values is not empty (.*)$")
    public void the_response_of_each_property_values_is_not_empty(String propertyId) throws Throwable {

        assertThat(session.entities().collection().size(), greaterThan(0));
        for (Entity entity : session.entities().collection()) {
            assertFalse(StringUtils.isEmpty(entity.get(propertyId)));

        }
    }
  
    @Then("^property (.*) matcher (.*) method (.*) value (.*) in entity$")
    public void property_should_be_entity_Y_multipleKeyValue(String propertyId, String matchMethod, String matcherArg, String propertyValue )
            throws Throwable {
        String[] paramSplit = propertyValue.split(",");
       // assertThat(session.entities().collection().size(), equalTo(paramSplit.length));
        Matcher<String>[] params = new Matcher[paramSplit.length];
        int count = 0;
        while (paramSplit.length > count
                && (params[count] = (Matcher<String>) getMatcherFunction(Arrays.asList(matchMethod),
                        paramSplit[count++])) != null)
            ;
        
        Matcher matcher = getMatcherFunction(Arrays.asList(matcherArg), params);
        for (Entity entity : session.entities().collection()) {
            assertThat(entity.get(propertyId), matcher);
        }
    }

    @Then("^property (.*) should be (.*) (.*) in entity$")
    public void property_should_be_in_entity_Z(String propertyId, ArrayList<String>  matchMethod, String propertyValue)
            throws Throwable {
        Matcher<String> matcher = (Matcher<String>) getMatcherFunction(matchMethod,propertyValue);
        runMatchAssertion(session, propertyId, matcher);
    }
      
    @Then("^check the property (.*) should be (.*) in entity response$")
    public void property_should_be_entity(String propertyId, ArrayList<String>  matchMethod)
            throws Throwable {
        Matcher<String> matcher = (Matcher<String>) getMatcherFunction(matchMethod,"");
        runMatchAssertion(session, propertyId, matcher);
    }
  
    @Then("^entity property (.*) with value (.*), should have (.*) value (.*) (.*)$")
    public void entity_property_should_be_entity_Z(String key, String value, String targetKey, ArrayList<String> matchMethod, String targetValue)
            throws Throwable {
        Entity targetEntity = null;
        for (Entity entity : session.entities().collection()) {
            if(entity.get(key) != null && entity.get(key).equals(value)){
                targetEntity = entity;
                break;
            }
        }
        assertNotNull(targetEntity);
        assertThat(targetEntity.get(targetKey), getMatcherFunction(matchMethod, targetValue));
    }

    @Then("^propertyindex (.*) and (.*) should be (.*) (.*) in entity$")
    public void property_index_should_be_in_entity_X(String index, String propertyId, String matchMethod,
            String propertyValue) throws Throwable {
        Matcher<String> matcher = (Matcher<String>) getMatcherFunction(Arrays.asList(matchMethod), propertyValue);
        assertThat(session.entities().collection().get(Integer.parseInt(index)).get(propertyId), matcher);
    }

    @Then("^entity size should be (.*) (.*) in entity$")
    public void collection_should_be_in_entity_Z(int propertyValue, ArrayList<String> matchMethod) throws Throwable {
        int entitiesCount = session.entities().collection().size();
        Matcher<Integer> matcher = getMatcherFunction(matchMethod, propertyValue);
        assertThat(entitiesCount, matcher);
    }

    @Then("^property (.*) should be (.*) in all entities on (.*)$")
    public void property_should_be_in_all_entities_on(String propertyId, String propertyValue, String key)
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
    
   @Then("^property (.*) should be (.*) in entity on (.*)$")
    public void property_should_be_in_entity_Z_on(String propertyId, String propertyValue, String key) throws Throwable {
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

    @Then("^property (.*) should be (.*) in at least one entity on (.*)$")
    public void property_should_be_in_at_least_one_entity_on(String propertyId, String propertyValue, String key)
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

}