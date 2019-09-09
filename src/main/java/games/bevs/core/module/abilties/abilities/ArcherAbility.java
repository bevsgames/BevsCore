package games.bevs.core.module.abilties.abilities;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import games.bevs.core.commons.itemstack.ItemStackBuilder;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import games.bevs.core.module.utils.LocationUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Archer",
        author = "Fundryi",
        description = {
                "&7Wow a bow, with knockback!",
                "&7You get a free arrow if you hit someone.",
                "&7You will find more flints & feathers."
        })

public class ArcherAbility extends Ability {

    public @Getter @Setter String itemName = "Sniper Bow";
    public @Getter @Setter Material itemMaterial = Material.BOW;
    public @Getter @Setter Material itemMaterialArrow = Material.ARROW;

    private @Getter ItemStack SniperBow;
    private @Getter ItemStack SniperArrow;

    private static final Map<Material, ItemStack> specialBlockBreaks = ImmutableMap.of(
            Material.GRAVEL, new ItemStack(Material.FLINT, 1)
    );

    @Override
    public void onLoad() {
        this.SniperBow = new ItemStackBuilder(itemMaterial).displayName(itemName).enchantment(Enchantment.ARROW_KNOCKBACK,1).build();
        this.SniperArrow = new ItemStackBuilder(itemMaterialArrow, 10).build();
    }

    @Override
    public List<ItemStack> getItems() {
        return Arrays.asList(SniperBow, SniperArrow);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(!this.hasAbility(event.getPlayer())) {
            return;
        }
        specialBlockBreaks.computeIfPresent(event.getBlock().getType(), (material, itemStack) -> {
            event.setCancelled(true);
            Location target = LocationUtils.toCenterBlockLocation(event.getBlock());
            event.getBlock().getWorld().dropItemNaturally(target, itemStack);
            event.getPlayer().playSound(target, Sound.DIG_GRAVEL, 1f, 1f);
            return itemStack;
        });
    }

    @EventHandler
    public void onEntityDamage(CustomDamageEvent event) {
        if (!event.isAttackerIsPlayer() && !this.hasAbility(event.getAttackerPlayer())) {
            return;
        }
        if (!(event.getAttackerEntity() instanceof Arrow)) {
            return;
        }
        Arrow arrow = (Arrow) event.getAttackerEntity();
        if (!(arrow.getShooter() instanceof Player)) {
            return;
        }
        Player player = (Player) arrow.getShooter();
        player.getInventory().addItem(new ItemStack(Material.ARROW));
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if(!this.hasAbility(event.getEntity().getKiller())) {
            return;
        }
        if (event.getEntity().getKiller() != null) {
            Material mat;
            if (event.getEntityType() == EntityType.SKELETON) {
                mat = Material.ARROW;
            } else if (event.getEntityType() == EntityType.CHICKEN) {
                mat = Material.FEATHER;
            } else {
                return;
            }
            /*++++++++++++++++++++++++++++++++++++++*/
            /*Dringend Collection#removeIf anschauen*/
            /*++++++++++++++++++++++++++++++++++++++*/
            Iterator<ItemStack> itel = event.getDrops().iterator();
            while (itel.hasNext()) {
                ItemStack item = itel.next();
                if (item == null || item.getType() != mat) {
                    continue;
                }
                itel.remove();
            }
            event.getDrops().add(new ItemStack(mat, 2));
        }
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        if (event.getItem().isSimilar(SniperBow)) {
            event.setCancelled(true);
        }
    }
}
