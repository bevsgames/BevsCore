package games.bevs.core.client;

import java.net.InetAddress;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.core.commons.managers.PlayerManager;

public class ClientManager extends PlayerManager<Client> 
{

	public ClientManager(JavaPlugin plugin) 
	{
		super(plugin, true);
	}

	@Override
	public Client onPlayerCreation(UUID uniquieId, String username, InetAddress ipAddress) 
	{
		Client client = new Client(uniquieId, username);
		
		client.setRank(Rank.STAFF);

		client.setLoaded(true);
		return client;
	}

	@Override
	public boolean onPlayerDestruction(Client playerObj) 
	{
		return true;
	}

}
