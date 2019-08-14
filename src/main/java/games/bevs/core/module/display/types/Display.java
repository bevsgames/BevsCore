package games.bevs.core.module.display.types;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

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

	public void close() 
	{
		HandlerList.unregisterAll(this);
		this.inventory.clear();
		this.player = null;
	}
}
