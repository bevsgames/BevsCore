package games.bevs.core.module.punishment.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.module.player.PlayerDataModule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class ChatListener implements Listener
{
	private @Getter @Setter PlayerDataModule playerDataModule;
	
	@EventHandler(priority= EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent e)
	{
		Player player = e.getPlayer();
		PlayerData playerData = this.playerDataModule.getPlayerData(player);
		if(playerData == null) return;
		if(playerData.isMuted()) e.setCancelled(true);
	}
}
