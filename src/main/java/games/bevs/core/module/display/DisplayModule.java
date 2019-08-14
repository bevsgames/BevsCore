package games.bevs.core.module.display;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.itemstack.ItemStackBuilder;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.display.types.Clickable;
import games.bevs.core.module.display.types.Display;
import games.bevs.core.module.display.types.Screen;

/**
 * Easily build gui's
 */
@ModInfo(name="Display")
public class DisplayModule extends Module
{

	public DisplayModule(BevsPlugin plugin)
	{
		super(plugin);
	}
	
	@Override
	public void onEnable()
	{
		this.registerSelf();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		Display display = new Display(""  + player.getName(), player);
		this.registerListener(display);
		
		Screen screen = new Screen("Sprock's demo", 18, display);
		
		screen.setClickable(1, new Clickable(new ItemStackBuilder(Material.BOW), (clickLog) ->  {
			clickLog.getPlayer().sendMessage("CLIKED the bow");
		}));
		
		display.setScreen(screen, player);
		display.open(player);
	}
}
