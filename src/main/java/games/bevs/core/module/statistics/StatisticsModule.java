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
	//Stats should save when you get out
	//OR when you win
	//We should save stats at the end of very game, and only add stats
	
	//When a player connects to another server
		//Asks the last server the player was on for new data
		//gets new data
		//Saves data
	
	public StatisticsModule(BevsPlugin plugin, CommandModule commandModule)
	{
		super(plugin, commandModule);
	}

	
}
