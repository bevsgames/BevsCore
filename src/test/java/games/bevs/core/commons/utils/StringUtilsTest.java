package games.bevs.core.commons.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class StringUtilsTest 
{
	@Test
	public void listToStringTest()
	{
		String formedListNormal = StringUtils.listToString(Arrays.asList("cats", "dogs", "cows"));
		String formedListOne = StringUtils.listToString(Arrays.asList("cats"));
		String formedListFive = StringUtils.listToString(Arrays.asList("cats", "dogs", "cows", "sheep", "pig"));
		
		
		assertEquals(formedListNormal, "cats, dogs and cows");
		assertEquals(formedListOne, "cats");
		assertEquals(formedListFive, "cats, dogs, cows, sheep and pig");
	}
}
