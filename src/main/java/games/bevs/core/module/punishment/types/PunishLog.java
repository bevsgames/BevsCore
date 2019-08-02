package games.bevs.core.module.punishment.types;

import games.bevs.core.module.punishment.PunishType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PunishLog 
{
	private long punishId;
	private int clientId;
	private PunishType type;
	private String reason;
	
	private long timestamp;
	private long expires;
	private boolean active;
	
	private int punisherId;

	public PunishLog(int clientId, long punishId, PunishType type, String reason, int punisherId) {
		this.clientId = clientId;
		this.punishId = punishId;
		this.type = type;
		this.reason = reason;
		this.punisherId = punisherId;
		
		long now = System.currentTimeMillis();
		this.timestamp = now;
		this.expires = now;
		this.active = false;
	}
}
