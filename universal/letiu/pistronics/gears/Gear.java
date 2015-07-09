package letiu.pistronics.gears;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import letiu.modbase.util.BlockItemUtil;
import letiu.pistronics.data.BlockData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Gear {

	public int size, meta;
	
	public Gear() {
		this.size = 1;
		this.meta = 0;
	}
	 
	public Gear(ItemStack stack) {
		if (stack != null && stack.stackTagCompound != null) {			
			readFromNBT(stack.stackTagCompound);
		}
		else {
			this.size = 1;
			this.meta = 0;
		}
	}
	
	public Gear(int size, int meta) {
		this.size = size;
		this.meta = meta;
	}
	
	public ItemStack getStack() {
		ItemStack stack = BlockItemUtil.getStack(BlockData.gear.block);
		stack.stackTagCompound = getNBT();
		return stack;
	}
	
	public NBTTagCompound getNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		
		nbt.setInteger("size", size);
		nbt.setInteger("meta", meta);
		nbt.setBoolean("rod", false);
		
		return nbt;
	}
	
	public static Gear getGearFromNBT(NBTTagCompound nbt) {
		Gear gear = new Gear();
		gear.readFromNBT(nbt);
		return gear;
	}
	
	public void readFromNBT(NBTTagCompound tagCompound) {
		this.size = tagCompound.getInteger("size");
		this.meta = tagCompound.getInteger("meta");
	}
	
	public void writeToStream(DataOutputStream out) throws IOException {
		out.writeInt(size);
		out.writeInt(meta);
	}
	
	public static Gear readFromStream(DataInputStream in) throws IOException {
		Gear gear = new Gear();
		gear.size = in.readInt();
		gear.meta = in.readInt();
		return gear;
	}
}
