package games.bevs.core.module.antihax;

import games.bevs.core.BevsPlugin;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;

/**
 * BevsAntiHax
 */
@ModInfo(name = "BAH")
public class AntiHaxModule extends Module
{

	public AntiHaxModule(BevsPlugin plugin, CommandModule commandModule) 
	{
		super(plugin, commandModule);
	}
	
	@Override
	public void onEnable()
	{
		
	}
}
