package games.bevs.core.module.player;

import java.net.InetAddress;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.commons.Rank;
import games.bevs.core.commons.managers.PlayerManager;
import games.bevs.core.commons.player.PlayerData;

public class PlayerDataManager extends PlayerManager<PlayerData> 
{

	public PlayerDataManager(JavaPlugin plugin) 
	{
		super(plugin, true);
	}

	@Override
	public PlayerData onPlayerCreation(UUID uniquieId, String username, InetAddress ipAddress) 
	{
		PlayerData client = new PlayerData(uniquieId);
		
		client.setRank(Rank.STAFF);

		client.setLoaded(true);
		return client;
	}

	@Override
	public boolean onPlayerDestruction(PlayerData playerObj) 
	{
		return true;
	}
}
