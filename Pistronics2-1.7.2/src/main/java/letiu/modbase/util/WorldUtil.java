package letiu.modbase.util;

import java.util.List;
import java.util.Random;

import letiu.modbase.blocks.BaseBlock;
import letiu.modbase.core.ModClass;
import letiu.modbase.render.FakeBlockData;
import letiu.modbase.render.FakeWorld;
import letiu.modbase.tiles.BaseTile;
import letiu.pistronics.data.PTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockVine;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.util.Vector3;

public class WorldUtil {

	public static int getFacing(World world, int x, int y, int z, EntityLivingBase player) {
		return BlockPistonBase.determineOrientation(world, x, y, z, player);
	}
	
	/** Sets the first 3 bits of block metadata depending on the players view. The last bit remains unchanged.*/
	public static int setBlockFacing(World world, int x, int y, int z, EntityLivingBase player) {
		int meta = world.getBlockMetadata(x, y, z);
		int bit4 = meta & 8;
		int facing = BlockPistonBase.determineOrientation(world, x, y, z, player);
        world.setBlockMetadataWithNotify(x, y, z, facing | bit4, 2);
        return facing;
	}
	
	/** Sets the first 3 bits of block metadata to the given value. The last bit remains unchanged.*/
	public static void setBlockFacing(World world, int x, int y, int z, int facing) {
		int meta = world.getBlockMetadata(x, y, z);
		int bit4 = meta & 8;
        world.setBlockMetadataWithNotify(x, y, z, facing | bit4, 2);
	}
	
	/** Returns the first 3 bits of block metadata.*/
	public static int getBlockFacing(IBlockAccess world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z) & 7;
	}
	
	/** Returns the first 3 bits of block metadata.*/
	public static int getBlockFacing(PTile tile) {
		return tile.tileEntity.getWorldObj().getBlockMetadata(tile.tileEntity.xCoord, tile.tileEntity.yCoord, tile.tileEntity.zCoord) & 7;
	}
	
	public static int getBlockMeta(IBlockAccess world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}
	
	public static void toggltBit4(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
        world.setBlockMetadataWithNotify(x, y, z, meta ^ 8, 7);
	}
	
	public static void toggltBit1(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
        world.setBlockMetadataWithNotify(x, y, z, meta ^ 1, 3);
	}
	
	public static void toggltBit4(TileEntity tile) {
		if (tile != null) toggltBit4(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
	}
	
	public static void setBlockBounds(IBlockAccess world, int x, int y, int z, float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		world.getBlock(x, y, z).setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	public static void setBlockBounds(IBlockAccess world, int x, int y, int z, AxisAlignedBB box) {
		world.getBlock(x, y, z).setBlockBounds((float) box.minX, (float) box.minY, (float) box.minZ, (float) box.maxX, (float) box.maxY, (float) box.maxZ);
	}
	
	public static void setBlockBounds(Block block, float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		block.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	public static TileEntity getTileEntity(IBlockAccess world, int x, int y, int z) {
		return world.getTileEntity(x, y, z);
	}
	
	public static void setTileEntity(World world, int x, int y, int z, TileEntity tile) {
		world.setTileEntity(x, y, z, tile);
	}
	
	/** Gets the TileEntity at the given position and returns the data if it's a BasicTile */
	public static PTile getPTile(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = getTileEntity(world, x, y, z);
		if (tile instanceof BaseTile) {
			return ((BaseTile) tile).data;
		}
		else {
			return null;
		}
	}
	
	public static void updateBlock(TileEntity tile) {
		if (tile != null) {
			tile.getWorldObj().markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
		}
	}
	
	public static void updateClientBlock(TileEntity tile) {
		if (tile != null) {
			World world = tile.getWorldObj();
			if (world != null && world.isRemote) {
				tile.getWorldObj().markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
			}
		}
	}
	
	public static void updateBlock(World world, int x, int y, int z) {
		world.markBlockForUpdate(x, y, z);
	}
	
	public static void setBlock(World world, int x, int y, int z, Block block) {
		world.setBlock(x, y, z, block);
	}
	
	public static void setBlock(World world, int x, int y, int z, Block block, int meta, int flag) {
		world.setBlock(x, y, z, block, meta, flag);
	}
	
	public static void setBlock(TileEntity tile, Block block) {
		tile.getWorldObj().setBlock(tile.xCoord, tile.yCoord, tile.zCoord, block);
	}
	
	public static void setBlockToAir(World world, int x, int y, int z) {
		world.setBlockToAir(x, y, z);
	}
	
	public static void setBlockToAir(TileEntity tile) {
		tile.getWorldObj().setBlockToAir(tile.xCoord, tile.yCoord, tile.zCoord);
	}
	
	public static void spawnItemStack(World world, int x, int y, int z, ItemStack stack) {
		EntityItem entity = new EntityItem(world, x, y, z, stack);
		world.spawnEntityInWorld(entity);
	}
	
	public static void spawnItemStacks(World world, int x, int y, int z, ItemStack... stacks) {
		for (ItemStack stack : stacks) {
			spawnItemStack(world, x, y, z, stack);
		}
	}
	
	public static void spawnItemStacks(World world, int x, int y, int z, List<ItemStack> stacks) {
		for (ItemStack stack : stacks) {
			spawnItemStack(world, x, y, z, stack);
		}
	}

	public static int getBlockID(World world, int x, int y, int z) {
		return BlockItemUtil.getBlockID(world.getBlock(x, y, z));
	}

	public static Block getBlock(IBlockAccess world, int x, int y, int z) {
		return world.getBlock(x, y, z);
	}
	
	public static PBlock getPBlock(IBlockAccess world, int x, int y, int z) {
		Block block = getBlock(world, x, y, z);
		if (block != null && block instanceof BaseBlock) {
			return ((BaseBlock) block).data;
		}
		return null;
	}

	public static boolean isAirBlock(World world, int x, int y, int z) {
		return world.isAirBlock(x, y, z);
	}

	public static boolean isSolid(World world, int x, int y, int z) {
		Block block = getBlock(world, x, y, z);
		if (block.getMobilityFlag() == 1) return false;
		return !isAirBlock(world, x, y, z);
	}
	
	public static boolean isBlockReplaceable(World world, int x, int y, int z) {
		Block block = getBlock(world, x, y, z);
		return block.isReplaceable(world, x, y, z);
	}
	
	public static void setBlockMeta(World world, int x, int y, int z, int meta, int flag) {
		world.setBlockMetadataWithNotify(x, y, z, meta, flag);
	}
	
	public static void markBlockForUpdate(World world, int x, int y, int z) {
		world.markBlockForUpdate(x, y, z);
	}
	
	public static void notifyBlockOfNeighborChange(World world, int x, int y, int z, int blockID) {
		world.notifyBlockOfNeighborChange(x, y, z, BlockItemUtil.getBlockByID(blockID));
	}
	
	public static void notifyBlocksOfNeighborChange(World world, int x, int y, int z, int blockID) {
		world.notifyBlocksOfNeighborChange(x, y, z, BlockItemUtil.getBlockByID(blockID));
	}

	public static void removeTileEntity(World world, int x, int y, int z) {
		world.removeTileEntity(x, y, z);
	}

	public static void cleanBlock(World world, int x, int y, int z) {
		if (!world.isRemote) {
			world.isRemote = true;
			world.setBlockToAir(x, y, z);
			world.isRemote = false;
		}
		else world.setBlockToAir(x, y, z);
	}
	
	public static TileEntity getTileFromBlock(World world, Block block, int meta) {
		return block.createTileEntity(world, meta);
	}
	
	public static boolean isIndirectlyPowered(World world, int x, int y, int z) { 
		return world.isBlockIndirectlyGettingPowered(x, y, z);
	}
	 
	public static boolean providesPowerTo(World world, int x, int y, int z, int side) {
		return 0 < world.isBlockProvidingPowerTo(x, y, z, side);
	}
	
	public static void spawnSmokeAt(World world, int x, int y, int z) {
		Random rand = new Random();
		for (int i = 0; i < 30; i++) {
			float randX = (float)x + rand.nextFloat();
            float randY = (float)y + rand.nextFloat();
            float randZ = (float)z + rand.nextFloat();
            world.spawnParticle("largesmoke", (double)randX, (double)randY, (double)randZ, 0.0D, 0.0D, 0.0D);
		}
	}
	
	public static void spawnCraftSmokeAt(World world, int x, int y, int z) {
		Random rand = new Random();
		for (int i = 0; i < 8; i++) {
			float randX = (float)x + rand.nextFloat();
            float randY = (float)y + rand.nextFloat();
            float randZ = (float)z + rand.nextFloat();
            world.spawnParticle("explode", (double)randX, (double)randY, (double)randZ, 0.0D, 0.0D, 0.0D);
		}
	}
	
	public static boolean dependsOnSide(World world, int x, int y, int z, int side) {
	
		FakeWorld fakeWorld = ModClass.proxy.getLogicFakeWorld(world);
		fakeWorld.reset();
		
		Block block = world.getBlock(x, y, z);

		int woX = x + Facing.offsetsXForSide[side];
		int woY = y + Facing.offsetsYForSide[side];
		int woZ = z + Facing.offsetsZForSide[side];
		
//		fakeWorld.setSolo(new Vector3(x, y, z));
		
		//if (!block.canPlaceBlockAt(world, x, y, z)) return false;
		
		fakeWorld.addFakeBlockData(new FakeBlockData(woX, woY, woZ, 0, 0));
		fakeWorld.addFakeBlockData(new FakeBlockData(x, y, z, 0, 0));
		if (block.canPlaceBlockAt(fakeWorld, x, y, z)) {
			
			if (block instanceof BlockTorch || block instanceof BlockButton) {
				int meta = getBlockMeta(world, x, y, z) & 7;
				switch (side ^ 1) {
				case 5: return meta == 1; // EAST
				case 4: return meta == 2; // WEST
				case 3: return meta == 3; // SOUTH
				case 2: return meta == 4; // NORTH
				}
			}
			
			if (block instanceof BlockLadder) {
				int meta = getBlockMeta(world, x, y, z) & 7;
				switch (side ^ 1) {
				case 5: return meta == 5; // EAST
				case 4: return meta == 4; // WEST
				case 3: return meta == 3; // SOUTH
				case 2: return meta == 2; // NORTH
				}
			}
			
			if (block instanceof BlockVine) {
				int meta = getBlockMeta(world, x, y, z) & 7;
				switch (side ^ 1) {
				case 5: return meta == 2; // EAST
				case 4: return meta == 8; // WEST
				case 3: return meta == 4; // SOUTH
				case 2: return meta == 1; // NORTH
				}
			}
			
			if (block instanceof BlockSign) {
				int meta = getBlockMeta(world, x, y, z) & 7;
				switch (side ^ 1) {
				case 5: return meta == 5; // EAST
				case 4: return meta == 4; // WEST
				case 3: return meta == 3; // SOUTH
				case 2: return meta == 2; // NORTH
				}
			} 
			
			if (block instanceof BlockLever) {
				int meta = getBlockMeta(world, x, y, z) & 7;
				switch (side ^ 1) {
				case 5: return meta == 1; // EAST
				case 4: return meta == 2; // WEST
				case 3: return meta == 3; // SOUTH
				case 2: return meta == 4; // NORTH
				case 1: return meta == 5 || meta == 6; // UP
				case 0: return meta == 7 || meta == 0; // DOWN
				}
			}
			
			return false;
		}
		else return true;
	}
	
	public static boolean dependsOnSide(World world, Vector3 coords, int side) {
		return dependsOnSide(world, coords.x, coords.y, coords.z, side);
	}
	
	public static void removeTile(World world, int x, int y, int z) {
		world.removeTileEntity(x, y, z);
	}
	
	public static <T extends PTile> T getTile(World world, int x, int y, int z, Class<T> type) {
		PTile tile = getPTile(world, x, y, z);
		if (type.isInstance(tile)) {
			return type.cast(tile);
		}
		else return null;
	}
}
