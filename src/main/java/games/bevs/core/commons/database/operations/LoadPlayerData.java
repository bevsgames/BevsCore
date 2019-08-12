package games.bevs.core.commons.database.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import games.bevs.core.commons.database.DatabaseSettings;
import games.bevs.core.commons.database.DatabseOperation;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.utils.DataUtils;

public class LoadPlayerData extends DatabseOperation
{
	private static final String GET_PLAYERDATA = "SELECT * FROM PlayerData WHERE uniqueId = ? LIMIT 1;";
	private static final String GET_PLAYERDATA_STATS = "SELECT * FROM PlayerStats WHERE player_id = ?;";

	public LoadPlayerData(DatabaseSettings settings, PlayerData playerData)
	{
		super(settings);
		
		try {
			Connection connection = settings.getMysqlManager().getConnection();
			
			//Load player data (Ranks, levels)
			PreparedStatement stmt = connection.prepareStatement(GET_PLAYERDATA);
			
			String uniqueIdStr = DataUtils.uniqueIdToString(playerData.getUniqueId());
			
			int i = 1;
			stmt.setString(i++, uniqueIdStr);
			
			ResultSet resultSet = stmt.executeQuery();
			playerData.loadPlayerDataViaDatabase(resultSet);
			
			resultSet.close();
			stmt.close();
			
			//Load player stats (Kills, deaths)
			stmt = connection.prepareStatement(GET_PLAYERDATA_STATS);
			
			i = 1;
			stmt.setLong(i++, playerData.getInternalId());
			
			resultSet = stmt.executeQuery();
			playerData.loadStatsViaDatabase(resultSet);
			
			resultSet.close();
			stmt.close();
			connection.close();
			
			playerData.setLoaded(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
