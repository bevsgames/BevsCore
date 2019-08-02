package games.bevs.core.module.placeholder.types;

import org.bukkit.entity.Player;

import games.bevs.core.module.placeholder.interfaces.IPlaceholder;

public class PlaceholderAdapter implements IPlaceholder
{

	@Override
	public String onReplace(Player player, String identifier)
	{
		return "";
	}

}
