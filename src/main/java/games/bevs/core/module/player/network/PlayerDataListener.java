package games.bevs.core.module.player.network;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import com.google.gson.JsonObject;

import games.bevs.core.commons.database.redis.JedisPublisher;
import games.bevs.core.commons.database.redis.JedisSettings;
import games.bevs.core.commons.database.redis.JedisSubscriber;
import games.bevs.core.commons.database.redis.subscribe.JedisSubscriptionHandler;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.utils.JsonUtils;
import games.bevs.core.module.player.PlayerDataModule;
import lombok.AllArgsConstructor;

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
public class PlayerDataListener implements Listener
{
	private PlayerDataModule playerDataModule;
	
	private JedisPublisher<JsonObject> messagesPublisher;
	private JedisSubscriber<JsonObject> messagesSubscriber;
	
	public PlayerDataListener(PlayerDataModule playerDataModule)
	{
		this.playerDataModule = playerDataModule;
		
		createNetworkMsgHandler();
	}
	
	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent e)
	{
		UUID uniqueId = e.getUniqueId();
		String username = e.getName();
		
		
		
		//
	}
	
	public void createNetworkMsgHandler()
	{
		JedisSettings settings = new JedisSettings("78.31.71.65", 6379, "McpvpIsLife4378@13123!F");
		
		messagesPublisher = new JedisPublisher<JsonObject>(settings, "global-messages");
		
		messagesSubscriber = new JedisSubscriber<JsonObject>(settings, "global-messages", JedisSubscriber.JSON_GENERATOR,
			new JedisSubscriptionHandler<JsonObject>()
			{
			    @Override
			    public void handleMessage(JsonObject object) {
			    	//ignore our messages
			    	if(object.get("id").getAsString().equalsIgnoreCase(""+Bukkit.getPort()))
			    		return;
			    	if(object.get("name").getAsString().equalsIgnoreCase("pong"))
			    	{
			    		//player data as json
			    		String payloadPlayerData = object.getAsString();
			    		PlayerData playerData = JsonUtils.fromJson(payloadPlayerData, PlayerData.class);
			    		playerData.setLoaded(true);
			    		return;
			    	}
			    	
			    	
			    	
			    	
			    }
			});
	}
}
