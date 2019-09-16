package games.bevs.core.module.abilties.abilities;

import games.bevs.core.commons.ActionType;
import games.bevs.core.commons.CC;
import games.bevs.core.commons.Duration.TimeUnit;
import games.bevs.core.commons.itemstack.ItemStackBuilder;
import games.bevs.core.commons.utils.PluginUtils;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.CooldownAbility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Blink",
        author = "teddyyy",
        description = {
                "Use your item to blink away",
                "from dangerous situations!"
        })

public class BlinkAbility extends CooldownAbility {

    // Class variables
    public static final String BLINK_COOLDOWN = "ability.blink";
    // Ability Settings
    private @Getter @Setter String itemName = CC.white + "Blink";
    private @Getter @Setter Material itemMaterial = Material.NETHER_STAR;
    private @Getter @Setter ActionType actionType = ActionType.AIR;
    private @Getter @Setter int maxUses = 5;
    private @Getter @Setter int multipleBlocksBy = 7;
    private @Getter ItemStack blinkItem;
    private HashMap<Player, Integer> itemUses;

    @Override
    public void onLoad() {
        this.initCooldown(BLINK_COOLDOWN, 45, TimeUnit.SECOND);

        this.blinkItem = new ItemStackBuilder(itemMaterial).displayName(itemName).build();

        this.itemUses = new HashMap<Player, Integer>();
    }

    @Override
    public List<ItemStack> getItems() {
        return Arrays.asList(blinkItem);
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (e.getItem() == null)
            return;

        if (!e.getItem().isSimilar(blinkItem))
            return;

        if (!hasAbility(player))
            return;

        if (!actionType.containsAction(e.getAction()))
            return;

        if (this.hasCooldownAndNotify(player, BLINK_COOLDOWN))
            return;

        int uses = (itemUses.containsKey(player) ? itemUses.get(player) : 0) + 1;

        Location destination = player.getTargetBlock(((HashSet<Byte>) null), multipleBlocksBy).getLocation();
        destination.setYaw(player.getLocation().getYaw());
        destination.setPitch(player.getLocation().getPitch());

        // We have blinked 5 times, so we take away the item
        if (uses >= maxUses) {// I moved this up, because, when you blink successfully it will return the
            // method stopping from continuing, in this case we can not accesses to this
            // code, until we move it up or remove the return
            itemUses.remove(player);
            this.setCooldown(player, BLINK_COOLDOWN);
        }

        if (destination.getBlock().getType() == Material.AIR) {
            destination.getBlock().setType(Material.LEAVES);
            PluginUtils.later(this.getPlugin(), () -> destination.getBlock().setType(Material.AIR), 20 * 7);
            player.teleport(destination.add(0, 1, 0));
            destination.getWorld().playSound(destination, Sound.FIREWORK_LAUNCH, 1f, 2f);
            // Can't blink into the ground
            itemUses.put(player, uses);// we do not want to count the uses if they can't go underground
            return;
        }

    }
}