package games.bevs.core.module.custommessager;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import games.bevs.core.BevsPlugin;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;

/**
 * I library to easily manage custom messages 
 *
 */
@ModInfo(name="CustomMessager")
public class CustomMessageModule extends Module
{
	private CustomMessageListener customMsgListener;
	
	public CustomMessageModule(BevsPlugin plugin)
	{
		super(plugin);
	}
	
	@Override
	public void onEnable()
	{
		customMsgListener =  new CustomMessageListener();
		Bukkit.getMessenger().registerIncomingPluginChannel(this.getPlugin(), "BungeeCord", customMsgListener);
		
		this.registerSelf();
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
		p.sendPluginMessage(this.getPlugin(), "BungeeCord", b.toByteArray());
	}
	
	
	@EventHandler
	public void onJon(PlayerJoinEvent e)
	{
		this.sendToBungeeCord(e.getPlayer(), "BungeeCord", "A random little message from the server");
	}

}
