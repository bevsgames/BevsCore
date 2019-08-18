package games.bevs.core.module.statistics;

import java.util.ArrayList;

import games.bevs.core.BevsPlugin;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.player.PlayerDataModule;
import games.bevs.core.module.statistics.types.StatTracker;

@ModInfo(name = "Statistics")
public class StatisticsModule extends Module 
{
	private ArrayList<StatTracker> trackers = new ArrayList<>();

	public StatisticsModule(BevsPlugin plugin, CommandModule commandModule, PlayerDataModule playerDataModule) {
		super(plugin, commandModule, playerDataModule);
	}
	
	public void registerTracker(StatTracker statTracker)
	{
		this.trackers.add(statTracker);
		this.registerListener(statTracker);
	}
	
	public void unregisterTracker(StatTracker statTracker)
	{
		this.trackers.remove(statTracker);
		this.registerTracker(statTracker);
	}
}
