package games.bevs.core.module.sponge.impli;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import games.bevs.core.module.sponge.SpongeModule;
import games.bevs.core.module.sponge.types.LauncherType;
import games.bevs.core.module.sponge.types.SpongeListener;

public class BevsSpongeListener extends SpongeListener
{
	public BevsSpongeListener(SpongeModule module)
	{
		super(module, LauncherType.BEVS);
	}
	
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
