package games.bevs.core.commons.database.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import games.bevs.core.commons.database.DatabaseSettings;
import games.bevs.core.commons.database.DatabseOperation;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.utils.DataUtils;

public class LoadPlayerData extends DatabseOperation {
	private static final String GET_PLAYERDATA = "SELECT * FROM PlayerData WHERE unique_id = ? LIMIT 1;";
	private static final String GET_PLAYERDATA_STATS = "SELECT * FROM PlayerStatistics WHERE internal_id = ?;";
	private static final String CREATE_PLAYERDATA = "INSERT INTO `PlayerData` (`unique_id`, `username`) VALUES (?, ?);";

	public LoadPlayerData(DatabaseSettings settings, PlayerData playerData) {
		super(settings);

		try {
			Connection connection = settings.getMysqlManager().getConnection();

			// Load player data (Ranks, levels)
			PreparedStatement stmt = connection.prepareStatement(GET_PLAYERDATA);

			String uniqueIdStr = DataUtils.uniqueIdToString(playerData.getUniqueId());

			int i = 1;
			stmt.setString(i++, uniqueIdStr);

			ResultSet resultSet = stmt.executeQuery();
			boolean isEmpty = false;
			
			if (!resultSet.next()) {
				isEmpty = true;
			}
			resultSet.beforeFirst();

			if(!isEmpty)
				playerData.loadPlayerDataViaDatabase(resultSet);

			resultSet.close();
			stmt.close();

			// If no PlayerData was found
			if (isEmpty) {
				stmt = connection.prepareStatement(CREATE_PLAYERDATA, Statement.RETURN_GENERATED_KEYS);

				i = 1;
				stmt.setString(i++, uniqueIdStr);// unique_id
				stmt.setString(i++, playerData.getUsername());// username
				stmt.executeUpdate();

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					playerData.setInternalId(rs.getInt(1));
				}
			} else {

				// Load player stats (Kills, deaths)
				stmt = connection.prepareStatement(GET_PLAYERDATA_STATS);

				i = 1;
				stmt.setLong(i++, playerData.getInternalId());

				resultSet = stmt.executeQuery();
				playerData.loadStatsViaDatabase(resultSet);

				resultSet.close();
				stmt.close();
			}
			connection.close();

			playerData.setLoaded(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
