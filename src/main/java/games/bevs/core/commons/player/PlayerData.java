package games.bevs.core.commons.player;

import java.util.UUID;

import games.bevs.core.commons.Rank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class PlayerData
{
	private int internalId;
	private @NonNull UUID uniqueId;
	private Rank rank;
	private Rank displayRank;
	
	private long gold;
	
	private int level;
	private int experience;
	private int expToLevel;
	
	private boolean loaded = false;
	
	public PlayerData(UUID uniqueId)
	{
		this.uniqueId = uniqueId;
	}
}
