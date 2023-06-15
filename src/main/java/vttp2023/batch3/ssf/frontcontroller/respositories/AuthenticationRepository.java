package vttp2023.batch3.ssf.frontcontroller.respositories;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.data.redis.core.RedisTemplate;

import vttp2023.batch3.ssf.frontcontroller.model.User;

@Repository
public class AuthenticationRepository 
{	
	@Autowired
	@Qualifier("login")
	private RedisTemplate<String, String> template;
	
	public void saveDisabledTime(String name, String time)
	{
		template.opsForValue()
			.set(name, time);
	}

	public String checkTime(String name)
	{
		String endTime = template.opsForValue()
						.get(name);
		return endTime;
	}

	public void deleteTime(String name)
	{
		template.delete(name);
	}
	// TODO Task 5
	// Use this class to implement CRUD operations on Redis

}
