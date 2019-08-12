package games.bevs.core.module.player;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.database.mysql.MySQLManager;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.player.network.NetworkPlayerDataListener;
import games.bevs.core.module.player.standalone.StandalonePlayerDataListener;

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
	private MySQLManager mySQLManager;
	
	private HashMap<UUID, PlayerData> players = new HashMap<>();
	
	public PlayerDataModule(BevsPlugin plugin, CommandModule commandModule, MySQLManager mySQLManager)
	{
		super(plugin, commandModule);
		this.mySQLManager = mySQLManager;
	}
	
	@Override
	public void onEnable()
	{
		if(this.getPlugin().getServerData().isOnNetwork())
		{
			this.log("Starting in NETWORK MODE!");
			this.registerListener(new NetworkPlayerDataListener(this));
		}
		else
		{
			this.log("Starting in STANDALONE MODE!");
			this.registerListener(new StandalonePlayerDataListener(this, this.mySQLManager));
		}
		
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