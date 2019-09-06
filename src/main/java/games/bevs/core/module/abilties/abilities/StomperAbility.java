package games.bevs.core.module.abilties.abilities;

import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Stomper",
        author = "Fundryi",
        description = {
                "&7Fall damage you would receive",
                "&7will be transferred to the player",
                "&7in a 2x2x2 radius where you land!",
                "&7If they sneak they will get max 3 hearts damage."
        })

public class StomperAbility extends Ability {

    public @Getter @Setter Material itemMaterial = Material.STICK;

    @EventHandler(priority = EventPriority.HIGH)
    public void omStomp(CustomDamageEvent event) {
        if(!event.isAttackerIsPlayer() && !this.hasAbility(event.getAttackerPlayer())) {
            return;
        }
        if (event.getInitCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }
        if (event.getDamage() <= 4) {
            return;
        }
        Player stomper = event.getAttackerPlayer();

        for (Entity entity : stomper.getNearbyEntities(2, 2, 2)) {
            if ((entity instanceof LivingEntity)) {
                Player stomped = (Player) entity;
                if (stomped.isSneaking()) {
                    stomped.damage(6, stomper);
                } else {
                    stomped.damage(event.getDamage(), stomper);
                    stomped.damage(stomper.getFallDistance());
                }
            }
            stomper.playSound(stomper.getLocation(), Sound.ANVIL_LAND, 1, 1);
            stomper.getWorld().playEffect(stomper.getLocation().add(0,1,0), Effect.EXPLOSION_HUGE, 1,1);
        }
        event.setInitDamage(4);
    }
}
