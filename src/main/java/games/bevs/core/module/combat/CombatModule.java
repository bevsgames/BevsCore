package games.bevs.core.module.combat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.utils.PacketUtils;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.player.PlayerDataModule;
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
public class CombatModule extends Module 
{
	private static final double PROJECTILE_KNOCKBACK_REDUCTER = 0.5;
	
	private CombatSettings combatSettings;

	public CombatModule(BevsPlugin plugin, CommandModule commandModule, PlayerDataModule clientModule,
			CombatSettings combatSettings) {
		super(plugin, commandModule, clientModule);
		this.combatSettings = combatSettings;
	}

	public CombatModule(BevsPlugin plugin, CommandModule commandModule, PlayerDataModule clientModule) {
		this(plugin, commandModule, clientModule, new CombatSettings());
	}

	@Override
	public void onEnable() {
		this.registerSelf();
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

	/**
	 * Play a red effect on the entity
	 * @param livingEntity
	 */
	private void broadcastDamageEffect(LivingEntity livingEntity)
	{
		Location loc = livingEntity.getLocation();
		
		String sound = PacketUtils.getLivingEntitySound(livingEntity);
		
		if(livingEntity instanceof Player)
		{
			Player player = ((Player)livingEntity);
			PacketUtils.playDamageAlert(player);
		}
		livingEntity.getNearbyEntities(5, 5, 5).stream().filter(entity -> entity instanceof Player).forEach(player -> PacketUtils.playSound((Player) player, sound, loc.getX(), loc.getY(), loc.getZ(), 1f, 1f));
		Bukkit.getOnlinePlayers().forEach(player -> playDamageEffect(livingEntity, player));
	}
	
	private void playDamageEffect(LivingEntity livingEntity, Player reciever)
	{
		PacketUtils.sendPacket(reciever, (new PacketPlayOutAnimation(((CraftEntity) livingEntity).getHandle(), 1)));
	}
	
	private void triggerPlayerDeathEvent(Location deathLoc, Player player)
	{
		List<ItemStack> drops = new ArrayList<>();
		
		drops.addAll(Arrays.asList(player.getInventory().getContents()));
		drops.addAll(Arrays.asList(player.getInventory().getArmorContents()));
		
		PlayerDeathEvent e = new PlayerDeathEvent(player, drops, (int) player.getExp(), 0, 0, 0, player.getName() + " died.");
		Bukkit.getPluginManager().callEvent(e);
		
		if(deathLoc.getWorld().getGameRuleValue("keepinventory").equalsIgnoreCase("false"))
		{
			player.setExp(e.getNewExp());
			player.setLevel(e.getNewLevel());
			
			if(e.getDroppedExp() > 0)
			{
				ExperienceOrb orb = deathLoc.getWorld().spawn(deathLoc, ExperienceOrb.class);
				orb.setExperience(e.getDroppedExp());
			}
			
			for (ItemStack stack : e.getDrops()) 
			{
			      if (stack == null || stack.getType() == Material.AIR || stack.getAmount() == 0)
			    	  continue; 
			      deathLoc.getWorld().dropItemNaturally(deathLoc, stack);
			} 
			
			player.getInventory().clear();
		}
		
		if(e.getDeathMessage() != null)
			Bukkit.broadcastMessage(e.getDeathMessage());
	}
	
	private void respawn(LivingEntity livingEntity)
	{
		if(livingEntity instanceof Player)
		{
			Player player = ((Player)livingEntity);
			triggerPlayerDeathEvent(livingEntity.getLocation(), player);
		}
		livingEntity.sendMessage("You have died and have been respawned");
		livingEntity.setHealth(livingEntity.getMaxHealth());
		livingEntity.teleport(livingEntity.getWorld().getSpawnLocation());
	}
	
	/**
	 * This function stops the death screen from appearing and allows us to have a cusotm damage
	 * event
	 * @param e
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onDamage(EntityDamageEvent e)
	{
		if(e.getEntity() instanceof LivingEntity)
		{
			LivingEntity livingEntity = (LivingEntity) e.getEntity();
			Entity entityDamager = null;
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
				entityDamager = damgedByEntityE.getDamager();
				knockbackTotal.add(this.getKnockback(livingEntity, entityDamager));
				
				//Check if they were shot
				if(entityDamager instanceof LivingEntity)
				{
					entityDamager = (LivingEntity) entityDamager;
				}
				else if(entityDamager instanceof Projectile)
				{
					
					ProjectileSource shooter = ((Projectile)entityDamager).getShooter();
					if(shooter != null && shooter instanceof LivingEntity)
						entityDamager = (LivingEntity)shooter;
					knockbackTotal = entityDamager.getVelocity();
					knockbackTotal = knockbackTotal.multiply(PROJECTILE_KNOCKBACK_REDUCTER);
					entityDamager.remove();//remove arrow
				}
			}
			CustomDamageEvent customDamageEvent = new CustomDamageEvent(e.getCause(), e.getFinalDamage(), knockbackTotal, livingEntity, entityDamager);
			customDamageEvent.call();
			
			if(!customDamageEvent.isCancelled()) 
			{
				livingEntity.setWhenDamage(this.combatSettings.getDamageCooldownTicks());
				double newHealth = livingEntity.getHealth();
				
				newHealth -= customDamageEvent.getDamage();
				
				broadcastDamageEffect(livingEntity);
				if(newHealth <= 0.01)
				{
					//Player died
					respawn(livingEntity);
					e.setCancelled(true);
					return;
				}
				
				livingEntity.setHealth(newHealth);
				if(!customDamageEvent.isCancelKnockback())
					livingEntity.setVelocity(customDamageEvent.getKnockback());
			}
			
			e.setCancelled(true);
		}
	}
}
