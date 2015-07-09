package letiu.modbase.util;

import java.util.List;

import letiu.modbase.core.ModClass;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PItem;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockItemUtil {

	public static Block getBlockFromStack(ItemStack stack) {
		if (stack == null) return null;
		return Block.getBlockFromItem(stack.getItem());
	}
	
	public static int getBlockID(Block block) {
		return Block.getIdFromBlock(block);
	}
	
	public static Block getBlockByID(int ID) {
		return Block.getBlockById(ID);
	}
	
	public static int getItemID(Item item) {
		return Item.getIdFromItem(item);
	}
	
	public static Item getItemByID(int ID) {
		return Item.getItemById(ID);
	}
	
	public static boolean defaultShouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return Blocks.stone.shouldSideBeRendered(world, x, y, z, side);
	}
	
	public static ItemStack getStack(PBlock block) {
		return new ItemStack(block.block);
	}
	
	public static ItemStack getStack(PBlock block, int amt, int dmg) {
		return new ItemStack(block.block, amt, dmg);
	}
	
	public static ItemStack getStack(PItem item) {
		return new ItemStack((Item) item.item);
	}
	
	public static ItemStack getStack(PItem item, int amt, int dmg) {
		return new ItemStack((Item) item.item, amt, dmg);
	}
	
	public static ItemStack getStack(Block block) {
		return new ItemStack(block);
	}
	
	public static ItemStack getStack(Block block, int amt, int dmg) {
		return new ItemStack(block, amt, dmg);
	}
	
	public static ItemStack getStack(Item item) {
		return new ItemStack(item);
	}
	
	public static ItemStack getStack(Item item, int amt, int dmg) {
		return new ItemStack(item, amt, dmg);
	}
	
	public static ItemStack getStack(int ID, int amt, int damage) {
		return new ItemStack(Item.getItemById(ID), amt, damage);
	}
	
	public static void addToTabList(Item item, List list) {
		item.getSubItems(item, ModClass.modTap, list);
	}
	
	public static void addToTabList(Block block, List list) {
		Item item = Item.getItemFromBlock(block);
		item.getSubItems(item, ModClass.modTap, list);
	}
	
	public static void addToTabList(PItem item, List list) {
		addToTabList((Item) item.item, list);
	}
	
	public static void addToTabList(PBlock block, List list) {
		addToTabList(block.block, list);
	}
	
	public static AxisAlignedBB getBoundingBox(World world, int x, int y, int z, Block block) {
		if (block == null) return null;
		return block.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
	
	public static ResourceLocation getResourceLocation(Block block, int meta) {

		if (block != null) {
			IIcon blockIcon = block.getIcon(0, meta);
			
			if (blockIcon == null) return null;
			
			String iconName = blockIcon.getIconName();
			ResourceLocation texture = new ResourceLocation("textures/blocks/" + iconName + ".png");
			
			String[] parts = iconName.split(":");
			if (parts.length == 2) {
				texture = new ResourceLocation(parts[0], "textures/blocks/" + parts[1] + ".png");
			}
			
			return texture;
		}
		
		return null;
	}
	
	
	
	
	
}
