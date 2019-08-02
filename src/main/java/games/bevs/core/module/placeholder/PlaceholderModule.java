package games.bevs.core.module.placeholder;

import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;

@ModInfo(name = "Placeholder")
public class PlaceholderModule extends Module {
//	private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("[%]([^%]+)[%]");
//	private static final Pattern BRACKET_PLACEHOLDER_PATTERN = Pattern.compile("[{]([^{}]+)[}]");
//	private static final Pattern RELATIONAL_PLACEHOLDER_PATTERN = Pattern.compile("[%](rel_)([^%]+)[%]");
//	private  Map<String, IPlaceholder> placeholders = new HashMap<>();
	
	public PlaceholderModule(JavaPlugin plugin) {
		super(plugin);
	}

}
