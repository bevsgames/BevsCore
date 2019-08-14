package games.bevs.core.commons.network;

import java.util.HashMap;

import com.google.gson.JsonObject;

import games.bevs.core.commons.network.types.Packet;
import games.bevs.core.commons.network.types.PacketHandler;
import games.bevs.core.commons.redis.subscribe.JedisSubscriptionHandler;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PacketListener implements JedisSubscriptionHandler<JsonObject> 
{
	private @NonNull PacketConnectionManager packetConnectionManager;
	
	private HashMap<String, PacketHandler<?>> packetHandlers = new HashMap<>();
	
	
	
	public void registerPacketHandler(Class<? extends Packet> clazz, PacketHandler<?> packetHandler)
	{
		this.packetHandlers.put(clazz.getSimpleName(), packetHandler);
	}
	
	public PacketHandler<?> getPacketHandler(Class<? extends Packet> clazz)
	{
		return getPacketHandler(clazz.getSimpleName());
	}
	
	public PacketHandler<?> getPacketHandler(String packetType)
	{
		return this.packetHandlers.get(packetType);
	}
	
	@Override
	public void handleMessage(JsonObject jsonObject)
	{
		String serverId = jsonObject.get("serverTo").getAsString();
		//if packet isn't globally or to us, ignore it
		if(serverId != Packet.UNKNOWN_SEND_TO 
				&& !serverId.equalsIgnoreCase(this.getPacketConnectionManager().getServerId()))
			return;
		
		String type = jsonObject.get("type").getAsString();
		PacketHandler<?> packetHandler = this.getPacketHandler(type);
		packetHandler.handleMessage(jsonObject);
	}

}
