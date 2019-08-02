package games.bevs.core.module.chat.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener
{
	@EventHandler(priority = EventPriority.LOWEST)
	public void HandleChat(AsyncPlayerChatEvent event)
	{
		if (event.isCancelled()) return;
		
	}
}
