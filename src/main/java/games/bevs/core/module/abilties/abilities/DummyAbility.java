package games.bevs.core.module.abilties.abilities;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.meta.FireworkMeta;

import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import lombok.AllArgsConstructor;

/**
 *  When the players is gonna die, it'll launch a firework
 */
@AllArgsConstructor
@AbilityInfo(name="Dummy", author = "Sprock", description = {"Will do a cute little firework effect"})
public class DummyAbility extends Ability
{
	@EventHandler
	public void onPunch(CustomDamageEvent e)
	{
		if(e.isAttackerIsPlayer() && (this.hasAbility(e.getAttackerPlayer())))
		{
			Player player = e.getAttackerPlayer();
			if(!e.willDie()) return;
			
			Firework fireWork = (Firework) player.getWorld().spawnEntity(e.getVictimLivingEntity().getLocation(), EntityType.FIREWORK);
            FireworkMeta fwMeta = fireWork.getFireworkMeta();
           
            fwMeta.addEffect(FireworkEffect.builder().flicker(false).trail(true).with(Type.BALL).with(Type.BALL_LARGE).with(Type.STAR).withColor(Color.ORANGE).withColor(Color.YELLOW).withFade(Color.PURPLE).withFade(Color.RED).build());

            fireWork.setFireworkMeta(fwMeta);
		}
	}
}
