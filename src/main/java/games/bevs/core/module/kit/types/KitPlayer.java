package games.bevs.core.module.kit.types;

import java.util.UUID;

import games.bevs.core.commons.player.SimpleMCPlayer;
import lombok.Getter;
import lombok.Setter;

public class KitPlayer extends SimpleMCPlayer
{
	@Getter 
	@Setter
	private Kit kit;
	
	public KitPlayer(UUID uniquieId)
	{
		super(uniquieId);
	}
}
