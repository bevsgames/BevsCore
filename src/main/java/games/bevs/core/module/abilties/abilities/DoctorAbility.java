package games.bevs.core.module.abilties.abilities;

import com.google.common.collect.ImmutableList;
import games.bevs.core.commons.itemstack.ItemStackBuilder;
import games.bevs.core.commons.utils.StringUtils;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(
        name = "Doctor",
        author = "Fundryi",
        description = {
                "&7You can heal players from their sickness",
                "&7with your scalpel.",
                "&7It will remove EVERY potion effect!",
                "&7Ohh and you're immune to any damage Potions/Effects!"
        })

public class DoctorAbility extends Ability {

    public @Getter @Setter String itemName = "Scalpel";
    public @Getter @Setter Material itemMaterial = Material.SHEARS;

    private @Getter ItemStack Scalpel;

    private List<EntityDamageEvent.DamageCause> filteredCauses = ImmutableList.of(
            EntityDamageEvent.DamageCause.POISON,
            EntityDamageEvent.DamageCause.WITHER,
            EntityDamageEvent.DamageCause.STARVATION,
            EntityDamageEvent.DamageCause.MAGIC
    );

    @Override
    public void onLoad() {
        this.Scalpel = new ItemStackBuilder(itemMaterial).displayName(itemName).build();
    }

    @Override
    public List<ItemStack> getItems() {
        return Arrays.asList(Scalpel);
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        ItemStack inHand = event.getPlayer().getItemInHand();
        if (inHand == null) {
            return;
        }
        if(!this.hasAbility(event.getPlayer())) {
            return;
        }
        if (!inHand.isSimilar(Scalpel)) {
            return;
        }
        event.setCancelled(true);
        if (!(event.getRightClicked() instanceof LivingEntity)) {
            return;
        }
        //cleanseEntity(event.getPlayer());
        cleanseEntity((LivingEntity) event.getRightClicked());

        Player selfPlayer = event.getPlayer();
        Player otherPlayer = (Player)event.getRightClicked();
        Entity otherEntity = event.getRightClicked();
        if (event.getRightClicked() instanceof Player) {
            otherPlayer.sendMessage(ChatColor.GREEN + "You have been healed by a doctor!");
            selfPlayer.sendMessage(ChatColor.GREEN + "You have healed " + otherPlayer.getName() + "!");
        } else {
            selfPlayer.sendMessage(ChatColor.GREEN + "You have healed a " +
                    StringUtils.capitalizeFirstLetter("" + otherEntity.getType()) + "!");
        }
    }

    private void cleanseEntity(LivingEntity entity) {
        entity.getActivePotionEffects().stream()
                .map(PotionEffect::getType)
                .forEach(entity::removePotionEffect);
    }

    @EventHandler
    public void onDamage(CustomDamageEvent event) {
        if(!event.isVictimIsPlayer() && !this.hasAbility(event.getVictimPlayer())) {
            return;
        }
        if (filteredCauses.contains(event.getInitCause())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        if(!(this.hasAbility(event.getPlayer())))
            return;
        if (event.getItem().isSimilar(Scalpel)) {
            event.setCancelled(true);
        }
    }

}
