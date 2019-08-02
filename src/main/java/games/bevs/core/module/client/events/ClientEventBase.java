package games.bevs.core.module.client.events;

import games.bevs.core.commons.event.EventBase;
import games.bevs.core.module.client.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ClientEventBase extends EventBase
{
	private @Getter Client client;
}
