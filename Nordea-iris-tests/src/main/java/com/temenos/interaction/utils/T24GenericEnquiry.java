package com.temenos.interaction.utils;

import com.temenos.interaction.test.EndpointConfig;
import com.temenos.interaction.test.InteractionHelper;
import com.temenos.useragent.generic.InteractionSession;

/**
 * Provides generic functions to run enquiries.
 *
 * @author mohamedasarudeen
 */
public class T24GenericEnquiry {

    private static final String QUERY = "%ss()";
    private static final String QUERY_BY_ID = "%ss('%s')";
    private static final String QUERY_FOR_COS = "%s()";

    public static InteractionSession query(String resource) {
        return getResource(String.format(QUERY, resource));
    }

    public static InteractionSession queryByFilter(String resource, String filter) {
        return queryByParam(resource, InteractionHelper.filter(filter));
    }

    public static InteractionSession queryByParam(String resource, String queryParam) {
        return getResource(String.format(QUERY, resource), queryParam);
    }

    public static InteractionSession queryByParamForCos(String resource, String queryParam) {
        return getResource(String.format(QUERY_FOR_COS, resource), queryParam);
    }

    public static InteractionSession queryById(String resource, String id) {
        return getResource(String.format(QUERY_BY_ID, resource, id));
    }

    private static InteractionSession getResource(String path) {
        return getResource(path, "");
    }

    private static InteractionSession getResource(String path, String queryParam) {
        InteractionSession session = InteractionHelper.newInitialisedSession("application/hal+json");
        session.url().baseuri(EndpointConfig.getUri() + EndpointConfig.getCompany()).path(path).queryParam(queryParam)
                .get();
        return session;
    }

}
