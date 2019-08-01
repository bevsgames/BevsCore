package games.bevs.core.commons.server;

import org.bukkit.Bukkit;

public class OnlinePlayers
{
	
	public OnlinePlayers()
	{
		
	}
	
	public int playerCount()
	{
		return Bukkit.getOnlinePlayers().size();
	}
}
