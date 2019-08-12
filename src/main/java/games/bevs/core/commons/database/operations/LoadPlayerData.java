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
	private static final String GET_PLAYERDATA = "SELECT * FROM player WHERE uniqueId = ? LIMIT 1;";

	public LoadPlayerData(DatabaseSettings settings, PlayerData playerData)
	{
		super(settings);
		
		try {
			Connection connection = settings.getMysqlManager().getConnection();
			PreparedStatement stmt = connection.prepareStatement(GET_PLAYERDATA);
			
			String uniqueIdStr = DataUtils.uniqueIdToString(playerData.getUniqueId());
			
			int i = 1;
			stmt.setString(i++, uniqueIdStr);
			
			ResultSet resultSet = stmt.executeQuery();
			playerData.loadViaDatabase(resultSet);
			
			//need to load Stats as well
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
}
