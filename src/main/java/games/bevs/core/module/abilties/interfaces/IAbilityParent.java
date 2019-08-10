package games.bevs.core.module.abilties.interfaces;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.cooldown.CooldownModule;

public interface IAbilityParent
{
	public String getName();
	
	public void addAbility(Ability ability);
	public void removeAbility(Ability ability);
	
	public List<Ability> getAbilities();
	
	/**
	 * Player has this ability parent eg player has this kit
	 * @param player
	 * @return player has abilities
	 */
	public boolean has(Player player);
	
	public boolean hasAbility(Ability ability, Player player);
	public boolean hasAbility(String ability, Player player);
	
	public JavaPlugin getPlugin();
	public CooldownModule getCooldownModule();
}
