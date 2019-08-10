package games.bevs.core.module.abilties.abilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import games.bevs.core.commons.ActionType;
import games.bevs.core.commons.CC;
import games.bevs.core.commons.Duration.TimeUnit;
import games.bevs.core.commons.itemstack.ItemStackBuilder;
import games.bevs.core.commons.utils.PluginUtils;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.CooldownAbility;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(name = "Phantom", author = "teddyyy", description = { "Click your item to have 5 seconds of flight." })
public class PhantomAbility extends CooldownAbility {
	// Ability Settings
	private @Getter @Setter String itemName = CC.white + "Phantom";
	private @Getter @Setter Material itemMaterial = Material.FEATHER;
	private @Getter @Setter ActionType actionType = ActionType.AIR;
	private @Getter @Setter double MESSAGE_RADIUS = 35;
	
	// Class variables
	public static final String PHANTOM_COOLDOWN = "ability.phantom";
	private static final HashMap<UUID, ItemStack[]> BACKUP_ARMOUR = new HashMap<UUID, ItemStack[]>();// relog for uuids
	private static final String FALL_META = "double_falldamage";
	private @Getter ItemStack phantomItem;
	private HashMap<Player, Integer> timer;
	private HashMap<Player, Integer> runnableID;

	@Override
	public void onLoad() {
		this.initCooldown(PHANTOM_COOLDOWN, 45, TimeUnit.SECOND);

		this.phantomItem = new ItemStackBuilder(itemMaterial).displayName(itemName).build();

		this.timer = new HashMap<Player, Integer>();
		this.runnableID = new HashMap<Player, Integer>();
	}

	@Override
	public List<ItemStack> getItems() {
		return Arrays.asList(phantomItem);
	}

	@EventHandler
	public void PhantomInteract(PlayerInteractEvent e) {

		Player player = e.getPlayer();

		if (e.getItem() == null)
			return;

		if (!e.getItem().isSimilar(phantomItem))
			return;

		if (!actionType.containsAction(e.getAction()))
			return;

		if (!hasAbility(player))
			return;

		if (hasCooldownAndNotify(player, PHANTOM_COOLDOWN))
			return;

		BACKUP_ARMOUR.put(player.getUniqueId(), player.getInventory().getArmorContents());
		player.setVelocity(new Vector(0.0, 0.5, 0.0));
		player.setAllowFlight(true);

		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);

		LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
		LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
		LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
		LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();

		helmetMeta.setColor(Color.WHITE);
		chestplateMeta.setColor(Color.WHITE);
		leggingsMeta.setColor(Color.WHITE);
		bootsMeta.setColor(Color.WHITE);

		helmet.setItemMeta(helmetMeta);
		chestplate.setItemMeta(chestplateMeta);
		leggings.setItemMeta(leggingsMeta);
		boots.setItemMeta(bootsMeta);

		helmet.setDurability((short) 55);
		chestplate.setDurability((short) 80);
		leggings.setDurability((short) 75);
		boots.setDurability((short) 65);

		player.getInventory().setHelmet(helmet);
		player.getInventory().setChestplate(chestplate);
		player.getInventory().setLeggings(leggings);
		player.getInventory().setBoots(boots);
		player.updateInventory();
		player.getWorld().playSound(player.getLocation(), Sound.WITHER_DEATH, 1, 1);
		player.getWorld().getPlayers().forEach(target -> {
			if (target.getLocation().distanceSquared(player.getLocation()) <= (MESSAGE_RADIUS * MESSAGE_RADIUS)) {
				target.sendMessage(CC.bWhite + "A phantom approaches...");
				target.sendMessage(CC.bWhite + "Note: they are not fly hacking, it's part of the kit.");
			}
		});

		timer.put(player, 5);
		int task = PluginUtils.repeat(this.getPlugin(), () -> {// i actually hate this.

			if (timer.containsKey(player)) {
				if (timer.get(player) <= 3)
					player.sendMessage(CC.red + timer.get(player) + " seconds of flight remaining!");

				if (timer.get(player) <= 1 && !player.isDead()) {
					player.getInventory().setArmorContents(BACKUP_ARMOUR.get(player.getUniqueId()));
					BACKUP_ARMOUR.remove(player.getUniqueId());
					player.setMetadata(FALL_META, new FixedMetadataValue(this.getPlugin(), true));
					PluginUtils.later(this.getPlugin(), () -> player.removeMetadata(FALL_META, this.getPlugin()),
							20 * 5);

				}
				if (timer.get(player) <= 1 || player.isDead()) {
					player.setAllowFlight(false);
					player.setFlying(false);
					player.getWorld().playSound(player.getLocation(), Sound.WITHER_SPAWN, 1, 1);
					Bukkit.getScheduler().cancelTask(runnableID.get(player));
					// we now should clean the list from the player.
					timer.remove(player);
					runnableID.remove(player);
				}
				if(timer.containsKey(player))//Just happened to throw an error.
				timer.put(player, timer.get(player) - 1);

			}

		}, 0L, 20L);
		runnableID.put(player, task);
		setCooldown(player, PHANTOM_COOLDOWN);

	}

	@EventHandler
	public void disableInventoryClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player && BACKUP_ARMOUR.containsKey(e.getWhoClicked().getUniqueId())) {
			if (e.getRawSlot() == 5 || e.getRawSlot() == 6 || e.getRawSlot() == 7 || e.getRawSlot() == 8)// Armour slots
				e.setCancelled(true);
		}
	}

	@EventHandler
	public void PlayerDeath(PlayerDeathEvent e) {
		Player player = e.getEntity();
		if (BACKUP_ARMOUR.containsKey(player.getUniqueId())) {
			e.getDrops().addAll(Arrays.asList(BACKUP_ARMOUR.get(player.getUniqueId())));
			BACKUP_ARMOUR.remove(player.getUniqueId());
		}

	}

	@EventHandler
	public void PlayerJoinxD(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (BACKUP_ARMOUR.containsKey(player.getUniqueId())) {
			player.getInventory().setArmorContents(BACKUP_ARMOUR.get(player.getUniqueId()));
			BACKUP_ARMOUR.remove(player.getUniqueId());
		}
	}

	@EventHandler
	public void PlayerDamage(CustomDamageEvent e) {

		if (!e.isVictimIsPlayer())
			return;

		if (e.getInitCause() != DamageCause.FALL)
			return;

		Player player = e.getVictimPlayer();

		if (player.hasMetadata(FALL_META)) {
			e.setInitDamage(e.getDamage() * 2.0);
			player.removeMetadata(FALL_META, this.getPlugin());
		}
	}

}
