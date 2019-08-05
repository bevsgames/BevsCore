package games.bevs.core.module.chat;

import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.player.PlayerDataModule;

/**
 * Handles
 * * Filtering blacklisted words
 * * Handles displaying ranks
 * * Handles server silences server 
 * * Handles ignores
 * * Handles private messaging
 */
@ModInfo(name = "Chat")
public class ChatModule extends Module
{
	public ChatModule(JavaPlugin plugin, CommandModule commandModule, PlayerDataModule clientModule)
	{
		super(plugin, commandModule, clientModule);
	}

	
}
