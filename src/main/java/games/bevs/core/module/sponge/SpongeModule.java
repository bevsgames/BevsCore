package games.bevs.core.module.sponge;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import games.bevs.core.module.Module;
import games.bevs.core.module.sponge.listeners.BevsSpongeListener;
import games.bevs.core.module.sponge.listeners.ClassicSpongeListener;
import lombok.Getter;

/**
 * This module will allow you 
 * to place sponges which will launch players
 * in the opposite director of the sponges
 * <br/>
 * We have Bevs implementation which is based on pressure plates 
 * which is handled on interaction Event (aka when a pleasure plate is step on)
 * and Classic which is mcpvp's which is based on move event 
 */
public class SpongeModule extends Module
{
	private @Getter NoFallDamageList noFallDamageList;
	private @Getter SpongeSettings spongeSettings;

	public SpongeModule(JavaPlugin plugin, SpongeSettings spongeSettings)
	{
		super(plugin);
		this.spongeSettings = spongeSettings;
		this.noFallDamageList = new NoFallDamageList();
		
		
		switch(this.getSpongeSettings().getLauncherType())
		{
			case BEVS:
				this.registerListener(new BevsSpongeListener(this));
				break;
			case CLASSIC:
				this.registerListener(new ClassicSpongeListener(this));
				break;
			default:
				break;
		}
	}
	
	public SpongeModule(JavaPlugin plugin)
	{
		this(plugin, new SpongeSettings());
	}

	public void launch(Player player, Block block) 
	{
		Vector velocity = new Vector(0, getSpongeDepth(block), 0);
		velocity = velocity.add(new Vector(getSpongeDepth(block.getRelative(-1, -1, 0)), 0, 0));
		velocity = velocity.add(new Vector(-getSpongeDepth(block.getRelative(1, -1, 0)), 0, 0));
		velocity = velocity.add(new Vector(0, 0, getSpongeDepth(block.getRelative(0, -1, -1))));
		velocity = velocity.add(new Vector(0, 0, -getSpongeDepth(block.getRelative(0, -1, 1))));
		velocity = velocity.multiply(10);
	}

	private int getSpongeDepth(Block block) 
	{
		int depth = 0;

		while (block.getType() == this.getSpongeSettings().getLaunchMaterial()) 
		{
			depth++;
			if (block.getY() == 0)
				break;
			block = block.getRelative(BlockFace.DOWN);
		}
		return depth;
	}
}
