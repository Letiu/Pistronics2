package letiu.pistronics.tiles;

import java.util.Iterator;
import java.util.List;

import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.NBTUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BMotionblock;
import letiu.pistronics.blocks.BPartblock;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PTile;
import letiu.pistronics.data.TileData;
import letiu.pistronics.piston.ISpecialRotator;
import letiu.pistronics.render.PRenderManager;
import letiu.pistronics.render.PTileRenderer;
import letiu.pistronics.util.RotateUtil;
import letiu.pistronics.util.Vector3;
import letiu.pistronics.util.VectorUtil;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import org.lwjgl.Sys;

public class TileMotion extends TileElementHolder implements ISpecialRenderTile, IMover {

	private int rotateDir = -1, moveDir = -1;
	private float angle, progress;
	private float rotateSpeed, moveSpeed;
	
	private Vector3 rotatePoint;
	
	public boolean remoteControl = true;
	
	public TileMotion() {
		rotatePoint = new Vector3(0, 0, 0);
//		data = new TempData();
//		data.p = 0.5F;
//		System.out.println("TileMotion.TileMotion()");
	}
	
	@Override
	public void update() {
//		super.update(); // TODOe
		
		//if (!remoteControl) {
//			System.out.println("TileMotion: local progress");
			if (rotateDir != -1 && rotateSpeed != 0) {
				angle += rotateSpeed;
				if (angle >= 90) {
					loadBlock();
					postRotate();
				}
			}
			if (moveDir != -1 && moveSpeed != 0) {
				progress += moveSpeed;
				
				pushEntities();
				
				if (progress >= 1F) {
					pushEntitiesFinal();
					loadBlock();
					postMove();
					//ktryToMoveOn();
				}
			}
		//}
//		System.out.println(data.p);
//		if (progress2 >= 1) loadBlock();
		
		
		//this.remoteControl = false;
	}
	
	@Override
	public void setProgress(float progress) {
//		System.out.println("TileMotion.setProgress() " + tileEntity.getWorldObj().isRemote + " " + progress + " " + tileEntity.xCoord + "/" + tileEntity.yCoord + "/" + tileEntity.zCoord);
//		this.progress2 = progress;
//		this.remoteControl = true;
//		this.data.p = progress2;
		//System.out.println(tileEntity.getWorldObj().isRemote + " Setting progress to: " + progress);
	}
	
	public AxisAlignedBB getBoxForPush(float pushSpace) {
		
		World world = tileEntity.getWorldObj();
		int x = tileEntity.xCoord;
		int y = tileEntity.yCoord;
		int z = tileEntity.zCoord;
		
		// get Box from Element //
		AxisAlignedBB box;
		if (this.getPElement() instanceof BPartblock) {
			box = AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);	
		}
		else {
			box = BlockItemUtil.getBoundingBox(world, x, y, z, element);
			if (box == null) {
				box = AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
			}
		}
		
		// expand Box //
		switch (moveDir) {
		case 0: box.maxY = y + 1; break;
		case 1: box.minY = y; break;
		case 2: box.maxZ = z + 1; break;
		case 3: box.minZ = z; break;
		case 4: box.maxX = x + 1; break;
		case 5: box.minX = x; break;
		}
		
		// offset depending on progress //
		float antiProgress = 1F - (progress + pushSpace);
		
		return box.getOffsetBoundingBox((double)(antiProgress * (float)Facing.offsetsXForSide[moveDir]), (double)(antiProgress * (float)Facing.offsetsYForSide[moveDir]), (double)(antiProgress * (float)Facing.offsetsZForSide[moveDir]));
	}
	
	private void pushEntities() {
		
		World world = tileEntity.getWorldObj();
		int x = tileEntity.xCoord;
		int y = tileEntity.yCoord;
		int z = tileEntity.zCoord;
		
		float moveAmt = 1F * moveSpeed;
	
		
		PBlock block = WorldUtil.getPBlock(world, x + Facing.offsetsXForSide[moveDir], y + Facing.offsetsYForSide[moveDir], z + Facing.offsetsZForSide[moveDir]);
		
		/** push Entities out of the Block */
		if (block != null && block instanceof BMotionblock) {
			
			List list = tileEntity.getWorldObj().getEntitiesWithinAABBExcludingEntity((Entity) null, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1));
			Iterator itr = list.iterator();
			
			while (itr.hasNext()) {
				
				Entity entity = (Entity) itr.next();
				
				double vX = 0.1D * (entity.posX - (x + 0.5D)); 
				double vY = 0.1D * (entity.posY - (y + 0.5D));
				double vZ = 0.1D * (entity.posZ - (z + 0.5D));
				
				entity.motionX = vX;
				entity.motionY = vY;
				entity.motionZ = vZ;
			}
		}
		/** push Entities in moveDir */
		else {
			
			List list = tileEntity.getWorldObj().getEntitiesWithinAABBExcludingEntity((Entity) null, getBoxForPush(0.25F));
			Iterator itr = list.iterator();
			
			while (itr.hasNext()) {
				
				Entity entity = (Entity) itr.next();
				entity.moveEntity((double)(moveAmt * (float)Facing.offsetsXForSide[moveDir]), (double)(moveAmt * (float)Facing.offsetsYForSide[moveDir]), (double)(moveAmt * (float)Facing.offsetsZForSide[moveDir]));
				
				// stop velocity against moveDir (e.g. gravity) //
				switch(moveDir) {
				case 0: if (entity.motionY > 0) entity.motionY = 0; break;
				case 1: if (entity.motionY < 0) entity.motionY = 0; break;
				case 2: if (entity.motionZ > 0) entity.motionZ = 0; break;
				case 3: if (entity.motionZ < 0) entity.motionZ = 0; break;
				case 4: if (entity.motionX > 0) entity.motionX = 0; break;
				case 5: if (entity.motionX < 0) entity.motionX = 0; break;
				}
			}
		}
	}
	
	private void pushEntitiesFinal() {
		
		List list = tileEntity.getWorldObj().getEntitiesWithinAABBExcludingEntity((Entity) null, getBoxForPush(0F));
		
		Iterator itr = list.iterator();
		
		while (itr.hasNext()) {
			
			Entity entity = (Entity) itr.next();
			
			// Ensures no entities are left in the bounding box at the end //
			switch(moveDir) {
			//case 0: entity.posY = entity.posY - 1.0D; break;
			case 1: entity.posY = Math.ceil(entity.posY); 
					entity.motionY = 0.25D; break;
			case 2: entity.motionZ = -0.25D; break;
			case 3: entity.motionZ = 0.25D; break;
			case 4: entity.motionX = -0.25D; break;
			case 5: entity.motionX = 0.25D; break;
			}
		}
	}
	
	public boolean tryToMoveOn() {
		
		int x = tileEntity.xCoord + Facing.offsetsXForSide[moveDir];
		int y = tileEntity.yCoord + Facing.offsetsYForSide[moveDir];
		int z = tileEntity.zCoord + Facing.offsetsZForSide[moveDir];
		
		PTile tile = WorldUtil.getPTile(tileEntity.getWorldObj(), x, y, z);
		
		if (tile != null && tile instanceof TileMotion) {
			this.progress = 0F;
			NBTTagCompound nbt = new NBTTagCompound();
			this.writeToNBT(nbt);
			tile.readFromNBT(nbt);
			this.moveDir = -1;
			this.element = null;
			return true;
		}
		
		return false;
	}
	
	@Override
	public String getKey() {
		return TileData.key_motion;
	}

	@Override
	public PTileRenderer getRenderer() {
		return PRenderManager.motionRenderer;
	}
	
	public int getRotateDir() {
		return rotateDir;
	}
	
	public int getMoveDir() {
		return moveDir;
	}
	
	public float getAngle() {
		return angle;
	}
	
	public float getProgress() {
		return progress;
	}
	
	public float getAngleForRender(float ticktime) {
		if (ticktime > 1F) ticktime = 1F;
		
		return ((float) angle) + rotateSpeed * ticktime - 90F;
	}
	
	public float getProgressForRender(float ticktime) {
		if (ticktime > 1F) ticktime = 1F;
		//return progress;
		return ((float) progress) + moveSpeed * ticktime;
	}

	public float getRotateSpeed() {
		return rotateSpeed;
	}
	
	public float getMoveSpeed() {
		return moveSpeed;
	}
	
	public Vector3 getRotatePoint() {
		return rotatePoint;
	}
	
	public boolean isRotating() {
		return rotateDir != -1 && rotateSpeed != 0F;
	}
	
	public boolean isMoving() {
		return moveDir != -1 && moveSpeed != 0F;
	}
	
	public boolean isInMotion() {
		return isMoving() || isRotating();
	}
	
	public void rotate(int rotateDir, float speed) {
		this.rotateDir = rotateDir;
		this.rotateSpeed = speed;
		this.rotatePoint = new Vector3(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
	}
	
	public void rotate(int rotateDir, float speed, Vector3 rotatePoint) {
		this.rotateDir = rotateDir;
		this.rotateSpeed = speed;
		this.rotatePoint = rotatePoint;
	}
	
	public void move(int moveDir, float speed) {
		this.moveDir = moveDir;
		this.moveSpeed = speed;
	}
	
	public void loadBlock() {
		
		int x = tileEntity.xCoord, y = tileEntity.yCoord, z = tileEntity.zCoord;
		World world = tileEntity.getWorldObj();
		
		if (element != null) {
			
//			if (!world.isRemote && ItemReference.isFallingBlock(element)) {
//				world.isRemote = true;
//				WorldUtil.setBlock(world, x, y, z, element, elementMeta, 4);
//				world.isRemote = false;
//			}
//			else {
				WorldUtil.setBlock(world, x, y, z, element, elementMeta, 4);
//			}
			
			if (elementTile != null) {			
				
	
				TileEntity tile = WorldUtil.getTileEntity(world, x, y, z);
				
//				elementTile.setWorldObj(world);
//				elementTile.xCoord = x;
//				elementTile.yCoord = y;
//				elementTile.zCoord = z;
//				
				NBTTagCompound nbt = NBTUtil.getNewCompound();
				elementTile.writeToNBT(nbt);
//				
//				TileEntity.createAndLoadEntity(nbt);
				
				// TODO: FMP crash here! (no tile? �O�)
				if (tile == null) {
					elementTile.setWorldObj(world);
					elementTile.xCoord = x;
					elementTile.yCoord = y;
					elementTile.zCoord = z;
					WorldUtil.setTileEntity(world, x, y, z, elementTile);
					WorldUtil.setBlock(world, x, y, z, element, elementMeta, 4);
				}
				
				tile = WorldUtil.getTileEntity(world, x, y, z);
				
				if (tile != null) {
					tile.readFromNBT(nbt);
					tile.setWorldObj(world);
					tile.xCoord = x;
					tile.yCoord = y;
					tile.zCoord = z;
				}
			}
		}
		else {
			WorldUtil.setBlockToAir(world, x, y, z);
		}
		
		WorldUtil.updateBlock(world, x, y, z);
	}
	
	private void postRotate() {
		int x = tileEntity.xCoord, y = tileEntity.yCoord, z = tileEntity.zCoord;
		World world = tileEntity.getWorldObj();
		
		PBlock block = WorldUtil.getPBlock(world, x, y, z);
		if (block instanceof ISpecialRotator) {
			((ISpecialRotator) block).postRotate(world, x, y, z, rotateDir, rotateSpeed, rotatePoint);
		}
        else {
            RotateUtil.rotateVanillaBlocks(world, x, y, z, elementMeta, rotateDir);
        }
	}

	private void postMove() {
		WorldUtil.setBlockMeta(tileEntity.getWorldObj(), tileEntity.xCoord,
				tileEntity.yCoord, tileEntity.zCoord, elementMeta, 3);
	}
	
	@Override
	public float getOffsetX(float ticktime) {
		if (!isMoving()) return 0F;
		return (float) Facing.offsetsXForSide[moveDir ^ 1] * (1 - getProgressForRender(ticktime));
    }

	@Override
    public float getOffsetY(float ticktime) {
		if (!isMoving()) return 0F;
		return (float) Facing.offsetsYForSide[moveDir ^ 1] * (1 - getProgressForRender(ticktime));
    }

	@Override
    public float getOffsetZ(float ticktime) {
		if (!isMoving()) return 0F;
		return (float) Facing.offsetsZForSide[moveDir ^ 1] * (1 - getProgressForRender(ticktime));
    }
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		tagCompound.setInteger("rotateDir", rotateDir);
		tagCompound.setInteger("moveDir", moveDir);
		tagCompound.setFloat("angle", angle);
		tagCompound.setFloat("progress", progress);
		tagCompound.setFloat("rotateSpeed", rotateSpeed);
		tagCompound.setFloat("moveSpeed", moveSpeed);
		tagCompound.setBoolean("remote", remoteControl);
		
		tagCompound.setTag("rotatePoint", VectorUtil.writeToNBT(rotatePoint));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
		this.rotateDir = tagCompound.getInteger("rotateDir");
		this.moveDir = tagCompound.getInteger("moveDir");
		this.angle = tagCompound.getFloat("angle");
		this.rotateSpeed = tagCompound.getFloat("rotateSpeed");
		this.moveSpeed = tagCompound.getFloat("moveSpeed");
		this.remoteControl = tagCompound.getBoolean("remote");
		
		this.rotatePoint = VectorUtil.readFromNBT(tagCompound.getCompoundTag("rotatePoint"));
		
		if (!isInMotion()) {
			this.progress = tagCompound.getFloat("progress");
		}
	}
}


