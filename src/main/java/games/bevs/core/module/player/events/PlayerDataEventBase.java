package games.bevs.core.module.player.events;

import games.bevs.core.commons.event.EventBase;
import games.bevs.core.module.player.PlayerData;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class PlayerDataEventBase extends EventBase
{
	private @Getter PlayerData client;
}
