package games.bevs.core.module.player.events;

import games.bevs.core.commons.player.PlayerData;

public class PlayerDataPreSaveEvent extends PlayerDataEventBase
{

	public PlayerDataPreSaveEvent(PlayerData client) 
	{
		super(client);
	}

}
