package games.bevs.core.commons.player.rank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RankLog
{
	private long rankId;
	private long ownerInternalId;
	private long giverInternalId;
	private long startTimestamp;
	private long expireTimestamp;
	private String reason;
}
