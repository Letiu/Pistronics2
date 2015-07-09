package letiu.modbase.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryUtil {

	public static boolean hasItemInHotbar(EntityPlayer player, Item item) {
		for (int i = 0; i < 9; i++) {
			ItemStack stack = player.inventory.getStackInSlot(i);
			if (CompareUtil.compareIDs(stack, item)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean consumeItem(InventoryPlayer inv, Item item) {
		return inv.consumeInventoryItem(item);
	}
	
}
