package letiu.modbase.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemReference {

	public static final Item SLIME = Item.slimeBall;
	public static final Block REDSTONE_TORCH = Block.torchRedstoneActive;
	public static final Block REDSTONE_BLOCK = Block.blockRedstone;
	
	public static final Block REDSTONE = Block.redstoneWire;
	public static final Item REDSTONE_ITEM = Item.redstone;
	
	public static final Block PISTON = Block.pistonBase;
	public static final Block PLANKS = Block.planks;
	public static final Block WOODEN_SLAB = Block.woodSingleSlab;
	public static final Block TRAP_DOOR = Block.trapdoor;
	public static final Block WOOL = Block.cloth;
	
	public static final Block STONE = Block.stone;
	
	public static final Item STICK = Item.stick;
	public static final Item IRON_INGOT = Item.ingotIron;
	public static final Item NETHER_WART = Item.netherStalkSeeds;
	public static final Item SUGAR = Item.sugar;
	public static final Item WHEAT_SEEDS = Item.seeds;
	public static final Item COMPARATOR = Item.comparator;
	public static final Item DIAMOND = Item.diamond;
	
	public static final Item BOW = Item.bow;
	public static final Item ARROW = Item.arrow;

	public static final Item ROTTEN_FLESH = Item.rottenFlesh;
	public static final Item POISONOUS_POTATO = Item.poisonousPotato;
	public static final Item SPIDER_EYE = Item.spiderEye;
	
	public static final Item POTION = Item.potion;
	
	public static final Item BUCKET = Item.bucketEmpty;
	public static final Item MILK = Item.bucketMilk;
	
	public static final Item BOOK = Item.book;
	
	public static boolean isDye(ItemStack stack, int color) {
		if (stack == null) return false;
		switch (color) {
			case 0: return isOre(stack, "dyeWhite");
			case 1: return isOre(stack, "dyeOrange");
			case 2: return isOre(stack, "dyeMagenta");
			case 3: return isOre(stack, "dyeLightBlue");
			case 4: return isOre(stack, "dyeYellow");
			case 5: return isOre(stack, "dyeLime");
			case 6: return isOre(stack, "dyePink");
			case 7: return isOre(stack, "dyeGray");
			case 8: return isOre(stack, "dyeLightGray");
			case 9: return isOre(stack, "dyeCyan");
			case 10: return isOre(stack, "dyePurple");
			case 11: return isOre(stack, "dyeBlue");
			case 12: return isOre(stack, "dyeBrown");
			case 13: return isOre(stack, "dyeGreen");
			case 14: return isOre(stack, "dyeRed");
			case 15: return isOre(stack, "dyeBlack");
		}
		
		return false;
	}
	
	public static int getStackDyeColor(ItemStack stack) {
		for (int i = 0; i < 16; i++) {
			if (isDye(stack, i)) return i;
		}
		return -1;
	}
			
	public static ItemStack getDye(int dmg) {
		return new ItemStack(Item.dyePowder, 1, dmg);
	}
	
	public static boolean isHarvestTool(ItemStack stack) {
		if (stack == null) return false;
		Item item = stack.getItem();
		return (item instanceof net.minecraft.item.ItemSpade) 
				|| (item instanceof ItemAxe) 
				|| (item instanceof ItemPickaxe);
	}
	
	public static boolean isOre(ItemStack stack, String ore) {
		if (stack == null) return false;
		return OreDictionary.getOreID(stack) == OreDictionary.getOreID(ore);
	}
	
	public static boolean isRedstone(ItemStack stack) {
		if (stack == null) return false;
		int id = stack.itemID;
		return id == BlockItemUtil.getItemID(Item.redstone) || id == BlockItemUtil.getBlockID(Block.redstoneWire);
	}
		
	public static boolean isFood(ItemStack stack) {
		return stack.getItem() instanceof ItemFood;
	}
}
