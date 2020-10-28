package com.sampleAPI.springbootrestapitests;

import com.sampleAPI.springbootrestapitests.users.Users;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootrestapiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringbootrestapiApplicationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:"+port;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllUsers(){
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + "/users", HttpMethod.GET, entity, String.class);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testCreateUser(){
		Users user = new Users();
		user.setFirstName("TestUser");
		user.setLastName("TestUser");
		user.setEmail("TestUSer@gmail.com");
		user.setUserName("TestUser");
		user.setPassword("TestUser");

		ResponseEntity<Users> postResponse = testRestTemplate.postForEntity(getRootUrl() + "/users", user, Users.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateUser(){
		int id = 1;
		Users user = testRestTemplate.getForObject(getRootUrl() + "/users" + id, Users.class);
		user.setFirstName("TestUser1");
		user.setLastName("TestUser1");
		user.setEmail("TestUser1@gmail.com");
		testRestTemplate.put(getRootUrl() + "/users" + id, Users.class);

		Users updatedUser = testRestTemplate.getForObject(getRootUrl() + "/users" + id, Users.class);
		Assert.assertNotNull(updatedUser);
	}

	@Test
	public void testDeleteUser(){
		int id = 2;
		Users user = testRestTemplate.getForObject(getRootUrl() + "/users" + id, Users.class);
		Assert.assertNotNull(user);

		testRestTemplate.delete(getRootUrl() + "/users" + id, Users.class);

		try{
			user = testRestTemplate.getForObject(getRootUrl() + "/users" + id, Users.class);
		} catch (final HttpClientErrorException e){
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
