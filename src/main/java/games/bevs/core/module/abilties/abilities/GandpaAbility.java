package games.bevs.core.module.abilties.abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import games.bevs.core.commons.itemstack.ItemStackBuilder;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *  If you hit a player with a stick, hey go flying
 */
@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(name="Grandpa", author = "Sprock", description = { "Hitting player with a selected item", "sends anyone flying" })
public class GandpaAbility extends Ability
{
	//Ability Settings
	public @Getter @Setter String itemName = "Granda's Cane";
	public @Getter @Setter Material itemMaterial = Material.STICK;
	public @Getter @Setter double knockbackMultipler = 3.0d;
	
	//Class variables
	private @Getter ItemStack knockbackItem;
	
	public void onLoad()
	{
		this.knockbackItem = new ItemStackBuilder(itemMaterial).displayName(itemName).build();
	}
	
	@Override
	public List<ItemStack> getItems()
	{
		return Arrays.asList(knockbackItem);
	}
	
	@EventHandler
	public void onPunch(CustomDamageEvent e)
	{
		if(e.isAttackerIsPlayer() && (this.hasAbility(e.getAttackerPlayer())))
		{
			Player player = e.getAttackerPlayer();
			ItemStack itemInHand = player.getItemInHand();
			if(itemInHand != null && itemInHand.getAmount() != 0 && itemInHand.isSimilar(this.getKnockbackItem()))
			{
				e.addKnockback(getKnockbackMultipler(), player.getName());
			}
		}
	}
}
