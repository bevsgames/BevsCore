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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Snail",
        author = "Fundryi",
        description = {
                "You have a 25% chance of giving",
                "slowness 2 for 5 seconds to a player."
        })

public class SnailAbility extends Ability {

    public @Getter @Setter Material itemMaterial = Material.STICK;

    private static final int chance = 25;
    private static final int length = 5;
    private static final int multiplier = 0;

    @EventHandler
    public void onSnailAttack(CustomDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if(!event.isAttackerIsPlayer() && !this.hasAbility(event.getAttackerPlayer())) {
            return;
        }
        LivingEntity entity = event.getVictimLivingEntity();
        if (ThreadLocalRandom.current().nextInt(101) <= chance) {
            entity.getWorld().playEffect(entity.getLocation().add(0,1,0), Effect.SMOKE, 3);
            entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, length * 20, multiplier), true);
        }
    }
}
