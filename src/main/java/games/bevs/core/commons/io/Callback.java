package games.bevs.core.commons.io;

public abstract class Callback<T>
{
	public Callback() {}
  
	public abstract void done(T paramT);
}
