package games.bevs.core.module.abilties.abilities;

import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Forger",
        author = "Fundryi",
        description = {
                "Allows you to instantly smelt Ores into Ingots!",
                "Just click with your Coal on the Ore in the inventory.",
                "You can mine more Coal, its not limited to the spawn items."
        })

public class ForgerAbility extends Ability {

    public @Getter @Setter Material itemMaterial = Material.COAL;

    @EventHandler
    public void onItemClick(InventoryClickEvent event)
    {
        if (event.getCursor() == null || event.getCurrentItem() == null){
            return;
        }
        if (!(event.getWhoClicked() instanceof Player)){
            return;
        }
        Player forger = (Player) event.getWhoClicked();
        if(!this.hasAbility(forger)) {
            return;
        }

        ItemStack coal = null;
        ItemStack ore = null;
        if (event.getCursor().getType() == Material.COAL)
            coal = event.getCursor();
        else if (event.getCurrentItem().getType() == Material.COAL)
            coal = event.getCurrentItem();
        if (coal == null)
            return;

        for (int i = 0; i < 2; i++)
        {
            if (ore == null)
                ore = event.getCurrentItem();
            else
                ore = event.getCursor();

            if (ore.getType() == Material.IRON_ORE)
            {
                event.setCursor(null);
                event.setCurrentItem(null);

                if(!this.hasAbility(forger)) {
                    return;
                }

                int remainder = ore.getAmount() - coal.getAmount();
                if (remainder > 0)
                    event.getWhoClicked().getInventory().addItem(new ItemStack(Material.IRON_INGOT, coal.getAmount()));
                else if (remainder <= 0)
                    event.getWhoClicked().getInventory().addItem(new ItemStack(Material.IRON_INGOT, ore.getAmount()));
                if (remainder > 0)
                    event.getWhoClicked().getInventory().addItem(new ItemStack(Material.IRON_ORE, remainder));
                else if (remainder < 0)
                    event.getWhoClicked().getInventory().addItem(new ItemStack(Material.COAL, coal.getAmount() - ore.getAmount()));
            }
            else if (ore.getType() == Material.GOLD_ORE)
            {
                event.setCursor(null);
                event.setCurrentItem(null);

                if(!this.hasAbility(forger)) {
                    return;
                }

                int remainder = ore.getAmount() - coal.getAmount();
                if (remainder > 0)
                    event.getWhoClicked().getInventory().addItem(new ItemStack(Material.GOLD_INGOT, coal.getAmount()));
                else if (remainder <= 0) // Theres more coal then iron
                    event.getWhoClicked().getInventory().addItem(new ItemStack(Material.GOLD_INGOT, ore.getAmount()));
                if (remainder > 0)
                    event.getWhoClicked().getInventory().addItem(new ItemStack(Material.GOLD_ORE, remainder));
                else if (remainder < 0)
                    event.getWhoClicked().getInventory().addItem(new ItemStack(Material.COAL, coal.getAmount() - ore.getAmount()));
            }
        }
    }
}
