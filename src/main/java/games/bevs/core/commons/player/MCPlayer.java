package games.bevs.core.commons.player;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import games.bevs.core.commons.utils.MCAPIUtils;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class MCPlayer extends SimpleMCPlayer
{
	private @NonNull String username;
	
	public MCPlayer(UUID uniquieId, String username)
	{
		super(uniquieId);
		this.username = username;
	}

	public MCPlayer(UUID uniquieId, boolean fetchName)
	{
		super(uniquieId);
		
		//fetch username
		if(fetchName)
			this.username = this.fetchUsername();
	}
	
	private String fetchUsername() 
	{
		try 
		{
			String username = MCAPIUtils.getUsername(this.getUniquieId());
			
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
	
	public Player getPlayer()
	{
		return Bukkit.getPlayer(this.getUniquieId());
	}
}
