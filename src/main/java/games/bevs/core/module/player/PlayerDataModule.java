package games.bevs.core.module.player;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.google.gson.JsonObject;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.database.redis.JedisPublisher;
import games.bevs.core.commons.database.redis.JedisSettings;
import games.bevs.core.commons.database.redis.JedisSubscriber;
import games.bevs.core.commons.database.redis.subscribe.JedisSubscriptionHandler;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;

/**
 * Player will joins, data is downloaded
 * and saved to redis
 * 
 * To do, this player disconnecting and connecting to another server via bungee
 *
 * We could cache the players rank on bungee and send that with the players
 *
 *
 * If we are on the bungee, we'll have to pull and cache from a redis
 * to avoid race conditions of getting data. but on a stand alone server
 * We can just deal with the database directly
 */
//RedisRequestPlayerData [uuid]
//RedisResponsePlayerData [interalId, uniqueId, rank, displayRank, gold, level, experience, expToLevel]
	//Remove data from old server
//New server listens for RedisResponsePlayerData
@ModInfo(name = "PlayerData")
public class PlayerDataModule extends Module
{
	private int gold = 0;
	private JedisPublisher<JsonObject> messagesPublisher;
	private JedisSubscriber<JsonObject> messagesSubscriber;

	public PlayerDataModule(BevsPlugin plugin, CommandModule commandModule)
	{
		super(plugin, commandModule);
	}
	
	@Override
	public void onEnable()
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
			    		System.out.println("WE WERE PONGED!!!!");
			    		return;
			    	}
			    	
			        System.out.println(object.get("name"));
			        writeTest("pong", gold, Bukkit.getPort() + "");
			    }
			});
		
		
		this.registerSelf();
	}
	
	@Override
	public void onDisble()
	{
		messagesSubscriber.close();
	}
	
	public PlayerData getPlayer(UUID uniqueId)
	{
		return null;
	}
	
	public PlayerData getPlayer(Player player)
	{
		return this.getPlayer(player.getUniqueId());
	}
	

	public void writeTest(String name, int gold, String id) {
		JsonObject object = new JsonObject();
		object.addProperty("name", name);
		object.addProperty("gold", gold);
		object.addProperty("id", id);
		messagesPublisher.write(object);
	}
	
	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent e)
	{
		this.writeTest(e.getName(), gold, Bukkit.getPort() + "");
		//stop player login in until we got their stats
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		System.out.println("tooo late, the player joined");
	}
	
	//Server B: AsyncPlayerPreLoginEvent, Pings old server
	//Server A: Recieves ping, sends players stats with pong
	//Server B: Accepts Pong and prints stats
}
