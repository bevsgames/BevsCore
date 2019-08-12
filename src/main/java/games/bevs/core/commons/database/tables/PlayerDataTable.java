package games.bevs.core.commons.database.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import games.bevs.core.commons.database.DatabaseSettings;
import games.bevs.core.commons.database.DatabseOperation;

public class PlayerDataTable extends DatabseOperation
{
	public static final String PLAYERDATA_TABLE = "CREATE TABLE `BevsGames`.`PlayerData` ( `internal_id` INT NOT NULL AUTO_INCREMENT , `unique_id` VARCHAR(34) NOT NULL , `username` VARCHAR(20) NOT NULL , `gold` BIGINT NOT NULL DEFAULT '0' , `join_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP , `current_rank_id` INT NOT NULL DEFAULT '-1' , `current_rank_name` VARCHAR(24) NOT NULL DEFAULT 'NORMAL' , `current_rank_expires` bigint(20) NOT NULL DEFAULT -1 , `current_level` INT NOT NULL DEFAULT '1' , `current_exp` INT NOT NULL DEFAULT '0' , `exp_to_level` INT NOT NULL DEFAULT '10' , `muted_expires` bigint(20) NOT NULL DEFAULT -1 , `ban_expires` bigint(20) NOT NULL DEFAULT -1 , PRIMARY KEY (`internal_id`), UNIQUE (`unique_id`)) ENGINE = InnoDB;";

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
