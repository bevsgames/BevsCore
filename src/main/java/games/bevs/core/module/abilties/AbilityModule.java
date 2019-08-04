package games.bevs.core.module.abilties;

import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.abilties.abilities.BoxerAbility;
import games.bevs.core.module.abilties.abilities.DummyAbility;
import games.bevs.core.module.abilties.abilities.GandpaAbility;
import games.bevs.core.module.abilties.abilities.KangarooAbility;
import games.bevs.core.module.abilties.types.DummyAbilityParent;
import games.bevs.core.module.commands.CommandModule;

/**
 * Events
 * <ul>
 * 	<li>BlockBreakEvent</li>
 * 	<li>BlockPlaceEvent</li>
 * 	<li>PlayerInteractionEvent</li>
 * 	<li>CustomDamageEvent</li>
 * 	<li>CustomDeathEvent</li>
 *  <li>InventoryClickEvent</li>
 *  <li>PlayerInteractEntityEvent</li>
 *  <li>EntityTargetEvent</li>
 * </ul>
 *
 */
@ModInfo(name = "Ability")
public class AbilityModule extends Module
{
	
	public AbilityModule(JavaPlugin plugin, CommandModule commandModule, boolean debug)
	{
		super(plugin, commandModule, debug);
	}
	
	public AbilityModule(JavaPlugin plugin, CommandModule commandModule)
	{
		super(plugin, commandModule);
	}
	
	public void onEnable()
	{
		
		if(!this.isDebug()) return;
		DummyAbilityParent dummy = new DummyAbilityParent(this.getPlugin(), this);
		dummy.addAbility(new BoxerAbility());
		dummy.addAbility(new DummyAbility());
		dummy.addAbility(new GandpaAbility());
		dummy.addAbility(new KangarooAbility());
	}

}
