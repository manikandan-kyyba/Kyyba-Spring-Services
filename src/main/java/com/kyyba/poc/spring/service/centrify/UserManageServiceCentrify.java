package com.kyyba.poc.spring.service.centrify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kyyba.poc.spring.config.AppProperties;
import com.kyyba.poc.spring.util.UtilApp;

@RestController
public class UserManageServiceCentrify {

	@Autowired
	RestTemplate restTemplate;

	@Autowired(required = true)
	private AppProperties appProperties;

	@RequestMapping(value = UtilApp.API_END_PONIT, method = RequestMethod.POST)
	public JSONObject createUsers() {
		HttpHeaders headers = new HttpHeaders();

		// Request to return JSON format
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-CENTRIFY-NATIVE-CLIENT", "true");

		Map<String, String> map = new HashMap<String, String>();
		map.put("TenantId", appProperties.getCentrify().getTenantId());
		map.put("User", appProperties.getCentrify().getUser());
		map.put("Version", appProperties.getCentrify().getVersion());

		JSONObject jsonObject = new JSONObject(map);
		
		System.out.println(jsonObject);

		HttpEntity<JSONObject> entity = new HttpEntity<>(jsonObject, headers);

		ResponseEntity<JSONObject> response = restTemplate.exchange(
				UtilApp.API_BASE_URL + "/Security/StartAuthentication", HttpMethod.POST, entity, JSONObject.class);
		return response.getBody();
	}
}
