package games.bevs.core.module.levels;

import games.bevs.core.BevsPlugin;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.chat.ChatModule;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.player.PlayerDataModule;
import lombok.Getter;

/**
 * Allows to give you levels for playing games
 * thus earning you more respect from other players
 */
@ModInfo(name = "Levels")
public class LevelsModule extends Module
{
	private static final int MAX_LEVEL = 1001;
	
	@Getter
	private ChatModule chatModule;
	
	private long[] experience = new long[MAX_LEVEL];
	private long[] totalExperience = new long[MAX_LEVEL];
	
	public LevelsModule(BevsPlugin plugin, CommandModule commandModule, PlayerDataModule clientModule, ChatModule chatModule) 
	{
		super(plugin, commandModule, clientModule);
		this.chatModule = chatModule;
	}
	
	@Override
	public void onEnable()
	{
		pregenerateLevels();
	}
	
	/**
	 * Pre-generates all the level mappings
	 */
	public void pregenerateLevels()
	{
		long sum = 0;
		for(int i = 0; i < MAX_LEVEL; i++)
		{
			experience[i] = this.getExpAtLevel(i);
			
			sum += experience[i];
			totalExperience[i] = sum;
		}
	}
	
	/**
	 * amount of exp required to get to level up
	 * Levels
	 * 0 = 90 exp  
	 * 1 = 115 exp  205 acc
	 * 2 = 190 exp  395 acc
	 * 3 = 315 exp	710 acc
	 * 4 = 490 exp  1200 acc
	 * 5 = 715 exp
	 * 6 = 990 exp
	 * @param level
	 * @return
	 */
	public long getExpAtLevel(int level)
	{
		int base = 90;
		long difficulty = 4l; // the larger, the easier
		return 100l * ((level * level) / difficulty) + base;
	}
	
	public long totalExpForLevel(int level)
	{
		long sum = 0;
		for(int curLevel = 0; curLevel <= level; curLevel++)
			sum += this.getExpAtLevel(curLevel);
		return sum;
	}

	/**
	 * Will calculate the level from the players currently raw exp
	 * 
	 * Note: this function is expensive to run and it only made for corrections
	 * @param rawExp
	 * @return
	 */
	public int rawExpToLevel(long rawExp)
	{
		for(int level = MAX_LEVEL; level >= 1; level++)
		{
			long fitInTimes = rawExp / this.totalExperience[level - 1];
			if(fitInTimes >= 1)
			{
				return level;
			}
		}
		return 0;
	}
}
