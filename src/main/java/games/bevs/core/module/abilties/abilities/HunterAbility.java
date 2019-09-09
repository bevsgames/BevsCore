package games.bevs.core.module.abilties.abilities;

import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
		name = "Hunter",
		author = "Fundryi",
		description = {
				"I heard you like cooked steaks...",
				"well this kits fits you well.",
				"Killing animals will drop their COOKED flesh!"
		})

public class HunterAbility extends Ability {

	public @Getter @Setter Material itemMaterial = Material.COOKED_BEEF;

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		Player player = event.getEntity().getKiller();
		if (player == null) {
			return;
		}
		if (!this.hasAbility(player)){
			return;
		}
		if ((event.getEntity() instanceof Chicken
				|| event.getEntity() instanceof Cow
				|| event.getEntity() instanceof Pig
				|| event.getEntity() instanceof Rabbit
				|| event.getEntity() instanceof Zombie)
				&& event.getEntity().getKiller() != null) {

			Iterator<ItemStack> itel = event.getDrops().iterator();
			List<ItemStack> toAdd = new ArrayList<ItemStack>();
			while (itel.hasNext()) {
				ItemStack item = itel.next();
				if (item == null
						|| (item.getType() != Material.RAW_CHICKEN
						&& item.getType() != Material.RAW_BEEF
						&& item.getType() != Material.PORK
						&& item.getType() != Material.RABBIT
						&& item.getType() != Material.ROTTEN_FLESH)) {
					continue;
				}
				if (item.getType() == Material.RAW_CHICKEN) {
					toAdd.add(new ItemStack(Material.COOKED_CHICKEN, item.getAmount()));
				} else if (item.getType() == Material.RAW_BEEF) {
					toAdd.add(new ItemStack(Material.COOKED_BEEF, item.getAmount()));
				} else if (item.getType() == Material.PORK) {
					toAdd.add(new ItemStack(Material.GRILLED_PORK, item.getAmount()));
				} else if (item.getType() == Material.RABBIT) {
					toAdd.add(new ItemStack(Material.COOKED_RABBIT, item.getAmount()));
				} else if (item.getType() == Material.ROTTEN_FLESH) {
					toAdd.add(new ItemStack(Material.MUSHROOM_SOUP, item.getAmount()));
				}
				itel.remove();
			}
			for (ItemStack item : toAdd) {
				event.getDrops().add(item);
			}
		}
	}
}
