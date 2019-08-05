package games.bevs.core.module.levels.event;

import org.bukkit.event.Cancellable;

import games.bevs.core.module.player.PlayerData;
import games.bevs.core.module.player.events.PlayerDataEventBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRecieveExpEvent extends PlayerDataEventBase implements Cancellable
{
	private long nextExp;
	private long amount;
	private String reason;
	private boolean cancelled = false;
	
	public ClientRecieveExpEvent(PlayerData client, long nextExp, long amount, String reason)
	{
		super(client);
		this.nextExp = nextExp;
		this.amount = amount;
		this.reason = reason;
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
