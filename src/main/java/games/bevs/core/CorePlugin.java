package games.bevs.core;

import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.abilties.AbilityModule;
import games.bevs.core.module.combat.CombatModule;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.essentials.EssentialsModule;
import games.bevs.core.module.player.PlayerDataModule;
import games.bevs.core.module.sponge.SpongeModule;
import games.bevs.core.module.sponge.SpongeSettings;
import games.bevs.core.module.sponge.types.LauncherType;

public class CorePlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		CommandModule commandModule = new CommandModule(this);
		PlayerDataModule clientModule = new PlayerDataModule(this, commandModule);
		commandModule.setClientModule(clientModule); // this allows us to check ranks

//		new CombatModule(this, commandModule, clientModule);
		new AbilityModule(this, commandModule, true);
		
//		new EssentialsModule(this, commandModule, clientModule);
		
//		SpongeSettings spongeSettings = new SpongeSettings();
//		spongeSettings.setLauncherType(LauncherType.CLASSIC);
//		
//		new SpongeModule(this, spongeSettings);
	}

	@Override
	public void onDisable() 
	{
	}

}
