package games.bevs.core.module.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import games.bevs.core.commons.player.rank.Rank;
import games.bevs.core.commons.utils.StringUtils;
import games.bevs.core.module.commandv2.types.BevsCommand;

/**
 * Get to set players flight
 * 
 * Useages
 * 		/fly <Target> <status>
 * 			Sets flying state
 * 			Target - is a player name 
 * 			Status - true or false on if they can fly
 * 		/fly <Target>
 * 			toggles flying state
 * 			Target - target player
 * 		/fly 
 * 			Affects your flying state
 */
public class FlyCommand extends BevsCommand
{
	public FlyCommand()
	{
		super("fly", Rank.MOD);
	}
	
	
	
	@Override
	public boolean onExecute(CommandSender sender, String commandName, String[] args) 
	{
		if(!(sender instanceof Player)) return false;
		
		Player player = (Player) sender;
		Player targetPlayer = player;
		boolean isFlying = !player.isFlying();
		
		if(args.length >= 1)
		{
			String targetName = args[0];
			targetPlayer = Bukkit.getPlayer(targetName);
			if(targetPlayer == null)
			{
				sender.sendMessage(this.error(targetName + " is not online!"));
				return false;
			}
			
			if(args.length == 2)
			{
				String statusStr = args[1];
				isFlying = StringUtils.toBoolean(statusStr);
			} 
			else
			{
				isFlying = !targetPlayer.isFlying();
			}
		}
		
		targetPlayer.setFlying(isFlying);
		if(player != targetPlayer)
			targetPlayer.sendMessage(this.success("You can " + ( isFlying ? "no longer" : "now") + " fly!"));
		sender.sendMessage(this.success(targetPlayer.getName() + " can " + ( isFlying ? "no longer" : "now") + " fly!"));
		return true;
	}
	
}
