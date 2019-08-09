package games.bevs.core.module.abilties.abilities;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(name = "Worm", author = "teddyyy", description = { "Break dirt blocks instantly, fall damage",
		"is reduced when landing on dirt." })
public class WormAbility extends Ability {

	public Material blockMaterial = Material.DIRT;

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (hasAbility(player)) {
			if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
				if (e.getClickedBlock() != null) {
					if (e.getClickedBlock().getType() == blockMaterial) {
						if (player.getFoodLevel() < 20) {
							player.setFoodLevel(player.getFoodLevel() + 1);
						} else
							player.setFoodLevel(20);
						if (player.getHealth() < 20)
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 0));

						e.getClickedBlock().breakNaturally();
						e.getClickedBlock().getWorld().playEffect(e.getClickedBlock().getLocation(), Effect.STEP_SOUND,
								blockMaterial);
					}
				}
			}
		}
	}

	@EventHandler
	public void onFall(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player && hasAbility((Player) e.getEntity())) {
			if (e.getCause() == DamageCause.FALL) {
				if (e.getEntity().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == blockMaterial) {
					e.setDamage(1.0);
				}
			}
		}
	}

}
