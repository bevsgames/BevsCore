package games.bevs.core.module.player.network;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.google.gson.JsonObject;

import games.bevs.core.commons.CC;
import games.bevs.core.commons.network.PacketConnectionManager;
import games.bevs.core.commons.network.packets.PlayerDataRequest;
import games.bevs.core.commons.network.packets.PlayerDataResponse;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.player.rank.Rank;
import games.bevs.core.commons.player.rank.events.RankChangeEvent;
import games.bevs.core.commons.redis.JedisSettings;
import games.bevs.core.commons.utils.JsonUtils;
import games.bevs.core.commons.utils.StringUtils;
import games.bevs.core.module.player.PlayerDataModule;
import lombok.Getter;

/**
 * This class handles when players are 
 * connecting on the network to other 
 * servers so we can maintain stats
 * correctly
 * 
 * WIP: have to hook it up to bungee for the first login
 *
 */
public class NetworkPlayerListener implements Listener
{
	private static long TIMEOUT_PLAYERDATA_RESPONSE = 3000l;
	
	private @Getter PlayerDataModule playerDataModule;
	private PacketConnectionManager packetConnectManage;
	
	public NetworkPlayerListener(PlayerDataModule playerDataModule)
	{
		this.playerDataModule = playerDataModule;
		
		String serverId = this.playerDataModule.getPlugin().getServerData().getId();
		JedisSettings settings = new JedisSettings("78.31.71.65", 6379, "McpvpIsLife4378@13123!F");
		packetConnectManage = new PacketConnectionManager(this.playerDataModule.getPlugin(), serverId, "PlayerData", settings);
		
		//Generates a PlayerDataResponse for a new server
		packetConnectManage.registerPacketHandler(PlayerDataRequest.class, (jsonObject) -> 
		{
			JsonObject data = jsonObject.get("data").getAsJsonObject();
			String unqieIdStr = data.get("uniqueId").getAsString();
			
			UUID uniqueId = UUID.fromString(unqieIdStr);
			//Server we'll send it to
			String serverTo = jsonObject.get("serverFrom").getAsString();
			
			PlayerData playerData = this.playerDataModule.getPlayerData(uniqueId);
			PlayerDataResponse playerDataResponse = new PlayerDataResponse(serverId, serverTo, playerData);
			packetConnectManage.sendPacket(playerDataResponse);
		});
		
		packetConnectManage.registerPacketHandler(PlayerDataResponse.class, (jsonObject) -> {
			String playerDataJson = jsonObject.get("data").getAsString();
			PlayerData playerData = JsonUtils.fromJson(playerDataJson, PlayerData.class);
			
			playerData.setLoaded(true);
			this.playerDataModule.registerPlayerData(playerData);
		});
	}
	
	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent e)
	{
		String username = e.getName();
		UUID uniqueId = e.getUniqueId();
		
		//Data is already loaded?
		if(this.getPlayerDataModule().getPlayerData(uniqueId) != null)
			return;
		
		long start = System.currentTimeMillis();

		
		String serverId = this.getPlayerDataModule().getPlugin().getServerData().getId();
		PlayerDataRequest playerDataRequest = new PlayerDataRequest(serverId, uniqueId, username);
		this.packetConnectManage.sendPacket(playerDataRequest);
		
		//Run Database connection for fallback here (Async)
		
		//Wait for PlayerData Response from last server
		while(this.getPlayerDataModule().getPlayerData(uniqueId) == null)
		{
			this.getPlayerDataModule().log(username + " is waiting for PlayerData...");
			if(System.currentTimeMillis() - start >= TIMEOUT_PLAYERDATA_RESPONSE)
			{
				this.getPlayerDataModule().log(username + "'s PlayerData took too long, went to fallback");
				//get fallback from database
				break;
			}
			try 
			{
				Thread.sleep(50l);
			} 
			catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
		}
		
		PlayerData playerData = this.getPlayerDataModule().getPlayerData(uniqueId);
		
		//Check ban
		if(playerData.getBanExpires() - System.currentTimeMillis() > 0)
		{
			e.disallow(Result.KICK_BANNED, CC.bAqua + "Banned");
			//so we don't register player
			return;
		}
		
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
