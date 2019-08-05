package games.bevs.core.module.abilties.abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Take a max amount of damage from a daamage type.
 */
@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(name = "HardFeet", author = "Sprock", description = { "Set Max damage Cause can do" })
public class HardFeetAbility extends Ability
{
	private double maxFallDamage = 7.0;
	private DamageCause damageCause = DamageCause.FALL;
	
	@EventHandler
	public void onReduceDamageType(CustomDamageEvent e) 
	{
		if(!e.isVictimIsPlayer()) return;
		Player player = e.getVictimPlayer();
		if(!hasAbility(player)) return;
		if(e.getInitCause() != damageCause ) return;
		if (e.getDamage() > maxFallDamage)
			e.setMaxDamage(maxFallDamage);
	}
}
