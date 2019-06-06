package com.kyyba.poc.spring.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.GsonBuilder;
import com.kyyba.poc.spring.config.AppProperties;
import com.kyyba.poc.spring.model.UserRequest;

public class CentrifyActionsUtil {

	private static String SessionId;
	private static String MechanismId;

	public static JSONObject makeAction(String endPoint, UserRequest userRequest, RestTemplate restTemplate, AppProperties appProperties) {
		CentrifyActionsUtil centrifyActionsUtil = new CentrifyActionsUtil();
		HttpEntity<JSONObject> entity = null;

		switch (endPoint) {

		case AppUtil.CENTRIFY_API_USER_LOGIN: {
			entity = new HttpEntity<>(
					new JSONObject(centrifyActionsUtil.getSessionMechanismIds(userRequest, restTemplate, appProperties)),
					centrifyActionsUtil.getHeaders());
		}
			break;

		case AppUtil.CENTRIFY_API_USER_LOGOUT: {
			Map<String, Object> map = new HashMap<>();
			map.put("success", true);
			map.put("Result", null);
			map.put("Message", null);
			map.put("MessageID", null);
			map.put("Exception", null);
			map.put("ErrorID", null);
			map.put("ErrorCode", null);
			map.put("InnerExceptions", null);
			entity = new HttpEntity<>(new JSONObject(map), centrifyActionsUtil.getHeaders());
		}
			break;

		default: {

		}

		}

		ResponseEntity<JSONObject> response = restTemplate.exchange(endPoint, HttpMethod.POST, entity,
				JSONObject.class);

		return response.getBody();

	}

	// get SessionId and MechanismId
	private Map<String, String> getSessionMechanismIds(UserRequest userRequest,RestTemplate restTemplate, AppProperties appProperties) {

		Map<String, String> map = new HashMap<>();
		map.put("TenantId", appProperties.getCentrify().getTenantId());
		map.put("User", userRequest.getEmail());
		map.put("Version", appProperties.getCentrify().getVersion());

		HttpEntity<JSONObject> entity = new HttpEntity<>(new JSONObject(map), getHeaders());

		ResponseEntity<JSONObject> response = restTemplate.exchange(AppUtil.CENTRIFY_API_START_AUTHENTICATION,
				HttpMethod.POST, entity, JSONObject.class);

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
		mapResult.put("Answer", userRequest.getPassword());

		return mapResult;
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-CENTRIFY-NATIVE-CLIENT", "true");
		return headers;
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
}
