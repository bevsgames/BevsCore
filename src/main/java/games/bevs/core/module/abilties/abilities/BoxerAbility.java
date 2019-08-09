package games.bevs.core.module.abilties.abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This ability will deal more damage with your fist
 * 
 * @owner BevsGames
 */
@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(name="Boxer", author = "Sprock", description = {"Fists will do more damage"})
public class BoxerAbility extends Ability
{
	private @Getter @Setter double damageDeal = 4;
	
	@EventHandler
	public void onPunch(CustomDamageEvent e)
	{
		if(e.isAttackerIsPlayer() && (this.hasAbility(e.getAttackerPlayer())))
		{
			Player player = e.getAttackerPlayer();
			ItemStack itemInHand = player.getItemInHand();
			if(itemInHand != null) return;
			e.addDamage(this.getDamageDeal(), player.getName(), "Boxer Ability");
		}
	}
}
