package games.bevs.core.module.levels.event;

import org.bukkit.event.Cancellable;

import games.bevs.core.module.client.Client;
import games.bevs.core.module.client.events.ClientEventBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRecieveExpEvent extends ClientEventBase implements Cancellable
{
	private long nextExp;
	private long amount;
	private String reason;
	private boolean cancelled = false;
	
	public ClientRecieveExpEvent(Client client, long nextExp, long amount, String reason)
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
