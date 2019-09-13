package games.bevs.core.module.abilties.abilities;

import games.bevs.core.commons.itemstack.ItemStackBuilder;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Frosty",
        author = "Fundryi",
        description = {
                "TESTING"
        })

public class FrostyAbility extends Ability {

    public @Getter @Setter Material itemMaterial = Material.SNOW_BALL;

    private @Getter ItemStack StartSnowballs;
    private List<Snowball> balls = new ArrayList<Snowball>();

    @Override
    public void onLoad() {
        this.StartSnowballs = new ItemStackBuilder(itemMaterial).amount(8).build();
    }

    @Override
    public List<ItemStack> getItems() {
        return Arrays.asList(StartSnowballs);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        if (this.hasAbility(p) && p.getLocation().getBlock().getType() == Material.SNOW) {
            p.removePotionEffect(PotionEffectType.SPEED);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 1));
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Snowball) || !(event.getEntity().getShooter() instanceof Player))
            return;
        if (!this.hasAbility((Player) event.getEntity().getShooter()))
            return;
        BlockIterator iterator = new BlockIterator(event.getEntity().getWorld(),
                event.getEntity().getLocation().toVector(), event.getEntity().getVelocity().normalize(), 0.0D, 4);
        Block hitBlock = null;
        while (iterator.hasNext()) {
            hitBlock = iterator.next();
            if (hitBlock.getTypeId() != 0)
                break;
        }
        if (hitBlock.getType() == Material.LONG_GRASS)
            hitBlock.setType(Material.ICE);
        else if (hitBlock.getRelative(BlockFace.UP).getType() == Material.AIR)
            hitBlock.getRelative(BlockFace.UP).setType(Material.SNOW);
        Location l = hitBlock.getLocation();
        int range = 40;
        int minX = l.getBlockX() - range / 2;
        int minY = l.getBlockY() - range / 2;
        int minZ = l.getBlockZ() - range / 2;
        for (int x = minX; x < minX + range; x++)
            for (int y = minY; y < minY + range; y++)
                for (int z = minZ; z < minZ + range; z++) {
                    Block b = Bukkit.getWorld("world").getBlockAt(x, y, z);
                    if (b.getType() == Material.WATER)
                        b.setType(Material.ICE);
                }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.SNOW)
            return;
        if (!this.hasAbility(event.getPlayer()))
            return;
        event.getBlock().setType(Material.AIR);
        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5D, 0D, 0.5D),
                new ItemStack(Material.SNOW_BALL, 2));
    }
}
