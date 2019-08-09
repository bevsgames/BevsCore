package games.bevs.core.module.antihax;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.CC;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;

/**
 * BevsAntiHax
 * 
 * This is a lightweight anticheat made for the BevsGames
 * So that we could make special made checks for popular
 * Hack Clients. 
 * 
 * We don't need to be the best, we just need to get rid
 * of a good number of hackers
 * 
 * We first will focus on blatant hacks
 * 
 * Checks
 * <ul>
 * 	<li>Fly</li>
 * 	<li>Speed</li>
 * 	<li>Timer</li>
 *  <li>NoFall</li>
 * </ul>
 * 
 * Note: We don't want peopld to think this anti-cheat is
 * unbeatable or something like that because it encourages 
 * people to make client specially for it.
 */
@ModInfo(name = "BAH")
public class AntiHaxModule extends Module
{

	public AntiHaxModule(BevsPlugin plugin, CommandModule commandModule) 
	{
		super(plugin, commandModule);
	}
	
	@Override
	public void onEnable()
	{
		
	}
	
	public String message(String message)
	{
		return CC.darkPurple + "= BAH = " + message;
	}
}
