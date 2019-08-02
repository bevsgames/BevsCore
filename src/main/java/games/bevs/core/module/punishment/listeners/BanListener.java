package games.bevs.core.module.punishment.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import games.bevs.core.module.client.Client;
import games.bevs.core.module.client.events.ClientLoadedEvent;

public class BanListener implements Listener
{
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLogin(ClientLoadedEvent e)
	{
		Client client = e.getClient();
	}

}
