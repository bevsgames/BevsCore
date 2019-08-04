package games.bevs.core.module.client;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commands.CommandModule;

/**
 * Player will joins, data is downloaded
 * and saved to redis
 * 
 * To do, this player disconnecting and connecting to another server via bungee
 *
 */
@ModInfo(name = "Client")
public class ClientModule extends Module
{

	public ClientModule(JavaPlugin plugin, CommandModule commandModule)
	{
		super(plugin, commandModule);
	}
	
	public Client getPlayer(UUID uniqueId)
	{
		return null;
	}
	
	public Client getPlayer(Player player)
	{
		return this.getPlayer(player.getUniqueId());
	}
}
