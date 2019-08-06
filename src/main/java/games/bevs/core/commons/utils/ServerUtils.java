package games.bevs.core.commons.utils;

import org.bukkit.Bukkit;

import games.bevs.core.commons.CC;

public class ServerUtils
{
	public static final String SERVER_NAME = "BevsGames";
	
	/**
	 * Says if the server is on the network
	 */
	public static boolean isOnBungee()
	{
		return Bukkit.spigot().getSpigotConfig().getBoolean("settings.bungeecord");
	}
	
	public static void broadcast(String message)
	{
		Bukkit.broadcastMessage(CC.yellow + SERVER_NAME + CC.bGold + " !! " + CC.white + message);
	}
}
