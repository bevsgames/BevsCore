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
                "You're immune against anything that deals knockback!",
                "You also have a 33% chance of giving no knockback!"
        })

public class TurtleAbility extends Ability {

    public @Getter @Setter Material itemMaterial = Material.DIAMOND_CHESTPLATE;

    private boolean needToBlock = false; //OFF because you cant deal damage when sneaking.
    private double maxTurtleDamage = 1;

    @EventHandler
    public void onTurtleAttack(CustomDamageEvent event) {
        Player player = event.getAttackerPlayer();
        if (!this.hasAbility(player)) {
            return;
        }
        if (!player.isSneaking()) {
            return;
        }
        if (needToBlock && !player.isBlocking()) {
            return;
        }
        event.setCancelled(true);
        if (!(event.getVictimLivingEntity() instanceof Player)) {
            return;
        }
        LivingEntity entity = event.getVictimLivingEntity();
        entity.damage(0);
        entity.getWorld().playEffect(entity.getLocation().add(0,1,0), Effect.HEART, 0);
    }

    @EventHandler
    public void onTurtleDefend(CustomDamageEvent event) {
        Player player = event.getVictimPlayer();
        if(!(this.hasAbility(player)))
            return;
        if (!player.isSneaking()) {
            return;
        }
        if (player.getHealth() <= 1) {
            return;
        }
        if (needToBlock && !player.isBlocking()) {
            return;
        }
        if (event.getDamage() > maxTurtleDamage) {
            event.setInitDamage(maxTurtleDamage);
        }
        player.getWorld().playEffect(player.getLocation().add(0,1,0), Effect.MOBSPAWNER_FLAMES, 0);
    }

}
