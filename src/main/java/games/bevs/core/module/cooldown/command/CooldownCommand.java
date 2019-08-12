package games.bevs.core.module.cooldown.command;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import games.bevs.core.commons.Duration;
import games.bevs.core.commons.Duration.TimeUnit;
import games.bevs.core.commons.player.rank.Rank;
import games.bevs.core.module.commandv2.types.BevsCommand;
import games.bevs.core.module.cooldown.CooldownModule;
import games.bevs.core.module.cooldown.types.CooldownPlayer;
import lombok.Getter;

/**
 *	Cooldown commands
 */
public class CooldownCommand extends BevsCommand
{
	private @Getter CooldownModule cooldownModule;

	public CooldownCommand(CooldownModule cooldownModule)
	{
		super("cooldown", Rank.MOD);
		this.cooldownModule = cooldownModule;
	}
	
	private void help(CommandSender sender)
	{
		sender.sendMessage(this.info("/Cooldown list <Player>"));
		sender.sendMessage(this.info("	List all active cooldowns for the player"));
		
		sender.sendMessage(this.info("/Cooldown set <Player> <CooldownName> <Seconds>"));
		sender.sendMessage(this.info("	Apply cooldown to player"));
		
		sender.sendMessage(this.info("/Cooldown remove <Player> <CooldownName>"));
		sender.sendMessage(this.info("	Remove a cooldown from player"));
		
		sender.sendMessage(this.info("/Cooldown clear <Player>"));
		sender.sendMessage(this.info("	Remove all cooldowns cooldown from player"));
	}
	
	private void cooldownList(CommandSender sender, Player player)
	{
		CooldownPlayer cooldownPlayer = this.getCooldownModule().getPlayerCooldownManager().getPlayer(player);
		sender.sendMessage(player.getName() + "'s cooldowns");
		cooldownPlayer.getCooldowns().forEach((key, value) -> sender.sendMessage(key + " : " + value));
	}
	
	private void cooldownSet(CommandSender sender, Player player, String name, int seconds)
	{
		CooldownPlayer cooldownPlayer = this.getCooldownModule().getPlayerCooldownManager().getPlayer(player);
		cooldownPlayer.setCooldown(name, seconds, TimeUnit.SECOND);
		
		Duration remainingTime = cooldownPlayer.getCooldown(name).getRemainingTime();
		String remainingTimeStr = remainingTime.getFormatedTime();
		
		sender.sendMessage(this.success("Set " + player.getName() + "'s cooldown for " + name + " to " + remainingTimeStr));
	}
	
	private void cooldownRemove(CommandSender sender, Player player, String name)
	{
		CooldownPlayer cooldownPlayer = this.getCooldownModule().getPlayerCooldownManager().getPlayer(player);
		cooldownPlayer.removeCooldown(name);
		sender.sendMessage(this.success("Removed " + player.getName() + "'s cooldown"));
	}
	
	private void cooldownClear(CommandSender sender, Player player)
	{
		CooldownPlayer cooldownPlayer = this.getCooldownModule().getPlayerCooldownManager().getPlayer(player);
		cooldownPlayer.clear();
		sender.sendMessage(this.success("Cleared All " + player.getName() + "'s cooldowns"));
	}

	@Override
	public boolean onExecute(CommandSender sender, String commandName, String[] args) {
		if(args.length < 2)
		{
			help(sender);
			return true;
		}
		
		String targetName = args[1];
		Player targetPlayer = Bukkit.getPlayer(targetName);
		if(targetPlayer == null)
		{
			sender.sendMessage(this.error("Could not find player"));
			return false;
		}
		
		String actionArg = args[0];
		if(actionArg.equalsIgnoreCase("list"))
		{
			this.cooldownList(sender, targetPlayer);
		}
		else if(actionArg.equalsIgnoreCase("set"))
		{
			if(args.length != 4)
			{
				sender.sendMessage(this.error("Must have 4 arguments"));
				this.help(sender);
				return false;
			}
			
			String cooldownName = args[2];
			String cooldownSecondsStr = args[3];
			
			if(NumberUtils.isNumber(cooldownSecondsStr))
			{
				sender.sendMessage(this.error("Seconds must be a number"));
				return false;
			}
			
			int seconds = Integer.parseInt(cooldownSecondsStr);
			
			this.cooldownSet(sender, targetPlayer, cooldownName, seconds);
			
		}
		else if(actionArg.equalsIgnoreCase("remove"))
		{
			if(args.length != 3)
			{
				sender.sendMessage(this.error("Must have 3 arguments"));
				this.help(sender);
				return false;
			}
			
			String cooldownName = args[2];
			
			this.cooldownRemove(sender, targetPlayer, cooldownName);
		}
		else if(actionArg.equalsIgnoreCase("clear"))
		{
			this.cooldownClear(sender, targetPlayer);
		}
		return true;
	}
}
