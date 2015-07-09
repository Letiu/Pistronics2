package letiu.pistronics.util;

import net.minecraft.world.World;
import letiu.modbase.network.PacketHandler;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.packets.RenderUpdatePacket;

public class MiscUtil {

	public static void updateBlockForAllPlayers(int x, int y, int z, int dimID) {
		PacketHandler.sendToAllInDimension(new RenderUpdatePacket(x, y, z, dimID), dimID);
	}
	
	public static int getPartFromClick(float xHit, float yHit, float zHit) {
		if (yHit < 0.325F) return 0;
		if (yHit > 0.625F) return 1;
		if (xHit < 0.325F) return 4;
		if (xHit > 0.625F) return 5;
		if (zHit < 0.325F) return 2;
		if (zHit > 0.625F) return 3;
		return -1;
	}
	
	public static boolean checkIfCuboidFree(World world, Vector3 start, Vector3 end) {
		for (int x = start.x; x <= end.x; x++) {
			for (int y = start.y; y <= end.y; y++) {
				for (int z = start.z; z <= end.z; z++) {
					if (!WorldUtil.isBlockReplaceable(world, x, y, z)) return false;
				}
			}
		}
		return true;
	}
	
	public static boolean checkSurroundings(World world, Vector3 coords, Vector3 start, Vector3 end) {
		for (int x = start.x; x <= end.x; x++) {
			for (int y = start.y; y <= end.y; y++) {
				for (int z = start.z; z <= end.z; z++) {
					if (coords.x != x || coords.y != y || coords.z != z) {
						if (!WorldUtil.isBlockReplaceable(world, x, y, z)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	public static int getGearPosFromClick(int facing, float xHit, float yHit, float zHit) {

		if (facing == 1) {
			if (yHit < 0.35F) return 0;
			if (yHit < 0.75F) return 1;
			else return 2;
		}
		if (facing == 0) {
			if (yHit < 0.35F) return 2;
			if (yHit < 0.75F) return 1;
			else return 0;
		}
		if (facing == 3) {
			if (zHit < 0.35F) return 0;
			if (zHit < 0.75F) return 1;
			else return 2;
		}
		if (facing == 2) {
			if (zHit < 0.35F) return 2;
			if (zHit < 0.75F) return 1;
			else return 0;
		}
		if (facing == 5) {
			if (xHit < 0.35F) return 0;
			if (xHit < 0.75F) return 1;
			else return 2;
		}
		if (facing == 4) {
			if (xHit < 0.35F) return 2;
			if (xHit < 0.75F) return 1;
			else return 0;
		}
		
		return 1;
	}
}
