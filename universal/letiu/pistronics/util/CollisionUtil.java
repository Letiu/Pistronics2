package letiu.pistronics.util;

import java.util.ArrayList;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CollisionUtil {

	public static MovingObjectPosition multiCollisionRayTrace(World world, int x, int y, int z, Vec3 playerPos, Vec3 targetPos, ArrayList<AxisAlignedBB> boxes) {
	
        playerPos = playerPos.addVector((double)(-x), (double)(-y), (double)(-z));
        targetPos = targetPos.addVector((double)(-x), (double)(-y), (double)(-z));
        
        Vec3 currentVec = null;
        byte currentSide = -1;
        int subHit = -1;
        
        for (int i = 0; i < boxes.size(); i++) {
        	
        	AxisAlignedBB box = boxes.get(i).getOffsetBoundingBox((double)(-x), (double)(-y), (double)(-z));
        	
        	Vec3 vec31 = playerPos.getIntermediateWithXValue(targetPos, box.minX);
            Vec3 vec32 = playerPos.getIntermediateWithXValue(targetPos, box.maxX);
            Vec3 vec33 = playerPos.getIntermediateWithYValue(targetPos, box.minY);
            Vec3 vec34 = playerPos.getIntermediateWithYValue(targetPos, box.maxY);
            Vec3 vec35 = playerPos.getIntermediateWithZValue(targetPos, box.minZ);
            Vec3 vec36 = playerPos.getIntermediateWithZValue(targetPos, box.maxZ);
            
            if (isVecInsideYZBounds(vec31, box)) {
                if (currentVec == null || (playerPos.squareDistanceTo(vec31) < playerPos.squareDistanceTo(currentVec))) {
                	currentVec = vec31;
                	currentSide = 4;
                	subHit = i;
                }
            }
            if (isVecInsideYZBounds(vec32, box)) {
                if (currentVec == null || (playerPos.squareDistanceTo(vec32) < playerPos.squareDistanceTo(currentVec))) {
                	currentVec = vec32;
                	currentSide = 5;
                	subHit = i;
                }
            }
            if (isVecInsideXZBounds(vec33, box)) {
                if (currentVec == null || (playerPos.squareDistanceTo(vec33) < playerPos.squareDistanceTo(currentVec))) {
                	currentVec = vec33;
                	currentSide = 0;
                	subHit = i;
                }
            }
            if (isVecInsideXZBounds(vec34, box)) {
                if (currentVec == null || (playerPos.squareDistanceTo(vec34) < playerPos.squareDistanceTo(currentVec))) {
                	currentVec = vec34;
                	currentSide = 1;
                	subHit = i;
                }
            }
            if (isVecInsideXYBounds(vec35, box)) {
                if (currentVec == null || (playerPos.squareDistanceTo(vec35) < playerPos.squareDistanceTo(currentVec))) {
                	currentVec = vec35;
                	currentSide = 2;
                	subHit = i;
                }
            }
            if (isVecInsideXYBounds(vec36, box)) {
                if (currentVec == null || (playerPos.squareDistanceTo(vec36) < playerPos.squareDistanceTo(currentVec))) {
                	currentVec = vec36;
                	currentSide = 3;
                	subHit = i;
                }
            }
        }
        
        if (currentVec == null) {
        	return null;
        }
        
        MovingObjectPosition result = new MovingObjectPosition(x, y, z, currentSide, currentVec.addVector((double)x, (double)y, (double)z));
        result.subHit = subHit;
        return result;
	}
	
	public static MovingObjectPosition multiCollisionRayTraceWithSubHitMap(World world, int x, int y, int z, Vec3 playerPos, Vec3 targetPos, ArrayList<AxisAlignedBB> boxes, ArrayList<Byte> subHitMap) {
		
		if (boxes.size() != subHitMap.size()) throw new IllegalArgumentException("SubHitMap doesn't match the boxes");
		
        playerPos = playerPos.addVector((double)(-x), (double)(-y), (double)(-z));
        targetPos = targetPos.addVector((double)(-x), (double)(-y), (double)(-z));
        
        Vec3 currentVec = null;
        byte currentSide = -1;
        int subHit = -1;
        
        for (int i = 0; i < boxes.size(); i++) {
        	
        	AxisAlignedBB box = boxes.get(i);//.getOffsetBoundingBox((double)(-x), (double)(-y), (double)(-z));
        	
        	Vec3 vec31 = playerPos.getIntermediateWithXValue(targetPos, box.minX);
            Vec3 vec32 = playerPos.getIntermediateWithXValue(targetPos, box.maxX);
            Vec3 vec33 = playerPos.getIntermediateWithYValue(targetPos, box.minY);
            Vec3 vec34 = playerPos.getIntermediateWithYValue(targetPos, box.maxY);
            Vec3 vec35 = playerPos.getIntermediateWithZValue(targetPos, box.minZ);
            Vec3 vec36 = playerPos.getIntermediateWithZValue(targetPos, box.maxZ);
            
            if (isVecInsideYZBounds(vec31, box)) {
                if (currentVec == null || (playerPos.squareDistanceTo(vec31) < playerPos.squareDistanceTo(currentVec))) {
                	currentVec = vec31;
                	currentSide = 4;
                	subHit = subHitMap.get(i);
                }
            }
            if (isVecInsideYZBounds(vec32, box)) {
                if (currentVec == null || (playerPos.squareDistanceTo(vec32) < playerPos.squareDistanceTo(currentVec))) {
                	currentVec = vec32;
                	currentSide = 5;
                	subHit = subHitMap.get(i);;
                }
            }
            if (isVecInsideXZBounds(vec33, box)) {
                if (currentVec == null || (playerPos.squareDistanceTo(vec33) < playerPos.squareDistanceTo(currentVec))) {
                	currentVec = vec33;
                	currentSide = 0;
                	subHit = subHitMap.get(i);;
                }
            }
            if (isVecInsideXZBounds(vec34, box)) {
                if (currentVec == null || (playerPos.squareDistanceTo(vec34) < playerPos.squareDistanceTo(currentVec))) {
                	currentVec = vec34;
                	currentSide = 1;
                	subHit = subHitMap.get(i);;
                }
            }
            if (isVecInsideXYBounds(vec35, box)) {
                if (currentVec == null || (playerPos.squareDistanceTo(vec35) < playerPos.squareDistanceTo(currentVec))) {
                	currentVec = vec35;
                	currentSide = 2;
                	subHit = subHitMap.get(i);;
                }
            }
            if (isVecInsideXYBounds(vec36, box)) {
                if (currentVec == null || (playerPos.squareDistanceTo(vec36) < playerPos.squareDistanceTo(currentVec))) {
                	currentVec = vec36;
                	currentSide = 3;
                	subHit = subHitMap.get(i);;
                }
            }
        }
        
        if (currentVec == null) {
        	return null;
        }
        
        MovingObjectPosition result = new MovingObjectPosition(x, y, z, currentSide, currentVec.addVector((double)x, (double)y, (double)z));
        result.subHit = subHit;
        return result;
	}
	
	public static boolean isVecInsideYZBounds(Vec3 vec, AxisAlignedBB box) {
		return vec != null && vec.yCoord >= box.minY && vec.yCoord <= box.maxY && vec.zCoord >= box.minZ && vec.zCoord <= box.maxZ;
	}
	
	public static boolean isVecInsideXZBounds(Vec3 vec, AxisAlignedBB box) {
		return vec != null && vec.xCoord >= box.minX && vec.xCoord <= box.maxX && vec.zCoord >= box.minZ && vec.zCoord <= box.maxZ;
	}
	
	public static boolean isVecInsideXYBounds(Vec3 vec, AxisAlignedBB box) {
		return vec != null && vec.xCoord >= box.minX && vec.xCoord <= box.maxX && vec.yCoord >= box.minY && vec.yCoord <= box.maxY;
	}
}
