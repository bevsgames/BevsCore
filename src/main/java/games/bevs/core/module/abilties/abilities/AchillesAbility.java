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

    private @Getter @Setter double DamageModifier = 2;

    @EventHandler
    public void onAchilles(CustomDamageEvent event) {
        Player attacker = event.getAttackerPlayer();
        Player defender = event.getVictimPlayer();
        if (!event.isVictimIsPlayer() && !this.hasAbility(defender)){
            return;
        }
        if (event.isAttackerIsPlayer() && attacker.getItemInHand().getType().name().toLowerCase().contains("wood")) {
            defender.getWorld().playEffect(defender.getLocation().add(0, 1, 0), Effect.STEP_SOUND, 66);
            attacker.playSound(attacker.getLocation(), Sound.IRONGOLEM_HIT, 10.0f, 1.0f);
            event.setInitDamage(event.getInitDamage() * DamageModifier);
        }else{
            event.setInitDamage(event.getInitDamage() / DamageModifier);
            if(event.isAttackerIsPlayer() && !attacker.getItemInHand().getType().name().toLowerCase().contains("wood")){
                attacker.sendMessage(CC.red + "Huh, the " + CC.bGold + "Wood" + CC.reset + "" + CC.red + " handle did more damage to this guy...");
            }
        }
    }
}
