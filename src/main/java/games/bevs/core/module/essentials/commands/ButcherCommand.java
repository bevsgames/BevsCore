package games.bevs.core.module.essentials.commands;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import games.bevs.core.commons.player.rank.Rank;
import games.bevs.core.module.commandv2.types.BevsCommand;

/**
 * Kills all entities in your world
 * 
 * Usage
 * 		/butcher
 * 			kills all unnamed living entities
 * 		/butcher all
 * 			kills all entities.
 */
public class ButcherCommand extends BevsCommand
{
	public ButcherCommand()
	{
		super("butcher", Rank.MOD);
	}
	
	@Override
	public boolean onExecute(CommandSender sender, String commandName, String[] args) 
	{
		if(!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		World world = player.getWorld();
		
		if(args.length == 1 && args[0].equalsIgnoreCase("all"))
		{
			//remove all entities
			world.getEntities().stream()
							   .filter(entity -> !(entity instanceof Player))
							   .forEach(entity -> entity.remove());
			player.sendMessage(this.success("Destroyed all living entities in " + world.getName()));
		} 
		else 
		{
			//remove all mobs that don't have names
			world.getEntities().stream()
							   .filter(entity -> (entity instanceof LivingEntity))
							   .filter(entity -> !(entity instanceof Player))
							   .filter(entity -> !((LivingEntity)entity).isCustomNameVisible())
							   .forEach(entity -> entity.remove());
			player.sendMessage(this.success("Destroyed all living entities in " + world.getName()));
			player.sendMessage(this.info("To kill ALL entities, use '/butcher all'"));
		}
		
		return true;
	}
	
}
