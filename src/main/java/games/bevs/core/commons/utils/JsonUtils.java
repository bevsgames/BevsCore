package games.bevs.core.commons.utils;

import org.json.simple.parser.JSONParser;

public class JsonUtils
{
	private static final JSONParser JSON_PARSER = new JSONParser();
	
	public static JSONParser getJSONParser()
	{
		return JSON_PARSER;
	}
}
