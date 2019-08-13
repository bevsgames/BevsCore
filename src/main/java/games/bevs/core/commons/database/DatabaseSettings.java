package games.bevs.core.commons.database;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatabaseSettings 
{
	private  String url;
	private String port;
	private String database;
	private String username;
	private String password;
}
