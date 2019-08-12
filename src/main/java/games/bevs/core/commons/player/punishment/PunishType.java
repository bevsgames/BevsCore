package games.bevs.core.commons.player.punishment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PunishType 
{
	MUTE(false), KICK(true), BAN(true), BLACKLIST(true);
	
	private @Getter boolean kick;
}
