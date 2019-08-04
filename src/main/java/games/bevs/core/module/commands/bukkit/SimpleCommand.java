package games.bevs.core.module.commands.bukkit;

import games.bevs.core.module.client.Rank;
import games.bevs.core.module.commands.types.CommandArgs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class SimpleCommand 
{
	private @Getter @NonNull String command;
	private @Getter @NonNull Rank requiredRank;
	private @Getter String permission;

	public boolean onExecute(CommandArgs args) {return false;}
	public boolean onTab(CommandArgs args) {return false;}
}
