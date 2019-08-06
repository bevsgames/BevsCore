package games.bevs.core.module.player;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.JsonObject;

import games.bevs.core.commons.database.redis.JedisPublisher;
import games.bevs.core.commons.database.redis.JedisSettings;
import games.bevs.core.commons.database.redis.JedisSubscriber;
import games.bevs.core.commons.database.redis.subscribe.JedisSubscriptionHandler;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;

/**
 * Player will joins, data is downloaded
 * and saved to redis
 * 
 * To do, this player disconnecting and connecting to another server via bungee
 *
 */
@ModInfo(name = "PlayerData")
public class PlayerDataModule extends Module
{
	private JedisPublisher<JsonObject> messagesPublisher;
	private JedisSubscriber<JsonObject> messagesSubscriber;

	public PlayerDataModule(JavaPlugin plugin, CommandModule commandModule)
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
			        writeTest("pong", Bukkit.getPort() + "");
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
	

	public void writeTest(String name, String id) {
		JsonObject object = new JsonObject();
		object.addProperty("name", name);
		object.addProperty("id", id);
		messagesPublisher.write(object);
	}
	
	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent e)
	{
		System.out.println("Trigged write");
		this.writeTest(e.getName(), Bukkit.getPort() + "");
	}
}
