package games.bevs.core.module.player;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.database.api.Database;
import games.bevs.core.commons.database.api.minidbs.PlayerDataMiniDB;
import games.bevs.core.commons.network.PacketConnectionManager;
import games.bevs.core.commons.network.packets.PlayerDataRequest;
import games.bevs.core.commons.network.packets.PlayerDataResponse;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.redis.JedisSettings;
import games.bevs.core.commons.utils.JsonUtils;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.player.commands.RankCommand;
import games.bevs.core.module.player.listeners.PlayerListener;
import lombok.Getter;

/**
 * We want to first check if the player is on the network?
 * - We could preload them on to the network when they join
 * - 
 * 
 * PlayerData Transfer Protocol
 * * A Player changes servers on bungee
 * * The new server makes a request for playerData of the old server
 * * old server replies with payload
 * 
 * <b>REQUEST JSON</b>
 * <code>
 * {
 *	  'Server': 'newServersId',
 *	  'type': 'REQUEST',
 *	  'data': {
 *				'uniqueId': '',//the uuid of the player
 *				'username': ''
 *			  } 
 * }
 * </code>
 * <br/>
 * <b>RESPONSE JSON</b>
 * <code>
 * {
 *	  'Server': 'newServersId',
 *	  'type': 'RESPONSE',
 *	  'data': {
 *				 //DATA GENERATED FROM PLAYERDATA
 *			  } 
 * }
 * </code>
 */
@ModInfo(name = "PlayerData")
public class PlayerDataModule extends Module
{
	private @Getter Database database;
	
	private HashMap<UUID, PlayerData> players = new HashMap<>();
	
	public PlayerDataModule(BevsPlugin plugin, CommandModule commandModule, Database database)
	{
		super(plugin, commandModule);
		this.database = database;
	}
	
	@Override
	public void onEnable()
	{
		
		Bukkit.getOnlinePlayers().forEach(player -> {
			PlayerData playerData = this.getPlayerDataMiniDB().loadPlayerData(player);
			this.registerPlayerData(playerData);
		});
		
		boolean isOnNetwork = this.getPlugin().getServerData().isOnNetwork();
		this.log("Boosting in " + (isOnNetwork ? "Network" : "StandAlone"));
		if(this.getPlugin().getServerData().isOnNetwork())
		{
			//Network Mode
			this.registerListener(new NetworkPlayerListener(this));
		}
		else
		{
			//Standalone Mode
			this.registerListener(new PlayerListener(this));
		}
		
		this.registerCommand(new RankCommand(this));
	}
	
	public PlayerDataMiniDB getPlayerDataMiniDB()
	{
		return this.getDatabase().getMiniDatabase(PlayerDataMiniDB.class);
	}
	
	public PlayerData registerPlayerData(PlayerData playerData)
	{
		return this.players.put(playerData.getUniqueId(), playerData);
	}
	
	public PlayerData getPlayerData(Player player)
	{
		return this.getPlayerData(player.getUniqueId());
	}
	
	public PlayerData getPlayerData(UUID uniqueId)
	{
		return this.players.get(uniqueId);
	}
	
	public void unregisterPlayerData(UUID uniqueId)
	{
		this.players.remove(uniqueId);
	}
}