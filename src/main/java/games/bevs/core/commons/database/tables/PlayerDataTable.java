package games.bevs.core.commons.database.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import games.bevs.core.commons.database.DatabaseSettings;
import games.bevs.core.commons.database.DatabseOperation;

public class PlayerDataTable extends DatabseOperation
{
	public static final String PLAYERDATA_TABLE = "CREATE TABLE IF NOT EXISTS `PlayerData` (  `internal_id` int(11) NOT NULL,  `unique_id` varchar(36) NOT NULL,  `username` varchar(18) NOT NULL,  `gold` bigint(20) NOT NULL DEFAULT '0',  `join_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  `current_rank_id` int(11) NOT NULL DEFAULT '-1',  `current_rank_name` varchar(20) NOT NULL DEFAULT 'NORMAL',  `current_rank_expires` timestamp NOT NULL DEFAULT '1999-12-31 11:00:00',  `current_level` int(11) NOT NULL DEFAULT '1',  `current_exp` int(11) NOT NULL DEFAULT '0',  `exp_to_level` int(11) NOT NULL DEFAULT '0',  `muted_expires` timestamp NOT NULL DEFAULT '1999-12-31 11:00:00',  `ban_expires` timestamp NOT NULL DEFAULT '1999-12-31 11:00:00') ENGINE=InnoDB DEFAULT CHARSET=utf8;";

	public PlayerDataTable(DatabaseSettings settings) 
	{
		super(settings);
		
		try {
			Connection connection = this.getSettings().getMysqlManager().getConnection();
			PreparedStatement stmt = connection.prepareStatement(PLAYERDATA_TABLE);
			stmt.execute();
			
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
