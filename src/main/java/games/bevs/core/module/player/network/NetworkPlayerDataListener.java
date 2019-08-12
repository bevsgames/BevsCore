package games.bevs.core.module.player.network;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.network.packets.PlayerDataRequest;
import games.bevs.core.commons.network.packets.PlayerDataResponse;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.redis.JedisPublisher;
import games.bevs.core.commons.redis.JedisSettings;
import games.bevs.core.commons.redis.JedisSubscriber;
import games.bevs.core.commons.redis.subscribe.JedisSubscriptionHandler;
import games.bevs.core.commons.server.ServerData;
import games.bevs.core.commons.utils.JsonUtils;
import games.bevs.core.module.player.PlayerDataModule;

/**
* PlayerData Transfer Protocol
* * A Player changes servers on bungee
* * The new server makes a request for playerData of the old server
* * old server replies with payload
* 
* <b>REQUEST JSON</b>
* <code>
* {
*	  'server': 'newServersId',
*	  'serverTo': 'newServersId',
*	  'type': 'REQUEST',
*	  'data': {
*				'uniqueId': '',//the uuid of the player
*				'username': ''
*			  } 
* }
* </code>
* <br/>
* <b>RESPONSE JSON</b>
* <code>
* {
*	  'server': 'oldServerId',
*	  'serverTo': 'newServersId'
*	  'type': 'RESPONSE',
*	  'data': {
*				 //DATA GENERATED FROM PLAYERDATA
*			  } 
* }
* </code>
*/
public class NetworkPlayerDataListener implements Listener
{
	private PlayerDataModule playerDataModule;
	
	private JedisPublisher<JsonObject> messagesPublisher;
	private JedisSubscriber<JsonObject> messagesSubscriber;
	
	public NetworkPlayerDataListener(PlayerDataModule playerDataModule)
	{
		this.playerDataModule = playerDataModule;
		
		createNetworkMsgHandler();
	}
	
	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent e)
	{
		UUID uniqueId = e.getUniqueId();
		String username = e.getName();
		
		String message = username + " is waitng for their PlayerData to load...";
		
		while(this.playerDataModule.getPlayerData(uniqueId) == null)
		{
			try {
				this.playerDataModule.log(message);
				Thread.sleep(25L);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
		this.playerDataModule.log(username + "'s PlayerData has been loaded");
	}
	
	public void createNetworkMsgHandler()
	{
		JedisSettings settings = new JedisSettings("78.31.71.65", 6379, "McpvpIsLife4378@13123!F");
		
		messagesPublisher = new JedisPublisher<JsonObject>(settings, "global-messages");
		
		messagesSubscriber = new JedisSubscriber<JsonObject>(settings, "global-messages", JedisSubscriber.JSON_GENERATOR,
			new JedisSubscriptionHandler<JsonObject>()
			{
			    @Override
			    public void handleMessage(JsonObject packet) {
			    	//ignore our messages
			    	
			    	String packetType = packet.get("type").getAsString();
			    	String serverSender = packet.get("server").getAsString();
			    	String serverSendTo = packet.get("serverTo").getAsString();
			    	JsonObject data = packet.get("data").getAsJsonObject();
			    	
			    	//log
			    	NetworkPlayerDataListener.this.playerDataModule.log("Packet (" + packetType + ") recieved from " + serverSender + " with data " + data);
			    	
			    	BevsPlugin plugin = NetworkPlayerDataListener.this.playerDataModule.getPlugin();
			    	ServerData serverData = plugin.getServerData();
			    	
			    	switch(packetType)
			    	{
			    		// A request was made, so we'll send out our data
			    		// In the future this will be issued by the bungee, so we know the serverTo, thus we can know before if we have a mis
			    		case PlayerDataRequest.PACKET_TYPE:
			    			String username = data.get("username").getAsString();
			    			String uniqueIdStr = data.get("uniqueId").getAsString();
			    			
			    			UUID uniqueId = UUID.fromString(uniqueIdStr);
			    			
			    			PlayerData playerData = NetworkPlayerDataListener.this.playerDataModule.getPlayerData(uniqueId);
			    			if(playerData == null) return;
			    			
			    			PlayerDataResponse response = new PlayerDataResponse(serverData.getId(), serverSender, playerData);
			    			messagesPublisher.write(response.build());
			    		break;
			    		
			    		case PlayerDataResponse.PACKET_TYPE:
			    			PlayerData importedPlayerData = JsonUtils.fromJson(data.getAsString(), PlayerData.class);
			    			NetworkPlayerDataListener.this.playerDataModule.registerPlayerData(importedPlayerData);
				    	break;
			    	}
			    	
			    }
			});
	}
}
