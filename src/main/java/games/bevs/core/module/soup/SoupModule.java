package games.bevs.core.module.soup;

import java.util.WeakHashMap;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.commons.Duration;
import games.bevs.core.commons.utils.InventoryUtils;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import lombok.Getter;

/**
 * Soup will give the player the ability to 
 * drink it, to regain a bit of health
 */
@ModInfo(name = "Soup")
public class SoupModule extends Module
{
	private @Getter WeakHashMap<Player, Long> playerCooldown = new WeakHashMap<>();
	private @Getter SoupSettings soupSetting;

	public SoupModule(JavaPlugin plugin, SoupSettings soupSetting) 
	{
		super(plugin);
		
		this.soupSetting = soupSetting;
	}
	
	public SoupModule(JavaPlugin plugin) 
	{
		this(plugin, new SoupSettings());
	}
	
	
	@Override
	public void onEnable()
	{
		this.registerSelf();
	}
	
	private boolean applyHealth(Player player)
	{
		if(player.getHealth() >= player.getMaxHealth()) return false;
		if(player.getHealth() < 0) return false;//player is already dead
		
		double curHealth = player.getHealth();
		curHealth += this.getSoupSetting().getHealth();
		
		if(curHealth > player.getMaxHealth())
			curHealth = player.getMaxHealth();
		
		player.setHealth(curHealth);
		return true;
	}
	
	private void triggerDrinkables(Player player)
	{
		this.getSoupSetting().getDrinkEffects().forEach(drinkable -> drinkable.onDrink(player));
	}
	
	private void drinkSoup(Player player)
	{
		ItemStack item = player.getItemInHand();
		int amount = item.getAmount();
		//default, results in bowl
		ItemStack resultIn = this.getSoupSetting().getResultsIn();
		if(amount > 1)
		{
			item.setAmount(amount - 1);
			InventoryUtils.addItem(player.getInventory(), resultIn);
		}
		else
		{
			player.setItemInHand(resultIn);
		}
	}
	
	private void applyCooldown(Player player)
	{
		long now = System.currentTimeMillis();
		long cooldownMilli = this.getSoupSetting().getCooldownMillis();
		this.playerCooldown.put(player, now + cooldownMilli);
	}
	
	private long tillCooldownExpires(Player player)
	{
		long now = System.currentTimeMillis();
		long cooldownTill = this.playerCooldown.getOrDefault(player, 0L);
		return cooldownTill - now; 
	}
	
	private boolean hasCooldown(Player player)
	{
		return this.tillCooldownExpires(player) > 0; 
	}
	
	@EventHandler
	public void onSoup(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		Action action = e.getAction();
		if(player.getGameMode() == GameMode.CREATIVE) return;
		if(e.isCancelled()) return;
		if(!action.name().contains("RIGHT_CLICK_")) return;
		ItemStack item = player.getItemInHand();
		if(item == null) return;
		if(item.getType() != this.getSoupSetting().getMaterial()) return; //default, soup
		
		boolean cooldownsEnabled = this.getSoupSetting().isCooldown();
		if(cooldownsEnabled && this.hasCooldown(player))
		{
			Duration cooldownLength = new Duration(this.tillCooldownExpires(player));
			
			String msg = this.getSoupSetting().getCooldownMessage();
			msg = msg.replaceAll("{cooldown}", cooldownLength.getFormatedTime());
			player.sendMessage(msg);
		}
		
		boolean souped = applyHealth(player);
		
		if(souped)
		{
			//runs special code for speed and other things
			triggerDrinkables(player);
			//turns soup into just a bowl
			drinkSoup(player);
			
			if(cooldownsEnabled)
				applyCooldown(player);
			e.setCancelled(true);
		}
		
	}
}
