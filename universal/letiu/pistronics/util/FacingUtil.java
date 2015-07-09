package letiu.pistronics.util;

import net.minecraft.util.Facing;

public class FacingUtil {
	
//	private static final int[][] sideToData = {{0,1,2,3,4,5},{1,0,3,2,5,4},{2,3,4,5,0,1},{3,2,5,4,1,0},{4,5,0,1,2,3},{5,4,1,0,3,2}};
	private static final int[][] sideToData = {{0,1,2,3,4,5},{1,0,3,2,5,4},{2,3,0,1,4,5},{3,2,1,0,5,4},{4,5,2,3,0,1},{5,4,3,2,1,0}};
	private static final int[][] leftCrossData = {{-1,-1,4,5,3,2},{-1,-1,4,5,3,2},{5,4,-1,-1,0,1},{5,4,-1,-1,0,1},{3,2,0,1,-1,-1},{3,2,0,1,-1,-1}};
	
	public static int[][] rotateData = {{0,1,4,5,3,2},{0,1,5,4,2,3},{5,4,2,3,0,1},{4,5,2,3,1,0},{2,3,1,0,4,5},{3,2,0,1,4,5}};
	
	public static int rotateSideTo(int zero, int side) {
		return sideToData[zero][side];
	}
	
	public static int getLeftCross(int top, int side) {
		return leftCrossData[top][side];
	}
	
	public static int getFacingFromClick(int facing, float xHit, float yHit, float zHit) {

		switch (facing) {
		case 0: case 1: return yHit > 0.5F ? 1 : 0;
		case 2: case 3: return zHit > 0.5F ? 3 : 2;
		case 4: case 5: return xHit > 0.5F ? 5 : 4;
		}
		return 0;
	}
	
	public static float getRelevantAxis(int facing, float xHit, float yHit, float zHit) {
		switch (facing) {
		case 0: return 1F - yHit;
		case 1: return yHit;
		case 2: return 1F - zHit;
		case 3: return zHit;
		case 4: return 1F - xHit;
		case 5: return xHit;
		}
		return 0;
	}
	
	public static Vector3 getOffsetForSide(int side) {
		return new Vector3(Facing.offsetsXForSide[side], Facing.offsetsYForSide[side], Facing.offsetsZForSide[side]);
	}
	
	public static int getSideForOffset(Vector3 offset) {
		if (offset.x < 0) return 4;
		if (offset.x > 0) return 5;
		if (offset.y < 0) return 0;
		if (offset.y > 0) return 1;
		if (offset.z < 0) return 2;
		if (offset.z > 0) return 3;
		return -1;
	}
	
	public static int rotate(int meta, int rDir) {
		 int extra = (meta & 8);
		 int dir = meta & 7;
		 dir = rotateData[rDir][dir];
		 return dir | extra;
	 }
}
