package games.bevs.core.commons.database.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import games.bevs.core.commons.database.Database;
import games.bevs.core.commons.database.DatabaseSettings;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.player.dao.PlayerDataDao;

public class MongoDatabase extends Database {
	private MongoClient mongo;
	private Morphia morphia;
	private Datastore datastore;
	
	private PlayerDataDao playerdataDao;

	public MongoDatabase(DatabaseSettings settings) 
	{
		super(settings);
		ServerAddress addr = new ServerAddress(settings.getUrl(), (int) Integer.parseInt(settings.getPort()));
		List<MongoCredential> credentials = new ArrayList<>();
		
		if(settings.getUsername() != null)
			credentials.add(MongoCredential.createCredential(settings.getUsername(), settings.getDatabase(), settings.getPassword().toCharArray()));

		mongo = new MongoClient(addr, credentials);
		morphia = new Morphia();

		morphia.map(PlayerData.class);
		
		datastore = morphia.createDatastore(mongo, "bevsGames");
		datastore.ensureIndexes();
		

		playerdataDao = new PlayerDataDao(PlayerData.class, datastore);
	}

	public PlayerData getUserByPlayer(Player player)
	{
		PlayerData playerData = playerdataDao.findOne("uuid", player.getUniqueId().toString());
		if (playerData == null) 
		{
			playerData = new PlayerData(player.getName(), player.getUniqueId());
			this.playerdataDao.save(playerData);
		}
		return playerData;
	}

	public void saveUser(PlayerData user) 
	{
		playerdataDao.save(user);
	}

	public List<PlayerData> getAllUsers() 
	{
		return playerdataDao.find().asList();
	}
}
