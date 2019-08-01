package games.bevs.core.module.ticker;

import lombok.Getter;

public enum UnitType
{
	TICK(49L),
	SLOW(200l),
	SECOND(1000L),
	MINUTE(60_000L),
	HOUR(3_600_000L),
	DAY(86_400_000L),
	WEEK(604_800_000L),
	MONTH(2_592_000_000L),
	YEAR(31_536_000_000L);
	
	
	private long last;
	private @Getter long millis;
	
	UnitType(long millis)
	{
		this.millis = millis;
	}
	
	public boolean elapsed() 
	{
		if(System.currentTimeMillis() - last > this.millis) 
		{
			this.last = System.currentTimeMillis();
			return true;
		}
		return false;
	}
}
