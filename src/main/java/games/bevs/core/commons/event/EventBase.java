package games.bevs.core.commons.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EventBase extends Event
{
	/* Bukkit code that isn't really needed in every class 99% of the time */
	private static final HandlerList handlerList = new HandlerList();
    @Override public HandlerList getHandlers() { return handlerList; }
    public static HandlerList getHandlerList() { return handlerList; }
}
