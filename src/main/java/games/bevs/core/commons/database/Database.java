package games.bevs.core.commons.database;

import lombok.Getter;

public class Database
{
	private @Getter DatabaseSettings settings;
	
	public Database(DatabaseSettings settings)
	{
		this.settings = settings;
	}
}
