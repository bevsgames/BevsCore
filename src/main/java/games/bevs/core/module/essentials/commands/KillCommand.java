package games.bevs.core.module.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import games.bevs.core.commons.Rank;
import games.bevs.core.module.client.ClientModule;
import games.bevs.core.module.commandv2.types.BevsCommand;

/**
 * kill a player
 * 
 * Useages
 * 		/kill <Target>
 * 			kill another player
 * 			Target - is a player name 
 * 		/kill 
 * 			kill yourself
 */
public class KillCommand extends BevsCommand
{
	private static final int DEATH_DAMAGE = 1000;
	
	public KillCommand(ClientModule clientModule)
	{
		super("kill", Rank.MOD, clientModule);
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
		
		targetPlayer.damage(DEATH_DAMAGE);
		if(player != targetPlayer)
			targetPlayer.sendMessage(this.success("You have been killed by a command."));
		sender.sendMessage(this.success(targetPlayer.getName() + " has been killed by your hand."));
		return true;
	}
	
}
