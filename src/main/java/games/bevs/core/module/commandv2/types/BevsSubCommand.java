package games.bevs.core.module.commandv2.types;

import java.util.List;

import games.bevs.core.commons.player.rank.Rank;

public class BevsSubCommand extends BevsCommand
{

	public BevsSubCommand(String name, String description, String usageMessage, List<String> aliases,
			Rank requiredRank) {
		super(name, description, usageMessage, aliases, requiredRank);
	}
	
	public BevsSubCommand(String name, Rank requiredRank) 
	{
		super(name, requiredRank);
	}

	
}
