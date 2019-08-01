package games.bevs.core.module.ticker;

import games.bevs.core.commons.event.EventBase;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Thanks Mineplex <2
 */
@RequiredArgsConstructor
public class UpdateEvent extends EventBase
{
	private @NonNull @Getter @Setter UnitType type;
}
