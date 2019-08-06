package games.bevs.core.module.sponge.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import games.bevs.core.module.sponge.SpongeModule;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This method is the classic mcpvp method 
 * but it makes uses of playerMoveEvent which 
 * doesn't scale well
 */
@AllArgsConstructor
public class ClassicSpongeListener implements Listener 
{
	private @Getter SpongeModule module;

	@EventHandler(ignoreCancelled = true)
	public void onPlayerMove(PlayerMoveEvent e) 
	{
		Player player = e.getPlayer();
		Block block = e.getTo().getBlock().getRelative(0, -1, 0);

		if (block.getType() == this.getModule().getSpongeSettings().getLaunchMaterial())
		{
			this.getModule().launch(player, block);
		}
	}
}
