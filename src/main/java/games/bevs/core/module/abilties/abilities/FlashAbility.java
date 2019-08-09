package games.bevs.core.module.abilties.abilities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

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

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(name = "Flash", author = "teddyyy", description = { "Click your item to teleport",
		"where you are looking at!" })
public class FlashAbility extends CooldownAbility {
	// Ability Settings
	private @Getter @Setter String itemName = CC.green + "Flash";
	private @Getter @Setter Material itemMaterial = Material.REDSTONE_TORCH_ON;
	private @Getter @Setter ActionType actionType = ActionType.AIR;
	private @Getter @Setter int maxBlocks = 100;

	// Class variables
	public static final String FLASH_COOLDOWN = "ability.flash";
	private @Getter ItemStack flashItem;

	@Override
	public void onLoad() {
		this.initCooldown(FLASH_COOLDOWN, 70, TimeUnit.SECOND);

		this.flashItem = new ItemStackBuilder(itemMaterial).displayName(itemName).build();
	}

	@Override
	public List<ItemStack> getItems() {
		return Arrays.asList(flashItem);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();

		if (e.getItem() == null)
			return;

		if (!e.getItem().isSimilar(flashItem))
			return;

		if (!hasAbility(player))
			return;

		if (!actionType.containsAction(e.getAction()))
			return;

		if (this.hasCooldownAndNotify(player, FLASH_COOLDOWN))
			return;

		Block targetBlock = player.getTargetBlock((HashSet<Byte>) null, maxBlocks);
		Location targetLoc = targetBlock.getLocation();
		if (targetBlock == null || targetBlock.getType() == Material.AIR) {
			player.sendMessage(CC.red + "Too far away! You can only flash up to " + maxBlocks + " blocks away.");
			return;
		}
		targetLoc.setYaw(player.getLocation().getYaw());
		targetLoc.setPitch(player.getLocation().getPitch());
		targetLoc.setY(targetLoc.getY() + 1.5);

		// doing mcpvp's effect
		Vector blockVector = player.getLocation().toVector().subtract(targetBlock.getLocation().toVector());
		double blockDistance = player.getLocation().distance(targetBlock.getLocation());
		for (int i = 0; i < blockDistance; i++) {
			final Location loc = player.getLocation().clone().subtract((blockVector.getX() / blockDistance) * i,
					(blockVector.getY() / blockDistance) * i, (blockVector.getZ() / blockDistance) * i);
			PluginUtils.later(this.getPlugin(), () -> {
				for(int x = 0; x < 5; x ++)
				loc.getWorld().playEffect(loc, Effect.ENDER_SIGNAL, x);
			}, i * 2);
		}
		targetLoc.getWorld().strikeLightningEffect(targetLoc.add(0, 1.5, 0));
		player.teleport(targetLoc);
		this.setCooldown(player, FLASH_COOLDOWN);

	}

}
