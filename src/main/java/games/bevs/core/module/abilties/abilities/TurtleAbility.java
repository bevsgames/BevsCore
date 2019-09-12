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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Turtle",
        author = "Fundryi",
        description = {
                "You only take 0.5 hearts of damage if you sneak!",
                "You can not deal any damage while sneaking!"
        })

public class TurtleAbility extends Ability {

    public @Getter @Setter Material itemMaterial = Material.DIAMOND_CHESTPLATE;

    private double maxTurtleDamage = 1;

    @EventHandler
    public void onTurtleAttack(CustomDamageEvent event) {
        Player attacker = event.getAttackerPlayer();
        if (event.isAttackerIsPlayer()) {
            if (!this.hasAbility(attacker)) {
                return;
            }
            if (!event.getAttackerPlayer().isSneaking()) {
                event.setCancelled(true);
                LivingEntity victim = event.getVictimLivingEntity();
                victim.damage(0);
                victim.getWorld().playEffect(victim.getLocation().add(0, 1, 0), Effect.HEART, 0);
            }
        }
    }

    @EventHandler
    public void onTurtleDefend(CustomDamageEvent event) {
        Player victim = event.getVictimPlayer();
        if (event.isVictimIsPlayer()) {
            if (!this.hasAbility(victim)) {
                return;
            }
            if (!victim.isSneaking()) {
                return;
            }
            if (victim.getHealth() <= 1) {
                return;
            }
            if (event.getInitDamage() > maxTurtleDamage) {
                event.setInitDamage(maxTurtleDamage);
            }
            victim.getWorld().playEffect(victim.getLocation().add(0, 1, 0), Effect.MOBSPAWNER_FLAMES, 0);
        }
    }
}
