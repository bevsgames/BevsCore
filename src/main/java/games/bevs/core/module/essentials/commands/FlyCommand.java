package games.bevs.core.module.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import games.bevs.core.commons.Rank;
import games.bevs.core.commons.utils.StringUtils;
import games.bevs.core.module.commandv2.types.BevsCommand;
import games.bevs.core.module.player.PlayerDataModule;

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
	public FlyCommand(PlayerDataModule clientModule)
	{
		super("fly", Rank.MOD, clientModule);
	}
	
	
	
	@Override
	public boolean onExecute(CommandSender sender, String commandName, String[] args) 
	{
		if(!(sender instanceof Player)) return false;
		
		Player player = (Player) sender;
		Player targetPlayer = player;
		boolean isFlying = !player.isFlying();
		
		if(args.length >= 2)
		{
			String targetName = args[1];
			targetPlayer = Bukkit.getPlayer(targetName);
			if(targetPlayer == null)
			{
				sender.sendMessage(this.error(targetName + " is not online!"));
				return false;
			}
			
			if(args.length == 3)
			{
				String statusStr = args[2];
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
