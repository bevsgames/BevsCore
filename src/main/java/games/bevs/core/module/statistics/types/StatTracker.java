package games.bevs.core.module.statistics.types;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.module.player.PlayerDataModule;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StatTracker implements Listener
{
	private @NonNull String name;
	private @NonNull PlayerDataModule playerDataModules;
	
	public void increaseStat(Player player, int amount)
	{
		long newValue = this.getStat(player) + amount;
		this.setStat(player, newValue);
	}
	
	public void setStat(Player player, long stat)
	{
		PlayerData playerData = this.getPlayerDataModules().getPlayerData(player);
		if(playerData == null) return;
		playerData.setStatistic(this.getName(), stat);
	}

	public long getStat(Player player)
	{
		PlayerData playerData = this.getPlayerDataModules().getPlayerData(player);
		if(playerData == null) return 0L;
		return playerData.getStatistic(getName());
	}
}
