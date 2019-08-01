package games.bevs.core.module.commands;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.Module;
import games.bevs.core.module.commands.annotations.CommandHandler;
import games.bevs.core.module.commands.bukkit.BukkitCommand;
import games.bevs.core.module.commands.types.CommandArgs;

public class MiniCommandMap 
{
	private Map<String, Map.Entry<Method, Object>> commandMap = new HashMap<String, Map.Entry<Method, Object>>();
	private CommandMap map;
	private JavaPlugin plugin;
	private CommandExecutor commandExecutor;
	
	public boolean commandExecutor(CommandSender sender, Command cmd, String commandName, String[] args) 
	{
        for (int i = args.length; i >= 0; --i) 
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append(commandName.toLowerCase());
            for (int x = 0; x < i; ++x) 
                buffer.append("." + args[x].toLowerCase());
            String cmdLabel = buffer.toString();
            
            if (!this.commandMap.containsKey(cmdLabel)) continue;
            
            Method method = this.commandMap.get(cmdLabel).getKey();
            Object methodObject = this.commandMap.get(cmdLabel).getValue();
            CommandHandler command = method.getAnnotation(CommandHandler.class);
            if (command.permission() != "" && !sender.hasPermission(command.permission())) {
            	sender.sendMessage("need permission");
                return true;
            }
            if (command.inGameOnly() && !(sender instanceof Player)) 
            {
                sender.sendMessage("This command is only performable in game");
                return true;
            }
            
            try {
                method.invoke(methodObject, new CommandArgs(sender, cmd, commandName, args, cmdLabel.split("\\.").length - 1));
            }
            catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return true;
        }
        
        //incorrect command
        this.defaultCommand(new CommandArgs(sender, cmd, commandName, args, 0));
        return true;
    }
	
	private void defaultCommand(CommandArgs args) {
    	args.getSender().sendMessage("WRONG COMMAND");
    }
	
	public MiniCommandMap(JavaPlugin plugin, CommandExecutor commandExecutor)
	{
		this.plugin = plugin;
		this.commandExecutor = commandExecutor;
		
		//Hijack SimpleCommandMap, so we can inject commands
		if (plugin.getServer().getPluginManager() instanceof SimplePluginManager)
		{
			SimplePluginManager manager = (SimplePluginManager) plugin.getServer().getPluginManager();
			try 
			{
				Field field = SimplePluginManager.class.getDeclaredField("commandMap");
				field.setAccessible(true);
				this.map = (CommandMap) field.get((Object) manager);
			} catch (IllegalArgumentException | SecurityException | IllegalAccessException | NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void registerCommand(CommandHandler command, String label, Method m, Object obj, Module module) 
	{
        commandMap.put(label.toLowerCase(), new AbstractMap.SimpleEntry<Method, Object>(m, obj));
       
        String cmdLabel = label.replace(".", ",").split(",")[0].toLowerCase();
        
        if (map.getCommand(cmdLabel) == null)
        {
            Command cmd = new BukkitCommand(cmdLabel, commandExecutor, this.plugin);
            map.register(this.plugin.getName(), cmd);
        }
        
        Command cmd = map.getCommand(cmdLabel);
        if (!command.description().equalsIgnoreCase("") && cmdLabel == label) 
        	cmd.setDescription(command.description());
        
        if (!command.usage().equalsIgnoreCase("") && cmdLabel == label)
        	cmd.setUsage(command.usage());
    }
	
	 public void unregisterCommands(Object obj, Module module)
	 {
	        for (Method method : obj.getClass().getMethods()) 
	        {
	            if (method.getAnnotation(CommandHandler.class) != null) 
	            {
	            	CommandHandler command = method.getAnnotation(CommandHandler.class);
	                commandMap.remove(command.name().toLowerCase());
	                map.getCommand(command.name().toLowerCase()).unregister(map);
	            }
	        }
	    }

}
