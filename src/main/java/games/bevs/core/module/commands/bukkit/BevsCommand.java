package games.bevs.core.module.commands.bukkit;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.client.Rank;

public class BevsCommand extends BukkitCommand
{

	public BevsCommand(String name, CommandExecutor executor, JavaPlugin plugin, Rank rank)
	{
		super(name, executor, plugin);
		this.setRequiredRank(rank);
	}

}
