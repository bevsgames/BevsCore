package games.bevs.core.module.combat.event;

import java.util.ArrayList;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;

import games.bevs.core.commons.event.EventBase;
import games.bevs.core.module.combat.types.DamageModifier;
import games.bevs.core.module.combat.types.KnockbackMultipler;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CustomDamageEvent extends EventBase implements Cancellable
{
	private ArrayList<String> cancellers = new ArrayList<>();
	
	private ArrayList<KnockbackMultipler> knockbackMultiplers = new ArrayList<>();
	private ArrayList<DamageModifier> damageModifiers = new ArrayList<>();
	
	private Entity attackerEntity;
	
	private LivingEntity victimLivingEntity;
	private LivingEntity attackerLivingEntity;
	
	private boolean victimIsPlayer = false;
	private boolean attackerIsPlayer = false;
	
	private Player victimPlayer;
	private Player attackerPlayer;
	
	private @Setter double initDamage = 0.0d;
	private @Setter Vector initKnockback;
	
	private @Setter @Getter double maxDamage = 10000d;
	
	private DamageCause initCause;
	
	public CustomDamageEvent(DamageCause initCause, double initDamage, Vector initKnockback, LivingEntity victimLivingEntity, Entity attackerEntity)
	{
		this.initCause = initCause;
		this.initDamage = initDamage;
		this.initKnockback = initKnockback;
		
		
		this.victimLivingEntity = victimLivingEntity;
		this.attackerEntity = attackerEntity;
		
		if(this.attackerEntity instanceof LivingEntity)
			this.attackerLivingEntity = (LivingEntity) this.attackerEntity;
		
		if(victimLivingEntity instanceof Player)
		{
			this.victimPlayer = (Player)victimLivingEntity;
			this.victimIsPlayer = true;
		}
		
		if(attackerLivingEntity instanceof Player)
		{
			this.attackerPlayer = (Player)attackerLivingEntity;
			this.attackerIsPlayer = true;
		}
	}
	
	/**
	 * State if they entity will die after all damage from this event is called
	 * @return if entity will die
	 */
	public boolean willDie()
	{
		return (this.getVictimLivingEntity().getHealth() - this.getDamage()) < 0.01;
	}
	
	public boolean hasAttacker()
	{
		return this.attackerLivingEntity != null;
	}
	
	/**
	 * Add a bit more damage to the entity
	 * @param damage
	 * @param source
	 * @param reason
	 */
	public void addDamage(double damage, String source, String reason)
	{
		this.getDamageModifiers().add(new DamageModifier(source, reason, damage));
	}
	
	public void addKnockback(double knockbackMulti, String source)
	{
		this.getKnockbackMultiplers().add(new KnockbackMultipler(source, knockbackMulti));
	}
	
	/**
	 * Knockback after all multipliers are applied
	 * @return
	 */
	public Vector getKnockback()
	{
		Vector knockback = this.getInitKnockback().clone();
		for(KnockbackMultipler multiper : this.getKnockbackMultiplers())
			knockback.multiply(multiper.getKnockbackMultipler());
		return knockback;
	}
	
	public double getDamage()
	{
		double damage = this.getInitDamage();
		for(DamageModifier modifier : this.getDamageModifiers())
			damage += modifier.getDamage();
		if(damage > this.getMaxDamage())
			return this.getMaxDamage();
		return damage;
	}
	
	public void setCancelled(String reason)
	{
		this.cancellers.add(reason);
	}

	@Override
	public boolean isCancelled()
	{
		return !this.cancellers.isEmpty();
	}

	@Deprecated
	@Override
	public void setCancelled(boolean arg0)
	{
		this.cancellers.add("Someone didn't give a reason rip");
	}
	
}
