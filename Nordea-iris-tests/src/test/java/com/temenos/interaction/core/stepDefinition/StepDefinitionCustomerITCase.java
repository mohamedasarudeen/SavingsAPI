package com.temenos.interaction.core.stepDefinition;

import static com.temenos.interaction.core.stepDefinition.StepDefinitonBase.getInteractionSession;
import static com.temenos.interaction.core.stepDefinition.StepDefinitonBase.getScenarioBundle;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.apache.http.HttpStatus;

import com.temenos.interaction.core.ScenarioBundle;
import com.temenos.interaction.utils.T24GenericCustomer;
import com.temenos.useragent.generic.InteractionSession;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;

public class StepDefinitionCustomerITCase {

    InteractionSession session;
    Scenario scenario;
    ScenarioBundle bundle;

    @Before
    public void setup(Scenario scenario) {
        this.scenario = scenario;
        this.session = getInteractionSession(scenario);
        this.bundle = getScenarioBundle(scenario);
    }

    @And("^Create test Customer$")
    public void testCustomer_createTestCustomer() {

        /*InteractionSession session = T24GenericCustomer.createNewCustomerEntity();
        assertThat(session.result().code(), equalTo(HttpStatus.SC_OK));
        String customerId = session.entities().item().get(T24GenericCustomer.CUSTOMER_ID);
        T24GenericCustomer.createLimitforCustomer(customerId);
        */
        //bundle.put("customerId"/* add in constant file */, customerId);
        bundle.put("customerId"/* add in constant file */, "100336");
    }

}
