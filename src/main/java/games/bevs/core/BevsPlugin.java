package games.bevs.core;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.commons.server.Config;
import games.bevs.core.commons.server.ServerData;
import games.bevs.core.module.Module;
import lombok.Getter;

public class BevsPlugin extends JavaPlugin
{
	private static final String SERVER_ID_PATH = "server.id";
	private static final String SERVER_NAME_PATH = "server.name";
	private static final String SERVER_GROUP_PATH = "server.group";
	private static final String SERVER_DOMAIN_PATH = "server.domain";
	private static final String SERVER_VIEWED_PATH = "server.viewed";
	
	private @Getter ServerData serverData;
	
	@Override
	public void onEnable() 
	{
		//Generate basic settings for each server
		Config serverConfig = new Config("settings", this)
				{
					@Override
					public void onCreate(FileConfiguration configFile)
					{
						configFile.set(SERVER_ID_PATH, "test-sprock-1");
						configFile.set(SERVER_NAME_PATH, "Test");
						configFile.set(SERVER_GROUP_PATH, "test-group");
						configFile.set(SERVER_DOMAIN_PATH, "test.bevs.games");
						configFile.set(SERVER_VIEWED_PATH, false);
					}
				};
		FileConfiguration config = serverConfig.getConfig();
		String serverId = config.getString(SERVER_ID_PATH);
		String serverName = config.getString(SERVER_NAME_PATH);
		String serverGroup = config.getString(SERVER_GROUP_PATH);
		String serverDomain = config.getString(SERVER_GROUP_PATH);
		this.serverData = new ServerData(serverId, serverName, serverGroup, serverDomain);
		
		//Shut server down if we forgot to update server.yml
		if(!config.getBoolean(SERVER_VIEWED_PATH))
		{
			for(int i = 0; i < 20; i++)
			{
				System.out.println(" ");
				System.out.println(" ");
				System.out.println("PLEASE UPDATE ALL SETTINGS IN settings.yml!");
				System.out.println("SET settings.yml's " + SERVER_VIEWED_PATH + " to true to launch normally");
			}
		}
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
