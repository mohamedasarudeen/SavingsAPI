package com.temenos.interaction.utils;

import static com.temenos.interaction.cucumber.test.InteractionHelper.*;

import org.apache.http.HttpHeaders;

import com.temenos.interaction.cucumber.test.EndpointConfig;
import com.temenos.interaction.cucumber.test.IrisConstants;
import com.temenos.interaction.cucumber.test.T24Helper;
import com.temenos.useragent.generic.InteractionSession;
import com.temenos.useragent.generic.Url;

/**
 * Provides the generic functions for T24 Arrangement Activity version
 * @author mohamedasarudeen
 */
public class T24GenericArrangementActivity {

	public static final String DEFAULT_VER_AA = "verAaArrangementActivity";
	public static final String DEFAULT_VER_AA_SET = DEFAULT_VER_AA + "s";
	public static final String DEFAULT_VER_AA_INPUT = DEFAULT_VER_AA_SET + "('%s')";
	public static final String VER_AA = DEFAULT_VER_AA + "_AaNewTst";
	public static final String VER_AA_SET = VER_AA + "s";
	public static final String VER_AA_NEW = VER_AA_SET + "()/new";
	public static final String VER_AA_IAUTH_SEE = VER_AA_SET + "_IAuth('%s')/see?t24Intent=Edit";
	public static final String VER_AA_POPULATE = VER_AA_SET + "('%s')/aapopulate";
	public static final String VER_AA_INPUT = VER_AA_SET + "('%s')";
	public static final String PRODUCT = "Product";
	public static final String ACTIVITY = "Activity";
	public static final String EFFECTIVE_DATE = "EffectiveDate";
	public static final String CUSTOMER = VER_AA + "_CustomerMvGroup(0)/Customer";
	public static final String CURRENCY = "Currency";
	public static final String ARR_SEQUENCE = "ArrSequence";
	public static final String ARRANGEMENT = "Arrangement";

	public static InteractionSession getAaApplicationEntity(String id) {
		return getAaEntity(String.format(DEFAULT_VER_AA_INPUT, id));
	}

	public static InteractionSession getAaVersionEntity(String id) {
		return getAaEntity(String.format(VER_AA_INPUT, id));
	}

	public static InteractionSession initNewAaEntity() {
		InteractionSession session = newInitialisedSession(null);
		session.url()
        	.baseuri(EndpointConfig.getUri()+EndpointConfig.getCompany())
        	.path(VER_AA_NEW)
        	.queryParam(PRODUCT + "=CURRENT.ACCOUNT&" + ACTIVITY + "=ACCOUNTS-NEW-ARRANGEMENT&" + EFFECTIVE_DATE + "=20150722")
        	.post();		
		return session;
	}
	
	public static InteractionSession populateAaEntity(InteractionSession session) {
		session.links().byRel(IrisConstants.REL_AAPOPULATE).url().post();		
		return session;
	}
	
	public static InteractionSession populateAaEntityWithVerbosityHeader(InteractionSession session, String verbosity){
	    session.header("verbosity", verbosity).links().byRel(IrisConstants.REL_AAPOPULATE).url().post();
	    return session;
	}
	
	public static InteractionSession holdAaEntity(InteractionSession session) {
		session.links().byRel(IrisConstants.REL_HOLD).url().post();
		return session;		
	}
	
	public static InteractionSession inputAaEntity(InteractionSession session) {
	    Url inputUrl = session.reuse().links().byRel(IrisConstants.REL_INPUT).url();
	    inputUrl.post();
	    T24Helper.handleOverrideAcceptance(session, T24GenericArrangementActivity.VER_AA, inputUrl, T24Helper.getOverrideCodes(session));
		return session;
	}
	
	public static InteractionSession authoriseAaEntity(InteractionSession session) {
		session.basicAuth(EndpointConfig.getAuthUserName(), EndpointConfig.getPassword())
	         .header(HttpHeaders.IF_MATCH, session.header(HttpHeaders.ETAG))
	         .links().byRel(IrisConstants.REL_AUTH)
	         .url()
	         .put();
		return session;
	}
	
	public static InteractionSession deleteAaEntity(InteractionSession session) {
		session.header(HttpHeaders.IF_MATCH, session.header(HttpHeaders.ETAG))
        .links().byRel(IrisConstants.REL_DELETE)
        .url()
        .delete();
		return session;
	}
	
	public static InteractionSession reverseAaEntity(InteractionSession session) {
		session.header(HttpHeaders.IF_MATCH, session.header(HttpHeaders.ETAG))
        .links().byRel(IrisConstants.REL_REVERSE)
        .url()
        .put();
		return session;
	}

	public static InteractionSession addMandatoryFieldswithCustomerId(InteractionSession session, String customerId) {
        return session.reuse().set(CURRENCY, "USD").set(CUSTOMER, customerId);
    }

	private static InteractionSession getAaEntity(String path) {
		InteractionSession session = newInitialisedSession(null);
		session.url()
				.baseuri(EndpointConfig.getUri()+EndpointConfig.getCompany())
				.path(path)
				.get();
		return session;
	}

}
