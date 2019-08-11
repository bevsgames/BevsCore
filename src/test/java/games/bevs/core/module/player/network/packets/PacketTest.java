package games.bevs.core.module.player.network.packets;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import games.bevs.core.commons.Rank;
import games.bevs.core.commons.player.PlayerData;

/**
 * Any changes to the playerData class, this test will need to be upddated.
 */
public class PacketTest 
{
	@Test 
	public void onPlayerDataRequest()
	{
		String username = "Sprock";
		String id = "5d793eed-51b7-4765-9b38-4fdaa00034d7";
		UUID uuid = UUID.fromString(id);
		
		PlayerDataRequest request = new PlayerDataRequest("default", uuid, username);
		Assert.assertEquals(request.build(), "{\"server\":\"default\",\"type\":\"REQUEST\",\"data\":\"{\\\"uniqueId\\\":\\\"5d793eed-51b7-4765-9b38-4fdaa00034d7\\\",\\\"username\\\":\\\"Sprock\\\"}\"}");
	}
	
	@Test 
	public void onPlayerDataResponse()
	{
		String id = "5d793eed-51b7-4765-9b38-4fdaa00034d7";
		UUID uuid = UUID.fromString(id);
		
		PlayerData playerData = new PlayerData(uuid);
		playerData.setRank(Rank.FAMOUS);
		playerData.setGold(504);
		playerData.setLevel(120);
		playerData.setExperience(20000);
		playerData.setExpToLevel(2000000);
		
		PlayerDataResponse request = new PlayerDataResponse("default", playerData);
		
		Assert.assertEquals(request.build(), "{\"server\":\"default\",\"type\":\"RESPONSE\",\"data\":\"{\\\"internalId\\\":0,\\\"uniqueId\\\":\\\"5d793eed-51b7-4765-9b38-4fdaa00034d7\\\",\\\"rank\\\":\\\"FAMOUS\\\",\\\"jointimestamp\\\":0,\\\"gold\\\":504,\\\"level\\\":120,\\\"experience\\\":20000,\\\"expToLevel\\\":2000000,\\\"loaded\\\":false}\"}");
	}
}
