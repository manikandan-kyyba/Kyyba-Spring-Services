package com.kyyba.poc.spring.util;

public class AppUtil {
	
	private static final String CENTRIFY_API_BASE_URL = "https://eotss-dev.my.centrify.com";
	private static final String SWAGGER_API_PONIT_END_VERSION = ""; // example: /api/v1
	
	public static final String CENTRIFY_API_START_AUTHENTICATION = CENTRIFY_API_BASE_URL + "/Security/StartAuthentication";	
	public static final String CENTRIFY_API_USER_LOGIN = CENTRIFY_API_BASE_URL + "/Security/AdvanceAuthentication";	
	public static final String CENTRIFY_API_USER_LOGOUT = CENTRIFY_API_BASE_URL + "/Security/logout";
	
	public static final String SWAGGER_API_END_POINT_USER_LOGIN = SWAGGER_API_PONIT_END_VERSION + "/login";
	public static final String SWAGGER_API_END_POINT_USER_LOGOUT = SWAGGER_API_PONIT_END_VERSION + "/logout";
	
	public static String AUTH_TOKEN;
}
