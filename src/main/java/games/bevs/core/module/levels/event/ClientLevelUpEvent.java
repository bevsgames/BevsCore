package games.bevs.core.module.levels.event;

import org.bukkit.event.Cancellable;

import games.bevs.core.module.client.Client;
import games.bevs.core.module.client.events.ClientEventBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientLevelUpEvent extends ClientEventBase implements Cancellable
{
	private int fromLevel;
	private int toLevel;
	private boolean cancelled = false;
	
	public ClientLevelUpEvent(Client client, int fromLevel, int toLevel)
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
