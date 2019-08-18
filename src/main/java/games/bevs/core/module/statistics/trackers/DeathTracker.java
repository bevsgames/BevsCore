package games.bevs.core.module.statistics.trackers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import games.bevs.core.module.combat.event.CustomDamageEvent;
import games.bevs.core.module.player.PlayerDataModule;
import games.bevs.core.module.statistics.types.StatTracker;

public class DeathTracker extends StatTracker
{

	public DeathTracker(String name, PlayerDataModule playerDataModules)
	{
		super(name, playerDataModules);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onDeath(CustomDamageEvent e)
	{
		if(e.isVictimIsPlayer() &&  e.willDie())
			this.increaseStat(e.getAttackerPlayer(), 1);
	}

}
