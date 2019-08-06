package games.bevs.core.module.essentials.commands;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import games.bevs.core.commons.Rank;
import games.bevs.core.module.commandv2.types.BevsCommand;
import games.bevs.core.module.player.PlayerDataModule;

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
	public ButcherCommand(PlayerDataModule clientModule)
	{
		super("butcher", Rank.MOD, clientModule);
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
		} 
		else 
		{
			//remove all mobs that don't have names
			world.getEntities().stream()
							   .filter(entity -> (entity instanceof LivingEntity))
							   .filter(entity -> !(entity instanceof Player))
							   .filter(entity -> !((LivingEntity)entity).isCustomNameVisible())
							   .forEach(entity -> entity.remove());
		}
		player.sendMessage(this.success("Destroyed all living entities (That aren't named) in " + world.getName()));
		return true;
	}
	
}
