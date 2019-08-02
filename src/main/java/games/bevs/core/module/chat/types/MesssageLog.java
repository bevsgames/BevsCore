package games.bevs.core.module.chat.types;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MesssageLog 
{
	private String message;
	private long timestamp;
	
	public MesssageLog(String message)
	{
		this(message, System.currentTimeMillis());
	}
}
