package games.bevs.core.module.combat;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.client.ClientModule;
import games.bevs.core.module.commands.CommandModule;

/**
 * Get rid of the respawn screen
 * 
 * <ul>
 * <li>Applies Knockback</li>
 * <li>Applies Damage</li>
 * </ul>
 */
@ModInfo(name = "Combat")
public class CombatModule extends Module {
	private CombatSettings combatSettings;

	public CombatModule(JavaPlugin plugin, CommandModule commandModule, ClientModule clientModule,
			CombatSettings combatSettings) {
		super(plugin, commandModule, clientModule);
		this.combatSettings = combatSettings;
	}

	public CombatModule(JavaPlugin plugin, CommandModule commandModule, ClientModule clientModule) {
		this(plugin, commandModule, clientModule, new CombatSettings());
	}

	@Override
	public void onEnable() {
		this.registerSelf();
	}

	public Vector getKnockback(LivingEntity victim, Entity attacker) {
		Vector knockback = attacker.getVelocity().subtract(victim.getVelocity()).setY(0).normalize();
		knockback.multiply(0.6D);
		knockback.setY(Math.abs(knockback.getY()));

		return knockback;
	}

	/**
	 * This function stops the death screen from appearing and allows us to have a cusotm damage
	 * event
	 * @param e
	 */
	@EventHandler
	public void onDamage(EntityDamageEvent e)
	{
		
		if(e.getEntity() instanceof LivingEntity)
		{
			LivingEntity livingEntity = (LivingEntity) e.getEntity();
			if(livingEntity.getWhenDamage() <= 0) 
			{
				double newHealth = livingEntity.getHealth() - e.getFinalDamage();
				
				if(newHealth <= 0.01)
				{
					livingEntity.sendMessage("You have died and have been respawned");
					newHealth = livingEntity.getMaxHealth();
				
					livingEntity.teleport(livingEntity.getWorld().getSpawnLocation());
				}
				else
				{
					if(e instanceof EntityDamageByEntityEvent)
					{
						EntityDamageByEntityEvent damgedByEntityE = (EntityDamageByEntityEvent) e;
						Entity entityDamager = damgedByEntityE.getDamager();
						Vector knockbackTotal = livingEntity.getVelocity().add(this.getKnockback(livingEntity, entityDamager));
						livingEntity.setVelocity(knockbackTotal);
					}
				}
				
				livingEntity.setWhenDamage(this.combatSettings.getDamageCooldownTicks());
				livingEntity.setHealth(newHealth);
			}
			
			e.setCancelled(true);
		}
	}
}
