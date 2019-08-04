package games.bevs.core;

import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.abilties.AbilityModule;
import games.bevs.core.module.client.ClientModule;
import games.bevs.core.module.combat.CombatModule;
import games.bevs.core.module.commands.CommandModule;

public class CorePlugin extends JavaPlugin {

	private static CorePlugin inst;

	@Override
	public void onEnable() {
		inst = this;
		CommandModule commandModule = new CommandModule(this);
		ClientModule clientModule = new ClientModule(this, commandModule);

		new CombatModule(this, commandModule, clientModule);
		new AbilityModule(this, commandModule, true);
	}

	@Override
	public void onDisable() {
		inst = null;
	}

	public static CorePlugin getInstance() {
		return inst;
	}

}
