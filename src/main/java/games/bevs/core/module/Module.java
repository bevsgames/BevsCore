package games.bevs.core.module;

import org.bukkit.event.Listener;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.CC;
import games.bevs.core.commons.server.Config;
import games.bevs.core.commons.server.Console;
import games.bevs.core.commons.utils.PluginUtils;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.commandv2.types.BevsCommand;
import games.bevs.core.module.player.PlayerDataModule;
import lombok.Getter;
import lombok.Setter;

public class Module implements Listener
{
	private @Getter BevsPlugin plugin;
	private @Setter Config config;
	private @Getter @Setter boolean debug;
	private @Getter @Setter CommandModule commandModule;
	private @Getter @Setter PlayerDataModule playerDataModule;
	
	public Module(BevsPlugin plugin, CommandModule commandModule, boolean debug)
	{
		this.plugin = plugin;
		this.commandModule = commandModule;
		this.debug = debug;
		
		if(this.getModInfo().hasConfig())
		{
			this.config = new Config(this.getName(), "config", plugin);
			this.config.save();
		}
	}
	
	public Module(BevsPlugin plugin, CommandModule commandModule, PlayerDataModule playerDataModule)
	{
		this.plugin = plugin;
		this.commandModule = commandModule;
		this.playerDataModule = playerDataModule;
		
		if(this.getModInfo().hasConfig())
		{
			this.config = new Config(this.getName(), "config", plugin);
			this.config.save();
		}
	}
	
	public Module(BevsPlugin plugin, CommandModule commandModule)
	{
		this(plugin, commandModule, null);
	}
	
	public Module(BevsPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	public void log(String message)
	{
		Console.log(CC.green + this.getName(), CC.r + message);
	}
	
	public void debug(String message)
	{
		//add hide condition
		Console.log(CC.yellow + this.getName(), CC.gray + message);
	}
	
	public void registerListener(Listener listener)
	{
		PluginUtils.registerListener(listener, this.getPlugin());
	}
	
	public void registerSelf()
	{
		this.registerListener(this);
	}
	
	public  void registerCommand(BevsCommand simpleCommand)
	{
		if(this.commandModule == null) 
		{
			this.log(CC.red + "failed to registered command '" + simpleCommand.getName() + "'");
			return;
		}
		this.commandModule.registerBevsCommand(simpleCommand);
		this.log("registered command '" + simpleCommand.getName() + "'");
	}
	
	private ModInfo getModInfo()
	{
		return this.getClass().getAnnotation(ModInfo.class);
	}
	
	public String getName()
	{
		return getModInfo().name();
	}
	
	public String[] getAlises()
	{
		return getModInfo().alises();
	}
	
	public Config getConfig() throws Exception
	{
		if(!this.getModInfo().hasConfig())
			throw new Exception("HasConfig() is not enabled for this module '" + this.getName() + "'");
		return this.config;
	}
	
	public void enable()
	{
		this.log("Enabling....");
		
		this.onEnable();
		this.log("Enabled");
	}
	
	public void disable()
	{
		this.log("Disabling....");
		this.onDisble();
		this.log("Disabled");
	}
	
	public void onEnable() {}
	public void onDisble() {}
}
