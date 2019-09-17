package games.bevs.core.module.abilties.abilities;

import games.bevs.core.commons.utils.BlockUtils;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
		name = "Worm",
		author = "teddyyy",
		description = {
				"Break dirt blocks instantly, fall damage",
				"is reduced when landing on dirt."
		})

public class WormAbility extends Ability {

	public Material blockMaterial = Material.DIRT;
	public int maxSoftlandingDamage = 1;

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
	public void onFall(CustomDamageEvent e) {
		if (!e.isVictimIsPlayer())
			return;

		if (e.getInitCause() != DamageCause.FALL)
			return;

		Player player = e.getVictimPlayer();

		if (!this.hasAbility(player))
			return;

		Location loc = player.getLocation();
		loc.subtract(0, 0.2, 0);

		Material[] landedOnBlocks = new Material[]{loc.getBlock().getType(), BlockUtils.getAdjacentX(loc).getType(), BlockUtils.getAdjacentZ(loc).getType()};
		for (Material landingMat : landedOnBlocks)
			if (landingMat == blockMaterial) {
				if (maxSoftlandingDamage < e.getInitDamage())
					e.setInitDamage(maxSoftlandingDamage);
				return;
			}
	}

}
