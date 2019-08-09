package games.bevs.core.commons.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginUtils 
{
	public static void registerListener(Listener listener, JavaPlugin plugin)
	{
		Bukkit.getPluginManager().registerEvents(listener, plugin);
	}
	
	public static void repeat(JavaPlugin plugin, Runnable run, long deplay, long sprint)
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, run, deplay, sprint);
	}
	
	public static void later(JavaPlugin plugin, Runnable run, long deplay)
	{
		Bukkit.getScheduler().runTaskLater(plugin, run, deplay);
	}
}
