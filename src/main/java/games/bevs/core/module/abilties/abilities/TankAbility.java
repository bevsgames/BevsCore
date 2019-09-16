//package games.bevs.core.module.abilties.abilities;
//
//import games.bevs.core.module.abilties.AbilityInfo;
//import games.bevs.core.module.abilties.types.Ability;
//import games.bevs.core.module.combat.event.CustomDamageEvent;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.bukkit.Material;
//import org.bukkit.entity.Animals;
//import org.bukkit.entity.Entity;
//import org.bukkit.entity.Monster;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.entity.EntityDamageEvent;
//import org.bukkit.event.entity.EntityDeathEvent;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@AbilityInfo(
//        name = "Tank",
//        author = "Fundryi",
//        description = {
//                "TESTING"
//        })
//
//public class TankAbility extends Ability {
//
//    public @Getter @Setter Material itemMaterial = Material.TNT;
//
//    @EventHandler
//    public void onTankKill(EntityDeathEvent event) {
//        Player killer = event.getEntity().getKiller();
//        Entity victim = event.getEntity();
//        if (this.hasAbility(killer)) {
//            if (victim instanceof Animals) {
//                victim.getWorld().createExplosion(event.getEntity().getLocation(), 2);
//                return;
//            }
//            if (victim instanceof Monster) {
//                victim.getWorld().createExplosion(event.getEntity().getLocation(), 3);
//                return;
//            }
//            if (victim instanceof Player) {
//                victim.getWorld().createExplosion(event.getEntity().getLocation(), 5);
//            }
//        }
//    }
//
//    @EventHandler
//    public void onTankExplosionDamage(CustomDamageEvent event) {
//        if (!event.isVictimIsPlayer() && !this.hasAbility(event.getVictimPlayer())) {
//            return;
//        }
//        if (event.getInitCause() != EntityDamageEvent.DamageCause.ENTITY_EXPLOSION
//                || event.getInitCause() != EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
//            return;
//        }
//        event.setCancelled(true);
//    }
//}
