package games.bevs.core.module.punishment;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.Duration;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.player.PlayerDataModule;
import games.bevs.core.module.punishment.types.PunishLog;

@ModInfo(name = "Punishment")
public class PunishModule extends Module
{

	public PunishModule(BevsPlugin plugin, CommandModule commandModule, PlayerDataModule clientModule) 
	{
		super(plugin, commandModule, clientModule);
		
	}
	
	private PunishLog generatePunishLog(PunishType punishType, PlayerData client, PlayerData punisher, Duration duration, String reason)
	{
		long now = System.currentTimeMillis();
		Duration expires = new Duration(now);
		expires.add(duration);
		PunishLog log = new PunishLog(-1, client.getInternalId(), punishType, reason, now, expires.getMillis(), true, punisher == null ? -1 : punisher.getInternalId());
		return log;
	}
	
	public void ban(PlayerData client, PlayerData punisher, Duration duration, String reason)
	{
		this.generatePunishLog(PunishType.BAN, client, punisher, duration, reason);
	}
	
	public void mute(PlayerData client, PlayerData punisher, Duration duration, String reason)
	{
		this.generatePunishLog(PunishType.MUTE, client, punisher, duration, reason);
	}

	public void kick(PlayerData client, PlayerData punisher, String reason)
	{
		this.generatePunishLog(PunishType.KICK, client, punisher, Duration.ZERO_DURATION, reason);
//		client.getPlayer().kickPlayer("Yeet");
	}
}
