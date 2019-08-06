package games.bevs.core.module.commandv2.types;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import games.bevs.core.commons.utils.StringUtils;
import games.bevs.core.module.client.Client;
import games.bevs.core.module.client.ClientModule;
import games.bevs.core.module.client.Rank;
import lombok.Getter;

public class BevsCommand extends Command implements TabCompleter 
{
	public static final String COMMAND_NO_PERMISSION = "You do not have permission for this command!";
	private @Getter ClientModule clientModule;
	private @Getter Rank requiredRank;

	public BevsCommand(String name, String description, String usageMessage, List<String> aliases) {
		super(name, description, usageMessage, aliases);
	}

	public BevsCommand(String name, Rank requiredRank, ClientModule clientModule) {
		super(name);
		this.requiredRank = requiredRank;
	}

	protected List<String> getOnlinePlayers(CommandSender sender) {
		Stream<? extends Player> playerStream = Bukkit.getOnlinePlayers().stream();

		if (sender instanceof Player) {
			Player player = (Player) sender;
			playerStream.filter(targetPlayer -> player.canSee(targetPlayer));
		}

		return playerStream.map(player -> player.getName()).collect(Collectors.toList());
	}

	private Rank getRank(CommandSender sender) {
		if (sender instanceof Player) {
			Client client = clientModule.getPlayer((Player) sender); 
			if (client != null ) 
				
				return client.getRank();
			else
				return Rank.NORMAL;
		}

		
		return Rank.STAFF;
	}
	
	protected String error(String message)
	{
		return StringUtils.error(this.getName(), message);
	}
	
	protected String success(String message)
	{
		return StringUtils.success(this.getName(), message);
	}

	@Override
	public boolean execute(CommandSender sender, String commandName, String[] args) 
	{
		Rank rank = this.getRank(sender);
		if(!(rank.hasPermissionsOf(rank) || sender.hasPermission(this.getPermission())))
		{
			sender.sendMessage(error(COMMAND_NO_PERMISSION));
			return false;
		}
		
		return this.onExecute(sender, commandName, args);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String commandName, String[] args) {
		return this.onTab(sender, command, commandName, args);
	}

	public boolean onExecute(CommandSender sender, String commandName, String[] args) {
		return true;
	}

	public List<String> onTab(CommandSender sender, Command command, String commandName, String[] args) {
		return this.getOnlinePlayers(sender);
	}
}
