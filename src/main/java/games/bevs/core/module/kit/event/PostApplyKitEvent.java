package games.bevs.core.module.kit.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

import games.bevs.core.commons.event.PlayerEventBase;
import games.bevs.core.module.kit.types.Kit;
import lombok.Getter;
import lombok.Setter;

public class PostApplyKitEvent extends PlayerEventBase implements Cancellable
{
	@Getter
	@Setter
	private Kit kit;
	private boolean cancelled = false;

	public PostApplyKitEvent(Player who, Kit kit) 
	{
		super(who);
		this.kit = kit;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean isCancelled) {
		this.cancelled = isCancelled;
	}
}
