package games.bevs.core.module.player.standalone;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import games.bevs.core.commons.database.DatabaseSettings;
import games.bevs.core.commons.database.mysql.MySQLManager;
import games.bevs.core.commons.database.operations.LoadPlayerData;
import games.bevs.core.commons.database.tables.PlayerDataTable;
import games.bevs.core.commons.database.tables.StatisticsTable;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.module.player.PlayerDataModule;

public class StandalonePlayerDataListener implements Listener
{
	
	
	private PlayerDataModule playerDataModule;
	private MySQLManager mySQLManager;
	private DatabaseSettings databaseSetting;
	
	public StandalonePlayerDataListener(PlayerDataModule playerDataModule, MySQLManager mySQLManager)
	{
		this.playerDataModule = playerDataModule;
		this.mySQLManager = mySQLManager;
		this.databaseSetting = new DatabaseSettings(null, mySQLManager);
		
		//Create tables if they don't exist
		new PlayerDataTable(this.databaseSetting);
		new StatisticsTable(this.databaseSetting);
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
		
		//load from database
		PlayerData playerData = new PlayerData(username, uniqueId);
		this.playerDataModule.registerPlayerData(playerData);
		this.playerDataModule.log(username + " is waitng for their PlayerData to load...");
		new LoadPlayerData(this.databaseSetting, playerData);
		
		while(!playerData.isLoaded())
		{
			try {
				Thread.sleep(50L);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
		this.playerDataModule.log(username + "'s PlayerData has been loaded");
	}
	
	@EventHandler
	public void onJoinEvent(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		PlayerData playerData = this.playerDataModule.getPlayerData(player);
		if(playerData == null)
		{
			player.kickPlayer("Failed to load Player data in time");
			return;
		}
		player.setPlayerListName(playerData.getDisplayRank().getTagColor() + player.getName());
	}
}
