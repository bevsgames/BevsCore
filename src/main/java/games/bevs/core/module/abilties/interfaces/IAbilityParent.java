package games.bevs.core.module.abilties.interfaces;

import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.cooldown.CooldownModule;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public interface IAbilityParent {
	String getName();

	void addAbility(Ability ability);

	void removeAbility(Ability ability);

	List<Ability> getAbilities();

	/**
	 * Player has this ability parent eg player has this kit
	 *
	 * @param player
	 * @return player has abilities
	 */
	boolean has(Player player);

	boolean hasAbility(Ability ability, Player player);

	boolean hasAbility(String ability, Player player);

	JavaPlugin getPlugin();

	CooldownModule getCooldownModule();
}
