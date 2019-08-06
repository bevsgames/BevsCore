package games.bevs.core.module.commandv2;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.types.BevsCommand;
import games.bevs.core.module.player.PlayerDataModule;
import lombok.Getter;

/**
 * 
 * NOTE: YOU MUST set PlayerDataModule,
 * TODO: FIX THIS SO ITS COULD JUST USE PERMISSIONS TOO
 */
@ModInfo(name="Commands")
public class CommandModule extends Module
{
	private @Getter CommandMap commandMap;
	private @Getter PlayerDataModule playerDataModule;
	
	public CommandModule(JavaPlugin plugin) 
	{
		super(plugin);
	}
	
	@Override
	public void onEnable()
	{
		try {
			Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			
			bukkitCommandMap.setAccessible(true);
			commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void registerCommand(BevsCommand command)
	{
		this.registerBevsCommand(command);
	}
	
	public void setPlayerDataModule(PlayerDataModule playerDataModule)
	{
		this.playerDataModule = playerDataModule;
	}
	
	/**
	 * Call to register a command
	 * @param command
	 */
	public void registerBevsCommand(BevsCommand command) 
	{
		command.setCommandModule(this);
		commandMap.register(command.getName(), command);
	}
}
