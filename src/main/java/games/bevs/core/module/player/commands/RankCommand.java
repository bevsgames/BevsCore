package games.bevs.core.module.player.commands;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.json.simple.parser.ParseException;

import games.bevs.core.commons.Duration;
import games.bevs.core.commons.Duration.TimeUnit;
import games.bevs.core.commons.io.Callback;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.player.rank.Rank;
import games.bevs.core.commons.player.rank.events.RankChangeEvent;
import games.bevs.core.commons.utils.MCAPIUtils;
import games.bevs.core.module.commandv2.types.BevsCommand;
import games.bevs.core.module.player.PlayerDataModule;

/**
 * This command is used to give players ranks
 * Usage
 * 		/rank force <Username> <Rank> <Duration:x>
 * 			sets the players rank even if it's a demote
 * 		/rank set <Username> <Rank> <Duration:x>
 * 			Sets their rank for the next x amount of time
 * 		/rank get <Username> <Rank>
 * 			States the rank and the amount of time left for that rank
 * 		/rank reset <username>
 * 			Sets the persons rank back to normal
 *
 * NOTE:
 * All commands work even if the player is offline
 */
public class RankCommand extends BevsCommand
{
	private PlayerDataModule playerDataModule;

	public RankCommand(PlayerDataModule playerDataModule) 
	{
		super("rank", Rank.STAFF);
		this.playerDataModule = playerDataModule;
	}
	
	private void help(CommandSender sender)
	{
		StringBuilder durationsHelp = new StringBuilder();
		TimeUnit[] units = Duration.TimeUnit.values();
		for(int i = 0; i < units.length; i++)
		{
			TimeUnit unit = units[i];
			durationsHelp.append(( i == 0 ? "" :  ", ") + unit.getNameOfOne() + " = " + unit.getSymbol());
		}
		sender.sendMessage("Durations " + durationsHelp.toString());
		sender.sendMessage("Ranks " + Arrays.toString(Rank.values()));
		sender.sendMessage("/rank set <Username> <Rank> <Duration:x>");
		sender.sendMessage("/rank force <Username> <Rank> <Duration:x>");
		sender.sendMessage("/rank get <Username>");
		sender.sendMessage("/rank reset <username>");
		sender.sendMessage("Example");
		sender.sendMessage("/rank set Sprock STAFF 1w4d");
		sender.sendMessage("Which gives Sprock Staff for 1 week and 4 days");
	}
	
	private void runPlayerData(CommandSender sender, String username, Callback<PlayerData> playerDataCallBack)
	{
		Callback<String> callbackFail = (message) -> {
			sender.sendMessage(this.error(message));
		};
		
		Callback<String> callbackSuccess = (message) -> {
			sender.sendMessage(this.success(message));
		};
		
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
				callbackFail.done("Failed to load UUID");
				return;
			}
			
			PlayerData playerData = playerDataModule.getPlayerData(uuid);
			if(playerData == null)
			{
				//load from database
				//Notify Redis?
				playerData = playerDataModule.getPlayerDataMiniDB().loadPlayerData(uuid);
			}
			
			playerDataCallBack.done(playerData);
			
			playerDataModule.getPlayerDataMiniDB().savePlayerData(playerData);
		}).start();
	}
	
	private void setRank(CommandSender sender, boolean force, String username, Rank rank, Duration duraction)
	{
		runPlayerData(sender, username, (playerData) ->
		{
			if(force || !playerData.getRank().hasPermissionsOf(rank))
			{
				new RankChangeEvent(playerData, playerData.getRank(), rank).call();
				Duration expire = duraction.withNow();
				
				playerData.setRank(rank);
				playerData.setRankExpires(expire.getMillis());
				
				if(this.playerDataModule.getPlugin().getServerData().isOnNetwork())
				{
					//broadcast rank change if on network
				}
				sender.sendMessage(this.success(username + "'s rank is now " + rank.getDisplayName() + " for " + duraction.getFormatedTime() ));
			}
			
		});
	}
	
	private void getRank(CommandSender sender, String username)
	{
		runPlayerData(sender, username, (playerData) ->
		{
			Duration expires = new Duration(playerData.getRankExpires());
			Duration expiresDiff = expires.getRemainingTime();
			sender.sendMessage(this.success(playerData.getUsername() + "'s " + playerData.getRank().getDisplayName() + " will expire in " + (expires.getMillis() <= 0 ? "never" : expiresDiff.getFormatedTime())));
		});
	}
	
	private void resetRank(CommandSender sender, String username)
	{
		runPlayerData(sender, username, (playerData) ->
		{
			playerData.setRank(Rank.NORMAL);
			sender.sendMessage(this.success(playerData.getUsername() + "'s rank has been reset"));
		});
	}

	@Override
	public boolean onExecute(CommandSender sender, String commandName, String[] args) {
		if(args.length < 2)
		{
			help(sender);
			return false;
		}
		
		String label = args[0];
		String username = args[1];
		
		if(label.equalsIgnoreCase("force"))
		{
			if(args.length < 3 )
			{
				this.help(sender);
				return false;
			}
			
			String rankStr = args[2].toUpperCase();
			Rank rank = Rank.NORMAL;
			try
			{
				rank = Rank.valueOf(rankStr);
			} 
			catch(IllegalArgumentException e)
			{
				this.help(sender);
				sender.sendMessage("invalid rank");
				return false;
			}
			
			String durStr = "30d";//30 days
			if(args.length > 3)
			{
				StringBuilder strBuilder = new StringBuilder();
				for(int i = 3; i < args.length; i++)
					strBuilder.append(args[i]);
				durStr = strBuilder.toString();
			}
			Duration duration = new Duration(durStr);
			
			this.setRank(sender, true, username, rank, duration);
		} else if(label.equalsIgnoreCase("set"))
		{
			if(args.length < 3 )
			{
				this.help(sender);
				return false;
			}
			
			String rankStr = args[2].toUpperCase();
			Rank rank = Rank.NORMAL;
			try
			{
				rank = Rank.valueOf(rankStr);
			} 
			catch(IllegalArgumentException e)
			{
				this.help(sender);
				sender.sendMessage("invalid rank");
				return false;
			}
			
			String durStr = "30d";//30 days
			if(args.length > 3)
			{
				StringBuilder strBuilder = new StringBuilder();
				for(int i = 3; i < args.length; i++)
					strBuilder.append(args[i]);
				durStr = strBuilder.toString();
			}
			Duration duration = new Duration(durStr);
			
			this.setRank(sender, false, username, rank, duration);
		} 
		else if(label.equalsIgnoreCase("get"))
		{
			this.getRank(sender, username);
		} 
		else if(label.equalsIgnoreCase("reset"))
		{
			this.resetRank(sender, username);
		} 
		else
		{
			this.help(sender);
			return false;
		}
		
		return true;
	}
}
