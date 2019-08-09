package games.bevs.core.module.tablist;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import games.bevs.core.BevsPlugin;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.placeholder.PlaceholderModule;
import games.bevs.core.module.player.PlayerDataModule;
import lombok.Getter;

//TODO: need to finish Placeholder module
@ModInfo(name = "Tablist")
public class TabListModule extends Module
{
	private @Getter PlaceholderModule  placeholders;

	public TabListModule(BevsPlugin plugin, CommandModule commandModule, PlayerDataModule clientModule, PlaceholderModule  placeholders)
	{
		super(plugin, commandModule, clientModule);
		this.placeholders = placeholders;
	}
	
	@Override
	public void onEnable()
	{
		this.registerSelf();
	}

	@SuppressWarnings("unused")
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
//		this.getPlaceholders().
	}
}
