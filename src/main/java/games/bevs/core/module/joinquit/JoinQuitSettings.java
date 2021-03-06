package games.bevs.core.module.joinquit;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinQuitSettings 
{
	private String joinMessage = null;
	private String quitMessage = null;
	
	private ArrayList<String> welcomeMessages = new ArrayList<>();
}
