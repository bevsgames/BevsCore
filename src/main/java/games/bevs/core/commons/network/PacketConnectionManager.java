package games.bevs.core.commons.network;

import com.google.gson.JsonObject;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.network.types.Packet;
import games.bevs.core.commons.network.types.PacketHandler;
import games.bevs.core.commons.redis.JedisPublisher;
import games.bevs.core.commons.redis.JedisSettings;
import games.bevs.core.commons.redis.JedisSubscriber;
import lombok.Getter;

@Getter
public class PacketConnectionManager 
{
	private String channel;
	private String serverId;
	private BevsPlugin plugin;
	
	private PacketListener packetListener;
	
	private JedisPublisher<JsonObject> messagesPublisher;
	private JedisSubscriber<JsonObject> messagesSubscriber;
	
	public PacketConnectionManager(BevsPlugin plugin, String serverId, String channel, JedisSettings settings)
	{
		this.plugin = plugin;
		this.serverId = serverId;
		this.channel = channel;
		
		packetListener = new PacketListener(this);
		
		messagesPublisher = new JedisPublisher<JsonObject>(settings, channel);
		messagesSubscriber = new JedisSubscriber<JsonObject>(settings, channel, JedisSubscriber.JSON_GENERATOR, packetListener );
	}
	
	public <T extends Packet> void registerPacketHandler(Class<T> clazz, PacketHandler<T> packetHandler)
	{
		packetListener.registerPacketHandler(clazz, packetHandler);
	}
	
	public void sendPacket(Packet packet)
	{
		JsonObject jsonData = new JsonObject();
		
		jsonData.addProperty("serverFrom", packet.getServer());
		jsonData.addProperty("serverTo", packet.getServerTo());
		jsonData.add("data", packet.onBuildData());
		
		messagesPublisher.write(packet.build());
	}
}
