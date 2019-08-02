package games.bevs.core.module.client.events;

import games.bevs.core.module.client.Client;

public class ClientLoadedEvent extends ClientEventBase
{

	public ClientLoadedEvent(Client client) 
	{
		super(client);
	}

}
