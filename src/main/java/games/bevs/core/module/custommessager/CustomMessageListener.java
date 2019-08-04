package games.bevs.core.module.custommessager;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class CustomMessageListener implements PluginMessageListener 
{
	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message)
	{
		System.out.println("Got Plugin Message on " + channel + " from " + player.getName() + " messge was: "
				+ message.toString());
	}
}
