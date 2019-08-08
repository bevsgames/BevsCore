package games.bevs.core.commons;

import java.util.ArrayList;

import games.bevs.core.commons.utils.MathUtils;
import games.bevs.core.commons.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Duration 
{
	public static final Duration ZERO_DURATION = new Duration(0);
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
	
	public Duration getRemainingTime()
	{
		Duration remainingTime = new Duration(this.timeInMills - System.currentTimeMillis());
		return remainingTime;
	}
	
	public double getAsUit(TimeUnit timeUnit)
	{
		return MathUtils.round((double) this.getMillis() / timeUnit.getMilli(), 1);
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
				timeStrs.add( fitsIn + " " + unitName);
			}
		}
		
		//Bit of a hack but it'll just do it like this for now
		String outputStr = StringUtils.listToString(timeStrs);
		if(outputStr.length() > 0 && outputStr.charAt(outputStr.length() - 1) == ' ')
			outputStr = outputStr.substring(0, outputStr.length() - 1);
		return outputStr;
	}
	
	@AllArgsConstructor
	public enum TimeUnit
	{
		SECOND(1000l, "second", "seconds"),
		MINUTE(1000l * 60, "minute", "minutes"),
		HOUR(1000l * 60 * 60, "hour", "hours"),
		DAY(1000l * 60 * 60 * 24, "day", "days"),
		WEEK(1000l * 60 * 60 * 24 * 7, "week", "weeks"),
		MONTH(1000l * 60 * 60 * 24 * 30, "month", "months"),
		YEAR(1000l * 60 * 60 * 24 * 365, "year", "years");
		
		private @Getter long milli;
		private @Getter String nameOfOne;
		private @Getter String nameOfMany;
	}
}
