package games.bevs.core.module.client;

import java.util.HashSet;
import java.util.UUID;

import games.bevs.core.commons.Rank;
import games.bevs.core.commons.player.MCPlayer;
import lombok.Getter;
import lombok.Setter;

/**
 * Player store
 *
 */
@Getter
@Setter
public class Client extends MCPlayer {
	private @Getter int internalId;
	private @Getter @Setter Rank rank = Rank.NORMAL;
	private @Getter @Setter long joinTimestamp;

	private @Getter @Setter long bannedTill = -1;
	private @Getter @Setter long mutedTill = -1;

	private @Getter @Setter boolean loaded = false;
	private HashSet<String> flags = new HashSet<>();

	public Client(UUID uniquieId, String username) {
		super(uniquieId, username);
	}

}
