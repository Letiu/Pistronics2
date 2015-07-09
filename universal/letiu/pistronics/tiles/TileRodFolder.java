package letiu.pistronics.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.CompareUtil;
import letiu.modbase.util.NBTUtil;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.data.PTile;
import letiu.pistronics.data.TileData;
import letiu.pistronics.inventory.IInventoryTile;
import letiu.pistronics.inventory.InvRodFolder;
import letiu.pistronics.inventory.PInventory;
import letiu.pistronics.render.PRenderManager;
import letiu.pistronics.render.PTileRenderer;

public class TileRodFolder extends TileMech implements ISpecialRenderTile, IInventoryTile {
	
	private PInventory inventory;
	
	public TileRodFolder() {
		this.inventory = new InvRodFolder(this, 5);
	}
	
	@Override
	public void update() {
		super.update();
		if (!hasElement() && !inventory.isEmpty()) {
			int slot = inventory.getFirstValidSlot();
			ItemStack stack = inventory.getStackInSlot(slot);
			
			setElement(BlockData.rod);
			if (stack.stackTagCompound != null) {
				getPElementTile().readFromNBT(stack.stackTagCompound);
			}
			if (!tileEntity.getWorldObj().isRemote) {
				inventory.decrStackSize(slot, 1);
			}	
		}
	}
	
	public ItemStack getElementStack() {
		ItemStack stack = BlockItemUtil.getStack(getPElement().getItemBlock());
		
		stack.stackSize = 1;
		
		PTile tile = getPElementTile();
		if (tile != null) {
			if (tile instanceof TileRod) {
				stack.stackTagCompound = ((TileRod) tile).getNBTForItem();
			}
			else {
				stack.stackTagCompound = NBTUtil.getNewCompound();
				tile.writeToNBT(stack.stackTagCompound);
			}
		}
		
		return stack;
	}

	public boolean canTransfer() {
		ItemStack stack = getElementStack();
		if (CompareUtil.compareIDs(stack, ItemData.rod.item)) {
			return inventory.canInsert(getElementStack());
		}
		else return false; 
	}
	
	public void transferElementToInventory() {
		if (!tileEntity.getWorldObj().isRemote) {
			
			inventory.addStackToInventory(getElementStack());
		}
	}
	
	@Override
	public String getKey() {
		return TileData.key_rodfolder;
	}
	
	@Override
	public PTileRenderer getRenderer() {
		return PRenderManager.rodFolderElementRenderer;
	}

	@Override
	public boolean hasInventory() {
		return true;
	}
	
	@Override
	public PInventory getPInventory() {
		return inventory;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		inventory.writeToNBT(tagCompound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		inventory.readFromNBT(tagCompound);
	}
}
