package games.bevs.core.commons.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Property;

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
@Entity(value = "PlayerDatas", noClassnameStored = true)
public class PlayerData
{
	@Id
	private long internalId;
	
	@Indexed(options = @IndexOptions(unique = true))
	private @NonNull UUID uniqueId;
	
	@Indexed
	private String username;
	private Rank rank = Rank.NORMAL;
	private Rank displayRank = Rank.NORMAL;
	private long jointimestamp = System.currentTimeMillis();
	
	private long gold = 0;
	
	private long level = 0;
	private long experience = 0;
	private long expToLevel = 0;
	
	private long mutedExpires = -1;
	private long banExpires = -1;
	
	@Property("statistics")
	private HashMap<String, Long> statistics;
	
	private boolean loaded = false;
	
	public PlayerData(String username, UUID uniqueId)
	{
		this.username = username;
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
		
		System.out.println(System.currentTimeMillis() + " < " + rankExpires);
		
		Rank rank = Rank.NORMAL;
		if(System.currentTimeMillis() < rankExpires)
		{
			try
			{
				rank = Rank.valueOf(rankName.toUpperCase());
				System.out.println(rank);
			} 
			catch(Exception e)
			{
				System.out.println("Failed to get rank " + rankName);
				//We don't have that rank programmed?
			}
		}
		return rank;
	}
	
	public void loadPlayerDataViaDatabase(ResultSet resultSet)
	{
		try 
		{
			resultSet.next();
			this.internalId = resultSet.getLong("internal_id");
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
