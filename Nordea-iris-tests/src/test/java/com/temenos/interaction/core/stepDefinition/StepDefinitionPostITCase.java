package com.temenos.interaction.core.stepDefinition;

import static com.temenos.interaction.core.stepDefinition.StepDefinitonBase.getInteractionSession;
import static com.temenos.interaction.test.IrisConstants.REL_AUTH;

import org.apache.http.HttpHeaders;

import com.temenos.interaction.test.EndpointConfig;
import com.temenos.useragent.generic.InteractionSession;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;

public class StepDefinitionPostITCase {

    InteractionSession session;
    Scenario scenario;

    @Before
    public void setup(Scenario scenario) {
        this.scenario = scenario;
        session = getInteractionSession(scenario);
    }

    @And("^Authorise the created record$")
    public void authoriseEntity() throws Throwable {

        session.reuse().basicAuth(EndpointConfig.getAuthUserName(), EndpointConfig.getPassword())
                .header(HttpHeaders.IF_MATCH, session.header(HttpHeaders.ETAG)).links().byRel(REL_AUTH).url().put();
    }

}
