package letiu.modbase.util;

import letiu.modbase.items.IBaseItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CompareUtil {

	public static boolean compareIDs(Item item1, Item item2) {
		if (item1 == null || item2 == null) return false;
		return item1.itemID == item2.itemID; 
	}
	
	public static boolean compareIDs(IBaseItem item1, IBaseItem item2) {
		return compareIDs((Item) item1,(Item) item2);
	}
	
	public static boolean compareIDs(Block block1, Block block2) {
		if (block1 == null || block2 == null) return false;
		return block1.blockID == block2.blockID;
	}
	
	public static boolean compareIDs(ItemStack stack1, ItemStack stack2) {
		if (stack1 == null || stack2 == null) return false;
		return compareIDs(stack1.getItem(), stack2.getItem());
	}
	
	public static boolean compareIDs(ItemStack stack, Item item) {
		if (stack == null || item == null) return false;
		return compareIDs(stack.getItem(), item);
	}
	
	public static boolean compareIDs(Item item, ItemStack stack) {
		return compareIDs(stack, item);
	}
	
	public static boolean compareIDs(ItemStack stack, IBaseItem item) {
		return compareIDs(stack,(Item) item);
	}
	
	public static boolean compareIDs(IBaseItem item, ItemStack stack) {
		return compareIDs(stack, item);
	}
	
	public static boolean compareIDs(ItemStack stack, Block block) {
		if (stack == null || block == null) return false;
		return stack.itemID == block.blockID;
	}
	
	public static boolean compareIDs(Block block, ItemStack stack) {
		return compareIDs(stack, block);
	}
}
