package games.bevs.core.commons.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class MCNameUtils 
{
	private static final String PROFILE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";
	
	public static String getUsername(UUID uuid) throws IOException, ParseException 
	{
		HttpURLConnection connection = (HttpURLConnection) (new URL(PROFILE_URL + uuid.toString().replace("-", ""))).openConnection();
		JSONObject response = (JSONObject) JsonUtils.getJSONParser().parse(new InputStreamReader(connection.getInputStream()));
		
		String name = (String) response.get("name");
		if (name == null) return null;
		String cause = (String) response.get("cause");
		String errorMessage = (String) response.get("errorMessage");
		if (cause != null && cause.length() > 0) throw new IllegalStateException(errorMessage);
		return name;
	}
}
