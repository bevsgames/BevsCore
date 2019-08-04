package games.bevs.core.module.abilties.abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import games.bevs.core.CorePlugin;
import games.bevs.core.commons.itemstack.ItemStackBuilder;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * The ability to jump either upwards or forward
 */
@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(name = "Kangaroo", author = "teddyyy", description = { "Clicking your item while shifting",
		"will launch you forward", "without shifting it will launch", "you upwards." })
public class KangarooAbility extends Ability {

	private double upwardsForce = 1.2f;
	private double forwardForce = 1.32f;

	public ItemStack kangarooItem = new ItemStackBuilder(Material.FIREWORK).displayName(ChatColor.GOLD + "Kangaroo")
			.build();

	@Override
	public List<ItemStack> getItems() {
		return Arrays.asList(kangarooItem);
	}

	private final String KANGAROO = "KANGAROO";

	@EventHandler
	public void onKangaroo(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (hasAbility(player)) {
			if (player.getItemInHand() == null)
				return;
			if (player.getItemInHand() != kangarooItem)
				return;

			if (!player.isSneaking()) {
				Vector vector = player.getEyeLocation().getDirection();
				vector.multiply(0.0F);
				vector.setY(upwardsForce);
				player.setVelocity(vector);

			} else {
				Vector vector = player.getEyeLocation().getDirection();
				vector.multiply(forwardForce);
				vector.setY(0.8D);
				player.setVelocity(vector);
			}
			player.setFallDistance(-5.0F);
			player.setMetadata(KANGAROO, new FixedMetadataValue(CorePlugin.getInstance(), null));

		}
	}

	@EventHandler
	public void onKangarooMove(PlayerMoveEvent e) {
		Player player = ((PlayerMoveEvent) e).getPlayer();
		if (hasAbility(player)) {
			Block b = player.getLocation().getBlock();
			if ((b.getType() == Material.AIR) && (b.getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
				if (player.hasMetadata(KANGAROO)) {
					player.removeMetadata(KANGAROO, CorePlugin.getInstance());
				}
			}
		}
	}

	@EventHandler
	public void onKangarooFall(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player && hasAbility((Player) e.getEntity()) && e.getCause() == DamageCause.FALL
				&& e.getFinalDamage() > 7)
			e.setDamage(7.0);
	}

}
