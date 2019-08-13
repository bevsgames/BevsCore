package games.bevs.core.commons.player;

import java.util.HashMap;
import java.util.UUID;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Transient;

import games.bevs.core.commons.player.rank.Rank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(value = "PlayerDatas", noClassnameStored = true)
public class PlayerData
{
	@Id
	private long internalId;
	
	@Indexed(options = @IndexOptions(unique = true))
	private @NonNull String uniqueId;
	
	@Indexed
	private String username;
	private Rank rank = Rank.NORMAL;
	private Rank displayRank = Rank.NORMAL;
	private long rankExpires = 0;
	
	private long jointimestamp = System.currentTimeMillis();
	
	private long gold = 0;
	
	private long level = 0;
	private long experience = 0;
	private long expToLevel = 0;
	
	private long mutedExpires = -1;
	private long banExpires = -1;
	
	@Property("statistics")
	private HashMap<String, Long> statistics = new HashMap<>();
	
	@Transient
	private boolean loaded = false;
	
	public PlayerData(String username, UUID uniqueId)
	{
		this.username = username;
		this.uniqueId = uniqueId.toString();
	}
	
	public UUID getUniqueId()
	{
		return UUID.fromString(uniqueId);
	}
	
	public void setRank(Rank rank)
	{
		this.rank = rank;
		this.displayRank = rank;
	}
	
	public long getStatistic(String name)
	{
		return this.getStatistics().getOrDefault(name, 0l);
	}
	
	public void addStatistic(String name, long amount)
	{
		long currentValue = this.getStatistic(name);
		this.getStatistics().put(name, currentValue + amount);
	}
}
