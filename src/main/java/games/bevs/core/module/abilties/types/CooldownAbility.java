package games.bevs.core.module.abilties.types;

import java.util.HashMap;

import org.bukkit.entity.Player;

import games.bevs.core.commons.Duration;
import games.bevs.core.commons.Duration.TimeUnit;
import games.bevs.core.commons.io.Pair;
import games.bevs.core.commons.server.Console;
import games.bevs.core.commons.utils.StringUtils;
import games.bevs.core.module.cooldown.CooldownModule;
import games.bevs.core.module.cooldown.types.CooldownPlayer;

public class CooldownAbility extends Ability
{
	/**
	 * This is so we can just call a name to set a cooldown
	 */
	private HashMap<String, Pair<Integer, TimeUnit>> managedCooldowns = new HashMap<>();
	
	public CooldownModule getCooldownModule()
	{
		return this.getParent().getCooldownModule();
	}
	
	public void initCooldown(String name, int amount, TimeUnit timeUnit)
	{
		Pair<Integer, TimeUnit> pair = new Pair<>(amount, timeUnit);
		this.managedCooldowns.put(name.toLowerCase(), pair);
	}
	
	//========================{ Cooldown Default }========================\\
	
	public String getDefaultCooldownName()
	{
		return ("ability." + this.getName());
	}
	
	public void initDefaultCooldown( int amount, TimeUnit timeUnit)
	{
		initCooldown(getDefaultCooldownName(), amount, timeUnit);
	}
	
	public void setDefaultCooldown(Player player)
	{
		setCooldown(player, this.getDefaultCooldownName());
	}
	
	public boolean hasDefaultCooldown(Player player)
	{
		return hasCooldown(player, this.getDefaultCooldownName());
	}
	
	public boolean hasDefaultCooldownAndNotify(Player player)
	{
		return this.hasCooldownAndNotify(player, this.getDefaultCooldownName());
	}
	
	
	//========================{ Cooldown Normal }========================\\
	
	public void setCooldown(Player player, String name, int amount, TimeUnit timeUnit)
	{
		this.getCooldownModule().addCooldown(player.getUniqueId(), name.toLowerCase(), amount, timeUnit);
	}
	
	public void setCooldown(Player player, String name)
	{
		Pair<Integer, TimeUnit> pair = this.managedCooldowns.get(name.toLowerCase());
		if(pair == null)
		{
			for(int i = 0; i < 100; i++)
				Console.log("CooldownAbility", name + " doesn't have a cooldown length set, use initCooldown(name, amount, timeUnit)");
			return;
		}
		this.getCooldownModule().addCooldown(player.getUniqueId(), name.toLowerCase(), pair.getLeft(), pair.getRight());
	}
	
	public boolean hasCooldown(Player player, String name)
	{
		return this.getCooldownModule().hasCooldown(player.getUniqueId(), name.toLowerCase());
	}
	
	public boolean hasCooldownAndNotify(Player player, String name)
	{
		if(this.hasCooldown(player, name))
		{
			CooldownPlayer playerCooldown = this.getCooldownModule().getPlayerCooldownManager().getPlayer(player);
			if(playerCooldown == null)
			{
				System.out.println("ERROR : COOLDOWNPLAYER NOT FOUND");
				return true;
			}
			
			String displayName = StringUtils.capitalize(this.getName());
			Duration timeLeft = playerCooldown.getCooldown(name).getRemainingTime();
			timeLeft.add(1, TimeUnit.SECOND);//This is so they  don't see that it say's 0 for a second
			
			player.sendMessage(StringUtils.error(displayName, "Cooldown wears off in " + timeLeft.getFormatedTime() + "!"));
			return true;
		}
		return false;
	}
}
