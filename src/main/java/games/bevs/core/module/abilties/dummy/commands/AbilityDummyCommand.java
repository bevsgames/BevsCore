package games.bevs.core.module.abilties.dummy.commands;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import games.bevs.core.commons.CC;
import games.bevs.core.module.abilties.dummy.DummyAbilityParent;
import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.client.ClientModule;
import games.bevs.core.module.client.Rank;
import games.bevs.core.module.commandv2.types.BevsCommand;

public class AbilityDummyCommand extends BevsCommand
{
	private DummyAbilityParent dummyAbilityParent;
	
	public AbilityDummyCommand(ClientModule clientModule, DummyAbilityParent dummyAbilityParent) 
	{
		super("ability", Rank.STAFF, clientModule);
		this.dummyAbilityParent = dummyAbilityParent;
	}
	
	private void help(CommandSender sender)
	{
		sender.sendMessage(this.error("Usage '/ability enable <AbilityName>'"));
		sender.sendMessage(this.error("Usage '/ability disable <AbilityName>'"));
		sender.sendMessage(this.error("Usage '/ability list'"));
	}
	
	private void enableAbility(CommandSender sender, Ability ability)
	{
		if(sender instanceof Player)
		{
			sender.sendMessage(this.success("Given items for the " + ability.getName() + " Ability!"));
			ability.getItems().forEach(item -> ((Player) sender).getInventory().addItem(item));
		}
		
		sender.sendMessage(this.success("Enabled " + ability.getName() + " Ability!"));
		this.dummyAbilityParent.getEnabledAbilitiesNames().add(ability.getName().toLowerCase());
	}
	
	private void disableAbility(CommandSender sender, Ability ability)
	{
		sender.sendMessage(this.success("Disabled " + ability.getName() + " Ability!"));
		this.dummyAbilityParent.getEnabledAbilitiesNames().remove(ability.getName().toLowerCase());
	}
	
	@Override
	public boolean onExecute(CommandSender sender, String commandName, String[] args) {
		if(args.length == 1 && args[0].equalsIgnoreCase("list"))
		{
			sender.sendMessage(CC.aqua + "Enabled Abilties");
			this.dummyAbilityParent.getEnabledAbilitiesNames().forEach(abilityName -> sender.sendMessage(abilityName));
			return true;
		}
		
		if(args.length != 2)
		{
			help(sender);
			return false;
		}
		
		String status = args[0];
		String abilityName = args[1];
		Ability ability = this.dummyAbilityParent.getAbility(abilityName);
		
		if(ability == null)
		{
			sender.sendMessage(this.error("Ability '" + abilityName + "' not found!"));
			return false;
		}
		
		if(status.equalsIgnoreCase("enable"))
			enableAbility(sender, ability);
		else if(status.equalsIgnoreCase("disable"))
			disableAbility(sender, ability);
		else
			help(sender);
		
		return true;
	}

	@Override
	public List<String> onTab(CommandSender sender, Command command, String commandName, String[] args) {
		if(args.length == 0)
		{
			return Arrays.asList(new String[]{"list", "enable", "disable"});
		}
		else if(args.length == 1)
		{
			return this.dummyAbilityParent.getAbilitiesNames().stream().collect(Collectors.toList());
		}
		return null;
	}

}
