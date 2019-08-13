package games.bevs.core.module.player.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import games.bevs.core.commons.CC;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.player.rank.Rank;
import games.bevs.core.commons.player.rank.events.RankChangeEvent;
import games.bevs.core.commons.utils.StringUtils;
import games.bevs.core.module.player.PlayerDataModule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class PlayerListener implements Listener
{
	private @Getter PlayerDataModule playerDataModule;
	
	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent e)
	{
		String username = e.getName();
		UUID uniqueId = e.getUniqueId();
		
		//Data is already loaded?
		if(this.getPlayerDataModule().getPlayerData(uniqueId) != null)
			return;
		
		long start = System.currentTimeMillis();
		PlayerData playerData = this.getPlayerDataModule().getPlayerDataMiniDB().loadPlayerData(uniqueId);
		
		//Just incase they have changed their username
		playerData.setUsername(username);
		playerData.setLoaded(true);
		
		//Rank expired
		if(playerData.getRankExpires() - System.currentTimeMillis() < 0)
		{
			playerData.setRank(Rank.NORMAL);
		}
		
		this.getPlayerDataModule().registerPlayerData(playerData);
		long finish = System.currentTimeMillis() - start;
		this.getPlayerDataModule().log("It took " + finish + "ms for " + username + "'s profile to load!");
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		String username = player.getName();
		UUID uniqueId = player.getUniqueId();
		
		PlayerData playerData = this.getPlayerDataModule().getPlayerData(uniqueId);
		
		if(playerData != null)
		{
			player.setPlayerListName(playerData.getRank().getTagColor() + player.getName());
			playerData.addStatistic("general_server_joins", 1);
			return;
		}
		
		player.kickPlayer(CC.bdRed + "PlayerData couldn't load in time, you joined too fast!");
		this.getPlayerDataModule().log("Failed to load " + username + "'s profile!");
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		Player player = e.getPlayer();
		String username = player.getName();
		UUID uniqueId = player.getUniqueId();
		
		PlayerData playerData = this.getPlayerDataModule().getPlayerData(player);
		
		//no profile to save
		if(this.getPlayerDataModule().getPlayerData(uniqueId) == null)
			return;
		 this.getPlayerDataModule().unregisterPlayerData(uniqueId);
		
		new Thread(() ->
		{
			long start = System.currentTimeMillis();
			this.getPlayerDataModule().getPlayerDataMiniDB().savePlayerData(playerData);
			long finish = System.currentTimeMillis() - start;
			
			
			this.getPlayerDataModule().log("Successfully saved " + username + "'s profile in " + finish + "ms!");
		}).start();
	}
	
	@EventHandler
	public void onRankChange(RankChangeEvent e)
	{
		Player player = Bukkit.getPlayer(e.getPlayerData().getUniqueId());
		if(player == null) return;
		Rank rank = e.getNewRank();
		player.sendMessage(StringUtils.info("Rank", "You now have " + rank.getColouredDisplayName() + CC.gray + " rank!"));
		player.setPlayerListName(rank.getTagColor() + player.getName());
		
	}
}
