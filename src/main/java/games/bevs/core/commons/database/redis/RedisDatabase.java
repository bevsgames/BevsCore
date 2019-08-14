package games.bevs.core.commons.database.redis;

import games.bevs.core.commons.database.api.Database;
import games.bevs.core.commons.database.api.DatabaseSettings;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class RedisDatabase extends Database
{
	private JedisPool redis;

	public RedisDatabase(DatabaseSettings settings) 
	{
		super(settings);
//		redisSettings = new JedisSettings("78.31.71.65", 6379, "McpvpIsLife4378@13123!F");
		
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMinIdle(10);
		config.setMaxIdle(99);
		config.setNumTestsPerEvictionRun(4);
		config.setTestWhileIdle(true);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		config.setMinEvictableIdleTimeMillis(15000);
		config.setMaxTotal(100);
		config.setMaxWaitMillis(120000);
		config.setBlockWhenExhausted(false);

		redis = new JedisPool(config, settings.getUrl(), Protocol.DEFAULT_PORT, 0, settings.getPassword(), Protocol.DEFAULT_DATABASE, null);

		Runtime.getRuntime().addShutdownHook(new Thread() 
		{
			public boolean equals(Object obj) {
				return obj != null && hashCode() == obj.hashCode();
			}

			public int hashCode() {
				return "redis".hashCode();
			}

			public void run() 
			{
				if(!redis.isClosed())
					redis.close();
			}
		});
	}

	@Override
	public void close() 
	{
		redis.close();
	}

}
