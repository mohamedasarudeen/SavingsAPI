package com.temenos.interaction.cucumber.rest.step;

import static com.temenos.interaction.cucumber.rest.step.StepDefinitonBase.getInteractionSession;
import static com.temenos.interaction.cucumber.test.IrisConstants.*;

import org.apache.http.HttpHeaders;

import com.temenos.interaction.cucumber.test.EndpointConfig;
import com.temenos.useragent.generic.InteractionSession;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;

/**
 * Reusable step definition methods for authorise/reverse/delete functionality
 *
 * @author mohamedasarudeen
 *
 */
public class StepDefinitionPostITCase {

    InteractionSession session;
    Scenario scenario;

    @Before
    public void setup(Scenario scenario) {
        this.scenario = scenario;
        session = getInteractionSession(scenario);
    }

    @And("^Authorise the created record by user AUTHOR$")
    public void authoriseEntity() throws Throwable {

        session.reuse().basicAuth(EndpointConfig.getAuthUserName(), EndpointConfig.getPassword())
                .header(HttpHeaders.IF_MATCH, session.header(HttpHeaders.ETAG)).links().byRel(REL_AUTH).url().put();
    }

    @And("^Authorise the created record by different user INPUTTER$")
    public void authoriseEntityWithOtherUser() throws Throwable {

        session.reuse().basicAuth(EndpointConfig.getUserName(), EndpointConfig.getPassword())
                .header(HttpHeaders.IF_MATCH, session.header(HttpHeaders.ETAG)).links().byRel(REL_AUTH).url().put();
    }

    @And("^Reverse the created record$")
    public void reverseEntity() throws Throwable {

        session.reuse().basicAuth(EndpointConfig.getAuthUserName(), EndpointConfig.getPassword())
                .header(HttpHeaders.IF_MATCH, session.header(HttpHeaders.ETAG)).links().byRel(REL_REVERSE).url().put();
    }

    @And("^Delete the created record$")
    public void deleteEntity() throws Throwable {

        session.reuse().basicAuth(EndpointConfig.getAuthUserName(), EndpointConfig.getPassword())
                .header(HttpHeaders.IF_MATCH, session.header(HttpHeaders.ETAG)).links().byRel(REL_DELETE).url()
                .delete();
    }

}
