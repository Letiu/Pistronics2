package letiu.pistronics.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import letiu.modbase.network.PacketHandler;
import letiu.modbase.util.BlockItemUtil;
import letiu.modbase.util.CompareUtil;
import letiu.modbase.util.NBTUtil;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.ItemData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PItem;
import letiu.pistronics.data.PTile;
import letiu.pistronics.gears.Gear;
import letiu.pistronics.packets.GearDataPacket;
import letiu.pistronics.piston.IPistonElement;
import letiu.pistronics.piston.ISpecialRotator;
import letiu.pistronics.piston.PistonSystem;
import letiu.pistronics.piston.PistonSystem.SystemType;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.tiles.TileGear;
import letiu.pistronics.util.BlockProxy;
import letiu.pistronics.util.FacingUtil;
import letiu.pistronics.util.MiscUtil;
import letiu.pistronics.util.RotateUtil;
import letiu.pistronics.util.Vector3;

public class BGear extends PBlock implements IPistonElement, ISpecialRotator {
	
	public BGear() {
		this.name = "Gear";
		this.material = "wood";
		this.hardness = 4F;
		this.resistance = 10F;
		this.creativeTab = true;
		this.textures = new String[1];
		this.textures[0] = Textures.BOX;
		this.blockIcon = this.textures[0];
	}
	
	@Override
	public PItem getItemBlock() {
		return ItemData.gear;
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
	public boolean getSubBlocks(int itemID, CreativeTabs tab, List list) {
		
		ItemStack stack;
		
		stack = BlockItemUtil.getStack(this);
		stack.stackTagCompound = getDefaultNBT();
		list.add(stack);
		
		stack = BlockItemUtil.getStack(this);
		stack.stackTagCompound = getDefaultNBT();
		stack.stackTagCompound.setInteger("size", 3);
		list.add(stack);
		
		stack = BlockItemUtil.getStack(this);
		stack.stackTagCompound = getDefaultNBT();
		stack.stackTagCompound.setInteger("size", 5);
		list.add(stack);
		
		return true;
	}
	
	public static NBTTagCompound getDefaultNBT() {
		NBTTagCompound nbt = NBTUtil.getNewCompound();
		
		nbt.setInteger("size", 1);
		nbt.setInteger("meta", 0);
		nbt.setBoolean("rod", false);
		
		return nbt;
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public PTile createPTile() {
		return new TileGear();
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
	
	@Override
	public ArrayList<AxisAlignedBB> getBoxes(IBlockAccess world, int x, int y, int z, int meta) {
		ArrayList<AxisAlignedBB> boxes = new ArrayList<AxisAlignedBB>();
		
		TileGear tile = (TileGear) WorldUtil.getPTile(world, x, y, z);
		
		int gearSize = tile.getSize() / 2;
		
		AxisAlignedBB box = null;
		switch (meta & 7) {
		case 1: case 0: box = AxisAlignedBB.getBoundingBox(-gearSize, 0.0D, -gearSize, gearSize + 1, 1.0D, gearSize + 1); break;
		case 2: case 3: box = AxisAlignedBB.getBoundingBox(-gearSize, -gearSize, 0.0D, gearSize + 1, gearSize + 1, 1.0D); break;
		case 4: case 5: box = AxisAlignedBB.getBoundingBox(0.0D, -gearSize, -gearSize, 1.0D, gearSize + 1, gearSize + 1); break;
		}
		if (box == null) return null; 
		
		//boxes.add(AxisAlignedBB.getBoundingBox(0.0D, 0.375D, 0.375D, 2.0D, 0.625D, 0.625D));
		//boxes.add(AxisAlignedBB.getBoundingBox(0.0D, -1.0D, -1.0D, 0.5D, 2.0D, 2.0D));
		boxes.add(box);
		return boxes;
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return true;
	}
	
	public static void createGearBlock(World world, int x, int y, int z, int facing, int size, int plankMeta, boolean crafted) {
		WorldUtil.setBlock(world, x, y, z, BlockData.gear.block, facing, 3);
		
		TileGear tile = (TileGear) WorldUtil.getPTile(world, x, y, z);
		tile.setGear(new Gear(size, plankMeta), crafted ? 1 : 0);
		if (crafted) tile.hasRod = false;
		
		createGearParts(world, x, y, z, facing, size);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int facing = WorldUtil.setBlockFacing(world, x, y, z, player);
		
		TileGear tile = (TileGear) WorldUtil.getPTile(world, x, y, z);
		
		Gear gear = new Gear(stack);
		tile.setGear(gear, 1);
		tile.hasRod = false;
		
		createGearParts(world, x, y, z, facing, gear.size);
	}
	
	public static void createGearParts(World world, int x, int y, int z, int facing, int size) {
	
		Vector3 pos = new Vector3(x, y, z);
		Vector3 offsetA = null, offsetB = null;
		
		if (facing == 0 || facing == 1) {
			pos.x -= (size / 2);
			pos.z -= (size / 2);
			offsetA = Vector3.X_AXIS;
			offsetB = Vector3.Z_AXIS;
		}
		else if (facing == 2 || facing == 3) {
			pos.x -= (size / 2);
			pos.y -= (size / 2);
			offsetA = Vector3.X_AXIS;
			offsetB = Vector3.Y_AXIS;
		}
		else if (facing == 4 || facing == 5) {
			pos.y -= (size / 2);
			pos.z -= (size / 2);
			offsetA = Vector3.Y_AXIS;
			offsetB = Vector3.Z_AXIS;
		}
		
		for (int a = 0; a < size; a++) {
			for (int b = 0; b < size; b++) {
				int xPos = pos.x + offsetA.x * a + offsetB.x * b;
				int yPos = pos.y + offsetA.y * a + offsetB.y * b;
				int zPos = pos.z + offsetA.z * a + offsetB.z * b;
				if (xPos != x || yPos != y || zPos != z) {
					int meta = 0;
					if (b < (size / 2)) {
						meta = FacingUtil.getSideForOffset(offsetB);
					}
					else if (b == (size / 2)) {
						meta = FacingUtil.getSideForOffset(offsetA);
						if (a > (size / 2)) meta = meta ^ 1;
					}
					else {
						meta = FacingUtil.getSideForOffset(offsetB) ^ 1;
					}
					
					WorldUtil.setBlock(world, xPos, yPos, zPos, BlockData.gearPart.block, meta, 3);
				}
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		
		if (tile != null && tile instanceof TileGear) {
			
			TileGear gearTile = (TileGear) tile;
			ItemStack stack = player.getCurrentEquippedItem();
			int facing = WorldUtil.getBlockFacing(world, x, y, z);
			if (player.isSneaking()) side = side ^ 1;
			
			if (stack == null && gearTile.hasRod) {
				if (facing == side) {					
					if (!world.isRemote) {
						gearTile.shiftForward();
						PacketHandler.sendToAllInDimension(new GearDataPacket(gearTile, player.dimension), player.dimension);
					}
					return true;
				}
				else if (facing == (side ^ 1)) {
					if (!world.isRemote) {
						gearTile.shiftBack();
						PacketHandler.sendToAllInDimension(new GearDataPacket(gearTile, player.dimension), player.dimension);
					}
					return true;
				}
			}
			if (stack != null && CompareUtil.compareIDs(stack, BlockData.rod.block)) {	
				if (!gearTile.hasRod) {
					if (!player.capabilities.isCreativeMode) stack.stackSize--;
					if (!world.isRemote) {
						gearTile.hasRod = true;
						PacketHandler.sendToAllInDimension(new GearDataPacket(gearTile, player.dimension), player.dimension);
					}
					return true;
				}
			}
			if (stack != null && gearTile.hasRod && CompareUtil.compareIDs(stack, BlockData.gear.block)) {
				if (gearTile.getGear(1) == null) {

					Gear gear = new Gear(stack); 
					int size = gearTile.getSize();
					
					if (side == (facing ^ 1) && gearTile.getGear(0) == null) {
												
						if (gear.size < size || checkGearRingSpace(world, x, y, z, facing, gear.size)) {
							if (!player.capabilities.isCreativeMode) stack.stackSize--;
							if (!world.isRemote) {
								gearTile.setGear(gear, 0);
								if (gear.size > size) createGearParts(world, x, y, z, facing, gear.size);
								PacketHandler.sendToAllInDimension(new GearDataPacket(gearTile, player.dimension), player.dimension);
							}
							return true;							
						}
					}
					else if (side == facing && gearTile.getGear(2) == null) {
						
						if (gear.size < size || checkGearRingSpace(world, x, y, z, facing, gear.size)) {
							if (!player.capabilities.isCreativeMode) stack.stackSize--;
							if (!world.isRemote) {
								gearTile.setGear(gear, 2);
								if (gear.size > size) createGearParts(world, x, y, z, facing, gear.size);
								PacketHandler.sendToAllInDimension(new GearDataPacket(gearTile, player.dimension), player.dimension);
							}
							return true;							
						}
					}
				}
			}
		}
		
		return false;
	}
	
	public static boolean checkGearRingSpace(World world, int x, int y, int z, int facing, int size) {
		size = size / 2;
		switch (facing) {
		case 1: case 0: return MiscUtil.checkSurroundings(world, new Vector3(x, y, z), new Vector3(x - size, y, z - size), new Vector3(x + size, y, z + size));
		case 2: case 3: return MiscUtil.checkSurroundings(world, new Vector3(x, y, z), new Vector3(x - size, y - size, z), new Vector3(x + size, y + size, z));
		case 4: case 5: return MiscUtil.checkSurroundings(world, new Vector3(x, y, z), new Vector3(x, y - size, z - size), new Vector3(x, y + size, z + size));
		}
	
		return false;
	}
	
	@Override
	public void onBlockBreak(World world, int x, int y, int z, Block block2, int facing) {
		
		
		PTile tile = WorldUtil.getPTile(world, x, y, z);
		
		if (tile == null || !(tile instanceof TileGear)) {
			return;
		}
		
		TileGear tileGear = (TileGear) tile;
		
		int size = tileGear.getSize();
		
		Vector3 pos = new Vector3(x, y, z);
		Vector3 offsetA = null, offsetB = null;
		
		if (facing == 0 || facing == 1) {
			pos.x -= (size / 2);
			pos.z -= (size / 2);
			offsetA = Vector3.X_AXIS;
			offsetB = Vector3.Z_AXIS;
		}
		else if (facing == 2 || facing == 3) {
			pos.x -= (size / 2);
			pos.y -= (size / 2);
			offsetA = Vector3.X_AXIS;
			offsetB = Vector3.Y_AXIS;
		}
		else if (facing == 4 || facing == 5) {
			pos.y -= (size / 2);
			pos.z -= (size / 2);
			offsetA = Vector3.Y_AXIS;
			offsetB = Vector3.Z_AXIS;
		}
		
		for (int a = 0; a < size; a++) {
			for (int b = 0; b < size; b++) {
				int xPos = pos.x + offsetA.x * a + offsetB.x * b;
				int yPos = pos.y + offsetA.y * a + offsetB.y * b;
				int zPos = pos.z + offsetA.z * a + offsetB.z * b;

				WorldUtil.cleanBlock(world, xPos, yPos, zPos);
			}
		}
		
		if (!world.isRemote) {
			if (tileGear.hasRod) WorldUtil.spawnItemStack(world, x, y, z, BlockItemUtil.getStack(BlockData.rod)); 
			for (int i = 0; i < 3; i++) {
				Gear gear = tileGear.getGear(i);
				if (gear != null) {
					WorldUtil.spawnItemStack(world, x, y, z, gear.getStack());
				}
			}
		}
		
		WorldUtil.removeTile(world, x, y, z);
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
	public void getConnectedForSystem(PistonSystem system, BlockProxy proxy, boolean strongConnection) {
		
		if (system.getSystemType() == SystemType.REDSTONE) {
			return;
		}
		
		PTile tile = proxy.getPTile();
		if (tile != null && tile instanceof TileGear) {
			TileGear gearTile = (TileGear) tile;
			if (gearTile.hasRod || strongConnection) {

				int facing = proxy.getFacing();
				
				if (gearTile.hasRod) {
					
					BlockProxy neighbor = proxy.getNeighbor(facing);
					if (neighbor.connectsToSide(facing ^ 1, system.getSystemType())) {
						neighbor.getConnectedForSystem(system, false);
					}
					
					neighbor = proxy.getNeighbor(facing ^ 1);
					if (neighbor.connectsToSide(facing, system.getSystemType())) {
						neighbor.getConnectedForSystem(system, false);
					}
				}
				
				int size = gearTile.getSize();
				
				Vector3 pos = proxy.getCoords().copy();
				Vector3 offsetA = null, offsetB = null;
				
				if (facing == 0 || facing == 1) {
					pos.x -= (size / 2);
					pos.z -= (size / 2);
					offsetA = Vector3.X_AXIS;
					offsetB = Vector3.Z_AXIS;
				}
				else if (facing == 2 || facing == 3) {
					pos.x -= (size / 2);
					pos.y -= (size / 2);
					offsetA = Vector3.X_AXIS;
					offsetB = Vector3.Y_AXIS;
				}
				else if (facing == 4 || facing == 5) {
					pos.y -= (size / 2);
					pos.z -= (size / 2);
					offsetA = Vector3.Y_AXIS;
					offsetB = Vector3.Z_AXIS;
				}
				
				for (int a = 0; a < size; a++) {
					for (int b = 0; b < size; b++) {
						int xPos = pos.x + offsetA.x * a + offsetB.x * b;
						int yPos = pos.y + offsetA.y * a + offsetB.y * b;
						int zPos = pos.z + offsetA.z * a + offsetB.z * b;
						
						system.addElement(new BlockProxy(proxy.getWorld(), xPos, yPos, zPos), strongConnection);
					}
				}
				
			}
		}
	}

	@Override
	public boolean connectsToSide(BlockProxy proxy, int side, SystemType type) {
		int facing = proxy.getFacing();
		return (side == facing || side == (facing ^ 1));
	}

	@Override
	public boolean isTransmitter(BlockProxy proxy) {
		return false;
	}
}
