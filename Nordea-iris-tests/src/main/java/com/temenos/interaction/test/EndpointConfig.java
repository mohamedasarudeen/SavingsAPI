package com.temenos.interaction.test;

public class EndpointConfig {

	/**
	 * Return user name from system property if missing in system property then
	 * use default.
	 * 
	 * @return username
	 */
	public static String getUserName() {
		String username = System.getProperty("USERNAME");
		if (username == null) {
			username = EndpointConstants.TEST_USERNAME;
		}
		assert (username != null);
		return username;
	}

	/**
	 * Return user name to authorise from system property if missing in system
	 * property then use default.
	 *
	 * @return username
	 */
	public static String getAuthUserName() {
		String username = System.getProperty("AUTH_USERNAME");
		if (username == null) {
			username = EndpointConstants.TEST_AUTH_USERNAME;
		}
		assert (username != null);
		return username;
	}

	/**
	 * Return password from system property if missing in system property then
	 * use default.
	 * 
	 * @return password
	 */
	public static String getPassword() {
		String password = System.getProperty("PASSWORD");
		if (password == null) {
			password = EndpointConstants.TEST_PASSWORD;
		}
		assert (password != null);
		return password;
	}

	/**
	 * Return URI from system property if missing in system property then use
	 * default.
	 * 
	 * @return uri
	 */
	public static String getUri() {
		String uri = System.getProperty("URI");
		if (uri == null) {
			uri = EndpointConstants.TEST_ENDPOINT_URI;
		}
		assert (uri != null);
		return uri;
	}

	/**
	 * Return URI from system property if missing in system property then use
	 * default.
	 *
	 * @return uri
	 */
	public static String getSolrUrl() {
		String url = System.getProperty("SOLR_URL");
		if (url == null) {
			url = EndpointConstants.TEST_SOLR_URL;
		}
		assert (url != null);
		return url;
	}

	/**
	 * Return t24Date from system property if missing in system property then
	 * use default.
	 * 
	 * @return t24Date
	 */
	public static String getT24Date() {
		String t24Date = System.getProperty("T24DATE");
		if (t24Date == null) {
			t24Date = EndpointConstants.TEST_T24_DATE;
		}
		assert (t24Date != null);
		return t24Date;
	}

	/**
	 * Return t24 company from system property if missing in system property
	 * then use default.
	 * 
	 * @return t24Company
	 */
	public static String getCompany() {
		String t24Company = System.getProperty("COMPANY");
		if (t24Company == null) {
			t24Company = EndpointConstants.TEST_COMPANY;
		}
		assert (t24Company != null);
		return t24Company;
	}
}
