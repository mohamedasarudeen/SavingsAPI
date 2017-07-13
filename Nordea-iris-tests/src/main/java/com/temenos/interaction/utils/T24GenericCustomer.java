package com.temenos.interaction.utils;

import static com.temenos.interaction.cucumber.test.InteractionHelper.newInitialisedSession;
import static com.temenos.interaction.cucumber.test.IrisConstants.BASE_URI;
import static com.temenos.interaction.cucumber.test.IrisConstants.REL_AUTH;
import static com.temenos.interaction.cucumber.test.IrisConstants.REL_HOLD;
import static com.temenos.interaction.cucumber.test.IrisConstants.REL_INPUT;

import org.apache.http.HttpHeaders;

import com.temenos.interaction.cucumber.test.EndpointConfig;
import com.temenos.interaction.cucumber.test.T24Helper;
import com.temenos.useragent.generic.InteractionSession;
import com.temenos.useragent.generic.Url;

/**
 * Provides the generic functions around a T24 CUSTOMER version
 * 
 * @author mohamedasarudeen
 *
 */
public class T24GenericCustomer {

    public static final String VER_CUSTOMER_INPUT = "verCustomer_InputTst";
    public static final String VER_CUSTOMER_INPUT_SET = "verCustomer_InputTsts";
    public static final String VER_CUSTOMER_INPUT_WITH_ID = "verCustomer_InputTsts('%s')";
    public static final String VER_CUSTOMER_INPUT_NEW = VER_CUSTOMER_INPUT + "s()/new";
    public static final String VER_CUSTOMER_INPUT_POPULATE = VER_CUSTOMER_INPUT + "s()/populate";
    public static final String VER_CUSTOMER_SEE = "verCustomer_InputTsts('%s')/see";
    public static final String VER_CUSTOMER_CHANGEDVALUES = "verCustomer_InputTsts('%s')/changedValues";
    public static final String CUSTOMER_NAME = "Name1MvGroup(0)/Name1";
    public static final String CUSTOMER_SHORTNAME = "ShortNameMvGroup(0)/ShortName";
    public static final String CUSTOMER_ID = "CustomerCode";
    public static final String CUSTOMER_MNEMONIC = "Mnemonic";
    public static final String CUSTOMER_SECTOR = "Sector";
    public static final String CUSTOMER_GENDER = "Gender";
    public static final String CUSTOMER_TITLE = "Title";
    public static final String CUSTOMER_FAMILYNAME = "FamilyName";
    public static final String CUSTOMER_LANGUAGE = "Language";

    
    //LIMIT record for CUSTOMER
    public static final String VER_LIMIT = "verLimit";
    public static final String VER_LIMIT_SET = "verLimits";
    public static final String VER_LIMIT_ID = "verLimits('%s')";
    public static final String VER_LIMIT_POPUlATE = "verLimits()/populate";
    public static final String LIMIT_CREDIT_LINE = "CreditLineNo";
    public static final String LIMIT_CURRENCY = "LimitCurrency";
    public static final String LIMIT_INTERNAL_AMOUNT = "InternalAmount";
    public static final String LIMIT_AVAILABLE_MARKER = "AvailableMarker";
    public static final String VER_LIMIT_POPUlATE_EDIT = VER_LIMIT_POPUlATE+"?"+LIMIT_CREDIT_LINE+"=%s.100&t24Intent=EDIT";

    public static InteractionSession createNewCustomerEntity() {
        InteractionSession session = inputNewCustomerEntity();
        authoriseCustomerEntity(session);
        return session;
    }

    public static InteractionSession inputNewCustomerEntity() {
        InteractionSession setupSession = newInitialisedSession(null);
        setupSession.url().baseuri(BASE_URI).path(VER_CUSTOMER_INPUT_NEW).post();

        String customerId = setupSession.entities().item().get(CUSTOMER_ID);
        setMandatoryProperties(setupSession, customerId).entities().item().links().byRel(REL_INPUT).url().post();
        return setupSession;
    }

    public static InteractionSession createHoldEntity() {
        InteractionSession setupSession = newInitialisedSession(null);
        setupSession.url().baseuri(BASE_URI).path(VER_CUSTOMER_INPUT_NEW).post();
        String customerId = setupSession.entities().item().get(CUSTOMER_ID);
        setMandatoryProperties(setupSession, customerId).entities().item().links().byRel(REL_HOLD).url().post();
        return setupSession;
    }

    public static InteractionSession initNewCustomerEntity() {
        InteractionSession setupSession = newInitialisedSession(null);
        setupSession.url().baseuri(BASE_URI).path(VER_CUSTOMER_INPUT_NEW).post();
        return setupSession;
    }

    public static InteractionSession authoriseCustomerEntity(InteractionSession session) {
        session.reuse().basicAuth(EndpointConfig.getAuthUserName(), EndpointConfig.getPassword())
                .header(HttpHeaders.IF_MATCH, session.header(HttpHeaders.ETAG)).links().byRel(REL_AUTH).url().put();
        return session;
    }

    public static InteractionSession createLimitforCustomer(String customerId) {
        InteractionSession setupSession = populateLimitforCustomer(customerId);
        inputLimitforCustomer(setupSession);
        authoriseLimitforCustomer(setupSession);
        return setupSession;
    }

    private static InteractionSession populateLimitforCustomer(String customerId) {
        InteractionSession setupSession = newInitialisedSession(null);
        setupSession.url().baseuri(BASE_URI).path(String.format(VER_LIMIT_POPUlATE_EDIT, customerId)).post();
        return setupSession;
    }

    private static InteractionSession inputLimitforCustomer(InteractionSession session) {
        setLimitMandatoryProperties(session);
        Url inputUrl = session.entities().item().links().byRel(REL_INPUT).url();
        inputUrl.post();
        T24Helper.handleOverrideAcceptance(session, "", inputUrl, T24Helper.getOverrideCodes(session));
        return session;
    }

    private static InteractionSession authoriseLimitforCustomer(InteractionSession session) {
        session.reuse().basicAuth(EndpointConfig.getAuthUserName(), EndpointConfig.getPassword())
                .header(HttpHeaders.IF_MATCH, session.header(HttpHeaders.ETAG)).links().byRel(REL_AUTH).url().put();
        return session;
    }

    public static InteractionSession setMandatoryProperties(InteractionSession session, String customerId) {
        return session.reuse().set(CUSTOMER_MNEMONIC, "C" + customerId)
                .set(CUSTOMER_NAME, "Mr Robin Peterson" + customerId).set(CUSTOMER_SHORTNAME, "Rob" + customerId)
                .set(CUSTOMER_SECTOR, "1001").set(CUSTOMER_GENDER, "MALE").set(CUSTOMER_TITLE, "MR")
                .set(CUSTOMER_FAMILYNAME, "Peterson" + customerId);
    }

    private static InteractionSession setLimitMandatoryProperties(InteractionSession session) {
        return session.reuse().set(LIMIT_CURRENCY, "USD").set(LIMIT_AVAILABLE_MARKER, "N")
                .set(LIMIT_INTERNAL_AMOUNT, "123");
    }
}
