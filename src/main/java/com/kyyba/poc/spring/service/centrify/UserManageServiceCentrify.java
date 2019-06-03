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

	@RequestMapping(value = UtilApp.API_END_PONIT, method = RequestMethod.GET)
	public JSONObject createUsers() {
		return getSessionMechanismIds();
	}

	// get SessionId and MechanismId
	@SuppressWarnings("unchecked")
	private JSONObject getSessionMechanismIds() {
		HttpHeaders headers = new HttpHeaders();
		// Request to return JSON format
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-CENTRIFY-NATIVE-CLIENT", "true");

		Map<String, String> map = new HashMap<String, String>();
		map.put("TenantId", appProperties.getCentrify().getTenantId());
		map.put("User", appProperties.getCentrify().getUser());
		map.put("Version", appProperties.getCentrify().getVersion());

		HttpEntity<JSONObject> entity = new HttpEntity<>(new JSONObject(map), headers);

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

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("TenantId", appProperties.getCentrify().getTenantId());
		jsonObject.put("SessionId", SessionId);
		jsonObject.put("MechanismId", MechanismId);
		jsonObject.put("Action", "Answer");
		jsonObject.put("Answer", appProperties.getCentrify().getPassword());

		return jsonObject;
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
