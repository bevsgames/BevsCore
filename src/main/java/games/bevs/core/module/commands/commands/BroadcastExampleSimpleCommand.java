package games.bevs.core.module.commands.commands;

import org.bukkit.Bukkit;

import games.bevs.core.module.client.Rank;
import games.bevs.core.module.commands.bukkit.SimpleCommand;
import games.bevs.core.module.commands.types.CommandArgs;
import lombok.NonNull;

public class BroadcastExampleSimpleCommand extends SimpleCommand 
{

	public BroadcastExampleSimpleCommand()
	{
		super("shout", Rank.STAFF);
	}


	@Override
	public boolean onExecute(CommandArgs args) 
	{
		String message = String.join(" ", args.getArgs());
		Bukkit.broadcastMessage(message);
		return false;
	}
}
