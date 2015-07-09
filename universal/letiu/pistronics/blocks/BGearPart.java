package letiu.pistronics.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PTile;
import letiu.pistronics.piston.IPistonElement;
import letiu.pistronics.piston.ISpecialRotator;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.tiles.TileGear;
import letiu.pistronics.util.BlockProxy;
import letiu.pistronics.util.RotateUtil;
import letiu.pistronics.util.Vector3;

public class BGearPart extends PBlock implements IPistonElement, ISpecialRotator {
	
	public BGearPart() {
		this.name = "GearPart";
		this.material = "wood";
		this.hardness = 3F;
		this.resistance = 10F;
		this.creativeTab = false;
		this.textures = new String[1];
		this.textures[0] = Textures.BOX;
		this.blockIcon = this.textures[0];
	}
	

	@Override
	public String getTextureIndex(PTile tile, int meta, int side) {
		return Textures.BOX;
	}


	@Override
	public String getTexture(int meta, int side) {
		return Textures.BOX;
	}
	
	@Override
	public int getRenderID() {
		return -1;
	}
	
	@Override
	public boolean isOpaque() {
		return false;
	}
	
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	protected Vector3 findGear(BlockProxy proxy) {
		return this.findGear(proxy.getWorld(), proxy.getCoords().x, proxy.getCoords().y, proxy.getCoords().z, proxy.getMetadata());
	}
	
	protected Vector3 findGear(IBlockAccess world, int x, int y, int z, int meta) {
		
		Vector3 result = new Vector3(x, y, z);
	
		int facing = meta & 7;
		//int gearDir = FacingUtil.rotate(meta, ((meta & 8) == 0) ? 2 : 4);
		
		for (int counter = 0; counter < 10; counter++) {
			
			result.x += Facing.offsetsXForSide[facing];
			result.y += Facing.offsetsYForSide[facing];
			result.z += Facing.offsetsZForSide[facing];
			
			if (WorldUtil.getPBlock(world, result.x, result.y, result.z) instanceof BGear) {
				return result;
			}
			
			facing = WorldUtil.getBlockMeta(world, result.x, result.y, result.z) & 7;
		}
		
		return new Vector3(x, y, z);
	}
	
	@Override
	public ArrayList<AxisAlignedBB> getBoxes(IBlockAccess world, int x, int y, int z, int meta) {
		ArrayList<AxisAlignedBB> boxes = new ArrayList<AxisAlignedBB>();
		
		Vector3 gearPos = this.findGear(world, x, y, z, WorldUtil.getBlockMeta(world, x, y, z));
		
		int gearSize = 0; //Math.max(Math.max(Math.abs(gearPos.x - x), Math.abs(gearPos.y - y)), Math.abs(gearPos.z - z));
		
		PTile tile = WorldUtil.getPTile(world, gearPos.x, gearPos.y, gearPos.z);
		if (tile != null && tile instanceof TileGear) {
			gearSize = ((TileGear) tile).getSize() / 2;
		}
		
		AxisAlignedBB box = null;
		switch (WorldUtil.getBlockMeta(world, gearPos.x, gearPos.y, gearPos.z) & 7) {
		case 1: case 0: box = AxisAlignedBB.getBoundingBox(-gearSize, 0.0D, -gearSize, gearSize + 1, 1.0D, gearSize + 1); break;
		case 2: case 3: box = AxisAlignedBB.getBoundingBox(-gearSize, -gearSize, 0.0D, gearSize + 1, gearSize + 1, 1.0D); break;
		case 4: case 5: box = AxisAlignedBB.getBoundingBox(0.0D, -gearSize, -gearSize, 1.0D, gearSize + 1, gearSize + 1); break;
		}
		if (box == null) return null; 
		
		box = box.getOffsetBoundingBox(gearPos.x - x, gearPos.y - y, gearPos.z - z);
		
		boxes.add(box);
		
		return boxes;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		WorldUtil.setBlockFacing(world, x, y, z, player);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float xHit, float yHit, float zHit) {
	
		Vector3 gearCoords = findGear(world, x, y, z, WorldUtil.getBlockMeta(world, x, y, z));
		return BlockData.gear.onBlockActivated(world, gearCoords.x, gearCoords.y, gearCoords.z, player, side, xHit, yHit, zHit);
	}
	
	@Override
	public void onBlockBreak(World world, int x, int y, int z, Block block2, int meta) {
		Vector3 gearPos = findGear(world, x, y, z, meta);
		
		BlockData.gear.onBlockBreak(world, gearPos.x, gearPos.y, gearPos.z, BlockData.gear.block,
				WorldUtil.getBlockFacing(world, gearPos.x, gearPos.y, gearPos.z));
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<ItemStack>();
	}
	
	@Override
	public ItemStack getDroppedStack(PTile tile, int meta) {
		return null;
	}
	
	@Override
	public boolean canDropFromExplosion() {
		return false;
	}
	
	@Override
	public boolean canRotate(World world, int x, int y, int z, int rotateDir,
			float speed, Vector3 rotatePoint) {
		return true;
	}

	@Override
	public void preRotate(World world, int x, int y, int z, int rotateDir,
			float speed, Vector3 rotatePoint) {
		
	}

	@Override
	public void postRotate(World world, int x, int y, int z, int rotateDir,
			float speed, Vector3 rotatePoint) {
		
		int facing = WorldUtil.getBlockFacing(world, x, y, z);
		facing = RotateUtil.rotateDir(facing, rotateDir);
		WorldUtil.setBlockFacing(world, x, y, z, facing);
	}


	@Override
	public void getConnectedForSystem(PistonSystem system, BlockProxy proxy,
			boolean strongConnection) {
		
		BlockProxy gear = new BlockProxy(proxy.getWorld(), findGear(proxy));
		gear.getConnectedForSystem(system, strongConnection);
	}


	@Override
	public boolean connectsToSide(BlockProxy proxy, int side, SystemType type) {
		return false;
	}


	@Override
	public boolean isTransmitter(BlockProxy proxy) {
		return false;
	}

	
}
