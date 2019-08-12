package games.bevs.core.commons.player.punishment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PunishLog 
{
	private long punishId;
	private long punishedInternalId;
	private long punisherInternalId;
	private long startTimestamp;
	private long expireTimestamp;
	private String reason;
}
