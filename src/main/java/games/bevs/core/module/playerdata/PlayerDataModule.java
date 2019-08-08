package games.bevs.core.module.playerdata;

import java.util.HashMap;
import java.util.UUID;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;

@ModInfo(name = "PlayerData")
public class PlayerDataModule extends Module
{
	private HashMap<UUID, PlayerData> players = new HashMap<>();
	
	public PlayerDataModule(BevsPlugin plugin, CommandModule commandModule)
	{
		super(plugin, commandModule);
		
	}
	
	public PlayerData registerPlayerData(PlayerData playerData)
	{
		return this.players.put(playerData.getUniqueId(), playerData);
	}
	
	public PlayerData getPlayerData(UUID uniqueId)
	{
		return this.players.get(uniqueId);
	}
	
	public void unregisterPlayerData(UUID uniqueId)
	{
		this.players.remove(uniqueId);
	}
}
