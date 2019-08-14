package games.bevs.core.module.lag.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import games.bevs.core.commons.player.rank.Rank;
import games.bevs.core.module.commandv2.types.BevsCommand;
import games.bevs.core.module.lag.LagModule;

public class LagDataCommand  extends BevsCommand
{
	private LagModule lagModule;
	
	public LagDataCommand(LagModule lagModule) 
	{
		super("lag", Rank.NORMAL);
		this.lagModule = lagModule;
	}

	@Override
	public boolean onExecute(CommandSender sender, String commandName, String[] args) 
	{
		if(!(sender instanceof Player))
		{
			sender.sendMessage(this.error("You must be a player for this command"));
			return false;
		}
		
		this.lagModule.sendUpdate((Player) sender);
		return true;
	}
}
