package games.bevs.core;

import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.client.ClientModule;
import games.bevs.core.module.combat.CombatModule;
import games.bevs.core.module.commands.CommandModule;

public class CorePlugin extends JavaPlugin {

	@Override
	public void onEnable() 
	{
		CommandModule commandModule = new CommandModule(this);
		ClientModule clientModule = new ClientModule(this, commandModule);
		
		new CombatModule(this, commandModule, clientModule);
	}


}
