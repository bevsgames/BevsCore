package games.bevs.core.commons.database.none.minidbs;

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import games.bevs.core.commons.database.api.minidbs.PlayerDataMiniDB;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.player.rank.Rank;

public class NonePlayerDataMiniDB implements PlayerDataMiniDB
{
	@Override
	public PlayerData loadPlayerData(Player player) {
		return this.loadPlayerData(player.getUniqueId());
	}

	@Override
	public PlayerData loadPlayerData(UUID uniqueId) {
		PlayerData playerData = new PlayerData("",uniqueId);
		playerData.setRank(Rank.STAFF);
		playerData.setRankExpires(System.currentTimeMillis() + 86_400_000);
		return playerData;
	}

	@Override
	public void savePlayerData(PlayerData user) {
		
	}

	@Override
	public List<PlayerData> getAllPlayerDatas() {
		return null;
	}

}
