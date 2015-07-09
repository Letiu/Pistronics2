package letiu.pistronics.data;

import letiu.modbase.tiles.BaseTile;
import letiu.modbase.util.NBTUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public abstract class PTile {

	public BaseTile tileEntity;
	
	public abstract String getKey();
	
    public void readFromNBT(NBTTagCompound tagCompound) {}

    public void writeToNBT(NBTTagCompound tagCompound) {}

	public void update() {}

	public void postLoad() {}
	
	public boolean hasInventory() {
		return false;
	}
	
	public NBTTagCompound getNBTForItem() {
		NBTTagCompound nbt = NBTUtil.getNewCompound();
		writeToNBT(nbt);
		return nbt;
	}

	public AxisAlignedBB getRenderBoundingBox() {
		return null;
	}
}
