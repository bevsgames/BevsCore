package games.bevs.core.module.cooldown;

import java.util.UUID;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.Duration;
import games.bevs.core.commons.Duration.TimeUnit;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.cooldown.command.CooldownCommand;
import games.bevs.core.module.cooldown.types.CooldownPlayer;
import lombok.Getter;

/**
 * Be able to apply cooldowns to players
 * @author heathlogancampbell
 *
 */
@ModInfo(name = "Cooldowns")
public class CooldownModule extends Module
{
	private @Getter PlayerCooldownManager playerCooldownManager;
	
	public CooldownModule(BevsPlugin plugin, CommandModule commandModule) 
	{
		super(plugin, commandModule);
	}

	@Override
	public void onEnable()
	{
		this.playerCooldownManager = new PlayerCooldownManager(this.getPlugin());
		
		this.registerCommand(new CooldownCommand(this));
	}
	
	public void addCooldown(UUID uuid, String cooldownName, int amount, TimeUnit timeUnit)
	{
		CooldownPlayer cooldownPlayer = this.getPlayerCooldownManager().getPlayer(uuid);
		if(cooldownPlayer == null)
		{
			this.getPlayerCooldownManager().registerPlayer(uuid, null, null);
			cooldownPlayer = this.getPlayerCooldownManager().getPlayer(uuid);
		}
		
		cooldownPlayer.setCooldown(cooldownName, amount, timeUnit);
	}
	
	public boolean hasCooldown(UUID uuid, String cooldownName)
	{
		CooldownPlayer cooldownPlayer = this.getPlayerCooldownManager().getPlayer(uuid);
		if(cooldownPlayer == null) return false;
		return this.getPlayerCooldownManager().getPlayer(uuid).hasCooldown(cooldownName);
	}
	
	public Duration getRemainCooldown(UUID uuid, String cooldownName)
	{
		CooldownPlayer cooldownPlayer = this.getPlayerCooldownManager().getPlayer(uuid);
		if(cooldownPlayer == null) return Duration.ZERO_DURATION;
		return this.getPlayerCooldownManager().getPlayer(uuid).getCooldown(cooldownName).getRemainingTime();
	}
}
