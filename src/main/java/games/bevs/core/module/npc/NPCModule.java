package games.bevs.core.module.npc;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.CC;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.npc.types.FakePlayer;

@ModInfo( name = "NPC" )
public class NPCModule extends Module
{
	public NPCModule(BevsPlugin plugin, CommandModule commandModule) 
	{
		super(plugin, commandModule);
	}

	@Override
	public void onEnable()
	{
		this.registerSelf();
	}
	
	//For testing
	@EventHandler
	public void onPlace(BlockPlaceEvent e)
	{
		if(e.getBlock().getType() != Material.IRON_BLOCK) return;
		FakePlayer fakePlayer = new FakePlayer(CC.bYellow + "BevsGames", e.getBlock().getWorld().getSpawnLocation());
		fakePlayer.setSkin("Sprock");
		fakePlayer.spawn();
	}
}
