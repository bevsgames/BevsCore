package games.bevs.core.module.soup;

import java.util.LinkedList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import games.bevs.core.commons.CC;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class SoupSettings
{
	private @Getter @Setter double health = 5;
	private @Getter @Setter Material material = Material.MUSHROOM_SOUP;
	private @Getter @Setter ItemStack resultsIn = new ItemStack(Material.BOWL);
	private @Getter @Setter boolean cooldown = false;
	private @Getter @Setter long cooldownMillis = 1000;
	private @Getter @Setter String cooldownMessage = CC.red + "You soup is on cooldown for another {cooldown}!";
	private @Getter @Setter LinkedList<Drinkable> drinkEffects = new LinkedList<>();
	
	public SoupSettings(double health)
	{
		this.health = health;
	}
}
