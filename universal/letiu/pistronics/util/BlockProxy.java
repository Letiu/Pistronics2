package letiu.pistronics.util;

import java.util.HashSet;
import java.util.Set;

import letiu.modbase.blocks.BaseBlock;
import letiu.modbase.core.ModClass;
import letiu.modbase.render.FakeBlockData;
import letiu.modbase.tiles.BaseTile;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.NBTUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PTile;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.tiles.TileMotion;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import letiu.pistronics.piston.IPistonElement;
import letiu.pistronics.piston.PistonSystem.SystemType;

public class BlockProxy {

	private World world;
	private Vector3 coords;
	
	public BlockProxy(World world, Vector3 coords) {
		this.world = world;
		this.coords = coords;
	}
	
	public BlockProxy(World world, int x, int y, int z) {
		this(world, new Vector3(x, y, z));
	}
	
	public BlockProxy(TileEntity tile) {
		this(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
	}
	
	public BlockProxy(PTile tile) {
		this(tile.tileEntity);
	}
	
	public Vector3 getCoords() {
		return coords;
	}
	
	public void setCoords(Vector3 coords) {
		this.coords = coords;
	}
	
	public BlockProxy copy() {
		return new BlockProxy(world, coords.copy());
	}
	
	public BlockProxy getNeighbor(int side) {
		return new BlockProxy(world, coords.copy().add(FacingUtil.getOffsetForSide(side)));
	}
	
	public Set<BlockProxy> getNeighbors() {
		HashSet<BlockProxy> result = new HashSet<BlockProxy>();
		for (int i = 0; i < 6; i++) {
			result.add(getNeighbor(i));
		}
		return result;
	}
	
	public Set<BlockProxy> getNeighbors(int... sides) {
		HashSet<BlockProxy> result = new HashSet<BlockProxy>();
		for (int i : sides) {
			result.add(getNeighbor(i));
		}
		return result;
	}
	
	public Block getBlock() {
		return WorldUtil.getBlock(world, coords.x, coords.y, coords.z);
	}
	
	public PBlock getPBlock() {
		return WorldUtil.getPBlock(world, coords.x, coords.y, coords.z);
	}
	
	public boolean isAir() {
		return WorldUtil.isAirBlock(world, coords.x, coords.y, coords.z);
	}
	
	public boolean isSolid() {
		return WorldUtil.isSolid(world, coords.x, coords.y, coords.z);
	}
	
	public TileEntity getTileEntity() {
		return WorldUtil.getTileEntity(world, coords.x, coords.y, coords.z);
	}
	
	public PTile getPTile() {
		return WorldUtil.getPTile(world, coords.x, coords.y, coords.z);
	}
	
	public int getMetadata() {
		return WorldUtil.getBlockMeta(world, coords.x, coords.y, coords.z);
	}
	
	public int getFacing() {
		return WorldUtil.getBlockFacing(world, coords.x, coords.y, coords.z);
	}
	
	public int getFacing(EntityPlayer player) {
		return WorldUtil.getFacing(world, coords.x, coords.y, coords.z, player);
	}
	
	public String getBlockName() {
		return getBlock().getUnlocalizedName();
	}
	
	public World getWorld() {
		return world;
	}
	
	public int getMobilityFlag() {
		return getBlock().getMobilityFlag();
	}
	
	public int getPowerInput() {
		return world.getBlockPowerInput(coords.x, coords.y, coords.z);
	}
	
	public int getPowerOutput(int side) {
		Block block = this.getBlock();
		if (block != null) {
			int weak = block.isProvidingWeakPower(world, coords.x, coords.y, coords.z, side);
			int strong = block.isProvidingStrongPower(world, coords.x, coords.y, coords.z, side);
			return Math.max(weak, strong);
		}
		return 0;
	}
	
	public int getPowerInputFrom(int side) {
		BlockProxy neighProxy = this.getNeighbor(side);
		Block neighbor = neighProxy.getBlock();
		if (neighbor != null) {
			int weak = neighbor.isProvidingWeakPower(world, neighProxy.getCoords().x, neighProxy.getCoords().y, neighProxy.getCoords().z, side);
			int strong = neighbor.isProvidingStrongPower(world, neighProxy.getCoords().x, neighProxy.getCoords().y, neighProxy.getCoords().z, side);
			return Math.max(weak, strong);
		}
		return 0;
//		return world.getIndirectPowerLevelTo(coords.x, coords.y, coords.z, side); 
//		return this.getNeighbor(side).getPowerOutput(side ^ 1);
//		return this.getPowerOutput(side);
	}
	
	public boolean isPowered() {
		return WorldUtil.isIndirectlyPowered(world, coords.x, coords.y, coords.z);
	}
	
	public boolean providesPowerTo(int side) {
		return WorldUtil.providesPowerTo(world, coords.x, coords.y, coords.z, side);
	}
	
	public void setBlock(Block block) {
		WorldUtil.setBlock(world, coords.x, coords.y, coords.z, block);
	}
	
	public void setMetadata(int meta, int flag) {
		WorldUtil.setBlockMeta(world, coords.x, coords.y, coords.z, meta, flag);
	}
	
	public void setFacing(EntityLivingBase entity) {
		WorldUtil.setBlockFacing(world, coords.x, coords.y, coords.z, entity);
	}
	
	public void setFacing(int facing) {
		WorldUtil.setBlockFacing(world, coords.x, coords.y, coords.z, facing);
	}
	
	public void clean() {
		WorldUtil.cleanBlock(world, coords.x, coords.y, coords.z);
	}
	
	public void markForUpdate() {
		WorldUtil.markBlockForUpdate(world, coords.x, coords.y, coords.z);
	}
	
	public void notifyBlockOfNeighborChange(int blockID) {
		WorldUtil.notifyBlockOfNeighborChange(world, coords.x, coords.y, coords.z, blockID);
	}
	
	public void notifyNeighbors(int blockID) {
		WorldUtil.notifyBlocksOfNeighborChange(world, coords.x, coords.y, coords.z, blockID);
	}
	
	public boolean isPistonElement() {
		Block block = WorldUtil.getBlock(world, coords.x, coords.y, coords.z);
		if (block != null && block instanceof BaseBlock) {
			PBlock pBlock = ((BaseBlock) block).data;
			if (pBlock != null && pBlock instanceof IPistonElement) {
				return true;
			}
		}
		return false;
	}
	
	public void getConnectedForSystem(PistonSystem system, boolean strongConnection) {
		if (strongConnection) {
			if (system.hasStatic(this)) return; 
		}
		else if (system.hasElement(this)) return;
		Block block = WorldUtil.getBlock(world, coords.x, coords.y, coords.z);
		if (block != null && block instanceof BaseBlock) {
			PBlock pBlock = ((BaseBlock) block).data;
			if (pBlock != null && pBlock instanceof IPistonElement) {
				((IPistonElement) pBlock).getConnectedForSystem(system, this, strongConnection);
			}
		}
	}
	
	public boolean connectsToSide(int side, SystemType type) {
		Block block = WorldUtil.getBlock(world, coords.x, coords.y, coords.z);
		if (block != null && block instanceof BaseBlock) {
			PBlock pBlock = ((BaseBlock) block).data;
			if (pBlock != null && pBlock instanceof IPistonElement) {
				return ((IPistonElement) pBlock).connectsToSide(this, side, type);
			}
		}
		return false;
	}
	
	private void move(int dir, float speed, Vector3 target) {
		
		Block storedBlock = this.getBlock();
		int storedMetadata = this.getMetadata();
		TileEntity storedTl = this.getTileEntity();
		
		NBTTagCompound storedNBT = NBTUtil.getNewCompound();
        if (storedTl != null) storedTl.writeToNBT(storedNBT);
        
        if (this.world.isRemote) {
        	ModClass.proxy.getFakeWorld(world).clearFakeDataAt(coords);
        	ModClass.proxy.getFakeWorld(world).clearFakeDataAt(target);

        	FakeBlockData data = new FakeBlockData(target.x, target.y, target.z, BlockItemUtil.getBlockID(storedBlock), storedMetadata);
        	ModClass.proxy.getFakeWorld(world).addFakeBlockData(data);
        	
        	if (storedTl != null) {
        		ModClass.proxy.getFakeWorld(world).addFakeTl(storedTl, target);
        	}
        }
        
        WorldUtil.removeTileEntity(world, coords.x, coords.y, coords.z);
        
        try {
        	WorldUtil.cleanBlock(world, coords.x, coords.y, coords.z);
        }
        catch (NullPointerException e) {System.out.println("catched nullPointer in setToAir");}
        
        
       
        WorldUtil.setBlock(world, target.x, target.y, target.z, BlockData.motionblock.block, storedMetadata, 4);
       
        TileMotion motion = (TileMotion) WorldUtil.getPTile(world, target.x, target.y, target.z);
		motion.setContent(storedBlock, storedMetadata, storedTl);
		motion.move(dir, speed);
		
//        WorldUtil.notifyBlocksOfNeighborChange(world, target.x, target.y, target.z, motionID);
//        WorldUtil.notifyBlocksOfNeighborChange(world, coords.x, coords.y, coords.z, motionID);
//        WorldUtil.markBlockForUpdate(world, target.x, target.y, target.z);
	}
	
	/** moves the block and notifies neighbors of start and end position */
	public void move(int dir, float speed) {
		Vector3 target = coords.copy().add(FacingUtil.getOffsetForSide(dir));
		
		// move block //
		move(dir, speed, target);
		
		// notify neighbors //
		int motionID = BlockItemUtil.getBlockID(BlockData.motionblock.block);
		WorldUtil.notifyBlocksOfNeighborChange(world, target.x, target.y, target.z, motionID);
        WorldUtil.notifyBlocksOfNeighborChange(world, coords.x, coords.y, coords.z, motionID);
	}
	
	/** moves the block without notify */
	public void moveWithoutNotify(int dir, float speed) {
		move(dir, speed, coords.copy().add(FacingUtil.getOffsetForSide(dir)));
	} 
	
	/** moves the block with same start and end position */
	public void moveAnimation(int dir, float speed) {
		move(dir, speed, coords.copy());
	}
	
	@Deprecated
	public void rotate(Vector3 rotatePoint, int rotateDir, float speed) {
		
		Block storedBlock = this.getBlock();
		int storedMetadata = this.getMetadata();
		TileEntity storedTl = this.getTileEntity();
		
		NBTTagCompound storedNBT = NBTUtil.getNewCompound();
        if (storedTl != null) storedTl.writeToNBT(storedNBT);
        
        Vector3 target = VectorUtil.rotateAround(coords, rotatePoint, rotateDir);
        
        if (this.world.isRemote) {
        	ModClass.proxy.getFakeWorld(world).clearFakeDataAt(target);

        	FakeBlockData data = new FakeBlockData(target.x, target.y, target.z, BlockItemUtil.getBlockID(storedBlock), storedMetadata);
        	ModClass.proxy.getFakeWorld(world).addFakeBlockData(data);
        	
        	if (storedTl != null) {
        		ModClass.proxy.getFakeWorld(world).addFakeTl(storedTl, target);
        	}
        }
        
        WorldUtil.removeTileEntity(world, coords.x, coords.y, coords.z);
		
        try {
        	WorldUtil.cleanBlock(world, coords.x, coords.y, coords.z);
        }
        catch (NullPointerException e) {System.out.println("catched nullPointer in setToAir");}
        
        int motionID = BlockItemUtil.getBlockID(BlockData.motionblock.block);
        
      
		WorldUtil.setBlock(world, target.x, target.y, target.z, BlockData.motionblock.block, storedMetadata, 4);
		
		TileMotion motion = (TileMotion) WorldUtil.getPTile(world, target.x, target.y, target.z);
		motion.setContent(storedBlock, storedMetadata, storedTl);
		motion.rotate(rotateDir, speed, rotatePoint);
		
		WorldUtil.notifyBlocksOfNeighborChange(world, target.x, target.y, target.z, motionID);
        WorldUtil.notifyBlocksOfNeighborChange(world, coords.x, coords.y, coords.z, motionID);
//        WorldUtil.markBlockForUpdate(world, target.x, target.y, target.z);
	}
	
	public TileEntity getTileEntityForRotate(Vector3 rotatePoint, int rotateDir, float speed) {
		
		Block storedBlock = this.getBlock();
		int storedMetadata = this.getMetadata();
		TileEntity storedTl = this.getTileEntity();
		
		NBTTagCompound storedNBT = NBTUtil.getNewCompound();
        if (storedTl != null) storedTl.writeToNBT(storedNBT);
        
        BaseTile tile = (BaseTile) BlockData.motionblock.block.createTileEntity(world, 0);
        TileMotion motion = (TileMotion) tile.data;
        
        motion.setContent(storedBlock, storedMetadata, storedTl);
        motion.rotate(rotateDir, speed, rotatePoint);
        
        return tile;
		//return BlockMechRotating.getTileEntity(storedBlockID, storedMetadata, storedTl, storedNBT, 1, true, rotatePoint, rotateDir, speed);	
	}
	
	public void rotateWithTileEntity(Vector3 rotatePoint, int rotateDir, TileEntity tile) {
		TileMotion motion = (TileMotion) ((BaseTile) tile).data;
		int storedMetadata = motion.getElementMeta();
		
		Vector3 target = VectorUtil.rotateAround(coords, rotatePoint, rotateDir);
		TileEntity storedTl = motion.getElementTile();
		
		if (this.world.isRemote) {
        	//ModClass.proxy.getFakeWorld().clearFakeDataAt(coords);
        	ModClass.proxy.getFakeWorld(world).clearFakeDataAt(target);

        	FakeBlockData data = new FakeBlockData(target.x, target.y, target.z, BlockItemUtil.getBlockID(motion.getElement()), storedMetadata);
        	ModClass.proxy.getFakeWorld(world).addFakeBlockData(data);
        	
        	if (storedTl != null) {
        		ModClass.proxy.getFakeWorld(world).addFakeTl(storedTl, target);
        	}
        }
		
		try {
	        if (!(this.getPTile() instanceof TileMotion)) {
		        WorldUtil.removeTileEntity(world, coords.x, coords.y, coords.z);
		        WorldUtil.cleanBlock(world, coords.x, coords.y, coords.z);
	        }
	       
	        if (!(WorldUtil.getPTile(world, target.x, target.y, target.z) instanceof TileMotion)) {
	        	WorldUtil.removeTileEntity(world, target.x, target.y, target.z);
		        WorldUtil.cleanBlock(world, target.x, target.y, target.z);
	        }
        }
        catch (NullPointerException e) {System.out.println("catched nullPointer in setToAir");}
        
		int motionID = BlockItemUtil.getBlockID(BlockData.motionblock.block);
	      
		WorldUtil.setBlock(world, target.x, target.y, target.z, BlockData.motionblock.block, storedMetadata, 4);
		WorldUtil.setTileEntity(world, target.x, target.y, target.z, tile);
		
		WorldUtil.notifyBlocksOfNeighborChange(world, target.x, target.y, target.z, motionID);
        WorldUtil.notifyBlocksOfNeighborChange(world, coords.x, coords.y, coords.z, motionID);
        WorldUtil.markBlockForUpdate(world, target.x, target.y, target.z);
	}
	
	public void dropBlockAsItem() {
		getBlock().dropBlockAsItem(world, coords.x, coords.y, coords.z, getMetadata(), 0);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coords == null) ? 0 : coords.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlockProxy other = (BlockProxy) obj;
		if (coords == null) {
			if (other.coords != null)
				return false;
		} else if (!coords.equals(other.coords))
			return false;
		return true;
	}
	
	@Override 
	public String toString() {
		return "BlockProxy: " + coords.toString();
	}
}