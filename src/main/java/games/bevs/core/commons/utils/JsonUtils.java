package games.bevs.core.commons.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

public class JsonUtils
{
	private static final JSONParser JSON_PARSER = new JSONParser();
	private static final Gson GSON = new Gson();
	
	public static JSONParser getJSONParser()
	{
		return JSON_PARSER;
	}
	
	public static String toJson(Object obj)
	{
		return GSON.toJson(obj);
	}
	
	public static <T> T fromJson(String obj, Class<T> clazz)
	{
		return GSON.fromJson(obj, clazz);
	}
}
