package games.bevs.core.commons.database.mongo;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import games.bevs.core.commons.database.api.Database;
import games.bevs.core.commons.database.api.DatabaseSettings;
import games.bevs.core.commons.database.api.minidbs.PlayerDataMiniDB;
import games.bevs.core.commons.database.mongo.minidbs.MongoPlayerDataMiniDB;
import games.bevs.core.commons.player.PlayerData;
import lombok.Getter;

public class MongoDatabase extends Database {
	private MongoClient mongo;
	private Morphia morphia;
	private @Getter Datastore datastore;
	
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
		
		this.registerMiniDatabase(PlayerDataMiniDB.class, new MongoPlayerDataMiniDB(this));
	}

	
	@Override
	public void close()
	{
		this.mongo.close();
	}
}
