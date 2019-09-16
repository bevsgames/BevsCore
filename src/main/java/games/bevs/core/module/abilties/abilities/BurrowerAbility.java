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
import org.bukkit.Location;
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
        name = "Burrower",
        author = "teddyyy",
        description = {
                "Use your item to escape away",
                "creating your own personal room!"
        })

public class BurrowerAbility extends CooldownAbility {

    // Ability Settings
    private @Getter @Setter String itemName = CC.blue + "Burrower";
    private @Getter @Setter Material itemMaterial = Material.SLIME_BALL;
    //private @Getter @Setter ActionType actionType = ActionType.AIR;It suppose to work in every action rather than PHYSICAL
    private @Getter @Setter int underneathBlocksBy = 20;
    private @Getter @Setter int maxY = 30;

    // Class variables
    private @Getter ItemStack burrowerItem;

    @Override
    public void onLoad() {
        this.burrowerItem = new ItemStackBuilder(Material.SLIME_BALL).amount(5).displayName(itemName).build();

        this.initDefaultCooldown(15, TimeUnit.SECOND);
    }

    @Override
    public List<ItemStack> getItems() {
        return Arrays.asList(burrowerItem);
    }

    private void buildHut(Location preLoc) {
        for (int x = -2; x < 2; x++) {
            for (int y = -1; y < 3; y++) {
                for (int z = -2; z < 2; z++) {
                    Location loc = preLoc.clone().add(x, y, z);
                    loc.getBlock().setType(Material.BRICK);
                    if ((x == 0 && z == -1 || x == -1 && z == 0 || x == 0 && z == 0 || x == -1 && z == -1)
                            && (y == 0 || y == 1))
                        if (!(x == -1 && z == -1 && y == 0))
                            loc.getBlock().setType(Material.AIR);
                        else
                            loc.getBlock().setType(Material.TORCH);

                }
            }
        }
    }

    @EventHandler
    public void BurrowerAway(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (e.getItem() == null)
            return;

        if (!e.getItem().isSimilar(burrowerItem))
            return;

        if (!hasAbility(player))
            return;

        if (e.getAction() == Action.PHYSICAL)
            return;

        if (this.hasDefaultCooldownAndNotify(player))
            return;

        Location preLoc = player.getLocation();
        preLoc.setY(player.getLocation().getY() - underneathBlocksBy);

        if (preLoc.getY() <= maxY) {
            player.sendMessage(CC.red + "Your personal room can not be made under the Y of  " + maxY);
            return;
        }

        buildHut(preLoc);

        preLoc.setYaw(176.89682f);
        preLoc.setPitch(2.0f);
        player.teleport(preLoc);

        setDefaultCooldown(player);

        if (player.getItemInHand().getAmount() > 1)// You have 5 slime balls you using one will remove it
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        else
            player.setItemInHand(null);

    }

}
