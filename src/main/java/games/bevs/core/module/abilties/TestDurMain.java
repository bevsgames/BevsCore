package games.bevs.core.module.abilties;

import games.bevs.core.commons.Duration;
import games.bevs.core.commons.Duration.TimeUnit;

public class TestDurMain {

	public static void main(String[] args)
	{
		Duration duration = new Duration(System.currentTimeMillis() + 1000);
		System.out.println(duration.getRemainingTime().getAsUit(TimeUnit.SECOND));
		System.out.println(duration.getRemainingTime().getFormatedTime());
		
		
		Duration duration2 = new Duration(System.currentTimeMillis() + 60000);
		System.out.println(duration2.getRemainingTime().getFormatedTime());
		
		Duration duration3 = new Duration(System.currentTimeMillis() + 60000 * 3);
		System.out.println(duration3.getRemainingTime().getFormatedTime());
		
		duration = new Duration(System.currentTimeMillis() + 60000 * 60 * 24);
		System.out.println(duration.getRemainingTime().getFormatedTime());
		
		duration = new Duration(System.currentTimeMillis() + 60000 * 60 * 24 * 8);
		System.out.println(duration.getRemainingTime().getFormatedTime());
		
		duration = new Duration(System.currentTimeMillis() + 60000 * 60 * 24 * 17);//wrong
		System.out.println(duration.getRemainingTime().getFormatedTime());
	}

}
