package games.bevs.core.module.abilties.abilities;

import games.bevs.core.commons.itemstack.ItemStackBuilder;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Specialist",
        author = "Fundryi",
        description = {
                "Enchanting on the way!",
                "Portable Enchantment Table so no one can steal it.",
                "You also receive 2 EXP Bottles per kill!"
        })

public class SpecialistAbility extends Ability {

    public @Getter @Setter String itemName = "Portable Enchanter";
    public @Getter @Setter Material itemMaterial = Material.NETHER_STAR;

    private @Getter ItemStack PortableEnchanter;

    @Override
    public void onLoad() {
        this.PortableEnchanter = new ItemStackBuilder(itemMaterial).displayName(itemName).build();
    }

    @Override
    public List<ItemStack> getItems() {
        return Arrays.asList(PortableEnchanter);
    }

    @EventHandler
    public void onSpecialistEnchant(PlayerInteractEvent event) {
        ItemStack inHand = event.getPlayer().getInventory().getItemInHand();
        if (inHand == null) {
            return;
        }
        if (!inHand.isSimilar(PortableEnchanter)) {
            return;
        }
        if(!this.hasAbility(event.getPlayer())) {
            return;
        }
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            player.openEnchanting(player.getLocation(), true);
        }
    }

    @EventHandler
    public void onSpecialistKill(CustomDamageEvent event) {
        Player attacker = event.getAttackerPlayer();
        if (!event.isAttackerIsPlayer() && !this.hasAbility(attacker)) {
            return;
        }
        if (!event.isVictimIsPlayer()) {
            return;
        }
        Player victim = event.getVictimPlayer();
        if (!(victim.getHealth() <= 1 && victim.getLastDamage() >= 1 )){
            return;
        }
        attacker.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE, 2));
        attacker.updateInventory();
    }
}
