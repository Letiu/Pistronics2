package letiu.modbase.util;

import letiu.pistronics.items.ItemSpade;
import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemReference {

	public static final Item SLIME = Items.slime_ball;
	public static final Block REDSTONE_TORCH = Blocks.redstone_torch;
	public static final Block REDSTONE_BLOCK = Blocks.redstone_block;
	
	public static final Block REDSTONE = Blocks.redstone_wire;
	public static final Item REDSTONE_ITEM = Items.redstone;
	
	public static final Block PISTON = Blocks.piston;
	public static final Block PLANKS = Blocks.planks;
	public static final Block WOODEN_SLAB = Blocks.wooden_slab;
	public static final Block TRAP_DOOR = Blocks.trapdoor;
	public static final Block WOOL = Blocks.wool;

	public static final Block COBBLE = Blocks.cobblestone;
	public static final Block STONE = Blocks.stone;

	public static final Block OBSIDIAN = Blocks.obsidian;
	
	public static final Item STICK = Items.stick;
	public static final Item IRON_INGOT = Items.iron_ingot;
	public static final Item NETHER_WART = Items.nether_wart;
	public static final Item SUGAR = Items.sugar;
	public static final Item WHEAT_SEEDS = Items.wheat_seeds;
	public static final Item COMPARATOR = Items.comparator;
	public static final Item DIAMOND = Items.diamond;
	
	public static final Item BOW = Items.bow;
	public static final Item ARROW = Items.arrow;
	
	public static final Item ROTTEN_FLESH = Items.rotten_flesh;
	public static final Item POISONOUS_POTATO = Items.poisonous_potato;
	public static final Item SPIDER_EYE = Items.spider_eye;
	
	public static final Item POTION = Items.potionitem;
	
	public static final Item BUCKET = Items.bucket;
	public static final Item MILK = Items.milk_bucket;
	
	public static final Item BOOK = Items.book;
	
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
		return new ItemStack(Items.dye, 1, dmg);
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
		for (int id : OreDictionary.getOreIDs(stack)) {
			if (id == OreDictionary.getOreID(ore)) {
				return true;
			}
		}
		return false;
	}

    public static boolean isSameOre(ItemStack stack1, ItemStack stack2) {
        if (stack1 == null || stack2 == null) return false;
        for (int id1 : OreDictionary.getOreIDs(stack1)) {
            for (int id2 : OreDictionary.getOreIDs(stack2)) {
                if (id1 == id2) return true;
            }
        }
        return false;
    }
	
	public static boolean isRedstone(ItemStack stack) {
		if (stack == null) return false;
		int id = Item.getIdFromItem(stack.getItem());
		return id == Item.getIdFromItem(Items.redstone) || id == Block.getIdFromBlock(Blocks.redstone_wire);
	}
	
	public static boolean isFallingBlock(Block block) {
		return block instanceof BlockFalling;
	}
	
	public static boolean isFood(ItemStack stack) {
		return stack.getItem() instanceof ItemFood;
	}

    public static boolean isStair(Block block) {
        return block instanceof BlockStairs;
    }

    public static boolean isLog(Block block) {
        return block instanceof BlockRotatedPillar;
    }

    public static boolean isChest(Block block) {
        return block instanceof BlockChest || block instanceof BlockFurnace;
    }

	public static boolean isPiston(Block block) {
		return block instanceof BlockPistonBase || block instanceof BlockDropper || block instanceof BlockDispenser;
	}
}
