package vttp2023.batch3.ssf.frontcontroller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpSession;
import vttp2023.batch3.ssf.frontcontroller.model.User;
import vttp2023.batch3.ssf.frontcontroller.respositories.AuthenticationRepository;

@Service
public class AuthenticationService 
{
	@Autowired
	private AuthenticationRepository repo;

	private String authenString = "https://authservice-production-e8b2.up.railway.app/api/authenticate";
	// TODO: Task 2
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write the authentication method in here
	public void authenticate(String username, String password) throws Exception 
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
		template.exchange(req, String.class);
	}

	public boolean checkAnswer(HttpSession session, String answer)
	{
		if(session.getAttribute("answer").equals(answer))
		return true;
		else 
		return false;
	}

	public int randomNum()
	{
		int ran = (int)Math.floor(Math.random()*50+1);
		System.out.println(ran);
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

	public String generateCaptcha(HttpSession session)
	{
		int x = randomNum();
		int y = randomNum();
		String op = randomOpe();
		session.setAttribute("answer", calculate(x, y, op));
		System.out.println(x+op+y);
		return x+op+y;
	}

	public String calculate(int x, int y, String op)
	{
		String xy;
		switch(op)
		{
			case "+": xy=Integer.toString(x+y);
				break;
			case "-": xy=Integer.toString(x-y);
				break;
			case "*": xy=Integer.toString(x*y);
				break;
			default: xy=Integer.toString(x/y);
		}
		return xy;
	}

	// TODO: Task 3
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to disable a user account for 30 mins
	public void disableUser(String username) 
	{
		long startTime = System.currentTimeMillis();
		long end = startTime+(30*60*1000);
		String endTime = String.valueOf(end);
		repo.saveDisabledTime(username, endTime);
	}
	
	// TODO: Task 5
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to check if a given user's login has been disabled
	public boolean isLocked(String username, HttpSession session) 
	{
		long timeNow = System.currentTimeMillis();
		try
		{
			String endTime = repo.checkTime(username);
			Long end = Long.parseLong(endTime);
			System.out.println(endTime+" "+timeNow);
			if(timeNow>end)
			{
				System.out.println(username+" is released");
				repo.deleteTime(username);
				session.setAttribute(username, 0);
				return false;
			} else
			{
				System.out.println(username+" is still locked");
				return true;
			}
		} catch(Exception ex)
		{
			System.out.println("Cannot find ending time for "+username);
			return false;
		}
	}
}
