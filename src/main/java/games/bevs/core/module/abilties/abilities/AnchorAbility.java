package games.bevs.core.module.abilties.abilities;

import games.bevs.core.commons.utils.MathUtils;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
		name = "Anchor",
		author = "Sprock & Fundryi",
		description = {
				"You're immune against anything that deals knockback!",
				"You also have a 33% chance of giving no knockback!"
		})

public class AnchorAbility extends Ability {

	private static final float NO_KNOCKBACK_CHANCE = 0.33f;
	private static final Sound HIT_SOUND = Sound.ANVIL_LAND;
	private static final float HIT_SOUND_PITCH_IN = 1.35f;
	public @Getter @Setter Material itemMaterial = Material.ANVIL;

	@EventHandler
	public void onAnchorKnockback(CustomDamageEvent e) {
		if (!e.isVictimIsPlayer()) return;
		Player player = e.getVictimPlayer();
		if (!this.hasAbility(player)) return;
		if (MathUtils.getRandom().nextFloat() > NO_KNOCKBACK_CHANCE) return;

		e.setCancelKnockback(true);
		player.playSound(player.getLocation(), HIT_SOUND, 0.5f, HIT_SOUND_PITCH_IN);

		if (e.isAttackerIsPlayer()) {
			Player attacker = e.getAttackerPlayer();
			attacker.playSound(attacker.getLocation(), HIT_SOUND, 0.5f, HIT_SOUND_PITCH_IN);
		}
	}
}
