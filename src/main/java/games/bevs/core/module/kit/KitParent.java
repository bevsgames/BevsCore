package games.bevs.core.module.kit;

import java.util.UUID;

import org.bukkit.entity.Player;

import games.bevs.core.module.kit.types.Kit;

public interface KitParent 
{
	public Kit createKit(String name);
	public Kit getKit(String name);
	public boolean hasKit(String name);
	public void deleteKit(String name);
	public Kit getPlayersKit(UUID uniqueId);
	public Kit getPlayersKit(Player player);
}
