package games.bevs.core.commons.player.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import games.bevs.core.commons.player.PlayerData;

public class PlayerDataDao extends BasicDAO<PlayerData, String>
{

	public PlayerDataDao(Class<PlayerData> entityClass, Datastore ds) 
	{
		super(entityClass, ds);
	}

}
