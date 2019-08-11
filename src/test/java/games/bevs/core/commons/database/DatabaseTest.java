package games.bevs.core.commons.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import games.bevs.core.commons.utils.DataUtils;

public class DatabaseTest
{
	private boolean secondProfile = false;
	private static HikariDataSource ds;
	
	@BeforeClass
	public static void setUpDatabase()
	{
		HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:8889/BevsGames");
        config.setUsername("core_user");
        config.setPassword("*c4GS-X2!wwrJnA");  
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("allowMultiQueries", "true");

        ds = new HikariDataSource(config);
	}
	
	
	@Test
	public void testMysqlInsertOnDupe()
	{
		long gold = 54;
		
		
		String username = "Sprock";
		String id = "5d793eed-51b7-4765-9b38-4fdaa00034d7";
		
		if(secondProfile)
		{
			username = "Issy";
			id = "46a492d2-2075-4195-b9c3-a51363cca82e";
		}
		
		UUID uuid = UUID.fromString(id);
		String uuidStr = DataUtils.uniqueIdToString(uuid);
		
		try {
	        Connection con = ds.getConnection();
	        PreparedStatement smt = con.prepareStatement("INSERT INTO PlayerData (username, unique_id, gold) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `gold` = VALUES(gold) + gold , `username`=VALUES(username);");
			
	        int i = 1;
	        smt.setString(i++, username);
	        smt.setString(i++, uuidStr);
	        smt.setLong(i++, gold);
	        
	        smt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMysqlInsert()
	{
		String username = "Sprock";
		String id = "5d793eed-51b7-4765-9b38-4fdaa00034d7";
		
		if(secondProfile)
		{
			username = "Issy";
			id = "46a492d2-2075-4195-b9c3-a51363cca82e";
		}
		
		UUID uuid = UUID.fromString(id);
		String uuidStr = DataUtils.uniqueIdToString(uuid);
		
		try {
	        Connection con = ds.getConnection();
	        PreparedStatement smt = con.prepareStatement("INSERT INTO PlayerData (username, unique_id) VALUES (?, ?, ?)");
			
	        int i = 1;
	        smt.setString(i++, username);
	        smt.setString(i++, uuidStr);
	        
	        smt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMysqlSelect()
	{
		//wait for add to be submitted
		try {
			Thread.sleep(1200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String id = "5d793eed-51b7-4765-9b38-4fdaa00034d7";
		
		if(secondProfile)
		{
			id = "46a492d2-2075-4195-b9c3-a51363cca82e";
		}
		
		UUID uuid = UUID.fromString(id);
		String uuidStr = DataUtils.uniqueIdToString(uuid);
		
		try {
	        PreparedStatement smt = ds.getConnection().prepareStatement("SELECT * FROM PlayerData WHERE `unique_id`=?");
	        smt.setString(1, uuidStr);
	        ResultSet rs = smt.executeQuery();
	        
	        while(rs.next())
	        {
	        	int interalId = rs.getInt("internal_id");
	        	String username = rs.getString("username");
	        	String uniqueId = rs.getString("unique_id");
	        	long gold = rs.getLong("gold");
	        	long firstJoined = rs.getTime("join_timestamp").getTime();
	        	
	        	System.out.println(interalId + " " + username + " " + uniqueId + " " + gold + " " + firstJoined);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	 @AfterClass
	 public static void cleanUpDatabase()
	 {
		 ds.close();
	 }
}
