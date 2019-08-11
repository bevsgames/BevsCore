package games.bevs.core.module.abilties.abilities;

import java.util.ArrayList;
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
import org.bukkit.util.Vector;

import games.bevs.core.commons.ActionType;
import games.bevs.core.commons.CC;
import games.bevs.core.commons.Duration;
import games.bevs.core.commons.Duration.TimeUnit;
import games.bevs.core.commons.itemstack.ItemStackBuilder;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.CooldownAbility;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import games.bevs.core.module.ticker.UnitType;
import games.bevs.core.module.ticker.UpdateEvent;
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
	
	private @Getter @Setter boolean armor = true;
	private @Getter @Setter Color leatherArmorColor = Color.WHITE;
	private @Getter @Setter String leatherArmorName = CC.bWhite + "Phantom Armor";
	
		//Alerts
	private @Getter @Setter boolean showPhantomAlertMessage = true;
	private @Getter @Setter double phantomAlertDistance = 35;
	private @Getter @Setter List<String> phantomAlertMessages = Arrays.asList(new String[] { CC.bWhite + "A phantom approaches...", CC.bWhite + "Note: they are not fly hacking, it's part of the kit." });
	
		// 
	private @Getter @Setter boolean alertRemainTime = true;
	private @Getter @Setter int startAlertRemainTimeAt = 3;

	// Class variables
	public static final String PHANTOM_COOLDOWN = "ability.phantom";
	public static final String PHANTOM_FLY = "ability.phantom.length";
	private static final String FALL_META = "double_falldamage";
	
	private static final HashMap<UUID, ItemStack[]> BACKUP_ARMOUR = new HashMap<UUID, ItemStack[]>();// relog for uuids

	private @Getter ItemStack[] phantomArmor;//set on load
	private @Getter ItemStack phantomItem;
	private @Getter double alertDistanceSquarted;//Set on load
	private @Getter List<Material> armorTypes = Arrays.asList(new Material[] { Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET, Material.LEATHER_BOOTS});
	private @Getter List<UUID> phantomedPlayers = new ArrayList<>();
	
	private ItemStack[] generateArmor()
	{
		ArrayList<ItemStack> armorContent = new ArrayList<>();
		for(Material armorMaterial : this.getArmorTypes())
		{
			ItemStackBuilder armorItemBuilder = new ItemStackBuilder(armorMaterial)
													.setLeatherColour(this.getLeatherArmorColor())
													.displayName(this.getLeatherArmorName())
													.durabilityLeft(1);
			armorContent.add(armorItemBuilder.build());
		}

		return armorContent.toArray(new ItemStack[0]);
	}
	
	@Override
	public void onLoad() {
		this.initCooldown(PHANTOM_COOLDOWN, 45, TimeUnit.SECOND);
		this.initCooldown(PHANTOM_FLY, 10, TimeUnit.SECOND);

		this.phantomItem = new ItemStackBuilder(itemMaterial).displayName(itemName).build();
		this.phantomArmor = generateArmor();
		
		this.alertDistanceSquarted = Math.pow(this.getPhantomAlertDistance(), this.getPhantomAlertDistance()) ;
	}

	@Override
	public List<ItemStack> getItems() 
	{
		return Arrays.asList(phantomItem);
	}
	
	/**
	 * Applys white leather armor to the player
	 * @param player
	 */
	private void equiptPhotomArmor(Player player)
	{
		player.getInventory().setArmorContents(this.getPhantomArmor());
	}
	
	private void alertPlayerTheyPhantomed(Player player)
	{
		player.getWorld().playSound(player.getLocation(), Sound.WITHER_DEATH, 1, 1);
	}
	
	private void alertOtherPlayers(Player player)
	{
		player.getWorld().getPlayers().stream()
									  .filter(other -> player.getLocation().distanceSquared(other.getLocation())
											  										<= this.getAlertDistanceSquarted())
									  .forEach(other -> 
									  {
										  this.getPhantomAlertMessages().forEach(message -> other.sendMessage(message));
										  
									  });
	}
	
	private void startPhantom(Player player)
	{
		if(this.isArmor())
		{
			BACKUP_ARMOUR.put(player.getUniqueId(), player.getInventory().getArmorContents());
			equiptPhotomArmor(player);
		}

		this.getPhantomedPlayers().add(player.getUniqueId());
		player.setVelocity(new Vector(0.0, 0.5, 0.0));
		player.setAllowFlight(true);
		
		
		this.alertOtherPlayers(player);
		this.alertPlayerTheyPhantomed(player);
		
		this.setCooldown(player, PHANTOM_FLY);
	}
	
	private void endPhantom(Player player)
	{
		UUID uniqueId = player.getUniqueId();
		//restore armor
		if(this.isArmor())
		{
			player.getInventory().setArmorContents(BACKUP_ARMOUR.get(uniqueId));
			BACKUP_ARMOUR.remove(uniqueId);
		}
		
		player.setAllowFlight(false);
		player.setFlying(false);
		player.getWorld().playSound(player.getLocation(), Sound.WITHER_SPAWN, 1, 1);
		this.getPhantomedPlayers().remove(uniqueId);
	}

	@EventHandler
	public void onPhantomInteract(PlayerInteractEvent e) {

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

		this.startPhantom(player);
	}

	/**
	 * Disable removing the armor while in phantom
	 * @param e
	 */
	@EventHandler
	public void disableInventoryClick(InventoryClickEvent e) {
		if(!this.isArmor())
			return;
		
		if (e.getWhoClicked() instanceof Player && BACKUP_ARMOUR.containsKey(e.getWhoClicked().getUniqueId())) {
			if (e.getRawSlot() == 5 || e.getRawSlot() == 6 || e.getRawSlot() == 7 || e.getRawSlot() == 8)// Armour slots
				e.setCancelled(true);
		}
	}

	/**
	 * Drops their armor before the phantoming ability was used
	 * @param e
	 */
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player player = e.getEntity();
		UUID uniqueId = player.getUniqueId();
		
		if(this.isArmor())
		{
			if (BACKUP_ARMOUR.containsKey(player.getUniqueId())) 
			{
				for(ItemStack phantomArmorItem : this.getPhantomArmor())
					e.getDrops().remove(phantomArmorItem);
				
				e.getDrops().addAll(Arrays.asList(BACKUP_ARMOUR.get(uniqueId)));
				BACKUP_ARMOUR.remove(uniqueId);
			}
		}

	}

	/**
	 * If they relog, we take away their stuff
	 * @param e
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) 
	{
		Player player = e.getPlayer();
		UUID uniqueId = player.getUniqueId();
		if (this.getPhantomedPlayers().contains(uniqueId))
			endPhantom(player);
	}

	@EventHandler
	public void onPlayerDamage(CustomDamageEvent e) {

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
	
	
	@EventHandler
	public void onSeconds(UpdateEvent e)
	{
		if(e.getType() != UnitType.SECOND)
			return;
		
		this.getPhantomedPlayers().forEach(uniqueId ->
		{
			Player player = Bukkit.getPlayer(uniqueId);
			if(player == null) return;
			
			if(this.hasCooldown(player, PHANTOM_FLY))
			{
				Duration flightLeft = this.getCooldown(player, PHANTOM_FLY);
				double secondsLeft = flightLeft.getAsUit(TimeUnit.SECOND);
				if(secondsLeft <= (this.getStartAlertRemainTimeAt() + 1))
				{
					String timeFormat = flightLeft.getFormatedTime();
					player.sendMessage(CC.red + timeFormat + " of flight remaining!");
				}
			} 
			else
			{
				this.endPhantom(player);
			}
		});
	}
	

}
