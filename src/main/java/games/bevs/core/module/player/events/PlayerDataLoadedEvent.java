package games.bevs.core.module.player.events;

import games.bevs.core.module.player.PlayerData;

public class PlayerDataLoadedEvent extends PlayerDataEventBase
{

	public PlayerDataLoadedEvent(PlayerData client) 
	{
		super(client);
	}

}
