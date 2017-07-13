package com.temenos.interaction.cucumber.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;

import com.temenos.interaction.utils.T24GenericEnquiry;
import com.temenos.useragent.generic.InteractionSession;
import com.temenos.useragent.generic.Link;
import com.temenos.useragent.generic.internal.ActionableLink;

/**
 * Handling generic Assertions to call in test
 *
 * @author mohamedasarudeen
 *
 */
public class Assertions {

    public static void assertLinkReturnsStatusOK(Link link, InteractionSession session) {
        session.url(link.baseUrl() + link.href()).get();
        assertEquals(200, session.result().code());
    }

    public static void assertLinkProperties(ActionableLink link, String expectedRel, String expectedTitle,
            String expectedHrefUrl, String[] expectedHrefReqParams, boolean triggerLink, InteractionSession session) {
        assertTrue(expectedRel.contains(link.rel()));
        assertTrue(expectedRel.contains(link.description()));
        assertEquals(expectedTitle, link.title());
        assertHref(expectedHrefUrl, expectedHrefReqParams, link.href());
        if (triggerLink) {
            assertLinkReturnsStatusOK(link, session);
        }
    }

    public static void assertHref(String expectedUrl, String[] requestParams, String actualHref) {
        assertTrue(StringUtils.startsWith(actualHref, expectedUrl));
        for (int arrayIndex = 0; arrayIndex < requestParams.length; arrayIndex++) {
            assertTrue(StringUtils.contains(actualHref, requestParams[arrayIndex]));
        }
    }

    public static void sortLinkCollection(List<ActionableLink> transitionLinkList) {
        Collections.sort(transitionLinkList, new Comparator<ActionableLink>() {
            // @Override
            public int compare(ActionableLink al1, ActionableLink al2) {
                return al1.rel().compareTo(al2.rel());
            }
        });
    }

}
