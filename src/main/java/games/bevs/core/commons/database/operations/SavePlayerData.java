package games.bevs.core.commons.database.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import games.bevs.core.commons.database.DatabaseSettings;
import games.bevs.core.commons.database.DatabseOperation;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.utils.DataUtils;
import games.bevs.core.module.statistics.StatisticsModule;

public class SavePlayerData extends DatabseOperation
{

	public SavePlayerData(DatabaseSettings settings, PlayerData playerData, StatisticsModule statisticsModule)
	{
		super(settings);
		
		try {
			Connection connection = settings.getMysqlManager().getConnection();
			String uniqueIdStr = DataUtils.uniqueIdToString(playerData.getUniqueId());
			
			//Batch save stats
//			playerData.getStatistics().forEach((name, value) ->
//			{
//				PreparedStatement stmt = connection.prepareStatement("UPDATE");
//				stmt.setLong(1, name);
//				stmt.setLong(2, value);
//			});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
}
