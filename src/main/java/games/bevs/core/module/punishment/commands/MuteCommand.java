package games.bevs.core.module.punishment.commands;

import org.bukkit.command.CommandSender;

import games.bevs.core.commons.Duration;
import games.bevs.core.commons.Duration.TimeUnit;
import games.bevs.core.commons.player.rank.Rank;
import games.bevs.core.module.commandv2.types.BevsCommand;
import games.bevs.core.module.player.PlayerDataModule;
import games.bevs.core.module.punishment.PunishmentModule;

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
public class MuteCommand extends BevsCommand
{
	private PlayerDataModule playerDataModule;
	private PunishmentModule punishmentModule;

	public MuteCommand(PunishmentModule punishmentModule, PlayerDataModule playerDataModule) 
	{
		super("rank", Rank.STAFF);
		this.playerDataModule = playerDataModule;
		this.punishmentModule = punishmentModule;
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
		sender.sendMessage("/mute <Username> <Duration:x>");
		sender.sendMessage("/mute Sprock 1w4d");
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
		Duration duration = new Duration(7, TimeUnit.DAY);
		
		if(args.length > 1)
		{
			StringBuilder durStr = new StringBuilder();
			for(int i = 1; i < args.length; i++)
				durStr.append(args[i]);
			
			duration = new Duration(durStr.toString());
		}
		
		this.punishmentModule.mute(sender, username, duration);
		return true;
	}
}
