package games.bevs.core.commons;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import games.bevs.core.commons.utils.MCNameUtils;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class MCPlayer 
{
	private @NonNull String username;
	private @NonNull UUID uniquieId;
	
	public MCPlayer(UUID uniquieId, String username)
	{
		this.uniquieId = uniquieId;
		this.username = username;
	}
	
	public MCPlayer(UUID uniquieId)
	{
		this.uniquieId = uniquieId;
		
		//fetch username
		this.username = this.fetchUsername();
	}
	
	private String fetchUsername() 
	{
		try 
		{
			String username = MCNameUtils.getUsername(this.getUniquieId());
			
			if(username == null)
			{
				throw new Exception("Couldn't find username");
			}
			
			return username;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		// Just in case this works
		OfflinePlayer player = Bukkit.getOfflinePlayer(getUniquieId());
		String username = player.getName();
		return username;
	}
}
