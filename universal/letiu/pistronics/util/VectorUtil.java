package letiu.pistronics.util;

import java.util.List;

import letiu.modbase.util.NBTUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Facing;

public class VectorUtil {

	public static Vector3 getOffsetVector(int dir) {
		int xOff = Facing.offsetsXForSide[dir];
		int yOff = Facing.offsetsYForSide[dir];
		int zOff = Facing.offsetsZForSide[dir];
		return new Vector3(xOff, yOff, zOff);
	}
	
	public static Vector3 getAntiOffset(int dir) {
		int xOff = Facing.offsetsXForSide[dir];
		int yOff = Facing.offsetsYForSide[dir];
		int zOff = Facing.offsetsZForSide[dir];
		return new Vector3(xOff ^ 1, yOff ^ 1, zOff ^ 1);
	}
	
	public static Vector3 getPositiveAntiOffset(int dir) {
		int xOff = Math.abs(Facing.offsetsXForSide[dir]);
		int yOff = Math.abs(Facing.offsetsYForSide[dir]);
		int zOff = Math.abs(Facing.offsetsZForSide[dir]);
		return new Vector3(xOff ^ 1, yOff ^ 1, zOff ^ 1);
	}
	
	public static Vector3 rotateAround(Vector3 coords, Vector3 rotatePoint, int rotateDir) {
		
		Vector3 temp = coords.copy().sub(rotatePoint);
		
		if (rotateDir == 1) {
			int x = temp.x;
			temp.x = -temp.z;
			temp.z = x;
		}
		else if (rotateDir == 0) {
			int x = temp.x;
			temp.x = temp.z;
			temp.z = -x;
		}
		else if (rotateDir == 5) {
			int y = temp.y;
			temp.y = temp.z;
			temp.z = -y;
		}
		else if (rotateDir == 4) {
			int y = temp.y;
			temp.y = -temp.z;
			temp.z = y;
		}
		else if (rotateDir == 3) {
			int x = temp.x;
			temp.x = temp.y;
			temp.y = -x;
		}
		else if (rotateDir == 2) {
			int x = temp.x;
			temp.x = -temp.y;
			temp.y = x;
		}
		
		
		return temp.add(rotatePoint);
	}
	
	public static void printList(List<Vector2> list) {
		for (Vector2 vec : list) {
			System.out.println(vec);
		}
	}
	
	/**
     * Reads a Vector from NBT.
     */
    public static Vector3 readFromNBT(NBTTagCompound tagCompound) {
    	if (tagCompound != null && tagCompound.hasKey("x") && tagCompound.hasKey("y") && tagCompound.hasKey("z")) {
    		int x = tagCompound.getInteger("x");
	        int y = tagCompound.getInteger("y");
	        int z = tagCompound.getInteger("z");
	        return new Vector3(x, y, z);
    	}
    	return null;
    }

    /**
     * Writes a Vector to NBT.
     */
    public static NBTTagCompound writeToNBT(Vector3 vec) {
    	if (vec == null) return null;
    	
    	NBTTagCompound tagCompound = NBTUtil.getNewCompound();
        tagCompound.setInteger("x", vec.x);
        tagCompound.setInteger("y", vec.y);
        tagCompound.setInteger("z", vec.z);
        return tagCompound;
    }
}
