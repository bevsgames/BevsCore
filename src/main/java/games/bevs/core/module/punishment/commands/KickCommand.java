package games.bevs.core.module.punishment.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import games.bevs.core.commons.player.rank.Rank;
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
public class KickCommand extends BevsCommand
{
	private PlayerDataModule playerDataModule;

	public KickCommand(PlayerDataModule playerDataModule) 
	{
		super("rank", Rank.STAFF);
		this.playerDataModule = playerDataModule;
	}
	
	private void help(CommandSender sender)
	{
		sender.sendMessage("/kick <Username> <Reason>");
		sender.sendMessage("/rank get <Username>");
		sender.sendMessage("/rank reset <username>");
		sender.sendMessage("Example");
		sender.sendMessage("/rank set Sprock STAFF 1w4d");
		sender.sendMessage("Which gives Sprock Staff for 1 week and 4 days");
	}
	
	@Override
	public boolean onExecute(CommandSender sender, String commandName, String[] args) {
		if(args.length < 1)
		{
			help(sender);
			return false;
		}
		
		String username = args[0];
		
		StringBuilder reason = new StringBuilder();
		for(int i = 1; i < args.length; i++)
			reason.append(args[i]);
		
		Player player = Bukkit.getPlayer(username);
		if(player == null)
		{
			sender.sendMessage(this.error( username + " isn't online"));
			return false;
		}
		
		player.kickPlayer(reason.toString());
		return true;
	}
}
