package games.bevs.core.module.sponge;

import org.bukkit.Material;

import games.bevs.core.module.sponge.types.LauncherType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class SpongeSettings 
{
	private @Getter @Setter Material launchMaterial = Material.SPONGE;
	private @Getter @Setter LauncherType launcherType = LauncherType.BEVS;
	private @Getter @Setter double vertVelocity = 10;
	private @Getter @Setter double hortVelocity = 4;
}
