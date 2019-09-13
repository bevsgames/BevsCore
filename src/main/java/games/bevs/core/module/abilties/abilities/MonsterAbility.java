package games.bevs.core.module.abilties.abilities;

import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Monster",
        author = "Fundryi",
        description = {
                "TESTING"
        })

public class MonsterAbility extends Ability {

    public @Getter @Setter Material itemMaterial = Material.MOB_SPAWNER;

    @EventHandler
    public void onMonsterTarget(EntityTargetLivingEntityEvent event) {
        if (event.getTarget() instanceof Player) {
            if (this.hasAbility((Player) event.getTarget()))
                event.setCancelled(true);
        }
    }

}
