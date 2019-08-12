package games.bevs.core.module.player.network.packets;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.utils.JsonUtils;
import lombok.Getter;

public class PlayerDataResponse extends Packet {
	public static final String PACKET_TYPE = "RESPONSE";

	private @Getter PlayerData playerData;

	public PlayerDataResponse(String server, String serverTo, PlayerData playerData) {
		super(server, serverTo, PACKET_TYPE);
		this.playerData = playerData;
	}

	@Override
	public JsonObject onBuildData() 
	{
		String json = JsonUtils.toJson(this.getPlayerData());
		JsonElement jsonElement = JsonUtils.toJsonFromString(json);
		return jsonElement.getAsJsonObject();
	}
}
