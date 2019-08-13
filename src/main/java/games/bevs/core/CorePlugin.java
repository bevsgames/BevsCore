package games.bevs.core;

import games.bevs.core.commons.database.api.DatabaseSettings;
import games.bevs.core.commons.database.mongo.MongoDatabase;
import games.bevs.core.commons.database.mysql.MySQLDatabase;
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

public class CorePlugin extends BevsPlugin 
{
	@Override
	public void onEnable() {
		super.onEnable();

		//Load databases
		MySQLDatabase mySQLDatabase = new MySQLDatabase(this, new DatabaseSettings("localhost", "8889", "BevsGames", "core_user", "*c4GS-X2!wwrJnA"));
		MongoDatabase mongoDatabse = new MongoDatabase(new DatabaseSettings("localhost", "8889", "BevsGames", "core_user", "*c4GS-X2!wwrJnA"));
		
		//load command and player Modules
		CommandModule commandModule = this.addModule(new CommandModule(this));
		PlayerDataModule playerModule = this.addModule(new PlayerDataModule(this, commandModule, mongoDatabse));
		commandModule.setClientModule(playerModule); // this allows us to check ranks

		this.addModule(new CombatModule(this, commandModule, playerModule));

		this.addModule(new CombatModule(this, commandModule, playerModule));
		CooldownModule cooldown = this.addModule(new CooldownModule(this, commandModule));
		this.addModule(new AbilityModule(this, commandModule, cooldown, true));

		this.addModule(new EssentialsModule(this, commandModule, playerModule));

		SpongeSettings spongeSettings = new SpongeSettings();
		spongeSettings.setLauncherType(LauncherType.CLASSIC);

		this.addModule(new SpongeModule(this, spongeSettings));
		this.addModule(new TickerModule(this));
		
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}
}
