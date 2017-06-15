package com.temenos.interaction.core.stepDefinition;

import static com.temenos.interaction.core.Assertions.assertLinkReturnsStatusOK;
import static com.temenos.interaction.core.Assertions.getResource;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.odata4j.format.xml.XmlFormatWriter;

import com.temenos.useragent.generic.InteractionSession;
import com.temenos.useragent.generic.Link;
import com.temenos.useragent.generic.Links;
import com.temenos.useragent.generic.internal.ActionableLink;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitionDrilldownITCase {

  // protected InteractionSession session =null;
   
    @When("^i execute GET request with resource (.*)$")
    public void i_execute_GET_request(String resource) throws Throwable {
     //   System.out.println("REsource "+resource);
       // InteractionSession sessionSetup = getResource(resource);
        //session = sessionSetup;  
    }

    @Then("^the response for drilldown enquiry is not empty for parameters(.*)$")
    public void the_response_drilldown_enq_not_empty(ArrayList<String> propertyIdValue) throws Throwable {
        InteractionSession session = getResource(propertyIdValue.get(2));
       // System.out.println("ARRAYLIST  " + propertyIdValue);
        String propertyId = propertyIdValue.get(0);
       // System.out.println("CustomerId   " + propertyId);
        
        Links links = session.entities().byId(propertyId).links();
     //   System.out.println(links);
        
        List<ActionableLink> selfLinkList = links.allByRel(propertyIdValue.get(1).toString());
        assertEquals(1, selfLinkList.size()); // Only one self link
        ActionableLink selfLink = selfLinkList.get(0);
        assertEquals(propertyIdValue.get(1), selfLink.rel());
        assertEquals(propertyIdValue.get(2), selfLink.title());
        assertEquals("'" + propertyIdValue.get(3) + "'('" + propertyId + "')", selfLink.href());
        assertLinkReturnsStatusOK(selfLink, session);
        List<ActionableLink> transitionLinkList = links.allByTitle(propertyIdValue.get(4));
        assertEquals(1, transitionLinkList.size()); // Only one transition link
        Link transitionLink = transitionLinkList.get(0);
        assertEquals(propertyIdValue.get(5), transitionLink.rel());
        assertEquals(XmlFormatWriter.related + propertyIdValue.get(6), transitionLink.description());
        assertEquals("'" + propertyIdValue.get(7) + "'('" + propertyId + "')'" + propertyIdValue.get(8) + "'",
                transitionLink.href());
        assertLinkReturnsStatusOK(transitionLink, session);
    }

}
