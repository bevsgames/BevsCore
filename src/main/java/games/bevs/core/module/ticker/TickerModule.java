package games.bevs.core.module.ticker;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.utils.PluginUtils;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;

/**
 * This module calls the {@link games.bevs.core.module.ticker.UpdateEvent} 
 * every time unit and allows you to filter by seconds, minutes, hours etc
 */
@ModInfo(name="ticker")
public class TickerModule extends Module
{
	public TickerModule(BevsPlugin plugin) 
	{
		super(plugin);
	}
	
	@Override
	public void onEnable()
	{
		PluginUtils.repeat(this.getPlugin(), () ->
		{
			UnitType[] types = UnitType.values();
			
			for(int i = 0; i < types.length; i++)
			{
				UnitType type = types[i];
				if(type.elapsed()) 
					new UpdateEvent(type).call();
			}
		}, 0L, 1L);
	}
}
