package games.bevs.core.module.player.operations.redis;

import games.bevs.core.commons.database.operation.DatabaseSettings;
import games.bevs.core.commons.database.operation.DatabseOperation;

public class RedisPlayerDataSave extends DatabseOperation
{

	public RedisPlayerDataSave(DatabaseSettings settings)
	{
		super(settings);
	}

}
