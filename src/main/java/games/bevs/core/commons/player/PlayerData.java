package games.bevs.core.commons.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import games.bevs.core.commons.player.rank.Rank;
import games.bevs.core.commons.utils.DataUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * 
 */
@Data
@AllArgsConstructor
public class PlayerData
{
	private long internalId;
	private @NonNull UUID uniqueId;
	private Rank rank;
	private Rank displayRank;
	private long jointimestamp;
	
	private long gold;
	
	private long level;
	private long experience;
	private long expToLevel;
	
	private long mutedExpires = -1;
	private long banExpires = -1;
	
	private HashMap<String, Long> statistics;
	
	private boolean loaded = false;
	
	public PlayerData(UUID uniqueId)
	{
		this.uniqueId = uniqueId;
		this.statistics = new HashMap<>();
	}
	
	// SAVE
	
	public void saveStatsViaDatabase()
	{
		
	}
	
	public void saveViaDatabase()
	{
		// First save stats
		
	}
	
	
	// LOAD
	
	private Rank loadRank(ResultSet resultSet) throws SQLException
	{
		//Not in use, Will do it later, just want to get ranks in a working state.
		long rankId = resultSet.getLong("current_rank_id");
		
		String rankName = resultSet.getString("current_rank_name");
		long rankExpires = resultSet.getLong("current_rank_expires");
		
		Rank rank = Rank.NORMAL;
		if(System.currentTimeMillis() < rankExpires)
		{
			try
			{
				rank = Rank.valueOf(rankName.toUpperCase());
			} 
			catch(Exception e)
			{
				//We don't have that rank programmed?
			}
		}
		return rank;
	}
	
	public void loadPlayerDataViaDatabase(ResultSet resultSet)
	{
		try 
		{
			this.internalId = resultSet.getLong("interal_id");
			String uniqueId = resultSet.getString("unique_id");
			this.uniqueId = DataUtils.stringToUniqueId(uniqueId);
			this.jointimestamp = resultSet.getTimestamp("join_timestamp").getTime();
			
			this.rank = this.loadRank(resultSet);
			this.displayRank = this.rank;
			
			this.gold = resultSet.getLong("gold");
			
			this.level = resultSet.getLong("current_level");
			this.experience = resultSet.getLong("current_exp");
			this.expToLevel = resultSet.getLong("exp_to_level");
			
			this.mutedExpires = resultSet.getLong("muted_expires");
			this.banExpires = resultSet.getLong("ban_expires");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void loadStatsViaDatabase(ResultSet resultSet)
	{
		try 
		{
			while(resultSet.next())
			{
				String name = resultSet.getString("stat_name");
				long value = resultSet.getLong("stat_value");
				this.getStatistics().put(name, value);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
