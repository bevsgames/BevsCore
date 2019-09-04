package games.bevs.core.module.abilties.abilities;

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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Turtle",
        author = "Fundryi",
        description = {
                "You only take 0.5 hearts of damage if you sneak!",
                "You can not deal any damage while sneaking!"
        })

public class TurtleAbility extends Ability {

    public @Getter @Setter Material itemMaterial = Material.DIAMOND_CHESTPLATE;

    private double maxTurtleDamage = 1;

    @EventHandler
    public void Turtle(CustomDamageEvent event) {
        if (event.isAttackerIsPlayer() && this.hasAbility(event.getAttackerPlayer())) {
            LivingEntity victim = event.getVictimLivingEntity();
            victim.damage(0);
            victim.getWorld().playEffect(victim.getLocation().add(0,1,0), Effect.HEART, 0);
        }

        Player victim = event.getVictimPlayer();
        if(event.isVictimIsPlayer() && this.hasAbility(victim) && victim.isSneaking()) {
            if (victim.getHealth() <= 1) {
                return;
            }
            if (event.getDamage() > maxTurtleDamage) {
                event.setInitDamage(maxTurtleDamage);
            }
            victim.getWorld().playEffect(victim.getLocation().add(0,1,0), Effect.MOBSPAWNER_FLAMES, 0);
        }
    }
    
    //OLD CODE
//    @EventHandler
//    public void onTurtleAttack(CustomDamageEvent event) {
//        //Attacker = Turtle Player
//        //Turtle Player > Check if he is Kit/Sneak/Blocking
//        Player attacker = event.getAttackerPlayer();
//        if (!this.hasAbility(attacker)) {
//            return;
//        }
//        if (!attacker.isSneaking()) {
//            return;
//        }
//        if (needToBlock && !attacker.isBlocking()) {
//            return;
//        }
//        event.setCancelled(true);
//        //Victim only gets 0 damage if above is right.
//        LivingEntity victim = event.getVictimLivingEntity();
//        victim.damage(0);
//        victim.getWorld().playEffect(victim.getLocation().add(0,1,0), Effect.HEART, 0);
//    }
//
//    @EventHandler
//    public void onTurtleDefend(CustomDamageEvent event) {
//        //Victim = Turtle Player
//        Player victim = event.getVictimPlayer();
//        if(!(this.hasAbility(victim)))
//            return;
//        if (!victim.isSneaking()) {
//            return;
//        }
//        if (victim.getHealth() <= 1) {
//            return;
//        }
//        if (needToBlock && !victim.isBlocking()) {
//            return;
//        }
//        if (event.getDamage() > maxTurtleDamage) {
//            event.setInitDamage(maxTurtleDamage);
//        }
//        victim.getWorld().playEffect(victim.getLocation().add(0,1,0), Effect.MOBSPAWNER_FLAMES, 0);
//    }

}
