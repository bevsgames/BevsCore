package games.bevs.core.commons.database.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.database.api.Database;
import games.bevs.core.commons.database.api.DatabaseSettings;
import games.bevs.core.commons.database.api.minidbs.PlayerDataMiniDB;
import games.bevs.core.commons.utils.StringUtils;

public class MySQLDatabase extends Database
{
	private BevsPlugin plugin;
	private HikariDataSource dataSource;
	
	public MySQLDatabase(BevsPlugin plugin, DatabaseSettings settings)
	{
		super(settings);		
		HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + settings.getUrl() + ":" + settings.getPort() + "/" + settings.getDatabase());
        config.setUsername(settings.getUsername());
        config.setPassword(settings.getPassword());  
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
	

	@Override
	public void close()
	{
		this.dataSource.close();
	}
}
