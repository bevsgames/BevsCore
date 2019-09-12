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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Jelly",
        author = "Fundryi",
        description = {
                "I guess you bounce a lot?",
                "You take no fall damage and will bounce around!",
                "Sneaking prevents you from bouncing but",
                "you will recive 50% of the normal fall damage!"
        })

public class JellyAbility extends Ability {

    public @Getter @Setter Material itemMaterial = Material.SLIME_BLOCK;

    @EventHandler
    public void onJellyFallDamage(CustomDamageEvent event) {
        Player player = event.getVictimPlayer();
        if (player == null) {
            return;
        }
        if (!event.isVictimIsPlayer() && !this.hasAbility(event.getVictimPlayer())) {
            return;
        }
        if (!(event.getInitCause() == EntityDamageEvent.DamageCause.FALL)) {
            return;
        }
        if (player.isSneaking()) {
            double damage = event.getDamage();
            damage *= 0.5;
            event.setInitDamage(damage);
            player.getWorld().playEffect(player.getLocation().add(0, 1, 0), Effect.EXPLOSION, 3);
            return;
        }
        player.getWorld().playEffect(player.getLocation().add(0, 1, 0), Effect.SLIME, 3);
        double velHeight = player.getVelocity().getY() + (player.getFallDistance() / 12);
        Vector vector = player.getEyeLocation().getDirection();
        vector.multiply(2);
        vector.setY(velHeight);
        player.setVelocity(vector);
        event.setCancelled(true);
    }
}
