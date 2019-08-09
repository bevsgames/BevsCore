package games.bevs.core.module.placeholder.interfaces;

import org.bukkit.entity.Player;

public interface IPlaceholder 
{
	public String getName();
	public String onReplace(Player player, String identifier);
}
