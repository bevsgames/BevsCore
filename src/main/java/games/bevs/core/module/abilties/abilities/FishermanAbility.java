package games.bevs.core.module.abilties.abilities;

import games.bevs.core.commons.itemstack.ItemStackBuilder;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Fisherman",
        author = "Fundryi",
        description = {
                "Throw out your hook and teleport",
                "any living being you hit instantly",
                "to your position!"
        })

public class FishermanAbility extends Ability {

    public @Getter @Setter String itemName = "The Hook";
    public @Getter @Setter Material itemMaterial = Material.FISHING_ROD;

    private @Getter ItemStack FishingRod;

    @Override
    public void onLoad() {
        this.FishingRod = new ItemStackBuilder(itemMaterial).displayName(itemName).build();
    }

    @Override
    public List<ItemStack> getItems() {
        return Arrays.asList(FishingRod);
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        ItemStack inHand = event.getPlayer().getInventory().getItemInHand();
        if (inHand == null) {
            return;
        }
        if (!inHand.isSimilar(FishingRod)) {
            return;
        }
        if (event.getState() != PlayerFishEvent.State.CAUGHT_ENTITY) {
            return;
        }
        Entity caught = event.getCaught();
        if (caught == null) {
            return;
        }
        caught.teleport(event.getPlayer());
//        if (caught instanceof Player) {
//            ActionBar.sendToPlayer(caught, ChatColor.RED + "You have been fished by a Fisherman!");
//        }
    }

	@EventHandler
	public void onItemDamage(PlayerItemDamageEvent event) {
    	if(!(this.hasAbility(event.getPlayer())))
    		return;
		if (event.getItem().isSimilar(FishingRod)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		if(!(this.hasAbility(event.getEntity())))
			return;
		event.getDrops().removeIf(itemStack -> itemStack.isSimilar(FishingRod));
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		if(!(this.hasAbility(event.getPlayer())))
			return;
		if (event.getItemDrop().getItemStack().isSimilar(FishingRod)) {
			event.setCancelled(true);
		}
	}
}
