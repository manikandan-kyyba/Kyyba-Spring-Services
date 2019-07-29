package com.kyyba.poc.spring.service;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kyyba.poc.spring.config.AppProperties;
import com.kyyba.poc.spring.model.UserRequest;
import com.kyyba.poc.spring.util.AppUtil;
import com.kyyba.poc.spring.util.CentrifyActionsUtil;

@RestController
public class UserManageService {

	@Autowired(required = true)
	private RestTemplate restTemplate;

	@Autowired(required = true)
	private AppProperties appProperties;

	// User Post Request for Login http://localhost:8080/login {"user": "",
	// "password": ""}
	@RequestMapping(value = AppUtil.SWAGGER_API_END_POINT_USER_LOGIN, method = RequestMethod.POST)
	public JSONObject userLogin(@RequestBody UserRequest userRequest) {
		return CentrifyActionsUtil.makeAction(AppUtil.CENTRIFY_API_USER_LOGIN, userRequest, restTemplate,
				appProperties);
	}

	// User Post Request for Register http://localhost:8080/register
	@RequestMapping(value = AppUtil.SWAGGER_API_END_POINT_USER_REGISTER, method = RequestMethod.POST)
	public JSONObject userRegister() {
		return CentrifyActionsUtil.makeAction(AppUtil.CENTRIFY_API_USER_REGISTER, new UserRequest(), restTemplate,
				appProperties);
	}

	// User Post Request for Logout http://localhost:8080/logout
	@RequestMapping(value = AppUtil.SWAGGER_API_END_POINT_USER_LOGOUT, method = RequestMethod.POST)
	public JSONObject userLogout() {
		return CentrifyActionsUtil.makeAction(AppUtil.CENTRIFY_API_USER_LOGOUT, new UserRequest(), restTemplate,
				appProperties);
	}
}
