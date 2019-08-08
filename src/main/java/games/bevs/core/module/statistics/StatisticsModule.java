package games.bevs.core.module.statistics;

import games.bevs.core.BevsPlugin;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;

//We'll only upload the difference


//How can we stop the player from connecting until
//all their information is saved on the old server then loaded 

@ModInfo(name = "Statistics")
public class StatisticsModule extends Module
{

	public StatisticsModule(BevsPlugin plugin, CommandModule commandModule)
	{
		super(plugin, commandModule);
	}

	
}
