package games.bevs.core.commons.database.operation;

import games.bevs.core.commons.database.mysql.MySQLManager;
import games.bevs.core.commons.database.redis.RedisManager;
import lombok.Getter;

public class DatabseOperation
{
	private @Getter boolean success = false;
	private @Getter DatabaseSettings settings;
	
	public DatabseOperation(DatabaseSettings settings)
	{
		this.settings = settings;
	}
	
	public void setSuccess()
	{
		this.success = true;
	}
}
