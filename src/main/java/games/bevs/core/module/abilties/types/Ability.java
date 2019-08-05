package games.bevs.core.module.abilties.types;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.interfaces.IAbilityParent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ability implements Listener
{
	private IAbilityParent parent; // we inject this on load
	
	public Ability()
	{
	}
	
	public void onLoad()
	{
		
	}
	
	public void onUnload()
	{
		
	}
	
	public List<ItemStack> getItems()
	{
		return new ArrayList<>();
	}
	
	public boolean hasAbility(Player player)
	{
		return parent.has(player);
	}
	
	private AbilityInfo getAbilityInfo()
	{
		return this.getClass().getAnnotation(AbilityInfo.class);
	}
	
	public String getName()
	{
		return this.getAbilityInfo().name();
	}
	
	public String getAuthor()
	{
		return this.getAbilityInfo().author();
	}
	
	public String[] getDescription()
	{
		return this.getAbilityInfo().description();
	}
	
	public JavaPlugin getPlugin()
	{
		return this.parent.getPlugin();
	}
}
