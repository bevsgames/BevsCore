package games.bevs.core.module.lag;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import games.bevs.core.BevsPlugin;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.lag.commands.LagCommand;
import games.bevs.core.module.lag.commands.LagDataCommand;
import games.bevs.core.module.ticker.TickerModule;
import games.bevs.core.module.ticker.UnitType;
import games.bevs.core.module.ticker.UpdateEvent;
import lombok.Getter;

@Getter
@ModInfo(name = "Lag'O'Meter")
public class LagModule extends Module 
{
	private long lastRun = -1L;
	private int count;

	private double ticksPerSecond;
	private double ticksPerSecondAverage;
	private long lastAverage;

	// force TickerModule, so I don't mess it up when making module run
	public LagModule(BevsPlugin plugin, TickerModule tickerModule) 
	{
		super(plugin);
	}

	@Override
	public void enable() 
	{
		this.lastRun = System.currentTimeMillis();
		this.lastAverage = System.currentTimeMillis();
		
		this.registerCommand(new LagCommand(this));
		this.registerCommand(new LagDataCommand(this));
	}
	
	public void sendUpdate(Player player) 
	{
		String tps = String.format("%.00f", this.ticksPerSecond);
		String tpsAvg = String.format("%.00f", this.ticksPerSecondAverage * 20.0D);
		String freeMemory = (Runtime.getRuntime().freeMemory() / 1048576L) + "MB";
		String maxMemory = (Runtime.getRuntime().maxMemory() / 1048576L) + "MB";
		
		player.sendMessage("TPS: " + tps);
		player.sendMessage("AVG TPS: " + tpsAvg);
		player.sendMessage("Free Memory: " + freeMemory);
		player.sendMessage("Max Memory: " + maxMemory);
	}
	

	@EventHandler
	public void update(UpdateEvent event) 
	{
		if (event.getType() != UnitType.SECOND)
			return;
		long now = System.currentTimeMillis();
		this.ticksPerSecond = 1000.0D / (now - this.lastRun) * 20.0D;

		if (this.count % 30 == 0) 
		{
			this.ticksPerSecondAverage = 30000.0D / (now - this.lastAverage) * 20.0D;
			this.lastAverage = now;
		}
		this.lastRun = now;

		this.count++;
	}
}
