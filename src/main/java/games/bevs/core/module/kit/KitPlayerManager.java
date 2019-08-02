package games.bevs.core.module.kit;

import java.net.InetAddress;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.commons.managers.PlayerManager;
import games.bevs.core.module.kit.types.KitPlayer;

public class KitPlayerManager extends PlayerManager<KitPlayer>
{
	public KitPlayerManager(JavaPlugin plugin)
	{
		super(plugin, true);
	}

	@Override
	public KitPlayer onPlayerCreation(UUID uniquieId, String username, InetAddress ipAddress) 
	{
		KitPlayer kitPlayer = new KitPlayer(uniquieId, username);
		return kitPlayer;
	}

	@Override
	public boolean onPlayerDestruction(KitPlayer playerObj) 
	{
		return true;
	}
}
