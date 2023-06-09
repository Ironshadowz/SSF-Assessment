package vttp2023.batch3.ssf.frontcontroller.services;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.catalina.startup.Catalina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.JsonObject;
import vttp2023.batch3.ssf.frontcontroller.model.User;
import vttp2023.batch3.ssf.frontcontroller.respositories.AuthenticationRepository;
@Service
public class AuthenticationService 
{
	@Autowired
	private AuthenticationRepository repo;

	private String authenString = "https://authservice-production-e8b2.up.railway.app";
	// TODO: Task 2
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write the authentication method in here
	public Boolean authenticate(String username, String password) throws Exception 
	{
		User newUser = new User(username, password);
		JsonObject userJson = newUser.toJson();
		System.out.println(userJson);
		
		RequestEntity<String> req = RequestEntity
										.post(authenString)
										.contentType(MediaType.APPLICATION_JSON)
										.header("Accept", "")
										.body(userJson.toString(), String.class);
		RestTemplate template = new RestTemplate();
		ResponseEntity<String> r = template.exchange(req, 
									String.class);
		
		if(r.getStatusCode().equals(HttpStatus.CREATED))
		{
			return true;
		} else
		{
			System.out.println(r.getStatusCode());
			return false;
		}
	}

	public int randomNum()
	{
		int ran = (int)Math.floor(Math.random()*50+1);
		return ran;
	}

	public String randomOpe()
	{	
		String operation;
		int ran = (int)Math.floor(Math.random()*4+1);
		switch(ran)
		{
			case 1:operation="+";
				break;
			case 2:operation="-";
				break;
			case 3:operation="*";
				break;
			default:operation="/";
		}
		return operation;
	}

	public float calculate(int x, int y, String op)
	{
		float xy;
		switch(op)
		{
			case "+": xy=x+y;
				break;
			case "-": xy=x-y;
				break;
			case "*": xy=x*y;
				break;
			default: xy=x/y;
		}
		return xy;
	}

	// TODO: Task 3
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to disable a user account for 30 mins
	public void disableUser(String username) 
	{
		long startTime = System.currentTimeMillis();
		long endTime = startTime+(30*60*1000);
		repo.saveDisabledTime(username, endTime);
	}
	public boolean timeCheck(String username)
	{
		long timeNow = System.currentTimeMillis();
		long endTime = repo.checkTime(username);
		if(timeNow>endTime)
		{
			return true;
		} else
		{
			return false;
		}
	}
	// TODO: Task 5
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to check if a given user's login has been disabled
	public boolean isLocked(String username) 
	{
		return false;
	}
}
