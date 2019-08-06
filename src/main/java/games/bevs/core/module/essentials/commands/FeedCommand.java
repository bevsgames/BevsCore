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
public class FeedCommand extends BevsCommand
{
	private static final int MAX_FOOD_LEVEL = 20;
	private static final float GOOD_SATURATION = 12.8f; //Saturation from eating a steak
	
	public FeedCommand(PlayerDataModule clientModule)
	{
		super("feed", Rank.MOD, clientModule);
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
		
		targetPlayer.setFoodLevel(MAX_FOOD_LEVEL);
		targetPlayer.setSaturation(GOOD_SATURATION);
		
		if(player != targetPlayer)
			targetPlayer.sendMessage(this.success("You have been feed."));
		sender.sendMessage(this.success(targetPlayer.getName() + " has been feed."));
		return true;
	}
	
}
