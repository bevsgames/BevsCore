package games.bevs.core.module.abilties.types;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.commons.CC;
import games.bevs.core.commons.server.Console;
import games.bevs.core.commons.utils.PluginUtils;
import games.bevs.core.module.abilties.AbilityModule;
import games.bevs.core.module.abilties.interfaces.IAbilityParent;
import games.bevs.core.module.client.Rank;
import games.bevs.core.module.commands.annotations.CommandHandler;
import games.bevs.core.module.commands.types.CommandArgs;
import lombok.Getter;
import lombok.NonNull;

/**
 * Dummy class for testing out abilities
 * 
 * Type
 * 	/ability add <Name>
 * 		To enable the ability added from {@link games.bevs.core.module.abilties.AbilityModule#onEnable() }
 *  /ability list <Name>
 *  	Lists the enabled abilties
 *
 */
public class DummyAbilityParent implements IAbilityParent
{
	private ArrayList<Ability> abilities = new ArrayList<>();
	private HashSet<String> abilitiesNames = new HashSet<>();
	private HashSet<String> enabledAbilitiesNames = new HashSet<>(); 
	
	private @Getter @NonNull JavaPlugin plugin;
	private @Getter @NonNull AbilityModule abilityModule;
	
	public DummyAbilityParent(JavaPlugin plugin, AbilityModule abilityModule)
	{
		this.plugin = plugin;
		
		abilityModule.registerCommand(this);
	}
	
	@CommandHandler(name = "", requiredRank = Rank.STAFF)
	public void abilityCmd(CommandArgs args) 
	{
		
		args.getPlayer().sendMessage(CC.aqua + "Enabled Abilities");
		this.enabledAbilitiesNames.forEach(abilityName -> args.getPlayer().sendMessage(abilityName));
		args.getPlayer().sendMessage(CC.gray + "Enabled more with '/ability add [abilityName]");
	}
	
	@CommandHandler(name = "add", requiredRank = Rank.STAFF)
	public void getAbilityCmd(CommandArgs args) 
	{
		String abilityName = args.getArgs(0);
		
		Ability ability = getAbility(abilityName);
		if(ability == null)
		{
			args.getSender().sendMessage(CC.red + abilityName + " Not found.");
			return;
		}
		
		ability.getItems().forEach(item -> args.getPlayer().getInventory().addItem(item));
		args.getSender().sendMessage(CC.green + "Given " + ability.getName() + " Ability!");
		enabledAbilitiesNames.add(ability.getName().toLowerCase());
	}
	
	@CommandHandler(name = "remove", requiredRank = Rank.STAFF)
	public void clearAbilityCmd(CommandArgs args) 
	{
		String abilityName = args.getArgs(0);
		
		Ability ability = getAbility(abilityName);
		if(ability == null)
		{
			args.getSender().sendMessage(CC.red + abilityName + " Not found.");
			return;
		}
		
		enabledAbilitiesNames.remove(ability.getName().toLowerCase());
		args.getSender().sendMessage(CC.green + "removed " + ability.getName() + " Ability!");
	}
	
	private Ability getAbility(String name)
	{
		Optional<Ability> ability = this.abilities.stream()
												  .filter(abl -> abl.getName().equalsIgnoreCase(name))
							   					  .findFirst();
		return ability.orElse(null);
	}
	
	@Override
	public void addAbility(Ability ability) {
		this.abilities.add(ability);
		this.abilitiesNames.add(ability.getName().toLowerCase());
		ability.setParent(this);
		PluginUtils.registerListener(ability, this.getPlugin());
		ability.onLoad();
		Console.log("DummyAbilityParent", "add ability " + ability.getName() + " by " + ability.getAuthor());
	}

	@Override
	public void removeAbility(Ability ability) {
		this.abilities.remove(ability);
		this.abilitiesNames.remove(ability.getName().toLowerCase());
		ability.onUnload();
	}

	@Override
	public List<Ability> getAbilities() {
		return abilities;
	}

	@Override
	public boolean has(Player player) {
		return true;
	}

	@Override
	public boolean hasAbility(Ability ability, Player player) {
		return enabledAbilitiesNames.contains(ability.getName());
	}

	@Override
	public boolean hasAbility(String ability, Player player) {
		return enabledAbilitiesNames.contains(ability);
	}

}
