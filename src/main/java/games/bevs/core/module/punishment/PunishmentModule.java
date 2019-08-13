package games.bevs.core.module.punishment;

import games.bevs.core.BevsPlugin;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.player.PlayerDataModule;

@ModInfo(name = "Punishment")
public class PunishmentModule extends Module
{

	public PunishmentModule(BevsPlugin plugin, CommandModule commandModule, PlayerDataModule clientModule)
	{
		super(plugin, commandModule, clientModule);
	}

}
