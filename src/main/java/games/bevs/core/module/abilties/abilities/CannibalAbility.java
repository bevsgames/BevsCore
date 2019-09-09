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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Cannibal",
        author = "Fundryi",
        description = {
                "&7You have a 25% chance of giving",
                "&7Poison 1 for 5 seconds to a player."
        })

public class CannibalAbility extends Ability {

    public @Getter @Setter Material itemMaterial = Material.STICK;

    private static final int chance = 35;
    private static final int length = 10;
    private static final int multiplier = 1;
    private static final int addHunger = 3;

    @EventHandler
    public void onViperAttack(CustomDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if(!event.isAttackerIsPlayer() && !this.hasAbility(event.getAttackerPlayer())) {
            return;
        }
        Player player = event.getAttackerPlayer();
        int hunger = player.getFoodLevel();
        if (hunger < 30) {
            hunger += addHunger;
            player.setFoodLevel(hunger);
        }

        LivingEntity entity = event.getVictimLivingEntity();
        if (ThreadLocalRandom.current().nextInt(101) <= chance) {
            entity.getWorld().playEffect(entity.getLocation().add(0,1,0), Effect.CLOUD, 3);
            entity.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, length * 20, multiplier), true);
        }
    }
}
