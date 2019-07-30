package games.bevs.core.redis.example;

import lombok.Getter;
import redis.clients.jedis.JedisPool;

public class Redis {
	private @Getter JedisPool pool;

	public Redis() {
		
	}
	
	public void connect()
	{
		ClassLoader previous = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(Redis.class.getClassLoader());
		pool = new JedisPool("78.31.71.65", 6379);
		Thread.currentThread().setContextClassLoader(previous);
	}
	
}
