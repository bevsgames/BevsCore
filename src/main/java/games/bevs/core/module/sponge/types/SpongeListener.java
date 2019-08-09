package games.bevs.core.module.sponge.types;

import org.bukkit.event.Listener;

import games.bevs.core.module.sponge.SpongeModule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SpongeListener  implements Listener 
{
	private @Getter SpongeModule module;
	private @Getter LauncherType type;
}
