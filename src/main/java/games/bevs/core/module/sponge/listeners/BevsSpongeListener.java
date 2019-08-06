package games.bevs.core.module.sponge.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import games.bevs.core.module.sponge.SpongeModule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BevsSpongeListener implements Listener
{
	private @Getter SpongeModule module;

	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		Action action = e.getAction();
		Block clickedBlock = e.getClickedBlock();
		Block standingBlock = clickedBlock.getRelative(BlockFace.DOWN);
		
		if(action == Action.PHYSICAL 
				&& clickedBlock.getType() == Material.STONE_PLATE
				&& standingBlock.getType() == this.getModule().getSpongeSettings().getLaunchMaterial())
		{
			this.getModule().launch(player, standingBlock);
		}
	}
}
