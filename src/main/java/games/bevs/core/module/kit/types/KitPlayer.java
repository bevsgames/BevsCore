package games.bevs.core.module.kit.types;

import java.util.UUID;

import games.bevs.core.commons.player.MCPlayer;
import lombok.Getter;
import lombok.Setter;

public class KitPlayer extends MCPlayer
{
	@Getter 
	@Setter
	private Kit kit;
	
	public KitPlayer(UUID uniquieId, String username) {
		super(uniquieId, username);
	}

	public KitPlayer(UUID uniquieId)
	{
		super(uniquieId);
	}
}
