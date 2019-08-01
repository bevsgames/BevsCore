package games.bevs.core.commons.utils;

import java.util.List;

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
}
