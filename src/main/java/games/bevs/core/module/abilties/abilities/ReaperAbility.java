package games.bevs.core.module.abilties.abilities;

import games.bevs.core.commons.itemstack.ItemStackBuilder;
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
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Reaper",
        author = "Fundryi",
        description = {
                "You have a 20% chance of giving",
                "Wither Effect 1 for 5 seconds to a player,",
                "if you hit them with your Hoe."
        })

public class ReaperAbility extends Ability {

    private static final int chance = 20;
    private static final int length = 5;
    private static final int multiplier = 0;
    public @Getter @Setter String itemName = "Reaper Hoe";
    public @Getter @Setter Material itemMaterial = Material.WOOD_HOE;
    private @Getter ItemStack ReaperHoe;

    @Override
    public void onLoad() {
        this.ReaperHoe = new ItemStackBuilder(itemMaterial).displayName(itemName).build();
    }

    @Override
    public List<ItemStack> getItems() {
        return Arrays.asList(ReaperHoe);
    }

    @EventHandler
    public void onReaperAttack(CustomDamageEvent event) {
        if (event.isAttackerIsPlayer() && this.hasAbility(event.getAttackerPlayer())) {
            ItemStack inHand = event.getAttackerPlayer().getInventory().getItemInHand();
            if (inHand == null) {
                return;
            }
            if (!inHand.isSimilar(ReaperHoe)) {
                return;
            }
            LivingEntity entity = event.getVictimLivingEntity();
            if (ThreadLocalRandom.current().nextInt(101) <= chance) {
                entity.getWorld().playEffect(entity.getLocation(), Effect.SMOKE, 3);
                entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, length * 20, multiplier), true);
            }
        }
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        if (!(this.hasAbility(event.getPlayer())))
            return;
        if (event.getItem().isSimilar(ReaperHoe)) {
            event.setCancelled(true);
        }
    }
}
