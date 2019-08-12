package games.bevs.core.module.player.network.packets;

import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Packet 
{
	public static final String UNKNOWN_SEND_TO = "global";
	
	private @NonNull String server;
	private @NonNull String serverTo;
	private @NonNull String type;
	
	public JsonObject onBuildData()
	{
		return new JsonObject();
	}
	
	public JsonObject build()
	{
		JsonObject jsonPacket = new JsonObject();
		jsonPacket.addProperty("server", this.getServer());
		jsonPacket.addProperty("serverTo", this.serverTo);
		jsonPacket.addProperty("type", this.getType());
		jsonPacket.add("data", this.onBuildData());
		return jsonPacket;
	}
}
