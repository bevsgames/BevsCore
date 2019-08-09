package games.bevs.core.module.player.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class PlayerListener 
{
	private @Getter JavaPlugin plugin;
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onAysncPlayerPreLogin(AsyncPlayerPreLoginEvent event)
	{

//		if (!this.getPlugin().isSetupMode()) {
//			event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Server is setting up!");
//			return;
//		}

		if (event.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) {
			return;
		}

		Bukkit.getScheduler().runTaskLaterAsynchronously(this.getPlugin(), () -> {
			//load data
		}, 40L);
	}
}
