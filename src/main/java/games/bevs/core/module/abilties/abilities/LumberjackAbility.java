//package games.bevs.core.module.abilties.abilities;
//
//import com.google.common.collect.ImmutableList;
//import games.bevs.core.commons.itemstack.ItemStackBuilder;
//import games.bevs.core.commons.utils.LocationUtils;
//import games.bevs.core.module.abilties.AbilityInfo;
//import games.bevs.core.module.abilties.types.Ability;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.Sound;
//import org.bukkit.block.Block;
//import org.bukkit.block.BlockFace;
//import org.bukkit.enchantments.Enchantment;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.block.BlockBreakEvent;
//import org.bukkit.event.player.PlayerItemDamageEvent;
//import org.bukkit.inventory.ItemStack;
//
//import java.util.Arrays;
//import java.util.List;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@AbilityInfo(
//        name = "Lumberjack",
//        author = "Fundryi",
//        description = {
//                "You can destroy whole trees",
//                "with breaking 1 block!"
//        })
//
//public class LumberjackAbility extends Ability {
//
//    private static final List<Material> LOG_MATERIALS = ImmutableList.of(
//            Material.LOG,
//            Material.LOG_2
//    );
//    private static final List<BlockFace> DIRECTIONS = ImmutableList.of(
//            BlockFace.UP,
//            BlockFace.DOWN,
//            BlockFace.NORTH,
//            BlockFace.EAST,
//            BlockFace.SOUTH,
//            BlockFace.WEST
//    );
//    public @Getter @Setter String itemName = "Wood Destroyer";
//    public @Getter @Setter Material itemMaterial = Material.WOOD_AXE;
//    private @Getter ItemStack WoodAxe;
//
//    @Override
//    public void onLoad() {
//        this.WoodAxe = new ItemStackBuilder(itemMaterial).displayName(itemName).enchantment(Enchantment.DIG_SPEED, 5).build();
//    }
//
//    @Override
//    public List<ItemStack> getItems() {
//        return Arrays.asList(WoodAxe);
//    }
//
//    @EventHandler
//    public void onLumberjackBreak(BlockBreakEvent event) {
//        ItemStack inHand = event.getPlayer().getItemInHand();
//        if (inHand == null) {
//            return;
//        }
//        if (inHand.isSimilar(WoodAxe)) {
//            return;
//        }
//        if (!this.hasAbility(event.getPlayer())) {
//            return;
//        }
//        if (LOG_MATERIALS.contains(event.getBlock().getType())) {
//            deforest(event.getBlock());
//        }
//    }
//
//    private void deforest(Block origin) {
//        ItemStack drop = new ItemStack(origin.getType(), 1, origin.getData());
//        Location center = LocationUtils.toCenterLocation(origin);
//
//        origin.getWorld().dropItemNaturally(center, drop);
//        origin.getWorld().playSound(center, Sound.DIG_WOOD, 1f, 1f);
//
//        origin.setType(Material.AIR);
//
//        for (BlockFace face : DIRECTIONS) {
//            Block adjacent = origin.getRelative(face);
//            if (!LOG_MATERIALS.contains(adjacent.getType())) {
//                continue;
//            }
//            deforest(adjacent);
//        }
//    }
//
//    @EventHandler
//    public void onItemDamage(PlayerItemDamageEvent event) {
//        if (!(this.hasAbility(event.getPlayer())))
//            return;
//        if (event.getItem().isSimilar(WoodAxe)) {
//            event.setCancelled(true);
//        }
//    }
//}
