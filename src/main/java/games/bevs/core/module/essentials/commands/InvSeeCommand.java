package games.bevs.core.module.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import games.bevs.core.commons.player.rank.Rank;
import games.bevs.core.module.commandv2.types.BevsCommand;

/**
 * open a player inventory
 * 
 * Useages
 * 		/invis <Target>
 * 			Look into someone elses inventory
 * 			Target - is a player name 
 */
public class InvSeeCommand extends BevsCommand
{
	public InvSeeCommand()
	{
		super("invsee", Rank.MOD);
	}
	
	
	
	@Override
	public boolean onExecute(CommandSender sender, String commandName, String[] args) 
	{
		if(!(sender instanceof Player)) return false;
		
		Player player = (Player) sender;
		Player targetPlayer = player;
		
		if(args.length >= 1)
		{
			String targetName = args[0];
			targetPlayer = Bukkit.getPlayer(targetName);
			if(targetPlayer == null)
			{
				sender.sendMessage(this.error(targetName + " is not online!"));
				return false;
			}
		} 
		else
		{
			sender.sendMessage(this.error("You cannot look in your own inventory."));
			return false;
		}
		
		player.openInventory(targetPlayer.getInventory());
		sender.sendMessage(this.success(targetPlayer.getName() + "'s inventory is open."));
		return true;
	}
	
}
