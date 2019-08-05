package games.bevs.core.module.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import games.bevs.core.commons.Rank;
import games.bevs.core.module.commandv2.types.BevsCommand;
import games.bevs.core.module.player.PlayerDataModule;

/**
 * Heal a players health
 * 
 * Useages
 * 		/Heal <Target>
 * 			Heal another player
 * 			Target - is a player name 
 * 		/heal 
 * 			Heal yourself
 */
public class HealCommand extends BevsCommand
{
	public HealCommand(PlayerDataModule clientModule)
	{
		super("heal", Rank.MOD, clientModule);
	}
	
	
	
	@Override
	public boolean onExecute(CommandSender sender, String commandName, String[] args) 
	{
		if(!(sender instanceof Player)) return false;
		
		Player player = (Player) sender;
		Player targetPlayer = player;
		
		if(args.length >= 2)
		{
			String targetName = args[1];
			targetPlayer = Bukkit.getPlayer(targetName);
			if(targetPlayer == null)
			{
				sender.sendMessage(this.error(targetName + " is not online!"));
				return false;
			}
		}
		
		targetPlayer.setHealth(targetPlayer.getMaxHealth());
		if(player != targetPlayer)
			targetPlayer.sendMessage(this.success("You have been healed."));
		sender.sendMessage(this.success(targetPlayer.getName() + " has been healed."));
		return true;
	}
	
}
