package games.bevs.core.commons.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import games.bevs.core.commons.Duration;

public class DurationTest 
{
	@Test 
	public void durationStringTest()
	{
		String duration1mStr = "1m"; //1 minute
		String duration1hStr = "1h"; //1 hours
		String duration1dStr = "1d"; //1 day
		String duration1wStr = "1w"; //1 week
		String duration1MStr = "1M"; //1 Month
		String duration1yStr = "1y"; //1 Year
		
		String duration1m1m1mStr = "1m1m1m"; //3 minute
		
		String duration3h3mStr = "3h 2m 1m"; //3 hours and 3 minutes
		
		Duration duration1m = new Duration(duration1mStr);
		Duration duration1h = new Duration(duration1hStr);
		Duration duration1d = new Duration(duration1dStr);
		Duration duration1w = new Duration(duration1wStr);
		Duration duration1M = new Duration(duration1MStr);
		Duration duration1y = new Duration(duration1yStr);
		
		Duration duration1m1m1m = new Duration(duration1m1m1mStr);
		Duration duration3h3m = new Duration(duration3h3mStr);
		
		
		assertEquals(duration1m.getMillis(), 60_000l);
		assertEquals(duration1h.getMillis(), 3_600_000l);
		assertEquals(duration1d.getMillis(), 86_400_000l);
		assertEquals(duration1w.getMillis(), 604_800_000l);
		assertEquals(duration1M.getFormatedTime(), "1 month");
		assertEquals(duration1y.getFormatedTime(), "1 year");
		
		assertEquals(duration1m1m1m.getFormatedTime(), "3 minutes");
		assertEquals(duration3h3m.getFormatedTime(), 180_000l);
	}
	
//	@Test
    public void durationToTimeforate() 
	{
		long second = 1000;
		long minute = second * 60;
		long hour = minute *  60;
		long day = hour *  24;
		long week = day *  7;
		long month = day * 30;
		long year = day * 365;
		
		Duration durationSecond = new Duration(minute);
		Duration durationMinuteAndSecond = new Duration(minute + second);
		Duration durationTenMinutes = new Duration(minute *  10);
		Duration durationHour = new Duration(hour);
		Duration durationTwentyThreeHour = new Duration(day - hour);
		Duration durationWeekAndADay = new Duration(week + day);
		Duration durationMonthAndADay = new Duration(month + day);
		Duration durationYearAndADay = new Duration(year + day);
		Duration durationYearAndADayHourMinuteSecond = new Duration(year + day + hour);
		
		assertEquals(durationSecond.getFormatedTime(), "1 minute");
		assertEquals(durationMinuteAndSecond.getFormatedTime(), "1 minute and 1 second");
		assertEquals(durationTenMinutes.getFormatedTime(), "10 minutes");
		assertEquals(durationHour.getFormatedTime(), "1 hour");
		assertEquals(durationTwentyThreeHour.getFormatedTime(), "23 hours");
		assertEquals(durationWeekAndADay.getFormatedTime(), "1 week and 1 day");
		assertEquals(durationWeekAndADay.getFormatedTime(), "1 week and 1 day");
		assertEquals(durationMonthAndADay.getFormatedTime(), "1 month and 1 day");
		assertEquals(durationYearAndADay.getFormatedTime(), "1 year and 1 day");
		assertEquals(durationYearAndADayHourMinuteSecond.getFormatedTime(), "1 year, 1 day and 1 hour");
    }
}