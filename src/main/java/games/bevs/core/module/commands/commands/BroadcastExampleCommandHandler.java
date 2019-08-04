package games.bevs.core.module.commands.commands;

import org.bukkit.Bukkit;

import games.bevs.core.module.client.Rank;
import games.bevs.core.module.commands.annotations.CommandHandler;
import games.bevs.core.module.commands.types.CommandArgs;

public class BroadcastExampleCommandHandler 
{
	@CommandHandler(name = "broadcast", requiredRank = Rank.STAFF)
	public void broadcastCmd(CommandArgs args) 
	{
		String message = String.join(" ", args.getArgs());
		Bukkit.broadcastMessage(message);
		args.getSender().sendMessage("Msg sent");
	}
}
