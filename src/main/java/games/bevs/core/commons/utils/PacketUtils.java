package games.bevs.core.commons.utils;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.Packet;

public class PacketUtils 
{
	public static void sendPacket(Player reciever, Packet<?>... packets)
	{
		for(Packet<?> packet : packets)
			((CraftPlayer) reciever).getHandle().playerConnection.sendPacket(packet);
	}
}
