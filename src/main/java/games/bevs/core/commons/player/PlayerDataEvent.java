package games.bevs.core.commons.player;

import games.bevs.core.commons.event.EventBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerDataEvent extends EventBase
{
	private PlayerData playerData;
}
