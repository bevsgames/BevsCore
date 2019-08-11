package games.bevs.core.commons.utils;

import static org.junit.Assert.assertEquals;
import java.util.UUID;

import org.junit.Test;

public class DataUtilsTests 
{
	@Test
	public void onStringToUUID()
	{
		String id = "5d793eed51b747659b384fdaa00034d7";
		UUID uuid = DataUtils.stringToUniqueId(id);
		assertEquals(uuid.toString(), "5d793eed-51b7-4765-9b38-4fdaa00034d7");
	}
	
	@Test
	public void onUUIDToString()
	{
		
		String id = "5d793eed-51b7-4765-9b38-4fdaa00034d7";
		UUID uuid = UUID.fromString(id);
		
		String uuidStr = DataUtils.uniqueIdToString(uuid);
		assertEquals(uuidStr, "5d793eed51b747659b384fdaa00034d7");
	}
}
