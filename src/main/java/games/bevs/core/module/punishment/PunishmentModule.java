package games.bevs.core.module.punishment;

import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.json.simple.parser.ParseException;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.io.Callback;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.utils.MCAPIUtils;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.player.PlayerDataModule;

@ModInfo(name = "Punishment")
public class PunishmentModule extends Module
{

	public PunishmentModule(BevsPlugin plugin, CommandModule commandModule, PlayerDataModule playerDataModule)
	{
		super(plugin, commandModule, playerDataModule);
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
