package games.bevs.core.commons.database.api.minidbs;

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import games.bevs.core.commons.database.api.MiniDatabase;
import games.bevs.core.commons.player.PlayerData;

public interface PlayerDataMiniDB extends MiniDatabase
{
	public PlayerData loadPlayerData(Player player);
	public PlayerData loadPlayerData(UUID uniqueId);
	
	public void savePlayerData(PlayerData user);
	
	public List<PlayerData> getAllPlayerDatas();
}
