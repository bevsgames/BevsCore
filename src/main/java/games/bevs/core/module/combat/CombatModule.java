package games.bevs.core.module.combat;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import games.bevs.core.commons.utils.PacketUtils;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.client.ClientModule;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import games.bevs.core.module.commands.CommandModule;
import games.bevs.core.module.ticker.UnitType;
import games.bevs.core.module.ticker.UpdateEvent;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;

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
	
	@EventHandler
	public void onTick(UpdateEvent e)
	{
		if(e.getType() != UnitType.SECOND) return;
		Bukkit.getOnlinePlayers().forEach(player ->
		{
			Location loc = player.getLocation();
			Bukkit.broadcastMessage("X: " + -MathHelper.sin(loc.getYaw()) * 5 + " Z: " + MathHelper.cos(loc.getYaw()) *  5);
		});
		
	}
	
	private int getKnockbackEnchantmentLevel(LivingEntity attacker)
	{
		if(attacker instanceof Player)
		{
			Player player = (Player) attacker;
			if(player.getItemInHand() != null)
				return player.getItemInHand().getEnchantmentLevel(Enchantment.KNOCKBACK);
			
		}
		else if(attacker != null && attacker.getEquipment() != null && attacker.getEquipment().getItemInHand() != null)
			return attacker.getEquipment().getItemInHand().getEnchantmentLevel(Enchantment.KNOCKBACK);
		return 0;
	}

	private Vector getKnockback(LivingEntity victim, Entity attacker) {
		Vector knockback = victim.getVelocity();
		
		int multi = 1;
		float yaw = attacker.getLocation().getYaw();
		
		if(attacker instanceof LivingEntity)
		{
			multi += getKnockbackEnchantmentLevel((LivingEntity) attacker);

			if(attacker instanceof Player && ((Player) attacker).isSprinting())
			{
				multi++;
				((Player) attacker).setSprinting(false);
			}
		}
		
		Vector baseKnockback = new Vector(-MathHelper.sin(yaw * 3.1415927F / 180.0F) * multi, 1, (MathHelper.cos(yaw * 3.1415927F / 180.0F) * multi)); 
		knockback.add(baseKnockback);
		knockback.multiply(0.6D);
		knockback.setY(0.4);
		  

		return knockback;
	}

	private void broadcastDamageEffect(LivingEntity livingEntity)
	{
		Bukkit.getOnlinePlayers().forEach(player -> playDamageEffect(livingEntity, player));
	}
	
	private void playDamageEffect(LivingEntity livingEntity, Player reciever)
	{
		PacketUtils.sendPacket(reciever, (new PacketPlayOutAnimation(((CraftEntity) livingEntity).getHandle(), 1)));
	}
	
	private void respawn(LivingEntity livingEntity)
	{
		livingEntity.sendMessage("You have died and have been respawned");
		livingEntity.setHealth(livingEntity.getMaxHealth());
		livingEntity.teleport(livingEntity.getWorld().getSpawnLocation());
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
			LivingEntity livingEntityDamager = null;
			Vector knockbackTotal = livingEntity.getVelocity();
			
			//They are on damage cooldown
			if(livingEntity.getWhenDamage() > 0) 
			{
				e.setCancelled(true);
				return;
			}
			
			
			if(e instanceof EntityDamageByEntityEvent)
			{
				EntityDamageByEntityEvent damgedByEntityE = (EntityDamageByEntityEvent) e;
				Entity entityDamager = damgedByEntityE.getDamager();
				knockbackTotal.add(this.getKnockback(livingEntity, entityDamager));
				
				//Check if they were shot
				if(entityDamager instanceof LivingEntity)
				{
					livingEntityDamager = (LivingEntity) entityDamager;
				}
				else if(entityDamager instanceof Projectile)
				{
					ProjectileSource shooter = ((Projectile)entityDamager).getShooter();
					if(shooter != null && shooter instanceof LivingEntity)
						livingEntityDamager = (LivingEntity)shooter;
				}
			}
			CustomDamageEvent customDamageEvent = new CustomDamageEvent(e.getFinalDamage(), knockbackTotal, livingEntity, livingEntityDamager);
			customDamageEvent.call();
			
			if(!customDamageEvent.isCancelled()) 
			{
				livingEntity.setWhenDamage(this.combatSettings.getDamageCooldownTicks());
				double newHealth = livingEntity.getHealth();
				
				newHealth -= customDamageEvent.getDamage();
				
				if(newHealth <= 0.01)
				{
					//Player died
					respawn(livingEntity);
					e.setCancelled(true);
					return;
				}
				livingEntity.setHealth(newHealth);
				livingEntity.setVelocity(customDamageEvent.getKnockback());
				broadcastDamageEffect(livingEntity);
			}
			
			e.setCancelled(true);
		}
	}
}
