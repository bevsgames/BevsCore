package games.bevs.core.commons.database;

import games.bevs.core.commons.database.mysql.MySQLManager;
import games.bevs.core.commons.redis.RedisManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DatabaseSettings 
{
	private RedisManager redisManager;
	private MySQLManager mysqlManager;
}
