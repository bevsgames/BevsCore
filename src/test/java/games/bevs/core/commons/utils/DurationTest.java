package games.bevs.core.commons.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import games.bevs.core.commons.Duration;

public class DurationTest 
{
	@Test
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