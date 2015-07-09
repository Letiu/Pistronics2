package letiu.modbase.util;

import letiu.modbase.items.IBaseItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CompareUtil {

    public static boolean compare(ItemStack stack1, ItemStack stack2) {
        if (stack1 == null && stack2 == null) return true;
        if (stack1 == null || stack2 == null) return false;

        stack1 = stack1.copy();
        stack2 = stack2.copy();

        stack1.stackSize = stack2.stackSize;

        if (ItemReference.isSameOre(stack1, stack2)) {
            ItemStack stack3 = stack1.copy();
            stack3.stackTagCompound = stack2.stackTagCompound;
            stack2 = stack3;
        }

        return ItemStack.areItemStacksEqual(stack1, stack2);
    }

	public static boolean compareIDs(Item item1, Item item2) {
		if (item1 == null || item2 == null) return false;
		return Item.getIdFromItem(item1) == Item.getIdFromItem(item2); 
	}
	
	public static boolean compareIDs(IBaseItem item1, IBaseItem item2) {
		return compareIDs((Item) item1, (Item) item2); 
	}
	
	public static boolean compareIDs(Block block1, Block block2) {
		if (block1 == null || block2 == null) return false;
		return Block.getIdFromBlock(block1) == Block.getIdFromBlock(block2);
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
		return compareIDs(stack, (Item) item);
	}
	
	public static boolean compareIDs(IBaseItem item, ItemStack stack) {
		return compareIDs(stack, item);
	}
	
	public static boolean compareIDs(ItemStack stack, Block block) {
		if (stack == null || block == null) return false;
		return Item.getIdFromItem(stack.getItem()) == Block.getIdFromBlock(block);
	}
	
	public static boolean compareIDs(Block block, ItemStack stack) {
		return compareIDs(stack, block);
	}
}
