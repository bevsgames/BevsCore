package games.bevs.core.module.abilties.dummy;

import games.bevs.core.commons.server.Console;
import games.bevs.core.commons.utils.PluginUtils;
import games.bevs.core.module.abilties.AbilityModule;
import games.bevs.core.module.abilties.dummy.commands.AbilityDummyCommand;
import games.bevs.core.module.abilties.interfaces.IAbilityParent;
import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.cooldown.CooldownModule;
import games.bevs.core.module.player.PlayerDataModule;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Dummy class for testing out abilities
 * <p>
 * Type
 * /ability add <Name>
 * To enable the ability added from {@link games.bevs.core.module.abilties.AbilityModule#onEnable() }
 * /ability list <Name>
 * Lists the enabled abilties
 */
public class DummyAbilityParent implements IAbilityParent {
	private ArrayList<Ability> abilities = new ArrayList<>();
	private @Getter HashSet<String> abilitiesNames = new HashSet<>();
	private @Getter HashSet<String> enabledAbilitiesNames = new HashSet<>();

	private @Getter @NonNull JavaPlugin plugin;
	private @Getter @NonNull AbilityModule abilityModule;
	private @NonNull CooldownModule cooldownModule;

	public DummyAbilityParent(JavaPlugin plugin, PlayerDataModule clientModule, CooldownModule cooldownModule, AbilityModule abilityModule) {
		this.plugin = plugin;
		this.cooldownModule = cooldownModule;

		abilityModule.registerCommand(new AbilityDummyCommand(this));
	}

	public Ability getAbility(String name) {
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
	public boolean hasAbility(String abilityName, Player player) {
		return enabledAbilitiesNames.contains(abilityName);
	}

	@Override
	public CooldownModule getCooldownModule() {
		return this.cooldownModule;
	}

	@Override
	public String getName() {
		return "Dummy";
	}
}
