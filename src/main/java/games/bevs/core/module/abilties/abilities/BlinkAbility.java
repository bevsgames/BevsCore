package games.bevs.core.module.abilties.abilities;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import games.bevs.core.commons.ActionType;
import games.bevs.core.commons.Duration.TimeUnit;
import games.bevs.core.commons.itemstack.ItemStackBuilder;
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
public class BlinkAbility extends CooldownAbility {
	// Ability Settings
	private @Getter @Setter String itemName = ChatColor.WHITE + "Blink";
	private @Getter @Setter Material itemMaterial = Material.NETHER_STAR;
	private @Getter @Setter ActionType actionType = ActionType.AIR;
	private @Getter @Setter int maxUses = 5;
	private @Getter @Setter int multipleBlocksBy = 7;

	// Class variables
	public static final String BLINK_COOLDOWN = "ability.blink";
	private @Getter ItemStack blinkItem;

	private HashMap<Player, Integer> USES = new HashMap<Player, Integer>();

	
	@Override
	public void onLoad() {
		this.initCooldown(BLINK_COOLDOWN, 45, TimeUnit.SECOND);

		this.blinkItem = new ItemStackBuilder(itemMaterial).displayName(itemName).build();
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

		if(this.hasCooldownAndNotify(player, BLINK_COOLDOWN))
			return;
		
		if (!USES.containsKey(player))
			USES.put(player, 1);

		if (USES.containsKey(player)) {
			Location destination = player.getTargetBlock(((HashSet<Byte>) null), multipleBlocksBy).getLocation();
			destination.setYaw(player.getLocation().getYaw());
			destination.setPitch(player.getLocation().getPitch());
			if (destination.getBlock().getType() == Material.AIR) {
				destination.getBlock().setType(Material.LEAVES);
				new BukkitRunnable() {

					@Override
					public void run() {
						destination.getBlock().setType(Material.AIR);
					}
				}.runTaskLater(this.getPlugin(), 20 * 7);
				player.teleport(destination.add(0, 1, 0));
				destination.getWorld().playSound(destination, Sound.FIREWORK_LAUNCH, 1f, 2f);
			}
			USES.put(player, USES.get(player) + 1);
		}

		if (USES.containsKey(player) && USES.get(player) > maxUses + 1) {
			USES.remove(player);
			this.setCooldown(player, BLINK_COOLDOWN);
			return;
		}

	}

}
