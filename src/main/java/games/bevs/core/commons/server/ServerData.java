package games.bevs.core.commons.server;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServerData 
{
	private String id;
	private String name;
	private String group;
	private String domain;
	
	private boolean disabledDatabase;
	private boolean onNetwork;
}
