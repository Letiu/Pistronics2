package letiu.pistronics.inventory;

import net.minecraft.item.ItemStack;
import letiu.modbase.util.CompareUtil;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.data.PTile;

public class InvRodFolder extends PInventory {

	public InvRodFolder(PTile pTile, int size) {
		super(pTile, size);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return CompareUtil.compareIDs(stack, ItemData.rod.item);
	}

}
