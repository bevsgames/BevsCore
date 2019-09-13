package games.bevs.core.module.abilties.abilities;

import games.bevs.core.commons.CC;
import games.bevs.core.commons.Duration.TimeUnit;
import games.bevs.core.commons.itemstack.ItemStackBuilder;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.CooldownAbility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Monk",
        author = "Fundryi",
        description = {
                "TESTING"
        })

public class MonkAbility extends CooldownAbility {

    // Class variables
    public static final String MONK_COOLDOWN = "ability.monk";
    // Ability Settings
    private @Getter @Setter String itemName = "Annoying Wand";
    private @Getter @Setter Material itemMaterial = Material.BLAZE_ROD;
    private @Getter ItemStack MonkItem;

    @Override
    public void onLoad() {
        this.initCooldown(MONK_COOLDOWN, 60, TimeUnit.SECOND);

        this.MonkItem = new ItemStackBuilder(itemMaterial).displayName(itemName).build();
    }

    @Override
    public List<ItemStack> getItems() {
        return Arrays.asList(MonkItem);
    }

    @EventHandler
    public void onInteractMonk(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (!this.hasAbility(player)
                || player.getItemInHand() == null
                || player.getItemInHand().getType() != Material.BLAZE_ROD
                || !(event.getRightClicked() instanceof Player))
            return;
        Player victim = (Player) event.getRightClicked();
        if (this.hasCooldownAndNotify(player, MONK_COOLDOWN)) {
            return;
        }
        Inventory inv = victim.getInventory();
        int slot = ThreadLocalRandom.current().nextInt(36);
        ItemStack randomItem = inv.getItem(slot), holdItem = victim.getItemInHand();
        victim.setItemInHand(randomItem);
        inv.setItem(slot, holdItem);
        player.sendMessage(CC.yellow + "You Monked " + CC.red + victim.getPlayer().getName());
        victim.sendMessage(CC.yellow + "You got Monked by " + CC.red + player.getPlayer().getName());
        this.setCooldown(player, MONK_COOLDOWN);
    }
}