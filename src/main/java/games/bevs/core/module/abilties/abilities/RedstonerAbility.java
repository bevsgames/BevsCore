//package games.bevs.core.module.abilties.abilities;
//
//import games.bevs.core.commons.itemstack.ItemStackBuilder;
//import games.bevs.core.module.abilties.AbilityInfo;
//import games.bevs.core.module.abilties.types.Ability;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.bukkit.Material;
//import org.bukkit.inventory.ItemStack;
//
//import java.util.Arrays;
//import java.util.List;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@AbilityInfo(
//        name = "Redstoner",
//        author = "Fundryi",
//        description = {
//                "You spawn with",
//                "32x Pistons",
//                "128x Redstone",
//                "16x Redstone Repeaters",
//                "4x Dispensers",
//                "16x Slime Balls",
//                "2x Slime Blocks",
//                "8x TNT"
//        })
//
//public class RedstonerAbility extends Ability {
//
//    public @Getter @Setter Material itemMaterial = Material.PISTON_BASE;
//
//    private @Getter ItemStack ABILITY_ITEM_PISTON = new ItemStack(Material.PISTON_BASE, 32);
//    private @Getter ItemStack ABILITY_ITEM_REDSTONE = new ItemStack(Material.REDSTONE, 64);
//    private @Getter ItemStack ABILITY_ITEM_REDSTONE_REPEATERS = new ItemStack(Material.DIODE, 16);
//    private @Getter ItemStack ABILITY_ITEM_DISPENSER = new ItemStack(Material.DISPENSER, 4);
//    private @Getter ItemStack ABILITY_ITEM_SLIME_BALLS = new ItemStack(Material.SLIME_BALL, 16);
//    private @Getter ItemStack ABILITY_ITEM_SLIME_BLOCK = new ItemStack(Material.SLIME_BLOCK, 2);
//    private @Getter ItemStack ABILITY_ITEM_TNT = new ItemStack(Material.TNT, 8);
//
//    @Override
//    public void onLoad() {
//        this.ABILITY_ITEM_PISTON = new ItemStackBuilder(ABILITY_ITEM_PISTON).build();
//        this.ABILITY_ITEM_REDSTONE = new ItemStackBuilder(ABILITY_ITEM_REDSTONE).build();
//        this.ABILITY_ITEM_REDSTONE_REPEATERS = new ItemStackBuilder(ABILITY_ITEM_REDSTONE_REPEATERS).build();
//        this.ABILITY_ITEM_DISPENSER = new ItemStackBuilder(ABILITY_ITEM_DISPENSER).build();
//        this.ABILITY_ITEM_SLIME_BALLS = new ItemStackBuilder(ABILITY_ITEM_SLIME_BALLS).build();
//        this.ABILITY_ITEM_SLIME_BLOCK = new ItemStackBuilder(ABILITY_ITEM_SLIME_BLOCK).build();
//        this.ABILITY_ITEM_TNT = new ItemStackBuilder(ABILITY_ITEM_TNT).build();
//    }
//
//    @Override
//    public List<ItemStack> getItems() {
//        return Arrays.asList(
//                ABILITY_ITEM_PISTON,
//                ABILITY_ITEM_REDSTONE,
//                ABILITY_ITEM_REDSTONE_REPEATERS,
//                ABILITY_ITEM_DISPENSER,
//                ABILITY_ITEM_SLIME_BALLS,
//                ABILITY_ITEM_SLIME_BLOCK,
//                ABILITY_ITEM_TNT
//        );
//    }
//
//}
