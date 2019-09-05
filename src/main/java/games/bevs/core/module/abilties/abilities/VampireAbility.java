package games.bevs.core.module.abilties.abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This ability will give the killer more health on kill
 * 
 * @owner BevsGames
 */
@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(name="Vampire", author = "Sprock", description = {"Gain extra hearts when you die"})
public class VampireAbility extends Ability
{
	//1 Minecraft Heart = 2 Heart
	private @Getter @Setter double maxStealHearts = 4;
	
	@EventHandler
	public void onPunch(CustomDamageEvent e)
	{
		if(e.isAttackerIsPlayer() && (this.hasAbility(e.getAttackerPlayer())))
		{
			if(!e.isVictimIsPlayer()) 
				return;
			Player player = e.getAttackerPlayer();
			
			if(e.willDie())
				player.setHealth(player.getHealth() + this.maxStealHearts);
		}
	}
}
