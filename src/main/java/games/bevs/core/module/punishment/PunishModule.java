package games.bevs.core.module.punishment;

import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.commons.Duration;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.client.Client;
import games.bevs.core.module.client.ClientModule;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.punishment.types.PunishLog;

@ModInfo(name = "Punishment")
public class PunishModule extends Module
{

	public PunishModule(JavaPlugin plugin, CommandModule commandModule, ClientModule clientModule) 
	{
		super(plugin, commandModule, clientModule);
	}
	
	private PunishLog generatePunishLog(PunishType punishType, Client client, Client punisher, Duration duration, String reason)
	{
		long now = System.currentTimeMillis();
		Duration expires = new Duration(now);
		expires.add(duration);
		PunishLog log = new PunishLog(-1, client.getInternalId(), punishType, reason, now, expires.getMillis(), true, punisher == null ? -1 : punisher.getInternalId());
		return log;
	}
	
	public void ban(Client client, Client punisher, Duration duration, String reason)
	{
		this.generatePunishLog(PunishType.BAN, client, punisher, duration, reason);
	}
	
	public void mute(Client client, Client punisher, Duration duration, String reason)
	{
		this.generatePunishLog(PunishType.MUTE, client, punisher, duration, reason);
	}

	public void kick(Client client, Client punisher, String reason)
	{
		this.generatePunishLog(PunishType.KICK, client, punisher, Duration.ZERO_DURATION, reason);
		client.getPlayer().kickPlayer("Yeet");
	}
}
