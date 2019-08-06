package games.bevs.core.module.player.operations.redis;

import games.bevs.core.commons.database.operation.DatabaseSettings;
import games.bevs.core.commons.database.operation.DatabseOperation;

public class RedisPlayerDataFetch extends DatabseOperation
{

	public RedisPlayerDataFetch(DatabaseSettings settings) 
	{
		super(settings);
	}

}
