package com.kyyba.poc.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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

	@RequestMapping(value = API_END_PONIT, method = RequestMethod.GET)
	public String getPostList() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		return restTemplate.exchange(API_BASE_URL + "/users", HttpMethod.GET, entity, String.class).getBody();
	}

	@RequestMapping(value = API_END_PONIT, method = RequestMethod.POST)
	public String createPosts(@RequestBody User user) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> entity = new HttpEntity<User>(user, headers);

		return restTemplate.exchange(API_BASE_URL + "/users", HttpMethod.POST, entity, String.class).getBody();
	}

	@RequestMapping(value = API_END_PONIT + "/{id}", method = RequestMethod.GET)
	public String getPost(@PathVariable("id") String id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> entity = new HttpEntity<User>(headers);

		return restTemplate.exchange(API_BASE_URL + "/users/" + id, HttpMethod.GET, entity, String.class).getBody();
	}

	@RequestMapping(value = API_END_PONIT + "/{id}", method = RequestMethod.PUT)
	public String updatePost(@PathVariable("id") String id, @RequestBody User user) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> entity = new HttpEntity<User>(user, headers);

		return restTemplate.exchange(API_BASE_URL + "/users/" + id, HttpMethod.PUT, entity, String.class).getBody();
	}

	@RequestMapping(value = API_END_PONIT + "/{id}", method = RequestMethod.DELETE)
	public String deletePost(@PathVariable("id") String id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> entity = new HttpEntity<User>(headers);

		return restTemplate.exchange(API_BASE_URL + "/users/" + id, HttpMethod.DELETE, entity, String.class).getBody();
	}
}
