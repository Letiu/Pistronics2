package letiu.modbase.tiles;

import letiu.pistronics.inventory.PInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import letiu.pistronics.inventory.IInventoryTile;

public class InventoryTile extends BaseTile implements IInventory {

	private PInventory getInvFromData() {
		if (data instanceof IInventoryTile) {
			return ((IInventoryTile) data).getPInventory();
		}
		else throw new IllegalArgumentException("InventoryTile was initialized without Inventory data!");
	}
	
	@Override
	public int getSizeInventory() {
		return getInvFromData().getSize();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return getInvFromData().getStackInSlot(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		return getInvFromData().decrStackSize(slot, amount);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return getInvFromData().getStackInSlotOnClosing(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		getInvFromData().setInventorySlotContents(slot, stack);
	}

	@Override
	public String getInventoryName() {
		return getInvFromData().getName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return getInvFromData().getStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return getInvFromData().isUseableByPlayer(player);
	}

	@Override
	public void openInventory() {
		getInvFromData().openInventory();
	}

	@Override
	public void closeInventory() {
		getInvFromData().closeInventory();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return getInvFromData().isItemValidForSlot(slot, stack);
	}

}
