package letiu.pistronics.inventory;

import letiu.modbase.util.CompareUtil;
import letiu.modbase.util.NBTUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.data.PTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public abstract class PInventory {

	protected int size;
	protected String name;
	protected ItemStack[] stacks;
	protected PTile pTile;
	
	public PInventory(PTile pTile, int size) {
		this.size = size;
		this.stacks = new ItemStack[size];
		this.pTile = pTile;
	}
	
	public int getSize() {
		return size;
	}

	public ItemStack getStackInSlot(int slot) {
		return stacks[slot];
	}
	
	public int getFirstValidSlot() {
		for (int i = 0; i < stacks.length; i++) {
			if (stacks[i] != null) return i;
		}
		return -1;
	}
	
	public boolean isEmpty() {
		for (ItemStack stack : stacks) {
			if (stack != null) return false;
		}
		return true;
	}
	
	public boolean canInsert(ItemStack stack) {
		ItemStack stackCopy = stack.copy();
		
		for (ItemStack invStack : stacks) {
			if (invStack == null) {
				stackCopy.stackSize -= getStackLimit();
			}
			if (CompareUtil.compareIDs(stackCopy, invStack)) {
				if (stackCopy.getItemDamage() == invStack.getItemDamage()) {
					NBTTagCompound nbt1 = stackCopy.stackTagCompound;
					NBTTagCompound nbt2 = invStack.stackTagCompound;
					
					if (nbt1.equals(nbt2)) {
						stackCopy.stackSize -= (getStackLimit() - invStack.stackSize);
					}
				}
			}
		}
		
		return stackCopy.stackSize <= 0;
	}
	
	public boolean addStackToInventory(ItemStack stack) {
		
		if (!canInsert(stack)) return false;
		
		for (int i = 0; i < stacks.length; i++) {
			if (stacks[i] == null) {
				stacks[i] = stack.copy();
				
				if (stack.stackSize > getStackLimit()) {
					stacks[i].stackSize = getStackLimit();
					stack.stackSize -= getStackLimit();
				}
				else {
					return true;
				}
			}
			if (CompareUtil.compareIDs(stack, stacks[i])) {
				if (stack.getItemDamage() == stacks[i].getItemDamage()) {
					NBTTagCompound nbt1 = stack.stackTagCompound;
					NBTTagCompound nbt2 = stacks[i].stackTagCompound;
					if ((nbt1 == null && nbt2 == null) || nbt1.equals(nbt2)) {
						int availableSpace = (getStackLimit() - stacks[i].stackSize);
						
						if (stack.stackSize > availableSpace) {
							stacks[i].stackSize = getStackLimit();
							stack.stackSize -= availableSpace;
						}
						else {
							stacks[i].stackSize += stack.stackSize;
							return true;
						}
					}
				}
			}
		}
		
		return true;
	}

	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack stack = getStackInSlot(slot);
       
		// TODO: Check if set...Content necessary
		
		if (stack != null) {
        	if (stack.stackSize <= amount) {
        		setInventorySlotContents(slot, null);
            } 
        	else {
        		stack = stack.splitStack(amount);
            	if (stack.stackSize == 0) {
            		setInventorySlotContents(slot, null);
                }
            }
        }    
        return stack;
	}

	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
        	// TODO: Check if necessary 
        	setInventorySlotContents(slot, null);
        }
        return stack;
	}

	public void setInventorySlotContents(int slot, ItemStack stack) {
		stacks[slot] = stack;
        if (stack != null && stack.stackSize > getStackLimit()) {
                stack.stackSize = getStackLimit();
        }  
	}

	public String getName() {
		return name;
	}

	public int getStackLimit() {
		return 64;
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(pTile.tileEntity.xCoord + 0.5, pTile.tileEntity.yCoord + 0.5, pTile.tileEntity.zCoord + 0.5) < 64;
	}

	public void openInventory() {
		
	}

	public void closeInventory() {
		
	}

	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}
	
    public void writeToNBT(NBTTagCompound compound) {
        
        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < stacks.length; i++) {
            ItemStack stack = stacks[i];
            if (stack != null) {
                NBTTagCompound tag = NBTUtil.getNewCompound();
                tag.setByte("Slot", (byte) i);
                stack.writeToNBT(tag);
                itemList.appendTag(tag);
            }
        }
        compound.setTag("Inventory", itemList);
    }
    
    public void readFromNBT(NBTTagCompound compound) {
        
    	NBTTagList tagList = NBTUtil.getCompoundList(compound, "Inventory");
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = NBTUtil.getCompoundAt(tagList, i);
            byte slot = tag.getByte("Slot");
            if (slot >= 0 && slot < stacks.length) {
                    stacks[slot] = ItemStack.loadItemStackFromNBT(tag);
            }
        }
    }

	public void dropContent(World world, int x, int y, int z) {
		for (ItemStack stack : stacks) {
			if (stack != null) {
				WorldUtil.spawnItemStack(world, x, y, z, stack);
			}
		}
	}
}
