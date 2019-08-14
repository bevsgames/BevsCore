package games.bevs.core.commons.player;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class SimpleMCPlayer
{
	private @NonNull UUID uniquieId;
	
	public Player getPlayer()
	{
		return Bukkit.getPlayer(this.getUniquieId());
	}
}
