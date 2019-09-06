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
import org.bukkit.event.EventHandler;

import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Magma",
        author = "Fundryi",
        description = {
                "&7You have a 15% chance on setting",
                "&7players on fire for 5 seconds."
        })

public class MagmaAbility extends Ability {

    public @Getter @Setter Material itemMaterial = Material.STICK;

    private static final int chance = 15;
    private static final int length = 5;

    @EventHandler
    public void MagmaAttack(CustomDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if(!event.isAttackerIsPlayer() && !this.hasAbility(event.getAttackerPlayer())) {
            return;
        }
        LivingEntity entity = event.getVictimLivingEntity();
        if (ThreadLocalRandom.current().nextInt(101) <= chance) {
            entity.getWorld().playEffect(entity.getLocation().add(0,1,0), Effect.SMOKE, 1,1);
            entity.setFireTicks(length * 20);
        }
    }
}
