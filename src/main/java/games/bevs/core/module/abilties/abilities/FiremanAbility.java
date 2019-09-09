package games.bevs.core.module.abilties.abilities;

import games.bevs.core.commons.itemstack.ItemStackBuilder;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Fireman",
        author = "Fundryi",
        description = {
                "Fire is a cold breeze for you, and only tickles.",
                "Lava only does half of the usual damage.",
                "You also spawn with an water bucket!"
        })

public class FiremanAbility extends Ability {

    public @Getter @Setter Material itemMaterial = Material.WATER_BUCKET;

    private @Getter ItemStack WaterBucket;

    @Override
    public void onLoad() {
        this.WaterBucket = new ItemStackBuilder(itemMaterial).build();
    }

    @Override
    public List<ItemStack> getItems() {
        return Arrays.asList(WaterBucket);
    }

    @EventHandler
    public void onFiremanDamage(CustomDamageEvent event) {
        Player player = event.getVictimPlayer();
        if (player == null) {
            return;
        }
        if (!event.isVictimIsPlayer() && !this.hasAbility(event.getVictimPlayer())) {
            return;
        }
        if (event.getInitCause() == EntityDamageEvent.DamageCause.FIRE) {
            event.setCancelled(true);
            return;
        }
        if (event.getInitCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
            event.setCancelled(true);
            return;
        }
        if (event.getInitCause() == EntityDamageEvent.DamageCause.LAVA) {
            double damage = event.getDamage();
            damage *= 0.5;
            event.setInitDamage(damage);
        }
    }
}
