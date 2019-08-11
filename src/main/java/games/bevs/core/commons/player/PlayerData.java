package games.bevs.core.commons.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import games.bevs.core.commons.Rank;
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
	
	private boolean loaded = false;
	
	public PlayerData(UUID uniqueId)
	{
		this.uniqueId = uniqueId;
	}
	
	
	
	public void saveViaDatabase()
	{
		
	}
	
	public void loadViaDatabase(ResultSet resultSet)
	{
		try 
		{
			this.internalId = resultSet.getLong("interal_id");
			String uniqueId = resultSet.getString("unique_id");
			this.uniqueId = DataUtils.stringToUniqueId(uniqueId);
			this.jointimestamp = resultSet.getTimestamp("join_timestamp").getTime();
			
			this.gold = resultSet.getLong("gold");
			
			this.level = resultSet.getLong("current_level");
			this.experience = resultSet.getLong("current_exp");
			this.expToLevel = resultSet.getLong("exp_to_level");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if(!resultSet.isClosed())
					resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
