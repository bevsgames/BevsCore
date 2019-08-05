package games.bevs.core.module.player;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;

/**
 * Player will joins, data is downloaded
 * and saved to redis
 * 
 * To do, this player disconnecting and connecting to another server via bungee
 *
 */
@ModInfo(name = "Client")
public class PlayerDataModule extends Module
{

	public PlayerDataModule(JavaPlugin plugin, CommandModule commandModule)
	{
		super(plugin, commandModule);
	}
	
	public PlayerData getPlayer(UUID uniqueId)
	{
		return null;
	}
	
	public PlayerData getPlayer(Player player)
	{
		return this.getPlayer(player.getUniqueId());
	}
}
