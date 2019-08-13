package games.bevs.core.commons.player.rank.events;

import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.player.PlayerDataEvent;
import games.bevs.core.commons.player.rank.Rank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankChangeEvent extends PlayerDataEvent {

	private Rank oldRank;
	private Rank newRank;
	
	public RankChangeEvent(PlayerData playerData, Rank oldRank, Rank newRank) 
	{
		super(playerData);
		this.oldRank = oldRank;
		this.newRank = newRank;
	}

}
