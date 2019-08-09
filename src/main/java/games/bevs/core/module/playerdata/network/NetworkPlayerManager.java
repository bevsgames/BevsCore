package games.bevs.core.module.playerdata.network;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.json.simple.JSONObject;

import com.google.gson.JsonObject;

import games.bevs.core.commons.CC;
import games.bevs.core.commons.database.redis.JedisPublisher;
import games.bevs.core.commons.database.redis.JedisSettings;
import games.bevs.core.commons.database.redis.JedisSubscriber;
import games.bevs.core.commons.database.redis.subscribe.JedisSubscriptionHandler;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.server.Console;
import games.bevs.core.commons.utils.JsonUtils;
import games.bevs.core.module.playerdata.PlayerDataModule;
import lombok.Getter;

public class NetworkPlayerManager
{
	private @Getter PlayerDataModule playerDataModule;
	
	private static JedisPublisher<JsonObject> messagesPublisher;
	private static JedisSubscriber<JsonObject> messagesSubscriber;
	
	public NetworkPlayerManager(PlayerDataModule playerDataModule, JedisSettings settings)
	{
//		JedisSettings settings = new JedisSettings("78.31.71.65", 6379, "McpvpIsLife4378@13123!F");

		messagesPublisher = new JedisPublisher<JsonObject>(settings, "global-messages");
		messagesSubscriber = new JedisSubscriber<JsonObject>(settings, "global-messages", JedisSubscriber.JSON_GENERATOR,
				new JedisSubscriptionHandler<JsonObject>()
				{
				    @Override
				    public void handleMessage(JsonObject object)
				    {
				    	//ignore messages from our selfs
				    	String serverId = Bukkit.getPort() + "";
				    	if(object.get("serverId").getAsString().equalsIgnoreCase(serverId))
				    		return;
				    	
				    	if(object.get("type").getAsString().equalsIgnoreCase("request"))
				    	{
				    		//Handle request
				    		String uuidStr = object.get("data").getAsJsonObject().get("uuid").getAsString();
				    		UUID uuid = UUID.fromString(uuidStr);
				    		
				    		System.out.println("Recieved request " + uuidStr);
				    		
				    		writePacket("response", serverId, generatePlayerData(playerDataModule.getPlayerData(uuid)));
				    
				    		System.out.println("Pushed response " + uuidStr);
				    	}
				    	

				    	if(object.get("type").getAsString().equalsIgnoreCase("response"))
				    	{
				    		//Handle request
				    		String playerData = object.get("data").getAsString();
				    		
				    		System.out.println("Receive response " + playerData);
				    		
				    		PlayerData playerDataResponse = JsonUtils.fromJson(playerData, PlayerData.class);
				    		playerDataResponse.setLoaded(true);
				    		
				    		getPlayerDataModule().registerPlayerData(playerDataResponse);
				    	}
				    	
				    }
				});
		
	}
	
	public String writeRequestPacket(UUID uuid) 
	{
		JsonObject object = new JsonObject();
		object.addProperty("uuid", uuid.toString());
		return object.toString();
	}
	
	
	public void writePacket(String type, String serverId, String data) 
	{
		JsonObject object = new JsonObject();
		object.addProperty("type", type);
		object.addProperty("serverId", serverId);
		object.addProperty("data", data);
		messagesPublisher.write(object);
	}
	
	public String generatePlayerData(PlayerData playerData) 
	{
		return JsonUtils.toJson(playerData);
	}
	
	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent e)
	{
		String username = e.getName();
		UUID uniqueId = e.getUniqueId();
		
		PlayerData playerData = new PlayerData(uniqueId);
		getPlayerDataModule().registerPlayerData(playerData);
		
		System.out.println("sending request! ");
		writePacket("Request", Bukkit.getPort() + "", writeRequestPacket(uniqueId));
		
		//Wait until we have loaded the data via the listener
		while(!playerData.isLoaded())
		{
			System.out.println("Waiting for player response");
			try 
			{
				Thread.sleep(50);
			} 
			catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
			
			playerData = getPlayerDataModule().getPlayerData(uniqueId);
		}
		
		Console.log("NetworkPlayerManager", username + "'s PlayerData has been loaded.");
		this.playerDataModule.registerPlayerData(playerData);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		UUID uniqueId = player.getUniqueId();
		
		PlayerData playerData = this.playerDataModule.getPlayerData(uniqueId);
		
		if(playerData == null)
		{
			player.kickPlayer(CC.red + "Failed to load player data via the network");
			return;
		}
	}
}
