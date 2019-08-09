package games.bevs.core.module.player.operations.redis;

import games.bevs.core.commons.database.operation.DatabaseSettings;
import games.bevs.core.commons.database.operation.DatabseOperation;

public class RedisPlayerDataDelete extends DatabseOperation
{

	public RedisPlayerDataDelete(DatabaseSettings settings)
	{
		super(settings);
	}

}
