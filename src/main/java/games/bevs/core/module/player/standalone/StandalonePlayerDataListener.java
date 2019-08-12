package games.bevs.core.module.player.standalone;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import games.bevs.core.commons.database.mysql.MySQLManager;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.module.player.PlayerDataModule;

public class StandalonePlayerDataListener implements Listener
{
	
	
	private PlayerDataModule playerDataModule;
	private MySQLManager mySQLManager;
	
	public StandalonePlayerDataListener(PlayerDataModule playerDataModule, MySQLManager mySQLManager)
	{
		this.playerDataModule = playerDataModule;
		this.mySQLManager = mySQLManager;
	}
	
	public void queryPlayerData(PlayerData playerData)
	{
		
//		mySQLManager.getConnection().prepareStatement(sql);
	}
	
	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent e)
	{
		UUID uniqueId = e.getUniqueId();
		String username = e.getName();
		
		String message = username + " is waitng for their PlayerData to load...";
		
		//load from database
		
		
		this.playerDataModule.log(username + "'s PlayerData has been loaded");
	}
}
