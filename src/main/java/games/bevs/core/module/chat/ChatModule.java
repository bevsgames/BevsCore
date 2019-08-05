package games.bevs.core.module.chat;

import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.client.ClientModule;
import games.bevs.core.module.commandv2.CommandModule;

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
	public ChatModule(JavaPlugin plugin, CommandModule commandModule, ClientModule clientModule)
	{
		super(plugin, commandModule, clientModule);
	}

	
}
