package games.bevs.core.module.placeholder;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import games.bevs.core.BevsPlugin;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.placeholder.interfaces.IPlaceholder;

@ModInfo(name = "Placeholder")
public class PlaceholderModule extends Module {
//	private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("[%]([^%]+)[%]");
//	private static final Pattern BRACKET_PLACEHOLDER_PATTERN = Pattern.compile("[{]([^{}]+)[}]");
//	private static final Pattern RELATIONAL_PLACEHOLDER_PATTERN = Pattern.compile("[%](rel_)([^%]+)[%]");
	private  Map<String, IPlaceholder> placeholders = new HashMap<>();
	
	public PlaceholderModule(BevsPlugin plugin) {
		super(plugin);
	}
	
	public void addPlaceholder(IPlaceholder placeholder)
	{
		this.placeholders.put(placeholder.getName(), placeholder);
	}

	public String replacePlaceholders(String message, Player player)
	{
		return message.replaceAll("", "");
	}
}
