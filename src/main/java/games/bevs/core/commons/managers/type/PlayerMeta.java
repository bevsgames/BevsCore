package games.bevs.core.commons.managers.type;

import java.util.HashMap;
import java.util.UUID;

import games.bevs.core.commons.player.MCPlayer;
import games.bevs.core.commons.utils.NumberUtils;
import lombok.Getter;

public class PlayerMeta extends MCPlayer
{
	private @Getter HashMap<String, String> meta;

	public PlayerMeta(UUID uniquieId, String username)
	{
		super(uniquieId, username);
		
		this.meta = new HashMap<>();
	}
	
	/**
	 * Use it only if you have to
	 * @param uniquieId
	 */
	@Deprecated
	public PlayerMeta(UUID uniquieId)
	{
		super(uniquieId);
		
		this.meta = new HashMap<>();
	}
	
	public void setMeta(String path, String value)
	{
		this.getMeta().put(path, value);
	}
	
	public String getMeta(String path)
	{
		return this.getMeta().get(path);
	}
	
	public void setMeta(String path, int value)
	{
		this.getMeta().put(path, value + "");
	}
	
	public int getMetaInteger(String path)
	{
		String value = this.getMeta().get(path);
		if(!NumberUtils.isNumber(value))
		{
			try {
				throw new Exception(value + " is not a number");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Integer.parseInt(value);
	}
}
