package games.bevs.core.commons.server;

import java.io.IOException;

import org.bukkit.Bukkit;

public class SpigotConfigChanger 
{
	public static void killOfflineUUIDs() 
	{
		Bukkit.spigot().getConfig().set("settings.bungeecord", true);
		try {
			Bukkit.spigot().getConfig().save("spigot.yml");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Bukkit.shutdown();
	}
}
