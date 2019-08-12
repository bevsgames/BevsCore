package games.bevs.core.module.abilties.abilities;

import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Anchor",
        author = "Fundryi",
        description = {
                "You're immune against anything that deals knockback!",
                "You also have a 33% chance of giving no knockback!"
        })

public class AnchorAbility extends Ability {

    public @Getter @Setter Material itemMaterial = Material.ANVIL;

    private static final double NO_KNOCKBACK_CHANCE = 0.33;

    private static final Sound HIT_SOUND = Sound.ANVIL_LAND;
    private static final float HIT_SOUND_PITCH_OUT = 0.75f;
    private static final float HIT_SOUND_PITCH_IN = 1.35f;


    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event instanceof EntityDamageByEntityEvent) {
            return;
        }
        handleIncomingDamage(event);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Projectile) {
            return;
        }
        if (this.hasAbility((Player)event.getEntity())) {
            handleIncomingDamage(event);
        } else if (this.hasAbility((Player)event.getDamager())) {
            handleDealDamage(event);
        }
    }

    private void handleDealDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) {
            return;
        }
        if (ThreadLocalRandom.current().nextDouble() > NO_KNOCKBACK_CHANCE) {
            return;
        }
        LivingEntity eventEntity = (LivingEntity) event.getEntity();

        Stream.of(event.getEntity(), event.getDamager())
                .filter(e -> e instanceof Player)
                .map(e -> (Player) e)
                .forEach(p -> p.playSound(event.getEntity().getLocation(), HIT_SOUND, 0.5f, HIT_SOUND_PITCH_OUT));

        handleAnchoredEntity(event, eventEntity);
    }

    private void handleIncomingDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) {
            return;
        }
        LivingEntity eventEntity = (LivingEntity) event.getEntity();

        Optional.of(event.getEntity())
                .filter(e -> e instanceof Player)
                .map(e -> (Player) e)
                .ifPresent(p -> p.playSound(event.getEntity().getLocation(), HIT_SOUND, 0.5f, HIT_SOUND_PITCH_IN));

        handleAnchoredEntity(event, eventEntity);
    }

    private void handleAnchoredEntity(EntityDamageEvent event, LivingEntity eventEntity) {
        if (eventEntity.getHealth() - event.getFinalDamage() <= 0) {
            return;
        }
        eventEntity.damage(event.getFinalDamage());

        event.setCancelled(true);
    }
}
