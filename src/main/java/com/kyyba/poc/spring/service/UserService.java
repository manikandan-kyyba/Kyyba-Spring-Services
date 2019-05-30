package com.kyyba.poc.spring.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kyyba.poc.spring.model.User;

@RestController
public class UserService {

	private static final String API_BASE_URL = "http://182.73.228.219:3000";

	private static final String API_END_VERSION = "/api/v1";

	private static final String API_END_PONIT = API_END_VERSION + "/users";

	@Autowired
	RestTemplate restTemplate;

	// get all users
	@SuppressWarnings("unchecked")
	@RequestMapping(value = API_END_PONIT, method = RequestMethod.GET)
	public List<User> getUserList() {
		HttpHeaders headers = new HttpHeaders();
		// Request to return JSON format
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		return restTemplate.exchange(API_BASE_URL + "/users", HttpMethod.GET, entity, List.class).getBody();
	}

	// create user
	@RequestMapping(value = API_END_PONIT, method = RequestMethod.POST)
	public User createUsers(@RequestBody User user) {
		HttpHeaders headers = new HttpHeaders();
		// Request to return JSON format
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> entity = new HttpEntity<User>(user, headers);

		// Send request with GET method, and Headers.
		ResponseEntity<User> response = restTemplate.exchange(API_BASE_URL + "/users/", HttpMethod.POST, entity,
				User.class);
		return response.getBody();
	}

	// get user by id
	@RequestMapping(value = API_END_PONIT + "/{id}", method = RequestMethod.GET)
	public User getUser(@PathVariable("id") String id) {
		HttpHeaders headers = new HttpHeaders();
		// Request to return JSON format
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> entity = new HttpEntity<User>(headers);
		// Send request with GET method, and Headers.
		ResponseEntity<User> response = restTemplate.exchange(API_BASE_URL + "/users/" + id, HttpMethod.GET, entity,
				User.class);
		return response.getBody();
	}

	// update existing user
	@RequestMapping(value = API_END_PONIT + "/{id}", method = RequestMethod.PUT)
	public User updateUser(@PathVariable("id") String id, @RequestBody User user) {
		HttpHeaders headers = new HttpHeaders();
		// Request to return JSON format
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> entity = new HttpEntity<User>(user, headers);
		// Send request with GET method, and Headers.
		ResponseEntity<User> response = restTemplate.exchange(API_BASE_URL + "/users/" + id, HttpMethod.PUT, entity,
				User.class);
		return response.getBody();
	}

	// delete existing user
	@RequestMapping(value = API_END_PONIT + "/{id}", method = RequestMethod.DELETE)
	public User deleteUser(@PathVariable("id") String id) {
		HttpHeaders headers = new HttpHeaders();
		// Request to return JSON format
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> entity = new HttpEntity<User>(headers);
		// Send request with GET method, and Headers.
		ResponseEntity<User> response = restTemplate.exchange(API_BASE_URL + "/users/" + id, HttpMethod.DELETE, entity,
				User.class);
		return response.getBody();
	}
}
