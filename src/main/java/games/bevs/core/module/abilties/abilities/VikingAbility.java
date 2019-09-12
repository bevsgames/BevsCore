package games.bevs.core.module.abilties.abilities;

import com.google.common.collect.ImmutableList;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Viking",
        author = "Fundryi",
        description = {
                "You're very strong with axes,",
                "in fact you deal 1 heart more damage",
                "with every axe in the game."
        })

public class VikingAbility extends Ability {

    private static final double VIKING_DAMAGE = 2;
    private static final List<Material> AXES = ImmutableList.of(
            Material.WOOD_AXE,
            Material.STONE_AXE,
            Material.IRON_AXE,
            Material.GOLD_AXE,
            Material.DIAMOND_AXE
    );
    public @Getter @Setter Material itemMaterial = Material.DIAMOND_AXE;

    @EventHandler
    public void onVikingAttack(CustomDamageEvent event) {
        if (event.isAttackerIsPlayer() && this.hasAbility(event.getAttackerPlayer())) {
            ItemStack inHand = event.getAttackerPlayer().getItemInHand();
            if (inHand == null) {
                return;
            }
            if (!AXES.contains(inHand.getType())) {
                return;
            }
            event.setInitDamage(event.getDamage() + VIKING_DAMAGE);
        }
    }
}
