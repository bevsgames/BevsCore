package games.bevs.core.module.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import games.bevs.core.commons.Rank;
import games.bevs.core.commons.player.MCPlayer;
import lombok.Getter;
import lombok.Setter;

/**
 * Player store
 *
 */
@Getter
@Setter
public class PlayerData extends MCPlayer
{
	private @Getter int internalId;
	private @Getter @Setter Rank rank = Rank.NORMAL;
	private @Getter @Setter Rank displayRank = Rank.NORMAL;
	
	private @Getter @Setter String currentServer;
	
	private @Getter @Setter long joinTimestamp;
	
	private @Getter @Setter long bannedTill = -1;
	private @Getter @Setter long mutedTill = -1;

	private @Getter @Setter boolean loaded = false;
	private HashSet<String> flags = new HashSet<>();	
	


	public PlayerData(UUID uniquieId, String username) 
	{
		super(uniquieId, username);
	}

	public PlayerData(UUID uniquieId) 
	{
		super(uniquieId);
	}
	
	
	public void save()
	{
//		PlayerData playerData = clone();
		
	}
	
}
