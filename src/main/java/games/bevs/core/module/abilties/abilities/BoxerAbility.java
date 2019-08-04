package games.bevs.core.module.abilties.abilities;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import games.bevs.core.module.abilties.Ability;
import games.bevs.core.module.abilties.AbilityInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@AbilityInfo(name = "Sprock", description = "Fists will do more damage")
public class BoxerAbility extends Ability
{
	private @Getter @Setter double damageDeal = 4;
	
//	@EventHandler
//	public void onPunch(PlayerDamageEntityEvent e)
//	{
//	}
}
