package games.bevs.core.module.abilties.abilities;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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
@AbilityInfo(name = "Blink", author = "teddyyy", description = { "Use your item to blink away",
		"from dangerous situations!" })
public class BlinkAbility extends CooldownAbility 
{
	// Ability Settings
	private @Getter @Setter String itemName = CC.white + "Blink";
	private @Getter @Setter Material itemMaterial = Material.NETHER_STAR;
	private @Getter @Setter ActionType actionType = ActionType.AIR;
	private @Getter @Setter int maxUses = 5;
	private @Getter @Setter int multipleBlocksBy = 7;

	// Class variables
	private @Getter ItemStack blinkItem;
	private HashMap<Player, Integer> itemUses;

	@Override
	public void onLoad() {
		this.initDefaultCooldown(45, TimeUnit.SECOND);

		this.blinkItem = new ItemStackBuilder(itemMaterial).displayName(itemName).build();
		
		this.itemUses = new HashMap<Player, Integer>();
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

		if(this.hasDefaultCooldownAndNotify(player))
			return;
		
		int numUses = itemUses.getOrDefault(player, 0) + 1;
	
		Location destination = player.getTargetBlock(((HashSet<Byte>) null), multipleBlocksBy).getLocation();
		destination.setYaw(player.getLocation().getYaw());
		destination.setPitch(player.getLocation().getPitch());
		
		if (destination.getBlock().getType() == Material.AIR) 
		{
			destination.getBlock().setType(Material.LEAVES);
			PluginUtils.later(this.getPlugin(), () -> destination.getBlock().setType(Material.AIR),  20 * 7);
			player.teleport(destination.add(0, 1, 0));
			destination.getWorld().playSound(destination, Sound.FIREWORK_LAUNCH, 1f, 2f);
			//Can't blink into the ground
			return;
		}
		
		itemUses.put(player, numUses);
		
		//We have blinked 5 times, so we take away the item
		if (numUses > maxUses) {
			itemUses.remove(player);
			this.setDefaultCooldown(player);
			return;
		}

	}

}