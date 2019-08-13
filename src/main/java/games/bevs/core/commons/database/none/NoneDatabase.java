package games.bevs.core.commons.database.none;

import games.bevs.core.commons.database.api.Database;
import games.bevs.core.commons.database.api.DatabaseSettings;
import games.bevs.core.commons.database.api.minidbs.PlayerDataMiniDB;
import games.bevs.core.commons.database.none.minidbs.NonePlayerDataMiniDB;

public class NoneDatabase extends Database {
	
	public NoneDatabase(DatabaseSettings settings) 
	{
		super(settings);
		
		this.registerMiniDatabase(PlayerDataMiniDB.class, new NonePlayerDataMiniDB());
	}

	
	@Override
	public void close()
	{
	}
}
