package games.bevs.core.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Duration 
{
	private long timeInMills;
	
	public Duration(Integer amount, TimeUnit timeUnits)
	{
		this.timeInMills = amount * timeUnits.getMilli();
	}
	
	public long getMillis()
	{
		return this.timeInMills;
	}
	
	public void add(Duration duration)
	{
		this.timeInMills += duration.getMillis();
	}
	
	public void subtract(Duration duration)
	{
		this.timeInMills -= duration.getMillis();
	}
	
	public void add(Integer amount, TimeUnit timeUnits)
	{
		this.timeInMills += amount * timeUnits.getMilli();
	}
	
	public void subtract(Integer amount, TimeUnit timeUnits)
	{
		this.timeInMills -= amount * timeUnits.getMilli();
	}
	
	public int getAsUit(TimeUnit timeUnit)
	{
		return (int) (this.getMillis() / timeUnit.getMilli());
	}
	
	@AllArgsConstructor
	public enum TimeUnit
	{
		SECOND(1000),
		MINUTE(1000 * 60),
		HOUR(1000 * 60 * 60),
		DAY(1000 * 60 * 60 * 24),
		WEEK(1000 * 60 * 60 * 24 * 7),
		MONTH(1000 * 60 * 60 * 24 * 30),
		YEAR(1000 * 60 * 60 * 365);
		
		private @Getter long milli;
	}
}
