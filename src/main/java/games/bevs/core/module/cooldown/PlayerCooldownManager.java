package games.bevs.core.module.cooldown;

import java.net.InetAddress;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.commons.managers.PlayerManager;
import games.bevs.core.module.cooldown.types.CooldownPlayer;

public class PlayerCooldownManager extends PlayerManager<CooldownPlayer>
{
	public PlayerCooldownManager(JavaPlugin plugin)
	{
		super(plugin, true);
		
		Bukkit.getOnlinePlayers().forEach(player -> 
		{
			this.registerPlayer(player.getUniqueId(), player.getName(), player.getAddress().getAddress());
		});
	}

	@Override
	public CooldownPlayer onPlayerCreation(UUID uniquieId, String username, InetAddress ipAddress) {
		CooldownPlayer cooldownPlayer = new CooldownPlayer(uniquieId);
		return cooldownPlayer;
	}

	@Override
	public boolean onPlayerDestruction(CooldownPlayer playerObj) {
		return true;
	}

}
