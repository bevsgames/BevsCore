//package games.bevs.core.module.abilties.abilities;
//
//import games.bevs.core.commons.itemstack.ItemStackBuilder;
//import games.bevs.core.module.abilties.AbilityInfo;
//import games.bevs.core.module.abilties.types.Ability;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.bukkit.Material;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.player.PlayerTeleportEvent;
//import org.bukkit.inventory.ItemStack;
//
//import java.util.Arrays;
//import java.util.List;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@AbilityInfo(
//        name = "Jumper",
//        author = "Fundryi",
//        description = {
//                "TESTING"
//        })
//
//public class JumperAbility extends Ability {
//
//    public @Getter @Setter Material itemMaterial = Material.ENDER_PEARL;
//
//    private @Getter ItemStack FishingRod;
//
//    @Override
//    public void onLoad() {
//        this.FishingRod = new ItemStackBuilder(itemMaterial).amount(8).build();
//    }
//
//    @Override
//    public List<ItemStack> getItems() {
//        return Arrays.asList(FishingRod);
//    }
//
//    @EventHandler
//    public void onPearl(PlayerTeleportEvent event) {
//        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL)
//            if (this.hasAbility(event.getPlayer())) {
//                event.setCancelled(true);
//                event.getPlayer().teleport(event.getTo());
//                event.getPlayer().setFallDistance(0f);
//            }
//    }
//
//}
