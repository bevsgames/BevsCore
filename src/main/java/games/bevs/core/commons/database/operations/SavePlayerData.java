package games.bevs.core.commons.database.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import games.bevs.core.commons.database.DatabaseSettings;
import games.bevs.core.commons.database.DatabseOperation;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.utils.DataUtils;

public class SavePlayerData extends DatabseOperation
{

	public SavePlayerData(DatabaseSettings settings, PlayerData playerData)
	{
		super(settings);
		
		try {
			Connection connection = settings.getMysqlManager().getConnection();
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM player WHERE uniqueId = ? LIMIT 1;");
			String uniqueIdStr = DataUtils.uniqueIdToString(playerData.getUniqueId());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
}
