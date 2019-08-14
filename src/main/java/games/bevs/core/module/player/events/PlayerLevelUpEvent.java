package games.bevs.core.module.player.events;

import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.player.PlayerDataEvent;
import lombok.Getter;

@Getter
public class PlayerLevelUpEvent extends PlayerDataEvent
{
	private long newLevel;
	private long oldLevel;
	
	private long maxExperience;
	private long experience;
	
	public PlayerLevelUpEvent( long newLevel, long oldLevel, long experience, long maxExperience, PlayerData playerData) 
	{
		super(playerData);
		this.newLevel = newLevel;
		this.oldLevel = oldLevel;
		this.experience = experience;
		this.maxExperience = maxExperience;
	}

}
