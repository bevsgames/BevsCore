//package games.bevs.core.module.abilties.abilities;
//
//import games.bevs.core.module.abilties.AbilityInfo;
//import games.bevs.core.module.abilties.types.Ability;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.bukkit.Material;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.player.PlayerMoveEvent;
//import org.bukkit.potion.PotionEffect;
//import org.bukkit.potion.PotionEffectType;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@AbilityInfo(
//        name = "Jelly",
//        author = "Fundryi",
//        description = {
//                "You gain strength and speed in the water",
//                "So be sure to lure your opponents into water lakes"
//        })
//
//public class PoseidonAbility extends Ability {
//
//    private static final int damageMultiplier = 0;
//    private static final int speedMultiplier = 1;
//    public @Getter @Setter Material itemMaterial = Material.SLIME_BLOCK;
//
//    @EventHandler
//    public void onPoseidonMove(PlayerMoveEvent event) {
//        Player player = event.getPlayer();
//        if (player == null) {
//            return;
//        }
//        if (!this.hasAbility(player)) {
//            return;
//        }
//        if (player.getLocation().getBlock().isLiquid()) {
//            if (event.getPlayer().getRemainingAir() < event.getPlayer().getMaximumAir()) {
//                player.setRemainingAir(event.getPlayer().getMaximumAir());
//            }
//            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 49, damageMultiplier), true);
//            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 69, speedMultiplier), true);
//        }
//    }
//}
