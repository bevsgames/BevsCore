package games.bevs.core.module.punishment.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.module.player.events.PlayerDataLoadedEvent;

public class BanListener implements Listener
{
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLogin(PlayerDataLoadedEvent e)
	{
		PlayerData client = e.getClient();
	}

}
