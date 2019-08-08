package games.bevs.core.commons.database.operation;

import lombok.Getter;

public class DatabseOperation
{
	private @Getter boolean success = false;
	private @Getter DatabaseSettings settings;
	
	public DatabseOperation(DatabaseSettings settings)
	{
		this.settings = settings;
	}
	
	public void setSuccess()
	{
		this.success = true;
	}
}
