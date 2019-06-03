package com.kyyba.poc.spring.service.centrify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import com.google.gson.GsonBuilder;
import com.kyyba.poc.spring.config.AppProperties;
import com.kyyba.poc.spring.util.UtilApp;

@RestController
public class UserManageServiceCentrify {

	@Autowired
	RestTemplate restTemplate;

	@Autowired(required = true)
	private AppProperties appProperties;

	private static String SessionId;
	private static String MechanismId;
	
	// User login path http://localhost:8080/centrify/user_login
	@RequestMapping(value = UtilApp.API_END_PONIT_USER_LOGIN, method = RequestMethod.GET)
	public JSONObject userLogin() {
		HttpEntity<JSONObject> entity = new HttpEntity<>(new JSONObject(getSessionMechanismIds()), getHeaders());
		ResponseEntity<JSONObject> response = restTemplate.exchange(
				UtilApp.API_BASE_URL + "/Security/AdvanceAuthentication", HttpMethod.POST, entity, JSONObject.class);
		JSONObject jsonObjectBody = response.getBody();
		return jsonObjectBody;
	}
	
	// User logout path http://localhost:8080/centrify/user_logout
	@RequestMapping(value = UtilApp.API_END_PONIT_USER_LOGOUT, method = RequestMethod.GET)
	public JSONObject userLogout() {
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("Result", null);
		map.put("Message", null);
		map.put("MessageID", null);
		map.put("Exception", null);
		map.put("ErrorID", null);
		map.put("ErrorCode", null);
		map.put("InnerExceptions", null);
		
		HttpEntity<JSONObject> entity = new HttpEntity<>(new JSONObject(map), getHeaders());
		
		ResponseEntity<JSONObject> response = restTemplate.exchange(
				UtilApp.API_BASE_URL + "/Security/logout", HttpMethod.POST, entity, JSONObject.class);
		
		return response.getBody();
	}

	// get SessionId and MechanismId
	private Map<String, String> getSessionMechanismIds() {

		Map<String, String> map = new HashMap<>();
		map.put("TenantId", appProperties.getCentrify().getTenantId());
		map.put("User", appProperties.getCentrify().getUser());
		map.put("Version", appProperties.getCentrify().getVersion());

		HttpEntity<JSONObject> entity = new HttpEntity<>(new JSONObject(map), getHeaders());

		ResponseEntity<JSONObject> response = restTemplate.exchange(
				UtilApp.API_BASE_URL + "/Security/StartAuthentication", HttpMethod.POST, entity, JSONObject.class);

		JSONObject jsonObjectBody = response.getBody();

		if (Boolean.parseBoolean(jsonObjectBody.get("success").toString())) {

			JSONObject jsonObjectResult = JSONObjectParser(jsonObjectBody.get("Result"));

			JSONArray jsonArrayChallenges = JSONArrayParser(jsonObjectResult.get("Challenges"));

			for (Object object : jsonArrayChallenges) {
				if (object instanceof JSONObject) {

					JSONArray jsonArrayMechanisms = (JSONArray) ((JSONObject) object).get("Mechanisms");

					for (Object objectSub : jsonArrayMechanisms) {
						if (objectSub instanceof JSONObject) {
							MechanismId = (String) ((JSONObject) objectSub).get("MechanismId");
						}
					}

				}
			}

			SessionId = jsonObjectResult.get("SessionId").toString();

		}

		Map<String, String> mapResult = new HashMap<>();
		mapResult.put("TenantId", appProperties.getCentrify().getTenantId());
		mapResult.put("SessionId", SessionId);
		mapResult.put("MechanismId", MechanismId);
		mapResult.put("Action", "Answer");
		mapResult.put("Answer", appProperties.getCentrify().getPassword());

		return mapResult;
	}

	private JSONArray JSONArrayParser(Object object) {
		try {
			return (JSONArray) new JSONParser().parse(new GsonBuilder().create().toJson(object));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new JSONArray();
	}

	private JSONObject JSONObjectParser(Object object) {
		try {
			return (JSONObject) new JSONParser().parse(new GsonBuilder().create().toJson(object));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new JSONObject();
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-CENTRIFY-NATIVE-CLIENT", "true");
		return headers;
	}
}
