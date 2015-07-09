package letiu.pistronics.gui;

import letiu.modbase.util.CompareUtil;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.tiles.TileCreativeMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCreativeMachine extends Container {
	
	protected TileCreativeMachine tile;

    public ContainerCreativeMachine (InventoryPlayer inventoryPlayer, TileCreativeMachine tile){
	    this.tile = tile;
	    
	    bindPlayerInventory(inventoryPlayer);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
    	return true; //tile.getPInventory().isUseableByPlayer(player);
    }


    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                        addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                                        8 + j * 18, 84 + i * 18));
                }
        }

        for (int i = 0; i < 9; i++) {
                addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
    	
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);
        
        // checks if the item is a rod
        ItemStack st = slotObject.getStack();
        if (!CompareUtil.compareIDs(st, ItemData.rod.item)) return null;
		
        // null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) {
                ItemStack stackInSlot = slotObject.getStack();
                stack = stackInSlot.copy();

                //merges the item into player inventory since its in the tileEntity
                if (slot < 5) {
                    if (!this.mergeItemStack(stackInSlot, 5, 35, true)) {
                            return null;
                    }
                }
                //places it into the tileEntity is possible since its in the player inventory
                else if (!this.mergeItemStack(stackInSlot, 0, 5, false)) {
                        return null;
                }

                if (stackInSlot.stackSize == 0) {
                        slotObject.putStack(null);
                } else {
                        slotObject.onSlotChanged();
                }

                if (stackInSlot.stackSize == stack.stackSize) {
                        return null;
                }
                slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return null;
    }
    
    @Override
    public ItemStack slotClick(int slot, int par2, int par3, EntityPlayer player) {
 
    	ItemStack stack = player.inventory.getItemStack();

    	if (stack != null && slot > -1 && slot < 5 && !CompareUtil.compareIDs(stack, ItemData.rod.item)) {
    		return stack;
    	}
    	else return super.slotClick(slot, par2, par3, player);
    }
}
