package games.bevs.core;

import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.abilties.AbilityModule;
import games.bevs.core.module.combat.CombatModule;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.essentials.EssentialsModule;
import games.bevs.core.module.player.PlayerDataModule;

public class CorePlugin extends JavaPlugin {

	private static CorePlugin inst;

	@Override
	public void onEnable() {
		inst = this;
		CommandModule commandModule = new CommandModule(this);
		PlayerDataModule clientModule = new PlayerDataModule(this, commandModule);
		commandModule.setClientModule(clientModule); // this allows us to check ranks

		new CombatModule(this, commandModule, clientModule);
		new AbilityModule(this, commandModule, true);
		
		new EssentialsModule(this, commandModule, clientModule);
	}

	@Override
	public void onDisable() {
		inst = null;
	}

	public static CorePlugin getInstance() {
		return inst;
	}

}
