package games.bevs.core.module.essentials.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import games.bevs.core.commons.Rank;
import games.bevs.core.commons.utils.PingUtils;
import games.bevs.core.module.commandv2.types.BevsCommand;
import games.bevs.core.module.player.PlayerDataModule;

/**
 * the players current ping
 * 
 * Note: I learned on munchy that people feel like, 
 * 		 when their ping is divide, they lag less
 * 		 even when their connection is the same.
 */
public class PingCommand extends BevsCommand
{
	public PingCommand(PlayerDataModule clientModule)
	{
		super("ping", Rank.NORMAL, clientModule);
	}
	
	@Override
	public boolean onExecute(CommandSender sender, String commandName, String[] args) 
	{
		if(!(sender instanceof Player)) return false;
		int ping = PingUtils.getPing((Player) sender);
		ping /= 2;
		
		sender.sendMessage(this.success(ping + " ms"));
		return true;
	}
	
}
