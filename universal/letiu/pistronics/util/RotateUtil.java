package letiu.pistronics.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import letiu.modbase.blocks.BaseBlock;
import letiu.modbase.util.ItemReference;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.piston.IPistonElement;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class RotateUtil {

    public static void rotateVanillaBlocks(World world, int x, int y, int z, int oldMeta, int rotateDir) {
        if ((rotateDir == 0 || rotateDir == 1) && ItemReference.isStair(WorldUtil.getBlock(world, x, y, z))) {
            int currentMeta = oldMeta;
            int meta = cycle(rotateDir == 1, currentMeta % 4, 0, 2, 1, 3) + (currentMeta / 4) * 4;
            WorldUtil.setBlockMeta(world, x, y, z, meta, 3);
        }
        else if ((rotateDir == 0 || rotateDir == 1) && ItemReference.isChest(WorldUtil.getBlock(world, x, y, z))) {
            int currentMeta = oldMeta;
            int meta = cycle(rotateDir == 1, currentMeta, 2, 5, 3, 4);
            WorldUtil.setBlockMeta(world, x, y, z, meta, 3);
        }
        else if (ItemReference.isLog(WorldUtil.getBlock(world, x, y, z))) {
            rotateDir = (rotateDir & 14) * 2;
            if (rotateDir != 0) rotateDir = rotateDir == 4 ? 8 : 4;
            int currentMeta = oldMeta;
            int meta = rotateDir + (currentMeta / 4) * 4;
            switch (meta) {
                case 8: meta = 4; break;
                case 4: meta = 8; break;
                case 12: meta = 0; break;
                default: meta = currentMeta;
            }
            if (meta != currentMeta) {
                meta += currentMeta % 4;
                WorldUtil.setBlockMeta(world, x, y, z, meta, 3);
            }
        }
		else if (ItemReference.isPiston(WorldUtil.getBlock(world, x, y, z))) {
		    int meta = oldMeta;
			int bit123 = meta & 7;
			int bit4 = meta & 8;
			bit123 = rotateDir(bit123, rotateDir);
			meta = bit123 | bit4;
			WorldUtil.setBlockMeta(world, x, y, z, meta, 3);
		}
		else {
			WorldUtil.setBlockMeta(world, x, y, z, oldMeta, 3);
		}

    }

    public static int cycle(boolean forward, int current, int... metas) {
        for (int i = 0; i < metas.length; i++) {
            if (current == metas[i]) {
                if (forward) {
                    return metas[(i + 1) % metas.length];
                }
                else {
                    return metas[(i - 1 + metas.length) % metas.length];
                }
            }
        }
        return current;
    }

	public static boolean isBlockRotateable(World world, int x, int y, int z) {
		
		Block block = WorldUtil.getBlock(world, x, y, z);
		
		// TODO: partblocks ? 
		
		if (block instanceof BaseBlock) {
			if (((BaseBlock) block).data instanceof IPistonElement) {
				return true;
			}
		}
		//if (block instanceof BlockPistonBase) return true;
		//if (block instanceof BlockDispenser) return true;
		//if (block instanceof BlockDropper) return true;
		
		return false;
	}
	
	public static int rotateDir(int meta, int rotateDir) {
		return FacingUtil.rotate(meta, rotateDir);
	}
	
	public static ArrayList<Vector2> getCircle(int r) {
		ArrayList<Vector2> result1 = new ArrayList<Vector2>();
		ArrayList<Vector2> result2 = new ArrayList<Vector2>();
		ArrayList<Vector2> result3 = new ArrayList<Vector2>();
		ArrayList<Vector2> result4 = new ArrayList<Vector2>();
		ArrayList<Vector2> result5 = new ArrayList<Vector2>();
		ArrayList<Vector2> result6 = new ArrayList<Vector2>();
		ArrayList<Vector2> result7 = new ArrayList<Vector2>();
		ArrayList<Vector2> result8 = new ArrayList<Vector2>();
		
		int d = -1 * r;
		int x = r - 1;
		int y = 0;
		
		int lastX = x;

		while (y <= x) {
			result1.add(new Vector2(x, y));
			result2.add(new Vector2(y, x));
			result3.add(new Vector2(-y, x));
			result4.add(new Vector2(-x, y));
			result5.add(new Vector2(-x, -y));
			result6.add(new Vector2(-y, -x));
			result7.add(new Vector2(y, -x));
			result8.add(new Vector2(x, -y));
			d = d + 2 * y + 1;
			y = y + 1;
			if (d > 0) {
				d = d - 2 * x + 2;
				x = x - 1;
			}
			if (r > 3 && x == lastX - 1) {
				result1.add(new Vector2 (x, y - 1));
				result2.add(new Vector2(y - 1, x));
				result3.add(new Vector2(-y + 1, x));
				result4.add(new Vector2(-x, y - 1));
				result5.add(new Vector2(-x, -y + 1));
				result6.add(new Vector2(-y + 1, -x));
				result7.add(new Vector2(y - 1, -x));
				result8.add(new Vector2(x, -y + 1));
			}
			lastX = x;
		}
		
		Collections.reverse(result2);
		Collections.reverse(result4);
		Collections.reverse(result6);
		Collections.reverse(result8);
		
		result1.addAll(result2);
		result1.addAll(result3);
		result1.addAll(result4);
		result1.addAll(result5);
		result1.addAll(result6);
		result1.addAll(result7);
		result1.addAll(result8);
		
		return result1;
	}
	
	public static ArrayList<Vector2> getCircleSection(Vector2 start, Vector2 end) {
		int r = start.amount() + 1;
		ArrayList<Vector2> circle = getCircle(r);
		ArrayList<Vector2> result = new ArrayList<Vector2>();
			
		boolean inSection = false;
		for (Vector2 coords : circle) {
			if (coords.equals(start)) {
				inSection = true;
			}
			if (inSection) {
				result.add(coords);
			}
			if (inSection && coords.equals(end)) {
				return result;
			}
		}
		for (Vector2 coords : circle) {
			if (inSection) {
				result.add(coords);
			}
			if (inSection && coords.equals(end)) {
				return result;
			}
		}
		
		// start or end not found....
		
		
		r = start.amount() + 2;
		circle = getCircle(r);
		result = new ArrayList<Vector2>();
			
		inSection = false;
		for (Vector2 coords : circle) {
			if (coords.equals(start)) {
				inSection = true;
			}
			if (inSection) {
				result.add(coords);
			}
			if (inSection && coords.equals(end)) {
				return result;
			}
		}
		for (Vector2 coords : circle) {
			if (inSection) {
				result.add(coords);
			}
			if (inSection && coords.equals(end)) {
				return result;
			}
		}
		
		return null;
	}
	
	public static Vector2 get2DVectorForDir(Vector3 vec, int dir) {
		
		if (dir == 0 || dir == 1) {
			return new Vector2(vec.x, vec.z);
		}
		if (dir == 2 || dir == 3) {
			return new Vector2(vec.x, vec.y);
		}
		if (dir == 4 || dir == 5) {
			return new Vector2(vec.y, vec.z);
		}
		return null;
	}
	
	public static ArrayList<BlockProxy> getProxysFromV2(World world, List<Vector2> vecs, Vector3 start, int rotateDir) {
		ArrayList<BlockProxy> result = new ArrayList<BlockProxy>();
		for (Vector2 vec : vecs) {
			result.add(new BlockProxy(world, getV3fromV2andProxy(vec, start, rotateDir)));
		}
		return result;
	}
	
	private static Vector3 getV3fromV2andProxy(Vector2 vec, Vector3 pVec, int dir) {
		if (dir == 0 || dir == 1) {
			return new Vector3(vec.x, pVec.y, vec.y);
		}
		if (dir == 2 || dir == 3) {
			return new Vector3(vec.x, vec.y, pVec.z);
		}
		if (dir == 4 || dir == 5) {
			return new Vector3(pVec.x, vec.x, vec.y);
		}
		return null;
	}
	
	public static AxisAlignedBB rotateBoxAround(AxisAlignedBB box, Vector3 rotatePoint, int rotateDir, int angle) {
		double x = box.minX;
		double y = box.minY;
		double z = box.minZ;
		
		double a = Math.toRadians(angle);
		if (rotateDir % 2 != 0) a = -a;
		
		x -= rotatePoint.x;
		y -= rotatePoint.y;
		z -= rotatePoint.z;
		
		if (rotateDir == 4 || rotateDir == 5) {
			y = y * Math.cos(a) + z * -Math.sin(a);
			z = y * Math.sin(a) + z * Math.cos(a);
		}
		else if (rotateDir == 0 || rotateDir == 1) {
			x = x * Math.cos(a) + z * -Math.sin(a);
			z = x * Math.sin(a) + z * Math.cos(a);
		}
		else if (rotateDir == 2 || rotateDir == 3) {
			x = x * Math.cos(a) + y * -Math.sin(a);
			y = x * Math.sin(a) + y * Math.cos(a);
		}
		
		x += rotatePoint.x;
		y += rotatePoint.y;
		z += rotatePoint.z;
		
		return box.getOffsetBoundingBox(x - box.minX, y - box.minY, z - box.minZ);
	}
}
