package games.bevs.core.module.cooldown.types;

import java.util.HashMap;
import java.util.UUID;

import games.bevs.core.commons.Duration;
import games.bevs.core.commons.Duration.TimeUnit;
import games.bevs.core.commons.player.SimpleMCPlayer;
import lombok.Getter;

public class CooldownPlayer extends SimpleMCPlayer
{
	private @Getter HashMap<String, Duration> cooldowns;
	
	public CooldownPlayer(UUID uniquieId) 
	{
		super(uniquieId);
		this.cooldowns = new HashMap<>();
	}
	
	public void setCooldown(String name, int amount, TimeUnit timeUnits)
	{
		Duration nowDuration = new Duration(System.currentTimeMillis());
		nowDuration.add(amount, timeUnits);
		//when cooldown wares off
		this.cooldowns.put(name, nowDuration);
	}
	
	/**
	 * Get the length of time 
	 * @param name
	 * @return
	 */
	public Duration getCooldown(String name)
	{
		return this.cooldowns.getOrDefault(name, Duration.ZERO_DURATION);
	}
	
	/**
	 * @param name
	 * @return if player cannot do action
	 */
	public boolean hasCooldown(String name)
	{
		return this.getCooldown(name).getMillis() < System.currentTimeMillis();
	}
	
	public void removeCooldown(String cooldownName)
	{
		this.cooldowns.remove(cooldownName);
	}
	
	public void clear()
	{
		this.cooldowns.clear();
	}
}
