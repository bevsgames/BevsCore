package games.bevs.core.module.display.types;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import games.bevs.core.commons.itemstack.ItemStackBuilder;
import lombok.Getter;

@Getter
public class Screen
{
	private String title;
	private int slots;
	private Display display;
	
	private HashMap<Integer, Clickable> clickables = new HashMap<>();
	
	public Screen(String title, int slots, Display display)
	{
		this.title = title;
		this.slots = slots;
		this.display = display;
	}
	
	public void setClickable(int slot, Clickable clickable)
	{
		this.clickables.put(slot, clickable);
	}
	
	public Clickable getClickable(int slot)
	{
		return this.getClickables().get(slot);
	}
	
	public void build(Inventory inventory, Player player)
	{
		this.clickables.forEach((slot, clickable) -> {
			ItemStackBuilder itemstack = clickable.getItemstack();
			inventory.setItem(slot, itemstack.build());
		});
	}
	
	public void onPreBuild(Player player) {}
	public void onPostBuild(Player player) {}
}
