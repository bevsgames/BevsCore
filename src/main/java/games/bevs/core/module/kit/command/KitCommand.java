package games.bevs.core.module.kit.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import games.bevs.core.commons.player.rank.Rank;
import games.bevs.core.module.commandv2.types.BevsCommand;

public class KitCommand extends BevsCommand
{

	public KitCommand(String name, Rank requiredRank)
	{
		super(name, Rank.NORMAL);
	}

	@Override
	public boolean onExecute(CommandSender sender, String commandName, String[] args) 
	{
		Player targetPlayer = null;
		if(sender instanceof Player)
			targetPlayer = (Player) sender;
		
		//Allow us to do /kit <KitName> <PlayerName>
		
		if(targetPlayer == null)
		{
			sender.sendMessage(this.error("A player must be selected!"));
			return false;
		}
		//Apply kit to player?
		
		return true;
	}
}
