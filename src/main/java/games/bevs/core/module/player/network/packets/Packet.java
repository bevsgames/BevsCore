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
	private @NonNull String server;
	private @NonNull String serverTo;
	private @NonNull String type;
	
	public String onBuildData()
	{
		return "{}";
	}
	
	public String build()
	{
		JsonObject jsonPacket = new JsonObject();
		jsonPacket.addProperty("server", this.getServer());
		jsonPacket.addProperty("serverTo", this.serverTo);
		jsonPacket.addProperty("type", this.getType());
		jsonPacket.addProperty("data", this.onBuildData());
		return jsonPacket.toString();
	}
}
