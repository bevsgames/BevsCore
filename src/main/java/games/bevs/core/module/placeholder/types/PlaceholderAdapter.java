package games.bevs.core.module.placeholder.types;

import org.bukkit.entity.Player;

import games.bevs.core.module.placeholder.interfaces.IPlaceholder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlaceholderAdapter implements IPlaceholder
{
	private String name;

	@Override
	public String onReplace(Player player, String identifier)
	{
		return "";
	}

	@Override
	public String getName() {
		return this.name;
	}

}
