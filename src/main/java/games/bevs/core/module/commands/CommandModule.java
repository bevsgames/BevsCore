package games.bevs.core.module.commands;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commands.annotations.CommandHandler;
import games.bevs.core.module.commands.types.CommandArgs;

@ModInfo(name = "Command", hasCommand = false)
public class CommandModule extends Module implements CommandExecutor {
	private MiniCommandMap miniCommandMap;

	public CommandModule(JavaPlugin plugin) 
	{
		super(plugin);

		this.miniCommandMap = new MiniCommandMap(plugin, this);
	}
	
	public void registerCommands(Object obj) 
	{
		this.registerCommands(obj, null);
    }
	
	public void registerCommands(Object obj, Module parentModule) 
	{
        for (Method method : obj.getClass().getMethods()) 
        {
            if (method.getAnnotation(CommandHandler.class) != null) 
            {
            	CommandHandler command = method.getAnnotation(CommandHandler.class);
                if (method.getParameterTypes().length > 1 || method.getParameterTypes()[0] != CommandArgs.class)
                {
                    System.out.println("Unable to register command " + method.getName() + ". Unexpected method arguments");
                    continue;
                }
                
                List<String> commandLabels = new ArrayList<>(5); 
                String commandLabel = command.name();
                
                if(parentModule != null)
                {
                	commandLabel = parentModule.getName() + "." + command.name();
                	for(String alises : parentModule.getAlises())
                		commandLabels.add(alises + "." + command.name());
                }
                
                commandLabels.add(commandLabel);
                
                
                
                for(String labelAliases : commandLabels)
                {
	                this.miniCommandMap.registerCommand(command, labelAliases, method, obj, parentModule);
	                for (String alias : command.aliases()) 
	                {
	                	if(parentModule != null) alias = parentModule.getName() + "." + alias;
	                	this.miniCommandMap.registerCommand(command, alias, method, obj, parentModule);
	                }
                }
            } 
        }
    }
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandName, String[] args) {
		return this.miniCommandMap.commandExecutor(sender, cmd, commandName, args);
	}

}
