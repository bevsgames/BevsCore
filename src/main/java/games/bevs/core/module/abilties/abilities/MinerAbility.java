package games.bevs.core.module.abilties.abilities;

import games.bevs.core.commons.itemstack.ItemStackBuilder;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Miner",
        author = "Fundryi",
        description = {
                "Mining is fun for you!",
                "You can Veinmine any kind of ORE!",
                "Your Pickaxe is quite fast..."
        })

public class MinerAbility extends Ability {

    public @Getter @Setter String itemName = "The Drill";
    public @Getter @Setter Material itemMaterial = Material.STONE_PICKAXE;

    private @Getter ItemStack Drill;

    @Override
    public void onLoad() {
        this.Drill = new ItemStackBuilder(itemMaterial).displayName(itemName).enchantment(Enchantment.DIG_SPEED, 5).build();
    }

    @Override
    public List<ItemStack> getItems() {
        return Arrays.asList(Drill);
    }

    @EventHandler
    public void onMinerBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }
        if (!this.hasAbility(player)){
            return;
        }
        Material getBlock = (event).getBlock().getType();
        if (!(getBlock == Material.COAL_ORE
                || getBlock == Material.DIAMOND_ORE
                || getBlock == Material.EMERALD_ORE
                || getBlock == Material.GLOWING_REDSTONE_ORE
                || getBlock == Material.GOLD_ORE
                || getBlock == Material.IRON_ORE
                || getBlock == Material.LAPIS_ORE
                || getBlock == Material.QUARTZ_ORE
                || getBlock == Material.REDSTONE_ORE)) {
            return;
        }
        Block block = event.getBlock();
        destroySuroundingBlocks(block);
    }

    private void destroySuroundingBlocks(Block block) {
        Material material = block.getType();
        block.breakNaturally();

        for (BlockFace face : BlockFace.values()) {
            if (face == BlockFace.SELF)
                continue;

            Block adjacentBlock = block.getRelative(face);
            if (adjacentBlock.getType() == material)
                destroySuroundingBlocks(adjacentBlock);
        }
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        if(!(this.hasAbility(event.getPlayer())))
            return;
        if (event.getItem().isSimilar(Drill)) {
            event.setCancelled(true);
        }
    }
}
