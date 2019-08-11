package games.bevs.core.module.player.network.packets;

import java.util.UUID;

import com.google.gson.JsonObject;

import lombok.Getter;

public class PlayerDataRequest extends Packet
{
	private static final String PACKET_TYPE = "REQUEST";
	
	private @Getter UUID uniqueId;
	private @Getter String username;

	public PlayerDataRequest(String server, UUID uniqueId, String username) 
	{
		super(server, null, PACKET_TYPE);

		this.uniqueId = uniqueId;
		this.username = username;
	}
	
	@Override
	public String onBuildData()
	{
		JsonObject jsonData = new JsonObject();
		jsonData.addProperty("uniqueId", this.getUniqueId().toString());
		jsonData.addProperty("username", this.getUsername());
		return jsonData.toString();
	}
}
