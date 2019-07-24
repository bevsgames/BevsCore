package games.bevs.core.commons;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar 
{
    private static void sendToPlayer(String text, Player player)
    {
    	PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte) 2);
        (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packet);
    }

    public static void sendToAll(String text) 
    {
        Bukkit.getOnlinePlayers().forEach(pl -> sendToPlayer(text, pl));
    }

}