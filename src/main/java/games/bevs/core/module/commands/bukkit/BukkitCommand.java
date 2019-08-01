package games.bevs.core.module.commands.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.client.Rank;
import lombok.Getter;
import lombok.Setter;

public class BukkitCommand extends Command
{
	private CommandExecutor executor;
	private JavaPlugin plugin;
	private @Getter @Setter Rank requiredRank;
	
	public BukkitCommand(String name, CommandExecutor executor, JavaPlugin plugin) 
	{
		super(name);
		this.executor = executor;
		this.plugin = plugin;
	}

	@Override
	public boolean execute(CommandSender sender, String commandName, String[] args) 
	{
		boolean success = false;
        if (!this.plugin.isEnabled()) return false;
        if (!this.testPermission(sender)) return true;

        try 
        {
            success = this.executor.onCommand(sender, (Command) this, commandName, args);
        }
        catch (Throwable ex) 
        {
            throw new CommandException("Unhandled exception executing command '" + commandName + "' in plugin " + this.plugin.getDescription().getFullName(), ex);
        }
        
        if (!success && this.usageMessage.length() > 0) 
            for (String line : this.usageMessage.replace("<command>", commandName).split("\n")) 
                sender.sendMessage(line);
        return success;
	}

}
