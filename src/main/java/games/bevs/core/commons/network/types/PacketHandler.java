package games.bevs.core.commons.network.types;

import com.google.gson.JsonObject;

public interface PacketHandler<P extends Packet> 
{
	public void handleMessage(JsonObject jsonObject);
}
