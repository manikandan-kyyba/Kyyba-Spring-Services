package com.kyyba.poc.spring.service;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kyyba.poc.spring.config.AppProperties;
import com.kyyba.poc.spring.util.AppUtil;
import com.kyyba.poc.spring.util.CentrifyActionsUtil;

@RestController
public class UserManageService {

	@Autowired(required = true)
	private RestTemplate restTemplate;

	@Autowired(required = true)
	private AppProperties appProperties;

	// User Login http://localhost:8080/api/v1/user_login
	@RequestMapping(value = AppUtil.SWAGGER_API_END_POINT_USER_LOGIN, method = RequestMethod.GET)
	public JSONObject userLogin() {
		return CentrifyActionsUtil.makeAction(AppUtil.CENTRIFY_API_USER_LOGIN, restTemplate, appProperties);
	}

	// User Logout http://localhost:8080/api/v1/user_logout
	@RequestMapping(value = AppUtil.SWAGGER_API_END_POINT_USER_LOGOUT, method = RequestMethod.GET)
	public JSONObject userLogout() {
		return CentrifyActionsUtil.makeAction(AppUtil.CENTRIFY_API_USER_LOGOUT, restTemplate, appProperties);
	}
}
