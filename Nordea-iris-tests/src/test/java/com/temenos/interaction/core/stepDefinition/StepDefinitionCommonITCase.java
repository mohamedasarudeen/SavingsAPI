package com.temenos.interaction.core.stepDefinition;

import static com.temenos.interaction.core.DashboardUtility.aftercase;
import static com.temenos.interaction.core.DashboardUtility.beforecase;
import static com.temenos.interaction.core.DashboardUtility.testCaseDescription;
import static com.temenos.interaction.core.DashboardUtility.testCaseName;
import static com.temenos.interaction.core.MatcherUtility.getMatcherFunction;
import static com.temenos.interaction.core.MatcherUtility.runMatchAssertion;
import static com.temenos.interaction.core.stepDefinition.StepDefinitonBase.executeRequest;
import static com.temenos.interaction.core.stepDefinition.StepDefinitonBase.getInteractionSession;
import static com.temenos.interaction.core.stepDefinition.StepDefinitonBase.getInteractionSessionURL;
import static com.temenos.interaction.core.stepDefinition.StepDefinitonBase.getScenarioBundle;
import static com.temenos.interaction.test.InteractionHelper.newInitialisedSession;
import static com.temenos.interaction.test.IrisConstants.REL_PREFIX;
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

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matcher;

import com.temenos.interaction.core.ScenarioBundle;
import com.temenos.interaction.test.T24Helper;
import com.temenos.useragent.generic.Entity;
import com.temenos.useragent.generic.InteractionSession;
import com.temenos.useragent.generic.internal.ActionableLink;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * TODO: Document me!
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

    @After
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
    }

    @Given("Set Interaction Session BASE_URI as (.*)$")
    public void interactionSession_setBaseUri(String baseURI) {
        getInteractionSessionURL(scenario, session).baseuri(baseURI);
    }

    @Given("Set Interaction Session path as (.*)$")
    public void interactionSession_setPath(String postServicePath) {
        getInteractionSessionURL(scenario, session).path(postServicePath);
    }
    
    @Given("Set Interaction Session query parameters as (.*)$")
    public void interactionSession_setUrlParams(String servicePath) {
        getInteractionSessionURL(scenario, session).queryParam(servicePath);
    }

    @And("^i reuse Interaction Session$")
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
        ActionableLink link = session.entities().item().links().byRel(REL_PREFIX + rel);
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

    @Then("^the response is not empty$")
    public void the_response_is_not_empty() throws Throwable {

        int responseCollectionSize = session.entities().collection().size();
        assertThat(responseCollectionSize, greaterThan(0));
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

    @Then("^property (.*) should be (.*) anyofparam matcher (.*) in entity Y$")
    public void property_should_be_entity_Y_multipleKeyValue(String propertyId, String matchMethod, String propertyValue)
            throws Throwable {
        String[] paramSplit = propertyValue.split(",");
        assertThat(session.entities().collection().size(), equalTo(paramSplit.length));
        Matcher<String>[] params = new Matcher[paramSplit.length];
        int count = 0;
        while (paramSplit.length > count
                && (params[count] = (Matcher<String>) getMatcherFunction(Arrays.asList(matchMethod),
                        paramSplit[count++])) != null)
            ;

        for (Entity entity : session.entities().collection()) {
            assertThat(entity.get(propertyId), anyOf(params));
        }
    }

    @Then("^property (.*) should be (.*) (.*) in entity Z$")
    public void property_should_be_in_entity_Z(String propertyId, String matchMethod, String propertyValue)
            throws Throwable {
        Matcher<String> matcher = (Matcher<String>) getMatcherFunction(Arrays.asList(matchMethod), propertyValue);
        runMatchAssertion(session, propertyId, matcher);
    }

    @Then("^propertyindex (.*) and (.*) should be (.*) (.*) in entity X$")
    public void property_index_should_be_in_entity_X(String index, String propertyId, String matchMethod,
            String propertyValue) throws Throwable {
        Matcher<String> matcher = (Matcher<String>) getMatcherFunction(Arrays.asList(matchMethod), propertyValue);
        assertThat(session.entities().collection().get(Integer.parseInt(index)).get(propertyId), matcher);
    }

    @Then("^entity size should be (.*) (.*) in entity Z$")
    public void collection_should_be_in_entity_Z(int propertyValue, ArrayList<String> matchMethod) throws Throwable {
        int entitiesCount = session.entities().collection().size();
        Matcher<Integer> matcher = getMatcherFunction(matchMethod, propertyValue);
        assertThat(entitiesCount, matcher);
    }

    @Then("^property (.*) should be (.*) in entity Z on (.*)$")
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
