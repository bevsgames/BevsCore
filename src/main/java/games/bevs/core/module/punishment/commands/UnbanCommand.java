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
 *		/ban <Username> <Reason>
 * NOTE:
 * All commands work even if the player is offline
 */
public class UnbanCommand extends BevsCommand
{
	private PlayerDataModule playerDataModule;
	private PunishmentModule punishmentModule;

	public UnbanCommand(PunishmentModule punishmentModule, PlayerDataModule playerDataModule) 
	{
		super("unban", Rank.MOD);
		this.playerDataModule = playerDataModule;
		this.punishmentModule = punishmentModule;
	}
	
	private void help(CommandSender sender)
	{
		sender.sendMessage("/unban <Username>");
	}
	
	@Override
	public boolean onExecute(CommandSender sender, String commandName, String[] args) {
		if(args.length < 1)
		{
			help(sender);
			return false;
		}
		
		String username = args[0];
		this.punishmentModule.unban(sender, username);
		return true;
	}
}
