package games.bevs.core.module.kit;

import java.net.InetAddress;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.commons.managers.PlayerManager;
import games.bevs.core.module.kit.types.KitPlayer;
import lombok.Getter;
import lombok.Setter;

public class KitPlayerManager extends PlayerManager<KitPlayer>
{
	@Getter
	@Setter
	private KitParent kitParent;
	
	public KitPlayerManager(JavaPlugin plugin, KitParent kitParent)
	{
		super(plugin, true);
		this.kitParent = kitParent;
	}

	@Override
	public KitPlayer onPlayerCreation(UUID uniquieId, String username, InetAddress ipAddress) 
	{
		KitPlayer kitPlayer = new KitPlayer(uniquieId);
		return kitPlayer;
	}

	@Override
	public boolean onPlayerDestruction(KitPlayer playerObj) 
	{
		return true;
	}
}
