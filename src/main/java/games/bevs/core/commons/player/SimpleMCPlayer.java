package games.bevs.core.commons.player;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class SimpleMCPlayer
{
	private @NonNull UUID uniquieId;
}
