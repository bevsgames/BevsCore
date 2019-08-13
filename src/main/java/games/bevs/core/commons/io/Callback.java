package games.bevs.core.commons.io;

@FunctionalInterface
public interface Callback<T>
{
	public abstract void done(T paramT);
}
