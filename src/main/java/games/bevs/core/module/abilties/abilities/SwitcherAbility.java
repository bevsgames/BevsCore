//package games.bevs.core.module.abilties.abilities;
//
//import games.bevs.core.commons.itemstack.ItemStackBuilder;
//import games.bevs.core.module.abilties.AbilityInfo;
//import games.bevs.core.module.abilties.types.Ability;
//import games.bevs.core.module.combat.event.CustomDamageEvent;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.entity.Entity;
//import org.bukkit.entity.EntityType;
//import org.bukkit.entity.Player;
//import org.bukkit.entity.Projectile;
//import org.bukkit.entity.Snowball;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.entity.ProjectileLaunchEvent;
//import org.bukkit.inventory.ItemStack;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@AbilityInfo(
//        name = "Switcher",
//        author = "Fundryi",
//        description = {
//                "TESTING"
//        })
//
//public class SwitcherAbility extends Ability {
//
//    public @Getter @Setter Material itemMaterial = Material.SNOW_BALL;
//
//    private @Getter ItemStack StartSnowballs;
//    private List<Snowball> balls = new ArrayList<Snowball>();
//
//    @Override
//    public void onLoad() {
//        this.StartSnowballs = new ItemStackBuilder(itemMaterial).amount(10).build();
//    }
//
//    @Override
//    public List<ItemStack> getItems() {
//        return Arrays.asList(StartSnowballs);
//    }
//
//    @EventHandler
//    public void onDamage(CustomDamageEvent event) {
//        if (!event.isAttackerIsPlayer() && !this.hasAbility(event.getAttackerPlayer())) {
//            return;
//        }
//        if ((event.getAttackerEntity() instanceof Snowball)) {
//            if (!balls.contains(event.getAttackerEntity()))
//                return;
//            Player thrower = (Player) ((Projectile) event.getAttackerEntity()).getShooter();
//            Location loc1 = thrower.getPlayer().getLocation().clone();
//            Location loc2 = event.getAttackerEntity().getLocation().clone();
//            thrower.getPlayer().teleport(loc2);
//            event.getAttackerEntity().teleport(loc1);
//        }
//    }
//
//    @EventHandler
//    public void onProjectileLaunch(ProjectileLaunchEvent event) {
//        if ((event.getEntityType() == EntityType.SNOWBALL) && ((event.getEntity().getShooter() instanceof Player))) {
//            Player p = (Player) event.getEntity().getShooter();
//            Entity ball = event.getEntity();
//            if (!this.hasAbility(p) || !p.getInventory().getItemInHand().hasItemMeta())
//                return;
//            balls.add((Snowball) ball);
//        }
//    }
//}
