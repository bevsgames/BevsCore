package games.bevs.core.commons.database.mongo.minidbs;

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import games.bevs.core.commons.database.api.minidbs.PlayerDataMiniDB;
import games.bevs.core.commons.database.mongo.MongoDatabase;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.player.dao.PlayerDataDao;

public class MongoPlayerDataMiniDB implements PlayerDataMiniDB
{
	private PlayerDataDao playerdataDao;
	
	public MongoPlayerDataMiniDB(MongoDatabase mongoDatabase)
	{
		playerdataDao = new PlayerDataDao(PlayerData.class, mongoDatabase.getDatastore());
	}
	
	@Override
	public PlayerData loadPlayerData(Player player)
	{
		PlayerData playerData = playerdataDao.findOne("uuid", player.getUniqueId().toString());
		if (playerData == null) 
		{
			playerData = new PlayerData(player.getName(), player.getUniqueId());
			this.playerdataDao.save(playerData);
		}
		return playerData;
	}
	
	@Override
	public PlayerData loadPlayerData(UUID uniqueId)
	{
		PlayerData playerData = playerdataDao.findOne("uniqueId", uniqueId.toString());
		if (playerData == null) 
		{
			playerData = new PlayerData("", uniqueId);
			this.playerdataDao.save(playerData);
		}
		return playerData;
	}

	@Override
	public void savePlayerData(PlayerData user) 
	{
		playerdataDao.save(user);
	}

	@Override
	public List<PlayerData> getAllPlayerDatas() 
	{
		return playerdataDao.find().asList();
	}

}
