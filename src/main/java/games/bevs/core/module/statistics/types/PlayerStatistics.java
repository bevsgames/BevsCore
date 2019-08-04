package games.bevs.core.module.statistics.types;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import games.bevs.core.commons.player.MCPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerStatistics extends MCPlayer
{
	private boolean readOnly = false;
	private HashMap<String, Long> statistics = new HashMap<String, Long>();
	
	public PlayerStatistics(UUID uniquieId, String username) 
	{
		super(uniquieId, username);
	}
	
	public PlayerStatistics(UUID uniquieId, String username, ResultSet rs) throws SQLException
	{
		super(uniquieId, username);
		
		rs.beforeFirst();
        while (rs.next())
        {
           // setStat(KeyMappings.getKey(rs.getInt("type")), rs.getLong("value"));
        }
	}
	
	public long getStatistic(String name)
	{
		return this.getStatistics().getOrDefault(name, 0L);
	}
	
	public long setStatistic(String name, long value)
	{
		return this.getStatistics().put(name, value);
	}
	
	public long increaseStatistic(String name, long amount)
	{
		return this.setStatistic(name, this.getStatistic(name) + amount);
	}

}
