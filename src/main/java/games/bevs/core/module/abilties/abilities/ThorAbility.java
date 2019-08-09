package games.bevs.core.module.abilties.abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import games.bevs.core.commons.ActionType;
import games.bevs.core.commons.CC;
import games.bevs.core.commons.Duration.TimeUnit;
import games.bevs.core.commons.itemstack.ItemStackBuilder;
import games.bevs.core.module.abilties.AbilityInfo;
import games.bevs.core.module.abilties.types.CooldownAbility;
import games.bevs.core.module.combat.event.CustomDamageEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This ability strike lighting on the ground
 * 
 * @owner BevsGames
 */
@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(name="Thor", author = "Sprock", description = {"Hitting Ground with an axe causes lighting to strike"})
public class ThorAbility extends CooldownAbility
{
	//Ability Settings
	private @Getter @Setter String itemName = CC.gold + "Thor's Hammer";
	private @Getter @Setter Material itemMaterial = Material.WOOD_AXE;

	private @Getter @Setter boolean allActions = false;
	private @Getter @Setter ActionType actionType = ActionType.BLOCK;
	private @Getter @Setter boolean causesFire = true;
	
	//Class variables
	private static final String THOR_METADATA = "THOR";
	private @Getter ItemStack thorHammerItem;
	
	@Override
	public void onLoad()
	{
		this.initDefaultCooldown(10, TimeUnit.SECOND);
		
		this.thorHammerItem = new ItemStackBuilder(itemMaterial).displayName(itemName).build();
	}
	
	@Override
	public List<ItemStack> getItems()
	{
		return Arrays.asList(thorHammerItem);
	}
	
	@EventHandler
	public void onThor(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Action action = e.getAction();
		
		if (!hasAbility(player)) 
			return;
			
		if(e.getItem() == null)
			return;
		
		if(!e.getItem().isSimilar(thorHammerItem))
			return;
		
		if(!allActions)
		{
			if(!this.actionType.containsAction(action))
				return;
		}
		else if(action == Action.PHYSICAL) 
			return;
		
		if(this.hasDefaultCooldownAndNotify(player))
			return;
		
		World world = e.getPlayer().getWorld();
		Block block = e.getClickedBlock();
		if(block == null) 
			return;
		Block highestBlock = world.getHighestBlockAt(block.getLocation());
		if(this.isCausesFire())
			highestBlock.getRelative(BlockFace.UP).setType(Material.FIRE);
		
		LightningStrike lighting = world.strikeLightning(highestBlock.getLocation());
		lighting.setMetadata(THOR_METADATA, new FixedMetadataValue(this.getPlugin(), player.getName()));
		
		this.setDefaultCooldown(player);
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onPunch(CustomDamageEvent e)
	{
		if(!(e.getAttackerEntity() instanceof LightningStrike))
			return;
		
		if(!e.getAttackerEntity().hasMetadata(THOR_METADATA))
			return;
		
		if(!e.isVictimIsPlayer())
			return;
					
		List<MetadataValue> metaValues = e.getAttackerEntity().getMetadata(THOR_METADATA);
		Player player = e.getVictimPlayer();
		String username = player.getName();
		
		for(MetadataValue meta : metaValues)
		{
			if(meta.asString().equalsIgnoreCase(username))
				e.setCancelled("Thor's Lightning");;
		}
	}
}
