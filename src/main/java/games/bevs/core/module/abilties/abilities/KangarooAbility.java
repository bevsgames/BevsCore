package games.bevs.core.module.abilties.abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import games.bevs.core.commons.ActionType;
import games.bevs.core.commons.itemstack.ItemStackBuilder;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * The ability to jump either upwards or forward
 * 
 * @owner BevsGames
 */
@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(name = "Kangaroo", author = "teddyyy", description = { "Clicking your item while shifting",
		"will launch you forward", "without shifting it will launch", "you upwards." })
public class KangarooAbility extends Ability {
	// Ability Settings
	private double upwardsForce = 1.2f;
	private double forwardForce = 1.32f;
	private Material itemMaterial = Material.FIREWORK;
	private String itemName = ChatColor.GOLD + "Kangaroo";
	private boolean allActions = true;
	private ActionType actionType = ActionType.BLOCK;

	// Class variables
	private final String KANGAROO_METADATA = "KANGAROO";
	public ItemStack kangarooItem;

	@Override
	public void onLoad() {
		this.kangarooItem = new ItemStackBuilder(itemMaterial).displayName(itemName).build();
	}

	@Override
	public List<ItemStack> getItems() {
		return Arrays.asList(kangarooItem);
	}

	@EventHandler
	public void onKangaroo(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (hasAbility(player)) {
			if (player.getItemInHand() == null)
				return;
			if (!player.getItemInHand().isSimilar(kangarooItem))
				return;
			if (!player.hasMetadata(KANGAROO_METADATA)) {
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
			}

			e.setCancelled(true);// we don't want the player to launch the fire work :P
			player.setFallDistance(-5.0F);
			player.setMetadata(KANGAROO_METADATA, new FixedMetadataValue(this.getPlugin(), null));

			Action action = e.getAction();

			if (!hasAbility(player))
				return;

			if (e.getItem() == null)
				return;

			if (!e.getItem().isSimilar(kangarooItem))
				return;

			if (!allActions) {
				if (!this.actionType.containsAction(action))
					return;
			} else if (action == Action.PHYSICAL)
				return;

			if (player.hasMetadata(KANGAROO_METADATA)) {
				e.setCancelled(true);
				return;
			}

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
			player.setMetadata(KANGAROO_METADATA, new FixedMetadataValue(this.getPlugin(), null));
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onKangarooMove(PlayerMoveEvent e) {
		Player player = ((PlayerMoveEvent) e).getPlayer();
		if (hasAbility(player)) {
			Block b = player.getLocation().getBlock();
			if ((b.getType() == Material.AIR) && (b.getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
				if (player.hasMetadata(KANGAROO_METADATA)) {
					player.removeMetadata(KANGAROO_METADATA, this.getPlugin());
				}
			}
		}
	}
}
