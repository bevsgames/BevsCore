package games.bevs.core.module.commands.types;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lombok.Getter;

public class CommandArgs 
{
	private @Getter CommandSender sender;
    private @Getter Command command;
    private @Getter String label;
    private @Getter String[] args;

    public CommandArgs(CommandSender sender, Command command, String label, String[] args, int subCommand) {
        String[] modArgs = new String[args.length - subCommand];
       
        for (int i = 0; i < args.length - subCommand; ++i) 
            modArgs[i] = args[i + subCommand];
       
        StringBuffer buffer = new StringBuffer();
        buffer.append(label);
        
        for (int x = 0; x < subCommand; ++x) 
            buffer.append("." + args[x]);
        
        String cmdLabel = buffer.toString();
        this.sender = sender;
        this.command = command;
        this.label = cmdLabel;
        this.args = modArgs;
    }

    public String getArgs(int index) 
    {
        return this.args[index];
    }

    public int length() 
    {
        return this.args.length;
    }

    public boolean isPlayer() 
    {
        return this.sender instanceof Player;
    }

    public Player getPlayer()
    {
        if (this.sender instanceof Player) 
            return (Player)this.sender;
        return null;
    }
}
