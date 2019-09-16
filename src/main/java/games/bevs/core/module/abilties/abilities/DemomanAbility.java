//package games.bevs.core.module.abilties.abilities;
//
//import games.bevs.core.commons.CC;
//import games.bevs.core.module.abilties.AbilityInfo;
//import games.bevs.core.module.abilties.types.Ability;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.block.Block;
//import org.bukkit.block.BlockFace;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.block.Action;
//import org.bukkit.event.block.BlockBreakEvent;
//import org.bukkit.event.block.BlockExplodeEvent;
//import org.bukkit.event.block.BlockPlaceEvent;
//import org.bukkit.event.player.PlayerInteractEvent;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@AbilityInfo(
//        name = "Demoman",
//        author = "Fundryi",
//        description = {
//                "TESTING"
//        })
//
//public class DemomanAbility extends Ability {
//
//    public @Getter @Setter Material itemMaterial = Material.STONE_PLATE;
//
//    private static List<Block> traps = new ArrayList<Block>();
//
//    @EventHandler
//    public void onPlace(BlockPlaceEvent event) {
//        if (!this.hasAbility(event.getPlayer()))
//            return;
//        Block placed = event.getBlock();
//        if (placed.getType() != Material.STONE_PLATE || placed.getType() != Material.WOOD_PLATE)
//            return;
//        if (placed.getRelative(BlockFace.DOWN).getType() != Material.GRAVEL)
//            return;
//        traps.add(placed);
//        event.getPlayer().sendMessage(CC.bGreen + "Demoman Trap Placed!");
//    }
//
//    @EventHandler
//    public void onBreak(BlockBreakEvent event) {
//        if (traps.contains(event.getBlock()))
//            traps.remove(event.getBlock());
//    }
//
//    @EventHandler
//    public void onExplode(BlockExplodeEvent event) {
//        if (traps.contains(event.getBlock()))
//            traps.remove(event.getBlock());
//    }
//
//    @EventHandler
//    public void onInteract(PlayerInteractEvent event) {
//        if (!(event.getAction() == Action.PHYSICAL))
//            return;
//        Block block = event.getClickedBlock();
//        Location locExplosion = block.getRelative(BlockFace.UP).getLocation();
//        if (block.getType() == Material.STONE_PLATE || block.getType() == Material.WOOD_PLATE && traps.contains(block)) {
//            traps.remove(block);
//            block.setType(Material.AIR);
//            block.getWorld().createExplosion(locExplosion, 6);
//        }
//    }
//
//}
