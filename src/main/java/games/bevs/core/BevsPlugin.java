package games.bevs.core;

import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.Module;

public class BevsPlugin extends JavaPlugin
{
	
	@Override
	public void onEnable() 
	{
		
	}
	
	@Override
	public void onDisable() 
	{
		
	}
	
	public <T extends Module> T addModule(T module)
	{
		module.enable();
		return module;
	}
}
