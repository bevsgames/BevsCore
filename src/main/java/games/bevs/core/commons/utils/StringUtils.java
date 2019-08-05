package games.bevs.core.commons.utils;

import java.util.Arrays;
import java.util.List;

import games.bevs.core.commons.CC;

public class StringUtils 
{
	public static String listToString(List<String> listStr)
	{
		StringBuilder strBuilder = new StringBuilder();
		for(int i = 0; i < listStr.size(); i++)
		{
			if(i != 0) strBuilder.append(" ");
			
			String element = listStr.get(i);
			strBuilder.append(element);
			
			if(i < (listStr.size() - 1))
				strBuilder.append(( i == (listStr.size() - 1) ? "," : "and"));
		}
		return strBuilder.toString();
	}
	
	/**
	 * will turn a string into a boolean
	 * @param stateStr
	 * @return
	 */
	public static boolean toBoolean(String stateStr)
	{
		return Arrays.asList(new String[] {"yes", "true", "on"}).contains(stateStr.toLowerCase());
	}
	
	public static String error(String header, String message)
	{
		return CC.red + header + CC.bRed + " !! " + CC.r + CC.gray + message;
	}
	
	public static String success(String header, String message)
	{
		return CC.green + header + CC.bRed + " ** " + CC.r + CC.gray + message;
	}
}
