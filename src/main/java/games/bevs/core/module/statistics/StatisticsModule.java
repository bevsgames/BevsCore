package games.bevs.core.module.statistics;

import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commands.CommandModule;

@ModInfo(name = "Statistics")
public class StatisticsModule extends Module
{

	public StatisticsModule(JavaPlugin plugin, CommandModule commandModule)
	{
		super(plugin, commandModule);
	}

}
