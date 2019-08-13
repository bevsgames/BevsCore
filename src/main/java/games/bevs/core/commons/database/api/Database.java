package games.bevs.core.commons.database.api;

import java.util.HashMap;

import lombok.Getter;

public abstract class Database
{
	private @Getter DatabaseSettings settings;
	private HashMap<Class<? extends MiniDatabase>, MiniDatabase> miniDatabases = new HashMap<>();
	
	public Database(DatabaseSettings settings)
	{
		this.settings = settings;
	}
	
	public void registerMiniDatabase(Class<? extends MiniDatabase> apiClazz, MiniDatabase minidatabase)
	{
		this.miniDatabases.put(apiClazz, minidatabase);
	}
	
	@SuppressWarnings("unchecked")
	public <M extends MiniDatabase> M getMiniDatabase(Class<M> clazz)
	{
		M miniDb = (M) this.miniDatabases.get(clazz);
		if(miniDb == null)
			throw new IllegalStateException(clazz.getName() + " Does not have implementation in this database type " + this.getClass().getName());
		return miniDb;
	}
	
	public abstract void close();
}
