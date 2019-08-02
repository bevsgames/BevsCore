package games.bevs.core.module.chat.types;

import java.util.Arrays;
import java.util.HashSet;

import lombok.Getter;

public class FilteredWords 
{
	@Getter
	private HashSet<String> words = new HashSet<>();
	
	public boolean isFiltered(String word)
	{
		return words.contains(word);
	}
	
	public void populate()
	{
		words.addAll(
				Arrays.asList(
						new String[]
								{
										"hypixel",
										"mineplex", 
								}
						)
				);
	}
}
