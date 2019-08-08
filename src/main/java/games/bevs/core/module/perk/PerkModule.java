package games.bevs.core.module.perk;

import games.bevs.core.BevsPlugin;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;

/**
 * This module allows you to control
 * if the player will take fall damage
 * Invis and others
 * 
 * TODO: Finish this so we can use it for sponge Module
 *
 */
@ModInfo(name = "Perks")
public class PerkModule extends Module
{

	public PerkModule(BevsPlugin plugin, CommandModule commandModule)
	{
		super(plugin, commandModule);
	}
}
