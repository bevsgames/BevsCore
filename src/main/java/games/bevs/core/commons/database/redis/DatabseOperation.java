package games.bevs.core.commons.database.redis;

import lombok.Getter;

public class DatabseOperation
{
	private @Getter boolean success = false;
	
	
	
	
	public void setSuccess()
	{
		this.success = true;
	}
}
