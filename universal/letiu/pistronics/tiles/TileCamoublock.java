package letiu.pistronics.tiles;

import cpw.mods.fml.client.FMLClientHandler;
import letiu.pistronics.data.PTile;
import letiu.pistronics.data.TileData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeHooks;

public class TileCamoublock extends PTile {

	private int[] camouIDs = {-1, -1, -1, -1, -1, -1};
	private int[] camouMetas = {-1, -1, -1, -1, -1, -1};
	
	public void setCamouID(int i, int value) {
//		System.out.println(tileEntity.getWorldObj().isRemote ? "CLIENT" : "SERVER");
//		System.out.println("Operation on " + i + ": Exchanging " + camouIDs[i] + " for " + value + ".");
		this.camouIDs[i] = value;
	}
	
	public void setCamouMeta(int i, int value) {
		this.camouMetas[i] = value;
	}
	
	public int getCamouID(int i) {
		return this.camouIDs[i];
	}
	
	public int getCamouMeta(int i) {
		return this.camouMetas[i];
	}
	
	@Override
	public String getKey() {
		return TileData.key_camoublock;
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		tagCompound.setIntArray("camouIDs", camouIDs);
		tagCompound.setIntArray("camouMetas", camouMetas);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		this.camouIDs = tagCompound.getIntArray("camouIDs");
		this.camouMetas = tagCompound.getIntArray("camouMetas");
	}
}
