package games.bevs.core.module.player.network.packets;

import games.bevs.core.commons.player.PlayerData;
import games.bevs.core.commons.utils.JsonUtils;
import lombok.Getter;

public class PlayerDataResponse extends Packet
{
	private static final String PACKET_TYPE = "RESPONSE";
	
	public @Getter PlayerData playerData;

	public PlayerDataResponse(String server, String serverTo, PlayerData playerData) 
	{
		super(server, serverTo, PACKET_TYPE);
		this.playerData = playerData;
	}

	@Override
	public String onBuildData()
	{
		return JsonUtils.toJson(this.getPlayerData());
	}
}
