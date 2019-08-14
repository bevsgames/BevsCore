package games.bevs.core.module.display.types;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import games.bevs.core.commons.inventory.InventoryTitleHelper;
import lombok.Getter;

@Getter
public class Display implements Listener 
{
	private String idName;// The real name of the inventory
	private Inventory inventory;
	private Screen currentScreen;
	private Player player;

	public Display(String idName, Player player) {
		this.idName = idName;
		this.inventory = Bukkit.createInventory(player, 54, idName);
		this.player = player;
	}
	
	public Screen getScreen()
	{
		return this.getCurrentScreen();
	}
	
	public void setScreen(Screen screen, Player player)
	{
		this.currentScreen = screen;
		this.update(player);
	}
	
	public void update(Player player)
	{
		this.getScreen().onPreBuild(player);
		this.getScreen().build(this.getInventory(), player);
		this.getScreen().onPostBuild(player);
		InventoryTitleHelper.sendInventoryTitle(player, this.getScreen().getTitle());
	}
	
	public void open(Player player) 
	{
		player.openInventory(this.inventory);
		this.update(player);
	} 
	
	public void open() 
	{ 
		this.open(this.player); 
		this.update(this.player);
	}
	
	@EventHandler
	public void onInventory(InventoryClickEvent e)
	{
		Player player = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		int slot = e.getSlot();
		if(inv == null) return;
		if(this.inventory == null) return;
		if(slot == -1) return;
		if(!inv.getTitle().equals(this.inventory.getTitle())) return;
		Clickable clickable = this.getScreen().getClickable(slot);
		if(clickable == null) return;
		boolean right = e.getAction().name().contains("PICKUP");
		boolean left = e.getAction().name().contains("PLACE");
		ClickLog clicklog = new ClickLog(player, left, right, e.isShiftClick(), e.getCurrentItem(), slot, this);
		clicklog.setCancelled(e.isCancelled());
		clickable.getClickAction().done(clicklog);
		e.setCancelled(clicklog.isCancelled());
	}

	public void close() 
	{
		HandlerList.unregisterAll(this);
		this.inventory.clear();
		this.player = null;
	}
}
