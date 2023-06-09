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
	private RedisTemplate<String, Long> template;
	
	public void saveDisabledTime(String name, Long time)
	{
		template.opsForValue()
			.set(name, time);
	}

	public Long checkTime(String name)
	{
		Long endTime = template.opsForValue()
						.get(name);
		return endTime;
	}
	// TODO Task 5
	// Use this class to implement CRUD operations on Redis

}
