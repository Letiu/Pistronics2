package letiu.pistronics.tiles;

import net.minecraft.nbt.NBTTagCompound;
import letiu.modbase.util.NBTUtil;
import letiu.pistronics.data.PTile;
import letiu.pistronics.data.TileData;

public class TileSail extends PTile {

	public int color, face;
	
	public boolean camou = false;
	public int camouID = -1;
	public int camouMeta = 0;
	
	@Override
	public String getKey() {
		return TileData.key_sail;
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		tagCompound.setInteger("color", color);
		tagCompound.setInteger("face", face);
		tagCompound.setBoolean("camou", camou);
		tagCompound.setInteger("camouID", camouID);
		tagCompound.setInteger("camouMeta", camouMeta);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		this.color = tagCompound.getInteger("color");
		this.face = tagCompound.getInteger("face");
		this.camou = tagCompound.getBoolean("camou");
		this.camouID = tagCompound.getInteger("camouID");
		this.camouMeta = tagCompound.getInteger("camouMeta");
	}
	
	@Override
	public NBTTagCompound getNBTForItem() {
		NBTTagCompound tagCompound = NBTUtil.getNewCompound();
		
		tagCompound.setInteger("color", color);
		tagCompound.setBoolean("camou", camou);
		tagCompound.setInteger("camouID", camouID);
		tagCompound.setInteger("camouMeta", camouMeta);
		
		return tagCompound;
	}
}
