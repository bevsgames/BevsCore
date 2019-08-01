package games.bevs.core.module.joinquit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import lombok.Getter;
import lombok.Setter;

@ModInfo(name = "joinquit")
public class JoinQuitModule extends Module
{
	private @Getter @Setter JoinQuitSettings joinQuitSettings;
	
	public JoinQuitModule(JavaPlugin plugin, JoinQuitSettings joinQuitSettings)
	{
		super(plugin);
		
		this.joinQuitSettings = joinQuitSettings;
	}
	
	public JoinQuitModule(JavaPlugin plugin)
	{
		this(plugin, new JoinQuitSettings());
	}
	
	@Override
	public void onEnable()
	{
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		e.setJoinMessage(this.getJoinQuitSettings().getJoinMessage());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		e.setQuitMessage(this.getJoinQuitSettings().getQuitMessage());
	}
}
