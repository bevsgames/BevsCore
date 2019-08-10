package games.bevs.core;

import games.bevs.core.module.abilties.AbilityModule;
import games.bevs.core.module.combat.CombatModule;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.cooldown.CooldownModule;
import games.bevs.core.module.essentials.EssentialsModule;
import games.bevs.core.module.player.PlayerDataModule;
import games.bevs.core.module.sponge.SpongeModule;
import games.bevs.core.module.sponge.SpongeSettings;
import games.bevs.core.module.sponge.types.LauncherType;
import games.bevs.core.module.ticker.TickerModule;

public class CorePlugin extends BevsPlugin {
	private static CorePlugin inst;

	@Override
	public void onEnable() {
		super.onEnable();

		inst = this;

		CommandModule commandModule = this.addModule(new CommandModule(this));
		PlayerDataModule clientModule = this.addModule(new PlayerDataModule(this, commandModule));
		commandModule.setClientModule(clientModule); // this allows us to check ranks

		new CombatModule(this, commandModule, clientModule);

		this.addModule(new CombatModule(this, commandModule, clientModule));
		CooldownModule cooldown = this.addModule(new CooldownModule(this, commandModule));
		this.addModule(new AbilityModule(this, commandModule, cooldown, true));

		this.addModule(new EssentialsModule(this, commandModule, clientModule));

		SpongeSettings spongeSettings = new SpongeSettings();
		spongeSettings.setLauncherType(LauncherType.CLASSIC);

		this.addModule(new SpongeModule(this, spongeSettings));
		this.addModule(new TickerModule(this));
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

	public static CorePlugin getInstance() {
		return inst;
	}
}
