package games.bevs.core.commons.managers;

import java.util.HashMap;

public class Manager<K, V>
{
	private HashMap<K, V> entities;
	
	public Manager()
	{
		this.entities = new HashMap<>();
	}
	
	protected void add(K k, V v)
	{
		this.entities.put(k, v);
	}
	
	protected V get(K k)
	{
		return this.entities.get(k);
	}
	
	protected void remove(K k)
	{
		this.entities.remove(k);
	}
}
