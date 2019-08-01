package games.bevs.core.client;

import java.util.HashSet;
import java.util.UUID;

import games.bevs.core.commons.player.MCPlayer;
import lombok.Getter;
import lombok.Setter;

public class Client extends MCPlayer
{
	private @Getter @Setter Rank rank = Rank.NORMAL;
	private @Getter @Setter long joinTimestamp;
	
	private HashSet<String> flags = new HashSet<>();
	
	private @Getter @Setter boolean loaded = false;

	public Client(UUID uniquieId, String username) {
		super(uniquieId, username);
	}
	
	public Client(UUID uniquieId) {
		super(uniquieId);
	}

}
