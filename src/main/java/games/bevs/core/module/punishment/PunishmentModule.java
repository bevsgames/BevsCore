package games.bevs.core.module.punishment;

import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.parser.ParseException;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.CC;
import games.bevs.core.commons.Duration;
import games.bevs.core.commons.io.Callback;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.utils.MCAPIUtils;
import games.bevs.core.commons.utils.StringUtils;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.player.PlayerDataModule;
import games.bevs.core.module.punishment.commands.BanCommand;
import games.bevs.core.module.punishment.commands.KickCommand;
import games.bevs.core.module.punishment.commands.MuteCommand;

@ModInfo(name = "Punishment")
public class PunishmentModule extends Module
{

	public PunishmentModule(BevsPlugin plugin, CommandModule commandModule, PlayerDataModule playerDataModule)
	{
		super(plugin, commandModule, playerDataModule);
	}
	
	@Override
	public void onEnable()
	{
		this.registerCommand(new BanCommand(this, this.getPlayerDataModule()));
		this.registerCommand(new MuteCommand(this, this.getPlayerDataModule()));
		this.registerCommand(new KickCommand(this.getPlayerDataModule()));
	}
	
	public void ban(CommandSender sender, String username, Duration dur)
	{
		runPlayerData(sender, username, (playerData) ->
		{
			playerData.setBanExpires(dur.withNow().getMillis());
			Player bannedPlayer = Bukkit.getPlayer(playerData.getUniqueId());
			if(bannedPlayer != null)
				bannedPlayer.kickPlayer(CC.bdRed + "Banned");
			sender.sendMessage(StringUtils.success("Ban", "Banned " + username + " for " + dur.getFormatedTime()));
		});
	}
	
	public void mute(CommandSender sender, String username, Duration duration)
	{
		runPlayerData(sender, username, (playerData) ->
		{
			playerData.setMutedExpires(duration.withNow().getMillis());
			sender.sendMessage(StringUtils.success("Mute", "Muted " + username + " for " + duration.getFormatedTime()));
		});
	}
	
	private void runPlayerData(CommandSender sender, String username, Callback<PlayerData> playerDataCallBack)
	{
		new Thread(() -> {
			
			UUID uuid = null;
			
			//Method 1
			OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(username);
			if(offlinePlayer != null)
				uuid = offlinePlayer.getUniqueId();
			
			//Method 2
			if(uuid == null)
			{
				try {
					uuid = MCAPIUtils.getUUID(username);
				} catch (IOException | ParseException e) {
					e.printStackTrace();
				}
			}
			
			if(uuid == null)
			{
				sender.sendMessage(StringUtils.error("Punishment", "Failed to load UUID"));
				return;
			}
			
			PlayerData playerData = this.getPlayerDataModule().getPlayerData(uuid);
			if(playerData == null)
			{
				//load from database
				//Notify Redis?
				playerData = this.getPlayerDataModule().getPlayerDataMiniDB().loadPlayerData(uuid);
			}
			
			playerDataCallBack.done(playerData);
			
			this.getPlayerDataModule().getPlayerDataMiniDB().savePlayerData(playerData);
		}).start();
	}
}
