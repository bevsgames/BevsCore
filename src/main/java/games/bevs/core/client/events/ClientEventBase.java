package games.bevs.core.client.events;

import games.bevs.core.client.Client;
import games.bevs.core.commons.event.EventBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class ClientEventBase extends EventBase
{
	private @Getter Client client;
}
