package games.bevs.core.commons.database.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import games.bevs.core.commons.database.DatabaseSettings;
import games.bevs.core.commons.database.DatabseOperation;

public class StatisticsTable extends DatabseOperation
{
	public static final String STATISTICS_TABLE = "CREATE TABLE IF NOT EXISTS `BevsGames`.`Statistics` ( `stat_id` INT NOT NULL AUTO_INCREMENT , `stat_name` INT NOT NULL , PRIMARY KEY (`stat_id`), UNIQUE (`stat_name`)) ENGINE = InnoDB;";
	public static final String PLAYER_STATISTICS_TABLE = "CREATE TABLE IF NOT EXISTS `BevsGames`.`PlayerStatistics` ( `stat_id` INT NOT NULL , `internal_id` INT NOT NULL , `value` INT NOT NULL ) ENGINE = InnoDB;";

	public StatisticsTable(DatabaseSettings settings) 
	{
		super(settings);
		
		try {
			Connection connection = this.getSettings().getMysqlManager().getConnection();
			PreparedStatement stmt = connection.prepareStatement(STATISTICS_TABLE);
			stmt.execute();
			
			stmt = connection.prepareStatement(PLAYER_STATISTICS_TABLE);
			stmt.execute();
			
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
