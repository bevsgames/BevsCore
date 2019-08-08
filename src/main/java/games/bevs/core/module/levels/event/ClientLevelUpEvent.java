package games.bevs.core.module.levels.event;

import org.bukkit.event.Cancellable;

import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.module.player.events.PlayerDataEventBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientLevelUpEvent extends PlayerDataEventBase implements Cancellable
{
	private int fromLevel;
	private int toLevel;
	private boolean cancelled = false;
	
	public ClientLevelUpEvent(PlayerData client, int fromLevel, int toLevel)
	{
		super(client);
		this.fromLevel = fromLevel;
		this.toLevel = toLevel;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean isCancelled)
	{
		this.cancelled = isCancelled;
	}

	
}
