package games.bevs.core.module.abilties.abilities;

import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

/**
 * This ability will deal more damage with your fist
 *
 * @owner BevsGames
 */
@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(name = "Boxer", author = "Sprock", description = {"Fists will do more damage", "You also receive less damage from players."})
public class BoxerAbility extends Ability {
    private @Getter @Setter double damageDeal = 4;

    @EventHandler
    public void onPunch(CustomDamageEvent e) {
        if (e.isAttackerIsPlayer() && (this.hasAbility(e.getAttackerPlayer()))) {
            Player player = e.getAttackerPlayer();
            ItemStack itemInHand = player.getItemInHand();
            if (itemInHand != null) return;
            e.addDamage(this.getDamageDeal(), player.getName(), "Boxer Ability");
        }
    }

    @EventHandler
    public void onGetPunched(CustomDamageEvent e) {
        if (e.isVictimIsPlayer() && (this.hasAbility(e.getVictimPlayer()))) {
            if (e.getInitCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK || e.getInitCause() != EntityDamageEvent.DamageCause.PROJECTILE) {
                return;
            }
            e.setInitDamage(e.getInitDamage() - 1);
        }
    }
}
