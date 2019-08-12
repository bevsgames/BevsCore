package games.bevs.core.commons.database.tables;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import games.bevs.core.commons.database.DatabaseSettings;
import games.bevs.core.commons.database.mysql.MySQLManager;

@Ignore("Takes too long to execute")
public class TableTest 
{
	private static MySQLManager mySQLManager;
	private static DatabaseSettings settings;
	
	@BeforeClass
	public static void onSetUp()
	{
		mySQLManager = new MySQLManager(null, "localhost", "8889", "BevsGames", "core_user", "*c4GS-X2!wwrJnA");
		settings = new DatabaseSettings(null, mySQLManager);
	}
	
	@Test
	public void generatePlayerTable()
	{
		PlayerDataTable playerDataTable = new PlayerDataTable(settings);
	}
	
	@Test
	public void generateStatistics()
	{
		StatisticsTable statisticsTable = new StatisticsTable(settings);
	}
	
	@AfterClass
	public static void onClean()
	{
		mySQLManager.close();
	}
}
