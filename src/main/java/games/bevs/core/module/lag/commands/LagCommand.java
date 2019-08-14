package games.bevs.core.module.lag.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import games.bevs.core.commons.CC;
import games.bevs.core.commons.player.rank.Rank;
import games.bevs.core.module.commandv2.types.BevsCommand;
import games.bevs.core.module.lag.LagModule;

public class LagCommand  extends BevsCommand
{
	private LagModule lagModule;
	
	public LagCommand(LagModule lagModule) 
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
		
		double ticks = this.lagModule.getTicksPerSecond();
		double percentage = ticks / 20.0D * 100.0D;
		String prefix = CC.dGreen;
		if (ticks < 18.0D) prefix = CC.green; 
	    else if (ticks < 16.0D) prefix = CC.gold; 
	    else if (ticks < 14.0D) prefix = CC.yellow; 
	    else if (ticks < 10.0D) prefix = CC.red; 
	    else if (ticks < 8.0D) prefix = CC.dRed; 
		sender.sendMessage(String.format(prefix + "Server is runnning at %.2f% (%.2f tps)", percentage, ticks));
		return true;
	}
}
