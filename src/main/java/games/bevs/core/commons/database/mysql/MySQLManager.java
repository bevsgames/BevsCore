package games.bevs.core.commons.database.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.utils.StringUtils;

public class MySQLManager 
{
	private BevsPlugin plugin;
	private HikariDataSource dataSource;
	
	public MySQLManager(BevsPlugin plugin, String url, String port, String database, String username, String password)
	{
		HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + url + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);  
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("allowMultiQueries", "true");

        this.dataSource = new HikariDataSource(config);
        this.plugin = plugin;
	}
	
	public Connection getConnection() throws SQLException
	{
		return this.dataSource.getConnection();
	}
	
	public void catchNonAsyncThread() {
		if(this.plugin != null)
			if (plugin.getServer().isPrimaryThread()) 
			{
				for(int i = 0; i < 20; i++)
					Bukkit.broadcastMessage(StringUtils.error("MySQLManager", "Thread was executed on main thread!!"));
				throw new IllegalStateException("Illegal call on main thread");
			}
	}
	
	public void closeComponents(PreparedStatement ps) 
	{
		this.closeComponents(null, ps, null);
	}
	
	
	public void closeComponents(Connection connection) 
	{
		this.closeComponents(null, null, connection);
	}
	
	public void closeComponents(PreparedStatement ps, Connection connection) 
	{
		this.closeComponents(null, ps, connection);
	}
	
	
	public void closeComponents(ResultSet rs, PreparedStatement ps, Connection connection) 
	{
		if (rs != null) 
		{
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (ps != null) 
		{
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void close()
	{
		this.dataSource.close();
	}
}
