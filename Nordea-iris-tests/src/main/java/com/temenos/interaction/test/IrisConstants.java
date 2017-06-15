package com.temenos.interaction.test;

/**
 * Defines the IRIS related constants being used across tests.
 * 
 * @author ssethupathi
 * 
 */
public class IrisConstants {

	// link relations
	public static final String REL_SELF = "self";
	public static final String REL_NEW = "http://temenostech.temenos.com/rels/new";
	public static final String REL_INPUT = "http://temenostech.temenos.com/rels/input";
	public static final String REL_POPULATE = "http://temenostech.temenos.com/rels/populate";
	public static final String REL_HOLD = "http://temenostech.temenos.com/rels/hold";
	public static final String REL_DELETE = "http://temenostech.temenos.com/rels/delete";
	public static final String REL_VALIDATE = "http://temenostech.temenos.com/rels/validate";
	public static final String REL_ERROR = "http://temenostech.temenos.com/rels/errors";
	public static final String REL_AUTH = "http://temenostech.temenos.com/rels/authorise";
	public static final String REL_METADATA = "http://temenostech.temenos.com/rels/metadata";
	public static final String REL_CHANGED_VALUES = "http://temenostech.temenos.com/rels/changedValues";
	public static final String REL_REVERSE = "http://temenostech.temenos.com/rels/reverse";
	public static final String REL_REVIEW = "http://temenostech.temenos.com/rels/review";
	public static final String REL_AAPOPULATE = "http://temenostech.temenos.com/rels/aapopulate";
	public static final String REL_VERIFY = "http://temenostech.temenos.com/rels/verify";
	public static final String REL_SEE = "http://temenostech.temenos.com/rels/see";
	public static final String REL_DEAL_SLIP = "http://temenostech.temenos.com/rels/dealSlip";
	public static final String REL_RESTORE = "http://temenostech.temenos.com/rels/restore";
	public static final String REL_COPY = "http://temenostech.temenos.com/rels/copy";
	public static final String REL_PASTE = "http://temenostech.temenos.com/rels/paste";
	public static final String REL_CONTEXT_ENQ = "http://www.temenos.com/rels/contextenquiry";
	public static final String REL_APPLICATION_CONTEXT_ENQ = "http://www.temenos.com/rels/appcontextenquiry";
	public static final String REL_FIELD_CONTEXT_ENQ = "http://www.temenos.com/rels/fieldcontextenquiry";
	public static final String REL_VERSION_CONTEXT_ENQ = "http://www.temenos.com/rels/versioncontextenquiry";
	public static final String REL_AUTO_LAUNCH = "http://www.temenos.com/rels/auto";
	public static final String REL_PREFIX = "http://temenostech.temenos.com/rels/";
	public static final String REL_CONTRACT = "http://temenostech.temenos.com/rels/contract";
	public static final String REL_PW = "http://temenostech.temenos.com/rels/pw";

	// default configuration information
	public static final String SVC_URI = EndpointConfig.getUri();
	//public static final String SERVICE_VERSIONS = "/v1/customers";
	public static final String BASE_URI = SVC_URI + EndpointConfig.getCompany();
			//+ SERVICE_VERSIONS;

	// APIs
	public static final String COLLECTION_API = "%ss()";
	public static final String ITEM_API = "%ss('%s')";
	public static final String ENTRY_API = "%sEntry";
	public static final String NEW_API = "%ss()/new";
	public static final String POPULATE_API = "%ss()/populate";
	public static final String AAPOPULATE_API = "%ss('%s')/aapopulate";
	public static final String IAUTH_ITEM_API = "%ss_IAuth('%s')";
	public static final String IAUTH_COLLECTION_API = "%ss_IAuth()";
	public static final String HOLD_API = "%ss('%s')/hold";
	public static final String AUTHORISE_API = "%ss('%s')/authorise";

}
