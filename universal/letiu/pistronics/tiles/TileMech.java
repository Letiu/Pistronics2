package letiu.pistronics.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import letiu.modbase.util.NBTUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.data.PTile;
import letiu.pistronics.render.PRenderManager;
import letiu.pistronics.render.PTileRenderer;
import letiu.pistronics.util.BlockProxy;

public abstract class TileMech extends TileElementHolder implements ISpecialRenderTile, ITransmitter {

//	private SystemController controller;
	
	private int moveDir = -1, rotateDir = -1;
	private float progress, angle;
	private float moveSpeed, rotateSpeed;
	
	public boolean camou = false;
	public int camouID = -1;
	public int camouMeta = 0;
	
	public boolean active = false;
	
	@Override
	public PTileRenderer getRenderer() {
		return PRenderManager.mechElementRenderer;
	}
	
	@Override
	public void update() {
		super.update();
		
		if (rotateDir != -1 && rotateSpeed != 0) {
			angle += rotateSpeed;
			if (angle >= 90) {
				rotateDir = -1;
				angle = 0;
			}
		}
		if (moveDir != -1 && moveSpeed != 0) {
			progress += moveSpeed;
//			if (controller != null) {
//				controller.updateAll(progress);
//			}
			if (progress >= 1F) {
				moveDir = -1;
				progress = 0;
			}
		}
		
		
		
	}
	
//	public void setController(SystemController controller) {
//		this.controller = controller;
//	}
	
	public void move(int moveDir, float speed) {
		this.progress = 0;
		this.moveDir = moveDir;
		this.moveSpeed = speed;
	}
	
	public void rotate(int rotateDir, float speed) {
		this.rotateDir = rotateDir;
		this.rotateSpeed = speed;
	}
	
	public void spawnElement(int moveDir, float speed) {
		
		int x = tileEntity.xCoord + Facing.offsetsXForSide[moveDir];
		int y = tileEntity.yCoord + Facing.offsetsYForSide[moveDir];
		int z = tileEntity.zCoord + Facing.offsetsZForSide[moveDir];
		World world = tileEntity.getWorldObj();
		
		if (element != null) WorldUtil.setBlock(world, x, y, z, element, moveDir, 0);
		
		if (elementTile != null) {			
			NBTTagCompound nbt = NBTUtil.getNewCompound();
			elementTile.writeToNBT(nbt);

			TileEntity tile = WorldUtil.getTileEntity(world, x, y, z);
			
			tile.readFromNBT(nbt);
			tile.setWorldObj(world);
			tile.xCoord = x;
			tile.yCoord = y;
			tile.zCoord = z;
		}
		
		BlockProxy proxy = new BlockProxy(world, x, y, z);
		proxy.moveAnimation(moveDir, speed);
		proxy.markForUpdate();
	}
	
	public float getProgressForRender(float ticktime) {
		if (ticktime > 1F) ticktime = 1F;
		
		return ((float) progress) + moveSpeed * ticktime;
	}
	
	public float getAngleForRender(float ticktime) {
		if (ticktime > 1F) ticktime = 1F;
		
		return ((float) angle) + rotateSpeed * ticktime - 90F;
	}

	public int getRotateDir() {
		return rotateDir;
	}
	
	public int getMoveDir() {
		return moveDir;
	}
	
	public boolean isMoving() {
		return moveDir != -1 && moveSpeed != 0F;
	}
	
	public boolean isRotating() {
		return rotateDir != -1 && rotateSpeed != 0F;
	}
	
	public boolean isInMotion() {
		return isMoving() || isRotating();
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
	
	// ITransmitter Methods //
	
	@Override
	public boolean isConductive() {
		PTile eTile = getPElementTile();
		if (eTile != null && eTile instanceof ITransmitter) {
			return ((ITransmitter) eTile).isConductive();
		}
		return false;
	}

	@Override
	public void setStrength(int strength) {
		PTile eTile = getPElementTile();
		if (eTile != null && eTile instanceof ITransmitter) {
			((ITransmitter) eTile).setStrength(strength);
		}
	}

	@Override
	public int getStrength() {
		PTile eTile = getPElementTile();
		if (eTile != null && eTile instanceof ITransmitter) {
			return ((ITransmitter) eTile).getStrength();
		}
		return 0;
	}

	@Override
	public boolean isActive() {
		PTile eTile = getPElementTile();
		if (eTile != null && eTile instanceof ITransmitter) {
			return ((ITransmitter) eTile).isActive();
		}
		return false;
	}

	@Override
	public void pulse(int ticks) {
		PTile eTile = getPElementTile();
		if (eTile != null && eTile instanceof ITransmitter) {
			((ITransmitter) eTile).pulse(ticks);
		}
	}

	@Override
	public void setToInput() {
		PTile eTile = getPElementTile();
		if (eTile != null && eTile instanceof ITransmitter) {
			((ITransmitter) eTile).setToInput();
		}
	}

	@Override
	public void setToOutput() {
		PTile eTile = getPElementTile();
		if (eTile != null && eTile instanceof ITransmitter) {
			((ITransmitter) eTile).setToOutput();
		}
	}

	@Override
	public boolean isInput() {
		PTile eTile = getPElementTile();
		if (eTile != null && eTile instanceof ITransmitter) {
			return ((ITransmitter) eTile).isInput();
		}
		return false;
	}

	@Override
	public void notifyOutputBlocks(BlockProxy proxy) {
		PTile eTile = getPElementTile();
		if (eTile != null && eTile instanceof TileExtension) {
			int facing = proxy.getFacing();
			proxy = proxy.getNeighbor(facing);
			proxy.notifyBlockOfNeighborChange(0);
			proxy = proxy.getNeighbor(facing);
			proxy.notifyNeighbors(0);
		}
	}

	@Override
	public void checkNextInput() {
		PTile eTile = getPElementTile();
		if (eTile != null && eTile instanceof ITransmitter) {
			((ITransmitter) eTile).checkNextInput();
		}
	}
	
	// ITransmitter Methods End //

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		tagCompound.setInteger("moveDir", moveDir);
		tagCompound.setFloat("progress", progress);
		tagCompound.setFloat("moveSpeed", moveSpeed);
		tagCompound.setInteger("rotateDir", rotateDir);
		tagCompound.setFloat("angle", angle);
		tagCompound.setFloat("rotateSpeed", rotateSpeed);
		
		tagCompound.setBoolean("active", active);
		
		tagCompound.setBoolean("camou", camou);
		tagCompound.setInteger("camouID", camouID);
		tagCompound.setInteger("camouMeta", camouMeta);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
		this.moveDir = tagCompound.getInteger("moveDir");
		this.progress = tagCompound.getFloat("progress");
		this.moveSpeed = tagCompound.getFloat("moveSpeed");
		this.rotateDir = tagCompound.getInteger("rotateDir");
		this.angle = tagCompound.getFloat("angle");
		this.rotateSpeed = tagCompound.getFloat("rotateSpeed");
		
		this.active = tagCompound.getBoolean("active");
		
		this.camou = tagCompound.getBoolean("camou");
		this.camouID = tagCompound.getInteger("camouID");
		this.camouMeta = tagCompound.getInteger("camouMeta");
		
		if (!camou && camouID == 0) camouID = -1; // prevents old machines from showing a missing texture
	}

}
