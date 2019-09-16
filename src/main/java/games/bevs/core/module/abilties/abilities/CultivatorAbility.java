package games.bevs.core.module.abilties.abilities;

import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Crops;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Cultivator",
        author = "Fundryi",
        description = {
                "TESTING"
        })

public class CultivatorAbility extends Ability {

    public @Getter @Setter Material itemMaterial = Material.SAPLING;

    @EventHandler
    public void onCultivatorSapling(BlockPlaceEvent event) {
        if (!this.hasAbility(event.getPlayer()))
            return;
        if (event.getBlock().getType() == Material.SAPLING) {
            event.getBlock().setType(Material.AIR);
            event.getPlayer().getWorld().generateTree(event.getBlock().getLocation(), TreeType.TREE);
        } else {
            event.getBlock().setData((byte) 7);
        }
    }

    @EventHandler
    public void onCultivatorCrops(PlayerInteractEvent event) {
        if (!this.hasAbility(event.getPlayer()))
            return;
        if (event.getClickedBlock() == null)
            return;
        Block block = event.getClickedBlock().getRelative(BlockFace.UP);
        if (block.getType() == Material.CROPS) {
            Crops crops = (Crops) block.getState();
            crops.setState(CropState.RIPE);
        }
    }
}
