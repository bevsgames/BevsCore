package games.bevs.core.commons;

import java.util.ArrayList;

import games.bevs.core.commons.utils.StringUtils;
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
	
	public Duration(long milli)
	{
		this.timeInMills = milli;
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
	
	public String getFormatedTime()
	{
		ArrayList<String> timeStrs = new ArrayList<>();
		long remainer = this.timeInMills;
		
		TimeUnit[] units = TimeUnit.values();
		for(int i = units.length - 1; i >= 0; i--)
		{
			TimeUnit timeUnit = units[i];
			long fitsIn = remainer / timeUnit.getMilli();
			if(fitsIn > 0)
			{
				remainer = remainer % timeUnit.getMilli();
				String unitName = fitsIn == 1 ? timeUnit.getNameOfOne() : timeUnit.getNameOfMany();
				timeStrs.add(fitsIn + " " + unitName + " ");
			}
		}
		
		return StringUtils.listToString(timeStrs);
	}
	
	@AllArgsConstructor
	public enum TimeUnit
	{
		SECOND(1000, "second", "seconds"),
		MINUTE(1000 * 60, "minute", "minutes"),
		HOUR(1000 * 60 * 60, "hour", "hours"),
		DAY(1000 * 60 * 60 * 24, "day", "days"),
		WEEK(1000 * 60 * 60 * 24 * 7, "week", "weeks"),
		MONTH(1000 * 60 * 60 * 24 * 30, "month", "months"),
		YEAR(1000 * 60 * 60 * 365, "year", "years");
		
		private @Getter long milli;
		private @Getter String nameOfOne;
		private @Getter String nameOfMany;
	}
}
