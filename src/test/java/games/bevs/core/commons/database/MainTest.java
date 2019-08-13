package games.bevs.core.commons.database;

import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;

import games.bevs.core.commons.database.api.DatabaseSettings;
import games.bevs.core.commons.database.api.minidbs.PlayerDataMiniDB;
import games.bevs.core.commons.database.mongo.MongoDatabase;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.player.rank.Rank;

/**
 * This test uploads a playerData info
 * then pulls it down and sets the rank to famous
 * with a few stats 
 *
 */
@Ignore("Takes too long to run due to databases")
public class MainTest {

	@Test
	public void onPlayerData()
	{
		
		MongoDatabase mongo = new MongoDatabase(new DatabaseSettings("localhost", "27017", "bevsGames", null, null));
		
		PlayerDataMiniDB playerDataDB = mongo.getMiniDatabase(PlayerDataMiniDB.class);
		
		String username = "Sprock";
		UUID uniqueId = UUID.fromString("5d793eed-51b7-4765-9b38-4fdaa00034d7");
		
		PlayerData playerDataUpload = new PlayerData(username, uniqueId);
		playerDataDB.savePlayerData(playerDataUpload);
		
		PlayerData playerDataDownload = playerDataDB.loadPlayerData(uniqueId);
		playerDataDownload.setRank(Rank.FAMOUS);
		playerDataDownload.addStatistic("hg_kills", 4);
		playerDataDownload.addStatistic("hg_deaths", 10);
		playerDataDownload.addStatistic("hg_killstreak", 6);
		playerDataDownload.addStatistic("hg_games", 4);
		
		playerDataDB.savePlayerData(playerDataDownload);
		
		PlayerData playerDataDownloaded2 = playerDataDB.loadPlayerData(uniqueId);
		System.out.println(playerDataDownloaded2.getRank());
		
		mongo.close();
	}

}
