package com.temenos.interaction.cucumber.test;

import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;

import com.temenos.useragent.generic.DefaultInteractionSession;
import com.temenos.useragent.generic.InteractionSession;

/**
 * Generic interaction helper providing utility methods.
 * 
 * @author ssethupathi
 *
 */
public class InteractionHelper {

    /**
     * Returns a new {@link InteractionSession session} which is initialised
     * with auth and content type.
     * 
     * @return
     */
    public static InteractionSession newInitialisedSession(String mediaType) {
        mediaType = System.getProperty("media.type", StringUtils.isNotEmpty(mediaType) ? mediaType
                : "application/hal+json");
        // mediaType = System.getProperty("media.type",
        // StringUtils.isNotEmpty(mediaType) ? mediaType :
        // "application/atom+xml");
        return DefaultInteractionSession.newSession()
                .basicAuth(EndpointConfig.getUserName(), EndpointConfig.getPassword())
                .header(HttpHeaders.CONTENT_TYPE, mediaType).header(HttpHeaders.ACCEPT, mediaType);
    }

    /**
     * Encodes the query param using {@link URLEncoder url encoder}.
     * 
     * @param queryParam
     * @return encoded query param
     */
    public static String encode(String queryParam) {
        try {
            return URLEncoder.encode(queryParam, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a $filter query param.
     * 
     * @param queryParam
     * @return $filter query param
     */
    public static String filter(String queryParam) {
        return "$filter=" + encode(queryParam);
    }

    /**
     * Creates a $orderBy query param.
     * 
     * @param queryParam
     * @return $orderBy query param
     */
    public static String orderby(String orderBy) {
        return "$orderBy=" + encode(orderBy);
    }

    /**
     * Creates a $select query param.
     * 
     * @param queryParam
     * @return $select query param
     */
    public static String select(String queryParam) {
        return "$select=" + encode(queryParam);
    }
}
