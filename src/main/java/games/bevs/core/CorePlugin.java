package games.bevs.core;

import games.bevs.core.commons.database.api.Database;
import games.bevs.core.commons.database.api.DatabaseSettings;
import games.bevs.core.commons.database.mongo.MongoDatabase;
import games.bevs.core.commons.database.none.NoneDatabase;
import games.bevs.core.module.abilties.AbilityModule;
import games.bevs.core.module.combat.CombatModule;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.cooldown.CooldownModule;
import games.bevs.core.module.display.DisplayModule;
import games.bevs.core.module.essentials.EssentialsModule;
import games.bevs.core.module.kit.KitModule;
import games.bevs.core.module.npc.NPCModule;
import games.bevs.core.module.player.PlayerDataModule;
import games.bevs.core.module.punishment.PunishmentModule;
import games.bevs.core.module.sponge.SpongeModule;
import games.bevs.core.module.sponge.SpongeSettings;
import games.bevs.core.module.sponge.types.LauncherType;
import games.bevs.core.module.ticker.TickerModule;

public class CorePlugin extends BevsPlugin 
{
	@Override
	public void onEnable() {
		super.onEnable();

		Database database = null;
		if(this.getServerData().isDisabledDatabase())
		{
			database = new NoneDatabase(null);
		}
		else
		{
//			MySQLDatabase mySQLDatabase = new MySQLDatabase(this, new DatabaseSettings("localhost", "8889", "BevsGames", "core_user", "*c4GS-X2!wwrJnA"));
			database = new MongoDatabase(new DatabaseSettings("localhost", "27017", "bevsGames", null, null));
		}
		
		//Load databases
		
		//load command and player Modules
		CommandModule commandModule = this.addModule(new CommandModule(this));
		PlayerDataModule playerModule = this.addModule(new PlayerDataModule(this, commandModule, database));
		commandModule.setPlayerDataModule(playerModule); // this allows us to check ranks

		this.addModule(new CombatModule(this, commandModule, playerModule));

		CooldownModule cooldown = this.addModule(new CooldownModule(this, commandModule));
		this.addModule(new AbilityModule(this, commandModule, cooldown, true));

		this.addModule(new EssentialsModule(this, commandModule, playerModule));
		this.addModule(new PunishmentModule(this, commandModule, playerModule));

		SpongeSettings spongeSettings = new SpongeSettings();
		spongeSettings.setLauncherType(LauncherType.CLASSIC);

		this.addModule(new SpongeModule(this, spongeSettings));
		this.addModule(new TickerModule(this));
		
		this.addModule(new DisplayModule(this));
		this.addModule(new NPCModule(this, commandModule));
		
		this.addModule(new KitModule(this));
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}
}
