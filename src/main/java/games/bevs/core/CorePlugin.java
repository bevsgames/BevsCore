package games.bevs.core;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.JsonObject;

import games.bevs.core.commons.redis.JedisPublisher;
import games.bevs.core.commons.redis.JedisSettings;
import games.bevs.core.commons.redis.JedisSubscriber;
import games.bevs.core.commons.redis.subscribe.JedisSubscriptionHandler;
import games.bevs.core.commons.utils.PluginUtils;
import games.bevs.core.module.client.Rank;
import games.bevs.core.module.custommessager.CustomMessageModule;

public class CorePlugin extends JavaPlugin {
	private static JedisPublisher<JsonObject> messagesPublisher;
	private static JedisSubscriber<JsonObject> messagesSubscriber;

	/**
	 * Bukkit.getMessenger().registerIncomingPluginChannel from bungee
	 * https://www.spigotmc.org/threads/tutorial-advanced-plugin-messaging-spigot-bungeecord.53440/
	 * bungee will hold stats
	 */

	@Override
	public void onEnable() {

		JedisSettings settings = new JedisSettings("78.31.71.65", 6379, "McpvpIsLife4378@13123!F");

		messagesPublisher = new JedisPublisher<JsonObject>(settings, "bevsplex-messages");
		messagesSubscriber = new JedisSubscriber<JsonObject>(settings, "bevsplex-messages",
				JedisSubscriber.JSON_GENERATOR, new JedisSubscriptionHandler<JsonObject>() {
					@Override
					public void handleMessage(JsonObject object) {
						System.out.println(object);

						// upload to redis
					}
				});

		PluginUtils.registerListener(new Listener() {
			@EventHandler
			public void onLogin(AsyncPlayerPreLoginEvent e) {
				System.out.println("preLogin");
			}

			@EventHandler
			public void onLogin(PlayerJoinEvent e) {
				System.out.println("Join");
			}

			@EventHandler
			public void onLogin(PlayerQuitEvent e) {
				System.out.println("Quit");
			}
		}, this);
		
		new CustomMessageModule(this);
	}

	/**
	 * - Bungee will inform the server that the player is switching - The server
	 * saves the players infomation to redis - player changes server - The new
	 * server pulls players infomation from redis
	 * 
	 * On bungee leave, - data is uploaded to redis and saved to a database
	 * 
	 * @param player
	 */

	public static void writeTest(Player player) {
		JsonObject object = new JsonObject();
		object.addProperty("name", player.getName());
		object.addProperty("uniqueId", player.getUniqueId().toString());
		object.addProperty("rank", Rank.STAFF.name());

		messagesPublisher.write(object);
	}

	
	public synchronized Object get(Player p, boolean integer) {  // here you can add parameters (e.g. String table, String column, ...)
        sendToBungeeCord(p, "get", integer ? "points" : "nickname");
 
        try {
            wait();
        } catch(InterruptedException e){}
 
        return "";
    }

	
	public void sendToBungeeCord(Player p, String channel, String sub) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF(channel);
			out.writeUTF(sub);
		} catch (IOException e) {
			e.printStackTrace();
		}
		p.sendPluginMessage(this, "BungeeCord", b.toByteArray());
	}

}
