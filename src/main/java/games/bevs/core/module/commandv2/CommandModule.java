package games.bevs.core.module.commandv2;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.client.ClientModule;
import games.bevs.core.module.commandv2.types.BevsCommand;
import lombok.Getter;
import lombok.Setter;

@ModInfo(name="Commands")
public class CommandModule extends Module
{
	private @Getter CommandMap commandMap;
	private @Getter @Setter ClientModule clientModule;

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
	
	public void registerCommand(BevsCommand command)
	{
		this.registerBevsCommand(command);
	}
	
	/**
	 * Call to register a command
	 * @param command
	 */
	public void registerBevsCommand(BevsCommand command) {
		commandMap.register(command.getName(), command);
	}
}
