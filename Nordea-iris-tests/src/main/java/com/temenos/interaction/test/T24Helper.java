package com.temenos.interaction.test;

import static com.temenos.interaction.test.IrisConstants.BASE_URI;
import static com.temenos.interaction.test.T24Constants.ERROR_CODE;
import static com.temenos.interaction.test.T24Constants.ERROR_ELEMENT;
import static com.temenos.interaction.test.T24Constants.ERROR_INFO;
import static com.temenos.interaction.test.T24Constants.ERROR_TEXT;
import static com.temenos.interaction.test.T24Constants.ERROR_TYPE;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.temenos.interaction.utils.T24GenericEnquiry;
import com.temenos.useragent.generic.Entity;
import com.temenos.useragent.generic.InteractionSession;
import com.temenos.useragent.generic.Link;
import com.temenos.useragent.generic.Url;

/**
 * Created by ikarady on 20/06/2016.
 */
public class T24Helper {

    public static final String BASE_REL = "http://temenostech.temenos.com/rels";
    public static final String REL_SELF = "self";
    public static final String REL_INPUT = BASE_REL + "/input";
    public static final String REL_REVERSE = BASE_REL + "/reverse";
    public static final String REL_DELETE = BASE_REL + "/delete";
    public static final String REL_AUTHORISE = BASE_REL + "/authorise";
    public static final String REL_ERRORS = BASE_REL + "/errors";
    public static final String OVERRIDE_FIELD = "_OverrideMvGroup(%s)/Override";
    private static final String ERRORS_MV_GROUP = "ErrorsMvGroup";

    /**
     * Accepts any overrides in {@link InteractionSession} and inputs them to
     * {@link Url}. It looks only for errors that are overrides. It checks all
     * {@link Entity} objects of {@link InteractionSession}. If it finds an
     * {@link Entity} which is both an error and an override, it accepts it,
     * otherwise ignores it. When all {@link Entity} objects are checked, it
     * posts to input {@link Url} if at least one override is accepted otherwise
     * no further action is taken.
     *
     * @param session
     *            the session in which overrides can occur
     * @param entityName
     *            the name of entity
     * @param inputURL
     *            the url where accepted overrides are posted to
     *
     */
    public static void handleOverrideAcceptance(InteractionSession session, String entityName, Url inputURL,
            Map<Integer, String> overrideCodes) {
        if (!entityName.isEmpty()) {
            entityName = entityName + "_";
        }
        if (!overrideCodes.isEmpty()) {
            session.reuse();
            for (Integer key : overrideCodes.keySet()) {
                session.set(entityName + "OverrideMvGroup(" + key + ")/Override", overrideCodes.get(key));
            }
            inputURL.post();
        }
    }

    public static Map<Integer, String> getOverrideCodes(InteractionSession session) {
        Map<Integer, String> codeMap = new LinkedHashMap<Integer, String>();
        Entity errorEntity = getErrorEntity(session);
        if (errorEntity == null) {
            return codeMap;
        }
        for (int i = 0; i < errorEntity.count(ERRORS_MV_GROUP); i++) {

            String errorType = errorEntity.get(ERRORS_MV_GROUP + "(" + i + ")/Type");
            if ("OVERRIDE".equals(errorType)) {
                codeMap.put(i, errorEntity.get(ERRORS_MV_GROUP + "(" + i + ")/Code"));
            } else if ("WARNING".equals(errorType)) {
                String overrideText = errorType + "_" + errorEntity.get(ERRORS_MV_GROUP + "(" + i + ")/Code") + "_"
                        + errorEntity.get(ERRORS_MV_GROUP + "(" + i + ")/Text") + "_RECEIVED";
                codeMap.put(i, overrideText);
            }
        }
        return codeMap;
    }

    private static Entity getErrorEntity(InteractionSession session) {
        if (getLinkByRel(session.links().all(), REL_ERRORS) == null) {
            return null;
        }
        return session.reuse().entities().item().embedded().entity();
    }

    private static Link getLinkByRel(List<Link> linkList, String rel) {
        if (linkList == null) {
            return null;
        }
        for (Link link : linkList) {
            if (link.rel().equals(rel)) {
                return link;
            }
        }
        return null;
    }

    /**
     * Obtains the first value of a record from the source enquiry that does not
     * exist in the destination enquiry.
     * 
     * @param session
     *            An initialised InteractionSession object
     * @param selectorSrc
     *            The source enquiry field that will be selected
     * @param selectorDest
     *            The destination enquiry field used for comparison
     * @param pathSrc
     *            The source enquiry to run
     * @param pathDest
     *            The destination enquiry to run
     * @return A field value from a record in the source enquiry with no
     *         associated data in the destination enquiry.
     */
    public static String getFirstAvailableId(InteractionSession session, String selectorSrc, String selectorDest,
            String pathSrc, String pathDest) {
        List<String> allRecordIds = new ArrayList<String>(), allOtherRecordIds = new ArrayList<String>();
        String chosenRecordId = "";

        session.url().baseuri(BASE_URI).path(pathSrc).get();

        for (Entity srcRecord : session.entities().collection()) {
            allRecordIds.add(srcRecord.get(selectorSrc));
        }

        session.url().baseuri(BASE_URI).path(pathDest).get();

        for (Entity targetRecord : session.entities().collection()) {
            allOtherRecordIds.add(targetRecord.get(selectorDest));
        }

        for (String recordId : allRecordIds) {
            if (!allOtherRecordIds.contains(recordId)) {
                chosenRecordId = recordId;
                break;
            }
        }

        return chosenRecordId;
    }

    /**
     * Encode the URL to UTF-8 format
     * 
     * @param input
     *            url
     * @return encoded url
     */
    public static String encodeUrl(String params) {
        String encodedUrl = "";
        try {
            return URLEncoder.encode(params, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            encodedUrl = null;
        }
        return encodedUrl;

    }

    /**
     * Checkes whether or not a given error details present in at least one of
     * many possible errors in the entity.
     * <p>
     * Returns <i>null</i> if the expected error presents. Otherwise, a non null
     * message.
     * </p>
     * 
     * @param errorEntity
     * @param expectedError
     * @return message if expectedError not present in errorEntity, null
     *         otherwise
     */
    public static boolean checkExpectedErrorExists(Entity errorEntity, String[] expectedError) {
        int errorsCount = errorEntity.count(ERROR_ELEMENT);
        for (int errIdx = 0; errIdx < errorsCount; errIdx++) {
            String[] actualError = new String[4];
            actualError[0] = errorEntity.get(String.format(ERROR_CODE, errIdx));
            actualError[1] = errorEntity.get(String.format(ERROR_TYPE, errIdx));
            actualError[2] = errorEntity.get(String.format(ERROR_TEXT, errIdx));
            actualError[3] = errorEntity.get(String.format(ERROR_INFO, errIdx));
            if (isEqual(expectedError, actualError)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the T24 business date in T24 format i.e., YYYYMMDD for a given
     * company.
     * 
     * @return T24 business date (aka TODAY).
     */
    public static String getT24BusinessDate(String company) {
        InteractionSession setupDatesession = T24GenericEnquiry.queryByFilter("enqlistDates", "Id eq " + company);
        return setupDatesession.entities().collection().get(0).get("Today");
    }

    private static boolean isEqual(String[] expected, String[] actual) {
        for (int i = 0; i < expected.length; i++) {
            if (!actual[i].contains(expected[i])) {
                return false;
            }
        }
        return true;
    }
}
