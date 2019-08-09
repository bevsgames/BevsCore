package games.bevs.core.module.abilties.abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import games.bevs.core.commons.ActionType;
import games.bevs.core.commons.CC;
import games.bevs.core.commons.Duration.TimeUnit;
import games.bevs.core.commons.itemstack.ItemStackBuilder;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.CooldownAbility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(name = "SpellMaster", author = "teddyyy", description = { "Click your item to cast a spell",
		"to those in a radius of 20 blocks" })
public class SpellMaster extends CooldownAbility {

	private @Getter @Setter String itemName = CC.red + "Spell Master";
	private @Getter @Setter Material itemMaterial = Material.LEASH;
	private @Getter @Setter ActionType actionType = ActionType.AIR;
	private @Getter @Setter int radius = 20;

	private @Getter @Setter List<PotionEffect> potions = new ArrayList<PotionEffect>(
			Arrays.asList(new PotionEffect(PotionEffectType.POISON, 20 * 30, new Random().nextInt(1)),
					new PotionEffect(PotionEffectType.HARM, 20, new Random().nextInt(1)),
					new PotionEffect(PotionEffectType.HUNGER, 20 * 30, 0),
					new PotionEffect(PotionEffectType.SLOW, 20 * 30, new Random().nextInt(1)),
					new PotionEffect(PotionEffectType.WEAKNESS, 20 * 30, 0)));

	// Class variables
	public static final String SPELL_COOLDOWN = "ability.spellmaster";
	private @Getter ItemStack spellMasterItem;

	@Override
	public void onLoad() {

		this.initCooldown(SPELL_COOLDOWN, 60, TimeUnit.SECOND);
		this.spellMasterItem = new ItemStackBuilder(itemMaterial).displayName(itemName).build();
	}

	@Override
	public List<ItemStack> getItems() {
		return Arrays.asList(spellMasterItem);
	}

	@EventHandler
	public void onCast(PlayerInteractEvent e) {
		Player player = e.getPlayer();

		if (e.getItem() == null)
			return;

		if (!e.getItem().isSimilar(spellMasterItem))
			return;

		if (!hasAbility(player))
			return;

		if (!actionType.containsAction(e.getAction()))
			return;

		if (this.hasCooldownAndNotify(player, SPELL_COOLDOWN))
			return;

		int players = 0;
		int random = new Random().nextInt(potions.size());
		for (Player online : Bukkit.getOnlinePlayers()) {
			if (online != player) {
				if (online.getLocation().distance(player.getLocation()) <= 20) {
					players++;
					online.addPotionEffect(potions.get(random));
				}
			}

			if (players > 0) {
				String message = CC.green + "Cast a spell on " + (players == 1 ? "a player!" : players + " people!");

				player.sendMessage(message);
				setCooldown(player, SPELL_COOLDOWN);
				return;
			}
		}

	}

}
