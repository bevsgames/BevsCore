package games.bevs.core.module.kit.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import games.bevs.core.module.kit.event.PostApplyKitEvent;
import games.bevs.core.module.kit.event.PreApplyKitEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Kit implements Listener
{
	private String name;
	@Setter
	private String displayName;
	private ItemStack icon;
	@Setter
	private double price;
	
	private HashMap<Integer, ItemStack> items = new HashMap<>();
	private List<PotionEffect> effects = new ArrayList<>();
	private ItemStack[] armors = new ItemStack[4]; 
	
	public Kit(String displayName)
	{
		this.name = displayName.toLowerCase();
		this.displayName = displayName;
	}
	
	public void setItem(int slot, ItemStack item)
	{
		this.items.put(slot, item);
	}
	
	public ItemStack getItem(int slot)
	{
		return this.items.get(slot);
	}
	
	private int getFirstEmptySlot()
	{
		for(int i = 0; i < 36; i++)
		{
			ItemStack item = this.getItem(i);
			if(item == null) return i;
		}
		return -1;
	}
	
	public void addItem(ItemStack item)
	{
		int slot = this.getFirstEmptySlot();
		this.setItem(slot, item);
	}
	
	public void setArmor(ArmorType type, ItemStack item)
	{
		this.armors[type.getId()] = item;
	}
	
	public ItemStack getArmor(ArmorType type)
	{
		return this.armors[type.getId()];
	}
	
	public void apply(Player player) 
	{
		PreApplyKitEvent preApplyEvent = new PreApplyKitEvent(player, this);
		preApplyEvent.call();
		if(preApplyEvent.isCancelled()) return;
		
		PlayerInventory inv = player.getInventory();
		this.getItems().forEach((slot, item) ->
		{
			inv.setItem(slot.intValue(), item);
		    	
			this.getEffects().forEach(potionEffect ->
				player.addPotionEffect(potionEffect)
			);
		});
		
		inv.setArmorContents(this.getArmors());
		
		PostApplyKitEvent postApplyEvent = new PostApplyKitEvent(player, this);
		postApplyEvent.call();
	}
	
	public enum ArmorType
	{
		HELMET, CHESTPLATE, LEGGINGS, BOOT;
		
		public int getId()
		{
			return this.ordinal();
		}
	}
}
