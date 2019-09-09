package games.bevs.core.module.abilties.abilities;

import games.bevs.core.commons.CC;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Achilles",
        author = "Fundryi",
        description = {
                "The only weakness you have is Wood.",
                "Wood does 2x times the damage,",
                "but everything else 2x times less!"
        })

public class AchillesAbility extends Ability {

    public @Getter @Setter Material itemMaterial = Material.WOOD_SWORD;

    private final static float DamageModifier = 2;

    @EventHandler
    public void onAchilles(CustomDamageEvent event) {
        Player attacker = event.getAttackerPlayer();
        Player defender = event.getVictimPlayer();
        if (attacker.getItemInHand().getType().name().toLowerCase().contains("wood")) {
            if (!event.isVictimIsPlayer() && !this.hasAbility(defender)){
                return;
            }
            defender.getWorld().playEffect(defender.getLocation().add(0, 1, 0), Effect.STEP_SOUND, 66);
            attacker.playSound(attacker.getLocation(), Sound.IRONGOLEM_HIT, 10.0f, 1.0f);
            event.setInitDamage(event.getDamage() * DamageModifier);
        }else{
            if (!event.isVictimIsPlayer() && !this.hasAbility(defender)){
                return;
            }
            event.setInitDamage(event.getDamage() / DamageModifier);
            Material item = attacker.getItemInHand().getType();
            boolean isWoodenSword = item == Material.WOOD_SWORD;
            if(!isWoodenSword)
            {
                attacker.sendMessage(CC.red + "Huh, the " + CC.bGold + "Wood" + CC.reset + "" + CC.red + " handle did more damage to this guy...");
            }
        }
    }
}
