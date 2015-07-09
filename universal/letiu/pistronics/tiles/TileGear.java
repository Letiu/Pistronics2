package letiu.pistronics.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import letiu.pistronics.data.PTile;
import letiu.pistronics.data.TileData;
import letiu.pistronics.gears.Gear;
import letiu.pistronics.render.PRenderManager;
import letiu.pistronics.render.PTileRenderer;

public class TileGear extends PTile implements ISpecialRenderTile {
	
	public boolean hasRod = true;
	
	private Gear[] gears = new Gear[3];
	
	public void setGear(Gear gear, int index) {
		if (index >= 0 && index <= 2) {
			gears[index] = gear;
		}
	}
	
	public Gear getGear(int index) {
		if (index >= 0 && index <= 2) {
			return gears[index];
		}
		return null;
	}
	
	public int getSize() {
		int result = 1;
		for (int i = 0; i < gears.length; i++) {
			if (gears[i] != null && gears[i].size > result) {
				result = gears[i].size;
			}
		}
		return result;
	}
	
	public boolean shiftForward() {
		if (gears[2] != null && gears[1] == null && gears[0] == null) {
			gears[1] = gears[2];
			gears[2] = null;
			return true;
		}
		else if (gears[1] != null && gears[0] == null) {
			gears[0] = gears[1];
			gears[1] = null;
			return true;
		}
		
		return false;
	}
	
	public boolean shiftBack() {
		if (gears[0] != null && gears[1] == null && gears[2] == null) {
			gears[1] = gears[0];
			gears[0] = null;
			return true;
		}
		else if (gears[1] != null && gears[2] == null) {
			gears[2] = gears[1];
			gears[1] = null;
			return true;
		}
		
		return false;
	}
	
	@Override
	public String getKey() {
		return TileData.key_gear;
	}

	@Override
	public PTileRenderer getRenderer() {
		return PRenderManager.gearRenderer;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(-5.0D, -5.0D, -5.0D, 6.0D, 6.0D, 6.0D).getOffsetBoundingBox(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
	}
	
//	@Override
//	public AxisAlignedBB getRenderBoundingBox() {
//		int gearSize = getSize() / 2;
//		
//		AxisAlignedBB box = null;
//		switch (tileEntity.blockMetadata & 7) {
//		case 1: case 0: box = AxisAlignedBB.getBoundingBox(-gearSize, 0.0D, -gearSize, gearSize + 1, 1.0D, gearSize + 1); break;
//		case 2: case 3: box = AxisAlignedBB.getBoundingBox(-gearSize, -gearSize, 0.0D, gearSize + 1, gearSize + 1, 1.0D); break;
//		case 4: case 5: box = AxisAlignedBB.getBoundingBox(0.0D, -gearSize, -gearSize, 1.0D, gearSize + 1, gearSize + 1); break;
//		}
//		if (box == null) return null; //AxisAlignedBB.getBoundingBox(-5.0D, -5.0D, -5.0D, 6.0D, 6.0D, 6.0D);;
//		
//		return box.getOffsetBoundingBox(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
//	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		
		tagCompound.setBoolean("gear1", gears[0] != null);
		if (gears[0] != null) tagCompound.setTag("gearData1", gears[0].getNBT());
		tagCompound.setBoolean("gear2", gears[1] != null);
		if (gears[1] != null) tagCompound.setTag("gearData2", gears[1].getNBT());
		tagCompound.setBoolean("gear3", gears[2] != null);
		if (gears[2] != null) tagCompound.setTag("gearData3", gears[2].getNBT());
		
		tagCompound.setBoolean("hasRod", hasRod);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		
		if (tagCompound.getBoolean("gear1")) gears[0] = Gear.getGearFromNBT(tagCompound.getCompoundTag("gearData1"));
		if (tagCompound.getBoolean("gear2")) gears[1] = Gear.getGearFromNBT(tagCompound.getCompoundTag("gearData2"));
		if (tagCompound.getBoolean("gear3")) gears[2] = Gear.getGearFromNBT(tagCompound.getCompoundTag("gearData3"));
		
		this.hasRod = tagCompound.getBoolean("hasRod");
	}

}
