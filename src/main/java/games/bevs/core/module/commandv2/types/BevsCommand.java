package games.bevs.core.module.commandv2.types;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.player.rank.Rank;
import games.bevs.core.commons.server.Console;
import games.bevs.core.commons.utils.StringUtils;
import games.bevs.core.module.commandv2.CommandModule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class BevsCommand extends Command implements TabCompleter {
	public static final String COMMAND_NO_PERMISSION = "You do not have permission for this command!";
	private @Getter @Setter CommandModule commandModule;
	private @Getter Rank requiredRank;
	private @Getter @Setter String displayName;
	
	@Getter(AccessLevel.PROTECTED)
	private HashMap<String, BevsSubCommand> subcommands = new HashMap<>();

	public BevsCommand(String name, String description, String usageMessage, List<String> aliases, Rank requiredRank) {
		super(name, description, usageMessage, aliases);
		this.setDisplayName(StringUtils.capitalize(this.getName()));
		this.requiredRank = requiredRank;
		this.setPermission("bevs.games." + this.requiredRank.name());
	}

	public BevsCommand(String name, Rank requiredRank) {
		super(name);
		this.setDisplayName(StringUtils.capitalize(this.getName()));
		this.requiredRank = requiredRank;
		this.setPermission("bevs.games." + this.requiredRank.name());
	}
	
	public void addSubCommand(BevsSubCommand... commands)
	{
		for(BevsSubCommand command : commands)
		{
			this.subcommands.put(command.getName(), command);
			command.getAliases().forEach(name -> {
				this.subcommands.put(name, command);
			});
		}
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
			if (this.getCommandModule() != null) {
				if (this.getCommandModule().getPlayerDataModule() != null) {
					PlayerData client = this.getCommandModule().getPlayerDataModule().getPlayerData((Player) sender);
					if (client != null)
						return client.getRank();
				}
			}
			return Rank.NORMAL;
		}

		return Rank.STAFF;
	}

	protected String error(String message) {
		return StringUtils.error(this.getDisplayName(), message);
	}

	protected String success(String message) {
		return StringUtils.success(this.getDisplayName(), message);
	}

	protected String info(String message) {
		return StringUtils.info(this.getDisplayName(), message);
	}

	@Override
	public boolean execute(CommandSender sender, String commandName, String[] args) {
		Rank rank = this.getRank(sender);
		// Player doesn't have permission or rank
		if (!(sender.hasPermission(this.getPermission()) || (rank.hasPermissionsOf(rank)))) {
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
		if(args.length >= 1)
		{
			BevsSubCommand subCommand = this.subcommands.get(args[0].toLowerCase());
			if(subCommand != null)
			{
				subCommand.onExecute(sender, args[0], args);
			}
		}
		
		return true;
	}

	public List<String> onTab(CommandSender sender, Command command, String commandName, String[] args) {
		return this.getOnlinePlayers(sender);
	}
}
